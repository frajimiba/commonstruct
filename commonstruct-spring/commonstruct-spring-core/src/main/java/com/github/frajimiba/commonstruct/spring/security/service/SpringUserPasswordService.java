package com.github.frajimiba.commonstruct.spring.security.service;

import java.io.Serializable;

import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.Authentication;

import com.github.frajimiba.commonstruct.security.service.UserPasswordService;
import com.github.frajimiba.commonstruct.spring.security.auth.SpringUser;

public interface SpringUserPasswordService<T extends SpringUser<ID>, ID extends Serializable>
    extends SpringUserService<T, ID>, UserPasswordService<T, ID> {

  ShaPasswordEncoder getPasswordEncoder();

  boolean isRepeatedPassword(T user, String password);

  Authentication refreshAuthenticated(Authentication auth, T user);

}