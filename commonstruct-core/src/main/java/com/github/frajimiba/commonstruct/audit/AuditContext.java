package com.github.frajimiba.commonstruct.audit;

import javax.servlet.http.HttpSession;

import com.github.frajimiba.commonstruct.action.Action;

public interface AuditContext {
	Action getLastAction();
	String getLastPrincipal();
	void removeLastAction();
	HttpSession getHttpSession();
}