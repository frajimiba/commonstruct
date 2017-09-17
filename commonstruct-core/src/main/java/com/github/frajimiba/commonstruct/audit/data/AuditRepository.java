package com.github.frajimiba.commonstruct.audit.data;

import java.util.List;

import com.github.frajimiba.commonstruct.audit.ModifiedEntity;
import com.github.frajimiba.commonstruct.audit.RevisionEntity;
import com.github.frajimiba.commonstruct.data.Repository;

public interface AuditRepository<E extends RevisionEntity<I>, I extends Number> extends Repository<E, I> {
	List<String> findActions();
	List<String> findPrincipals();
	List<? extends ModifiedEntity<?>> findChanges(E rev);
}
