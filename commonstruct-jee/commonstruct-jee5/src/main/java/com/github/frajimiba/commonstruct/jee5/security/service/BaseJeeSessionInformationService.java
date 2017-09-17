package com.github.frajimiba.commonstruct.jee5.security.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.github.frajimiba.commonstruct.jee5.domain.service.BaseJeeDomainService;
import com.github.frajimiba.commonstruct.security.auth.BaseSessionInformation;
import com.github.frajimiba.commonstruct.security.auth.BaseUser;

public abstract class BaseJeeSessionInformationService<E extends BaseSessionInformation, PK extends Serializable>
	extends BaseJeeDomainService<E, String> implements JeeSessionInformationService<E,String>{

	  public List<Object> getAllPrincipals() {
	    List<Object> allPrincipals = new ArrayList<Object>();
	    List<E> list = (List<E>) getRepository().findAll();
	    for (E sessionInformation : list) {
	      BaseUser<?> detail = this.getUserService().findByPrincipal(sessionInformation.getPrincipal());
	      if (detail != null) {
	        allPrincipals.add(detail);
	      }

	    }
	    return allPrincipals;
	  }

	  public List<BaseSessionInformation> getAllSessions(Object principal, boolean includeExpiredSessions) {
	    List<BaseSessionInformation> result = new ArrayList<BaseSessionInformation>();
	    if (includeExpiredSessions) {
	      List<E> list = (List<E>) getRepository().findByPrincipal(principal.toString());
	      if (list != null) {
	        result.addAll(list);
	      }
	    } else {
	      List<E> list = (List<E>) getRepository().findByPrincipalAndExpired(principal.toString(),false);
	      if (list != null) {
	        result.addAll(list);
	      }
	    }
	    return result;
	  }

	  public E getSessionInformation(String sessionId) {
	    return getRepository().findOne(sessionId);
	  }

	  public void refreshLastRequest(String sessionId) {
	    E sessionInformation = getSessionInformation(sessionId);
	    if (sessionInformation != null) {
	      sessionInformation.refreshLastRequest();
	      this.getRepository().save(sessionInformation);
	    }
	  }

	  @Override
	  public void registerNewSession(String sessionId,Object principal) {
	    E sessionInformation = this.getTypeInstance();
	    sessionInformation.setId(sessionId);
	    if (principal!=null){
			  sessionInformation.setPrincipal(principal.toString());
		  }
	    sessionInformation.setLastRequest(new Date());
	    this.getRepository().save(sessionInformation);
	  }

	  public void removeSessionInformation(String sessionId) {
	    E sessionInformation = getSessionInformation(sessionId);
	    if (sessionInformation != null) {
	      this.getRepository().delete(sessionInformation);
	    }
	  }
	  
	  @Override
	  public void removeAllSessions(){
		  this.getRepository().deleteAll();
	  }
	  
}