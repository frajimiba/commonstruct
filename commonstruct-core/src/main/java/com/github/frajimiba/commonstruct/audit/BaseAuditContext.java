package com.github.frajimiba.commonstruct.audit;

import com.github.frajimiba.commonstruct.action.Action;
import com.github.frajimiba.commonstruct.action.ActionAttributes;

public abstract class BaseAuditContext implements AuditContext {

	@Override
	public Action getLastAction() {
		Action action = null;
		if (getHttpSession() != null) {
			action = (Action) getHttpSession().getAttribute(ActionAttributes.ACTION_ATTRIBUTE);
		}
		return action;
	}

	
	@Override
	public void removeLastAction() {
		if (getHttpSession() != null) {
			getHttpSession().removeAttribute(ActionAttributes.ACTION_ATTRIBUTE);
		}
	}


}
