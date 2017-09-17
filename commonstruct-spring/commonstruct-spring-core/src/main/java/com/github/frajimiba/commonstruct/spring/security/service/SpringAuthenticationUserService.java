package com.github.frajimiba.commonstruct.spring.security.service;

import java.io.Serializable;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;

import com.github.frajimiba.commonstruct.spring.security.auth.SpringUser;

public interface SpringAuthenticationUserService<A extends Authentication, T extends SpringUser<ID>, ID extends Serializable>
    extends SpringUserService<T, ID>, AuthenticationUserDetailsService<A> {

}
