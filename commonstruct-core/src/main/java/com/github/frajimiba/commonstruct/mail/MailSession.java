package com.github.frajimiba.commonstruct.mail;

import javax.mail.Session;

/**
 * Defines a wrapper mail session from javax.mail.session
 * 
 * @author fjimenezi
 *
 */
public interface MailSession {
  /**
   * The standard mail session
   * 
   * @return the standard mailSession
   */
  Session getMailSession();
}