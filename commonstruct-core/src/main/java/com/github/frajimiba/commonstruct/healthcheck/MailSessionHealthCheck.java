package com.github.frajimiba.commonstruct.healthcheck;

import com.github.frajimiba.commonstruct.mail.MailSession;

/**
 * The mailSession HealthCheck
 * 
 * @author Francisco José Jiménez
 *
 */
public interface MailSessionHealthCheck extends HealthCheck {
  /**
   * The MailSession to check.
   * 
   * @return the MailSession
   */
  MailSession getMailSession();
  /**
   * From message to check.
   * 
   * @return the "from" message. 
   */
  String getFrom();
  /**
   * To message to check.
   * 
   * @return the "to" message. 
   */
  String getTo();
  /**
   * The message to check.
   * 
   * @return the message
   */
  String getMessage();
  /**
   * The subject to check
   * 
   * @return the subject
   */
  String getSubject();
}