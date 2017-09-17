package com.github.frajimiba.commonstruct.jee5.security.data;

import java.io.Serializable;

import com.github.frajimiba.commonstruct.jee5.data.domain.Page;
import com.github.frajimiba.commonstruct.jee5.data.domain.Pageable;
import com.github.frajimiba.commonstruct.jee5.data.repository.JeeAuditableRepository;
import com.github.frajimiba.commonstruct.security.auth.BaseUser;
import com.github.frajimiba.commonstruct.security.data.UserRepository;

public interface JeeUserRepository<E extends BaseUser<PK>, PK extends Serializable>
		extends JeeAuditableRepository<E, PK>, UserRepository<E, PK> {

	Page<E> findBySystem(boolean system, Pageable pageable);

}