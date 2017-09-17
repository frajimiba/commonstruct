package com.github.frajimiba.commonstruct.jee5.audit;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import org.apache.shiro.aop.AnnotationHandler;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.authz.aop.AuthenticatedAnnotationHandler;
import org.apache.shiro.authz.aop.AuthorizingAnnotationHandler;
import org.apache.shiro.authz.aop.GuestAnnotationHandler;
import org.apache.shiro.authz.aop.PermissionAnnotationHandler;
import org.apache.shiro.authz.aop.RoleAnnotationHandler;
import org.apache.shiro.authz.aop.UserAnnotationHandler;
import org.apache.shiro.util.ThreadContext;

import com.github.frajimiba.commonstruct.action.Action;

/**
 * The Class ShiroAnnotationInterceptor.
 */
public class ShiroAnnotationInterceptor {

	/** The Constant ANNOTATION_CLASSES. */
	private static final Map<Class<? extends Annotation>, AnnotationHandler> ANNOTATION_CLASSES;
	
	static
	{
		ANNOTATION_CLASSES = new HashMap<Class<? extends Annotation>,AnnotationHandler>();
		ANNOTATION_CLASSES.put(RequiresAuthentication.class, new AuthenticatedAnnotationHandler());
		ANNOTATION_CLASSES.put(RequiresGuest.class, new GuestAnnotationHandler());
		ANNOTATION_CLASSES.put(RequiresPermissions.class,new PermissionAnnotationHandler());
		ANNOTATION_CLASSES.put(RequiresRoles.class,new RoleAnnotationHandler());
		ANNOTATION_CLASSES.put(RequiresUser.class,new UserAnnotationHandler());		
		ANNOTATION_CLASSES.put(Action.class,new ShiroActionAnnotationHandler());
	}
	
	 /**
 	 * Handle.
 	 *
 	 * @param context the context
 	 * @return the object
 	 * @throws Exception the exception
 	 */
 	@AroundInvoke
	 public Object handle(InvocationContext context) throws Exception {

 		if (ThreadContext.getSecurityManager()!=null){
 			Method method = context.getMethod();
 			 for(Annotation annotation : method.getAnnotations()){
 				 AnnotationHandler handler = ANNOTATION_CLASSES.get(annotation.annotationType());
 				 if (handler!=null){
 					 if (handler instanceof AuthorizingAnnotationHandler){
 						 ((AuthorizingAnnotationHandler)handler).assertAuthorized(annotation);
 					 }
 					 else if (handler instanceof ShiroActionAnnotationHandler){
 						 ((ShiroActionAnnotationHandler)handler).handle(annotation);
 					 }
 				 }
 			 }
 		}
 		
 		return context.proceed();
	 }
	
	
}
