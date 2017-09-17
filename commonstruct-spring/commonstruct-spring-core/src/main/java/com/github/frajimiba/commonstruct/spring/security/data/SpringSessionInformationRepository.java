package com.github.frajimiba.commonstruct.spring.security.data;

import java.io.Serializable;
import java.util.List;

import com.github.frajimiba.commonstruct.spring.data.SpringRepository;
import com.github.frajimiba.commonstruct.spring.security.auth.SpringSessionInformation;

public interface SpringSessionInformationRepository<E extends SpringSessionInformation, PK extends Serializable>
    extends SpringRepository<E, String> {

  List<E> findByPrincipal(String principal);

  List<E> findByPrincipalAndExpired(String principal, boolean expired);
}
