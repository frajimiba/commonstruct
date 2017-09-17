package com.github.frajimiba.commonstruct.jee5.audit;

import com.github.frajimiba.commonstruct.audit.AuditContext;
import com.github.frajimiba.commonstruct.audit.envers.EnversAuditRevisionListener;

public class ShiroRevisionListener extends EnversAuditRevisionListener {
	
	private ShiroAuditContext context;

	@Override
	public AuditContext getAuditContext() {
		if (this.context==null){
			this.context = new ShiroAuditContext();
		}
		return this.context;
	}

 

}
