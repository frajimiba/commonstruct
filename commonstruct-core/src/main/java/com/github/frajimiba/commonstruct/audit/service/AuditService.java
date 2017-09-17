package com.github.frajimiba.commonstruct.audit.service;

import java.util.List;

import com.github.frajimiba.commonstruct.audit.RevisionChange;
import com.github.frajimiba.commonstruct.audit.RevisionEntity;
import com.github.frajimiba.commonstruct.domain.service.DomainService;


public interface AuditService<E extends RevisionEntity<I>, I extends Number> extends DomainService<E,I> {
	List<String> findActions();
	List<String> findPrincipals();
	List<RevisionChange> getRevisionChanges(E revision);
}