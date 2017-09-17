package com.github.frajimiba.commonstruct.jee5.security.service;

import java.io.Serializable;
import java.util.List;

import com.github.frajimiba.commonstruct.jee5.domain.service.JeeDomainService;
import com.github.frajimiba.commonstruct.jee5.security.data.JeeSessionInformationRepository;
import com.github.frajimiba.commonstruct.security.auth.BaseSessionInformation;

public interface JeeSessionInformationService<E extends BaseSessionInformation, PK extends Serializable> extends JeeDomainService<E, String> {

	@Override
	JeeSessionInformationRepository<E, String> getRepository();
	JeeUserService<?, ?> getUserService();
	
	List<Object> getAllPrincipals();
	List<BaseSessionInformation> getAllSessions(Object principal, boolean includeExpiredSessions);
	E getSessionInformation(String sessionId);
	void refreshLastRequest(String sessionId);
	void registerNewSession(String sessionId, Object principal);
	void removeSessionInformation(String sessionId);
	void removeAllSessions();
	
}
