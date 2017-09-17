package com.github.frajimiba.commonstruct.spring.audit.data;

import java.io.Serializable;

import com.github.frajimiba.commonstruct.domain.Entity;
import com.github.frajimiba.commonstruct.spring.data.SpringRepository;

public interface SpringAuditableRepository<E extends Entity<PK>, PK extends Serializable>
    extends SpringRepository<E, PK>, AuditableRepository<E, PK> {
}