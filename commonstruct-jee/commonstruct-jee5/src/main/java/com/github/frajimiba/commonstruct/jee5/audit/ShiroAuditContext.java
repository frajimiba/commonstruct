package com.github.frajimiba.commonstruct.jee5.audit;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.InvalidSessionException;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.subject.WebSubject;

import com.github.frajimiba.commonstruct.audit.AuditContext;
import com.github.frajimiba.commonstruct.audit.BaseAuditContext;
import com.github.frajimiba.commonstruct.security.auth.User;
import com.github.frajimiba.commonstruct.security.service.SecurityAttributes;

public class ShiroAuditContext extends BaseAuditContext implements AuditContext {

	@Override
	public String getLastPrincipal() {
		String principal = SecurityAttributes.SYSTEM_USER_PRINCIPAL;
	    if (getHttpSession() != null) {
	      principal = (String) getHttpSession().getAttribute(SecurityAttributes.AUTHENTICATION_LAST_PRINCIPAL);
	    }
	    
	    if (ThreadContext.getSecurityManager()!=null){
			Subject subject = SecurityUtils.getSubject();
			if (subject.getPrincipal()!=null){
				User<?> user = (User<?>) subject.getPrincipal();
				principal = user.getPrincipal();
			}
	    }
	    
		return principal;
	}

	@Override
	public HttpSession getHttpSession() {
		HttpSession session = null;
		if (ThreadContext.getSecurityManager()!=null){
			Subject subject = SecurityUtils.getSubject();
			boolean valid = true;
			try {
				SecurityUtils.getSubject().getSession().touch();
			}catch(InvalidSessionException ex){
				valid = false;
			}
			if (valid){
				ServletRequest request = ((WebSubject)subject).getServletRequest();
				session = ((HttpServletRequest)request).getSession(false);
			}
			
			
		}
		return session;
	}

}
