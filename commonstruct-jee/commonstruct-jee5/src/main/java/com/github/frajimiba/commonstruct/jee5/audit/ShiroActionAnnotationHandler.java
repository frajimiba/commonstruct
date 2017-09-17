package com.github.frajimiba.commonstruct.jee5.audit;

import java.lang.annotation.Annotation;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.aop.AnnotationHandler;
import org.apache.shiro.session.Session;

import com.github.frajimiba.commonstruct.action.Action;
import com.github.frajimiba.commonstruct.action.ActionAttributes;

/**
 * The Class ShiroActionAnnotationHandler.
 */
public class ShiroActionAnnotationHandler extends AnnotationHandler {

	/**
	 * Instantiates a new shiro action annotation handler.
	 */
	public ShiroActionAnnotationHandler() {
		super(Action.class);
	}

	/**
	 * Handle.
	 *
	 * @param a the a
	 */
	public void handle(Annotation a) {
		if (a instanceof Action){
			Action annotation = (Action) a;
			Session session = SecurityUtils.getSubject().getSession();
			session.setAttribute(ActionAttributes.ACTION_ATTRIBUTE, annotation);
		}
	}

}
