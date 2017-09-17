package com.github.frajimiba.commonstruct.spring.action;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;

import com.github.frajimiba.commonstruct.action.Action;
import com.github.frajimiba.commonstruct.action.ActionAttributes;

@Aspect
public class SpringActionInterceptor {

  @Inject
  private HttpSession session;

  @After("execution(@com.github.frajimiba.commonstruct.action.Action * *(..)) && @annotation(actionAnnotation)")
  public void action(Action actionAnnotation) {
    session.setAttribute(ActionAttributes.ACTION_ATTRIBUTE, actionAnnotation);
  }

}