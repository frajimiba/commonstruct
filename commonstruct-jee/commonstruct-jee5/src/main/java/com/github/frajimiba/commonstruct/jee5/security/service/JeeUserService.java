package com.github.frajimiba.commonstruct.jee5.security.service;

import java.io.Serializable;
import java.util.Date;

import com.github.frajimiba.commonstruct.jee5.domain.service.JeeDomainService;
import com.github.frajimiba.commonstruct.jee5.security.data.JeeUserRepository;
import com.github.frajimiba.commonstruct.security.auth.BaseUser;
import com.github.frajimiba.commonstruct.security.service.UserService;

public interface JeeUserService<T extends BaseUser<ID>, ID extends Serializable>
		extends JeeDomainService<T, ID>, UserService<T, ID> {

	@Override
	JeeUserRepository<T, ID> getRepository();
	@Override
	Date getLastAccess(T user);
	@Override
	void disable(T user);
	@Override
	void enable(T user);
	@Override
	void unlock(T user);
	@Override
	void acceptConditions(T user);
	@Override
	boolean isDisabledExpired(T user);
	@Override
	void remove(T user);
}