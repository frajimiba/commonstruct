package com.github.frajimiba.commonstruct.jee5.audit.service;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Transient;

import org.hibernate.Session;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.exception.AuditException;
import org.hibernate.internal.util.EntityPrinter;
import org.hibernate.proxy.HibernateProxy;

import com.github.frajimiba.commonstruct.audit.RevisionChange;
import com.github.frajimiba.commonstruct.audit.RevisionDataChange;
import com.github.frajimiba.commonstruct.audit.envers.EnversModifiedEntity;
import com.github.frajimiba.commonstruct.audit.envers.EnversRevisionEntity;
import com.github.frajimiba.commonstruct.domain.BaseEntity;
import com.github.frajimiba.commonstruct.jee5.domain.service.BaseJeeDomainService;


public abstract class BaseJeeAuditService<E extends EnversRevisionEntity<PK>, PK extends Number>
	extends BaseJeeDomainService<E, PK> implements JeeAuditService<E,PK>  {

	@Override
	public List<RevisionChange> getRevisionChanges(E revision) {
		List<RevisionChange> result = new ArrayList<RevisionChange>();
		List<? extends EnversModifiedEntity<?>>  entidades = this.getRepository().findChanges(revision);

		for (EnversModifiedEntity<?> entity : entidades) {
			AuditReader auditReader = this.getRepository().getAuditReader();
			Object entityObject = auditReader.find(getClassEntityType(entity), entity.getEntityId(), revision.getId());
			result.add(getRevisionChange(entity, entityObject));
		}
		return result;
	}
	
	
	
	private RevisionChange getRevisionChange(EnversModifiedEntity<?> entity, Object entityObject) {

		RevisionChange change = new RevisionChange();
		BeanInfo info;
		
		try {
			info = Introspector.getBeanInfo(getClassEntityType(entity));
		} catch (IntrospectionException e) {
			throw new AuditException(e);
		}
		
		PropertyDescriptor[] descriptors = info.getPropertyDescriptors();
		change.setEntityName(entity.getEntityClassName());

		for (PropertyDescriptor descriptor : descriptors) {

			if (descriptor.getReadMethod()!=null){
				if (!descriptor.getReadMethod().getDeclaringClass().equals(BaseEntity.class)
						&& !descriptor.getReadMethod().getDeclaringClass().equals(Object.class)) {
					if (descriptor.getReadMethod().getAnnotation(Transient.class)==null) {
						Object value = getValue(entityObject, descriptor);
						addRevisionDataChange(change, descriptor, value);
					}
				}
			}
		}
		return change;
	}

	private void addRevisionDataChange(RevisionChange change, PropertyDescriptor descriptor, Object value) {
		RevisionDataChange dataChange = new RevisionDataChange();
		dataChange.setPropertyName(descriptor.getName());
		if (value != null) {
			dataChange.setActualValue(getStringValue(descriptor.getPropertyType(),value));
		}
		change.getDataChanges().add(dataChange);
	}
	
	
	private Object getValue(Object bean, PropertyDescriptor propertyDescriptor) {
		Object result = null;
		Method getter = propertyDescriptor.getReadMethod();
		if (getter != null) {
			try {
				result = getter.invoke(bean, (Object[]) null);
			} catch (IllegalArgumentException e) {
				throw new AuditException(e);
			} catch (IllegalAccessException e) {
				throw new AuditException(e);
			} catch (InvocationTargetException e) {
				throw new AuditException(e);
			}
		}
		return result;

	}
	
	private String getStringValue(Class<?> type, Object value) {
		String stringObject = value.toString();
		if (value instanceof BaseEntity) {
			if (value instanceof HibernateProxy) {
				BaseEntity<?> entity = (BaseEntity<?>)value;
				HibernateProxy proxy = (HibernateProxy)value;
				String entityName = proxy.getHibernateLazyInitializer().getEntityName();
				Session session = (Session) this.getRepository().getEntityManager().getDelegate();
				SessionFactoryImplementor sessionFactory = (SessionFactoryImplementor) session.getSessionFactory();
				value = session.get(type, entity.getId());
				EntityPrinter printer = new EntityPrinter(sessionFactory);
				printer.toString(entityName, value);
				stringObject = printer.toString(entityName, value);
			}
		}
		return stringObject;
	}
	
	private Class<?> getClassEntityType(EnversModifiedEntity<?> entity) {
		Class<?> result = null;
		String entityName = entity.getEntityClassName();
		try {
			result = Class.forName(entityName);
		} catch (ClassNotFoundException e) {
			throw new AuditException("The class '" + entityName+ "' not exist!!!!");
		}
		return result;
	}

}