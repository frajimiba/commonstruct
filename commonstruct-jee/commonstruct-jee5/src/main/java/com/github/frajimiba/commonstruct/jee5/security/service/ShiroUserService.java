package com.github.frajimiba.commonstruct.jee5.security.service;

import java.io.Serializable;

import com.github.frajimiba.commonstruct.jee5.security.data.JeeCredentialsMatcher;
import com.github.frajimiba.commonstruct.security.auth.BaseUser;
import com.github.frajimiba.commonstruct.security.service.UserPasswordService;


public interface ShiroUserService<T extends BaseUser<ID>, ID extends Serializable>
	extends JeeUserService<T, ID>, UserPasswordService<T, ID> {

	JeeCredentialsMatcher getCredentialsMatcher();
	boolean isRepeatedPassword(T user, String password);
	int addLoginAttempt(String username);
	void onLogin(T user);
	@Override
	void encodePassword(T user, String password);
	@Override
	void changePassword(T user, String password);
}