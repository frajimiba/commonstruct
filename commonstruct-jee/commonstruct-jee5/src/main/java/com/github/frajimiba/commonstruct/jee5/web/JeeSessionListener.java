package com.github.frajimiba.commonstruct.jee5.web;

import javax.ejb.EJB;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.github.frajimiba.commonstruct.jee5.security.service.JeeSessionInformationService;
import com.github.frajimiba.commonstruct.security.auth.BaseSessionInformation;

public class JeeSessionListener implements HttpSessionListener {

	@EJB
	private JeeSessionInformationService<? extends BaseSessionInformation, ?> sessionService;
		
	public void sessionCreated(HttpSessionEvent sessionEvent) {}

	public void sessionDestroyed(HttpSessionEvent sessionEvent) {
		HttpSession session = sessionEvent.getSession();
		sessionService.removeSessionInformation(session.getId());
	}

}