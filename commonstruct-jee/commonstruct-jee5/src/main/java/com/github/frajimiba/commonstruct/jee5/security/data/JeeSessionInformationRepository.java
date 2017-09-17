package com.github.frajimiba.commonstruct.jee5.security.data;

import java.io.Serializable;
import java.util.List;

import com.github.frajimiba.commonstruct.jee5.data.repository.JeeRepository;
import com.github.frajimiba.commonstruct.security.auth.BaseSessionInformation;

public interface JeeSessionInformationRepository<E extends BaseSessionInformation, PK extends Serializable>
		extends JeeRepository<E, String> {

	List<E> findByPrincipal(String principal);

	List<E> findByPrincipalAndExpired(String principal, boolean expired);
}
