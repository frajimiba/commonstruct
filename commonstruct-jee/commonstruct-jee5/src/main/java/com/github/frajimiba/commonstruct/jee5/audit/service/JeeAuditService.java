package com.github.frajimiba.commonstruct.jee5.audit.service;

import com.github.frajimiba.commonstruct.audit.envers.EnversRevisionEntity;
import com.github.frajimiba.commonstruct.audit.service.AuditService;
import com.github.frajimiba.commonstruct.jee5.audit.data.JeeAuditRepository;
import com.github.frajimiba.commonstruct.jee5.domain.service.JeeDomainService;


public interface JeeAuditService<E extends EnversRevisionEntity<PK>, PK extends Number> 
	extends JeeDomainService<E,PK>, AuditService<E,PK> {

	@Override
	JeeAuditRepository<E, PK> getRepository();

}