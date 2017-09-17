package com.github.frajimiba.commonstruct.jee5.audit;

import com.github.frajimiba.commonstruct.audit.envers.EnversAuditIntegrator;

public class ShiroAuditIntegrator extends EnversAuditIntegrator {

	private ShiroAuditContext context;
	
	@Override
	public ShiroAuditContext getAuditContext() {
		if (this.context==null){
			this.context = new ShiroAuditContext();
		}
		return this.context;
	}
	
	
	
}