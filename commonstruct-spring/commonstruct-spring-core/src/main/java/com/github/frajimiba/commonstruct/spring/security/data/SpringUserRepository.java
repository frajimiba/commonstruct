package com.github.frajimiba.commonstruct.spring.security.data;

import java.io.Serializable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.github.frajimiba.commonstruct.security.data.UserRepository;
import com.github.frajimiba.commonstruct.spring.audit.data.SpringAuditableRepository;
import com.github.frajimiba.commonstruct.spring.security.auth.SpringUser;

public interface SpringUserRepository<E extends SpringUser<PK>, PK extends Serializable>
    extends SpringAuditableRepository<E, PK>, UserRepository<E, PK> {

  Page<E> findBySystem(boolean system, Pageable pageable);

}