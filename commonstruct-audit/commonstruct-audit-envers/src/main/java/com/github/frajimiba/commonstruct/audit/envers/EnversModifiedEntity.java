package com.github.frajimiba.commonstruct.audit.envers;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

import com.github.frajimiba.commonstruct.audit.ModifiedEntity;

@MappedSuperclass
public abstract class EnversModifiedEntity<PK extends Serializable> extends ModifiedEntity<PK> {

	private static final long serialVersionUID = -5668358103279534119L;

	public EnversModifiedEntity(){}
	
	public EnversModifiedEntity(String entityClassName, Serializable entityId) {
		super(entityClassName, entityId);
	}
	
	@Override
	public abstract EnversRevisionEntity<?> getRevision();

}