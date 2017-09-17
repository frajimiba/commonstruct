package com.github.frajimiba.commonstruct.audit.envers;

import org.hibernate.cfg.Configuration;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.envers.configuration.AuditConfiguration;
import org.hibernate.envers.event.EnversListenerDuplicationStrategy;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.metamodel.source.MetadataImplementor;
import org.hibernate.service.spi.SessionFactoryServiceRegistry;

import com.github.frajimiba.commonstruct.audit.AuditContext;
import com.github.frajimiba.commonstruct.audit.envers.ActionEnversPostCollectionRecreateEventListenerImpl;
import com.github.frajimiba.commonstruct.audit.envers.ActionEnversPostDeleteEventListenerImpl;
import com.github.frajimiba.commonstruct.audit.envers.ActionEnversPostInsertEventListenerImpl;
import com.github.frajimiba.commonstruct.audit.envers.ActionEnversPostUpdateEventListenerImpl;
import com.github.frajimiba.commonstruct.audit.envers.ActionEnversPreCollectionRemoveEventListenerImpl;
import com.github.frajimiba.commonstruct.audit.envers.ActionEnversPreCollectionUpdateEventListenerImpl;

public abstract class EnversAuditIntegrator implements Integrator {

	public static final String AUTO_REGISTER = "hibernate.listeners.envers.autoRegister";

	@Override
	public void integrate(Configuration configuration, SessionFactoryImplementor sessionFactory, SessionFactoryServiceRegistry serviceRegistry) {

		EventListenerRegistry listenerRegistry = serviceRegistry.getService(EventListenerRegistry.class);
		listenerRegistry.addDuplicationStrategy(EnversListenerDuplicationStrategy.INSTANCE);

		final AuditConfiguration enversConfiguration = AuditConfiguration.getFor(configuration);

		if (enversConfiguration.getEntCfg().hasAuditedEntities()) {
			listenerRegistry.appendListeners(EventType.POST_DELETE, new ActionEnversPostDeleteEventListenerImpl(enversConfiguration, getAuditContext()));
			listenerRegistry.appendListeners(EventType.POST_INSERT, new ActionEnversPostInsertEventListenerImpl(enversConfiguration,getAuditContext()));
			listenerRegistry.appendListeners(EventType.POST_UPDATE, new ActionEnversPostUpdateEventListenerImpl(enversConfiguration,getAuditContext()));
			listenerRegistry.appendListeners(EventType.POST_COLLECTION_RECREATE, new ActionEnversPostCollectionRecreateEventListenerImpl(enversConfiguration,getAuditContext()));
			listenerRegistry.appendListeners(EventType.PRE_COLLECTION_REMOVE, new ActionEnversPreCollectionRemoveEventListenerImpl(enversConfiguration,getAuditContext()));
			listenerRegistry.appendListeners(EventType.PRE_COLLECTION_UPDATE, new ActionEnversPreCollectionUpdateEventListenerImpl(enversConfiguration,getAuditContext()));
		}
	}

	@Override
	public void disintegrate(SessionFactoryImplementor sessionFactory, SessionFactoryServiceRegistry serviceRegistry) {
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.hibernate.integrator.spi.Integrator#integrate(org.hibernate.metamodel.source.MetadataImplementor,
	 *      org.hibernate.engine.spi.SessionFactoryImplementor,
	 *      org.hibernate.service.spi.SessionFactoryServiceRegistry)
	 */
	@Override
	public void integrate(MetadataImplementor metadata, SessionFactoryImplementor sessionFactory,
			SessionFactoryServiceRegistry serviceRegistry) {
	}
	
	public abstract AuditContext getAuditContext();

}