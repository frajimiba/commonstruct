package com.github.frajimiba.commonstruct.jee5.audit.data;

import java.util.List;

import com.github.frajimiba.commonstruct.audit.data.AuditRepository;
import com.github.frajimiba.commonstruct.audit.envers.EnversModifiedEntity;
import com.github.frajimiba.commonstruct.audit.envers.EnversRevisionEntity;
import com.github.frajimiba.commonstruct.jee5.data.repository.JeeAuditableRepository;

public interface JeeAuditRepository<E extends EnversRevisionEntity<PK>, PK extends Number>
	extends JeeAuditableRepository<E, PK>, AuditRepository<E,PK> {
	
	@Override
	List<? extends EnversModifiedEntity<?>> findChanges(E rev);
	
}
