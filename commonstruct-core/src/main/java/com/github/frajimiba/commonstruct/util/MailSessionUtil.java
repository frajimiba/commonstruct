package com.github.frajimiba.commonstruct.util;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

/**
 * Utils for mail session.
 *
 * @author Francisco José Jiménez
 *
 */
public final class MailSessionUtil {

  
  /**
   * The auth property key.
   */
  private static final String DEFAULT_AUTH_PROPERTY = "mail.smtp.auth";
  /**
   * The user name property key.
   */
  private static final String DEFAULT_USER_PROPERTY = "mail.smtp.user";
  /**
   * The password property key.
   */
  private static final String DEFAULT_PASSWORD_PROPERTY = "mail.smtp.password";

  private MailSessionUtil() {
  }
  
  /**
   * Checks if the session is authenticated.
   * 
   * If the authPropertyName is null the authPropertyName is 'mail.smtp.auth'.
   * 
   * @param mailSession
   *        The original mail session.
   * @param authPropertyName
   *        the auth property name.
   * @return true if the session is authenticated.
   */
  public static boolean isAuthenticateSession(final Session mailSession,  final String authPropertyName){
    String auth = mailSession.getProperty(DEFAULT_AUTH_PROPERTY);
    if (authPropertyName!=null){
      auth = mailSession.getProperty(DEFAULT_AUTH_PROPERTY);
    }
    return Boolean.parseBoolean(auth);
  }
  
  /**
   * Checks if the session is authenticated.
   * 
   * @param mailSession
   *        The original mail session.
   * @return true if the session is authenticated.
   */
  public static boolean isAuthenticateSession(final Session mailSession){
    return isAuthenticateSession(mailSession,null);
  }

  /**
   * Get new mail session to authenticate.
   * 
   * If the userPropertyName is null the userPropertyName value is 'mail.smtp.user'.
   * If the passwordPropertyName is null the passwordPropertyName value is 'mail.smtp.password'.
   *
   * @param mailSession
   *          the original mail session.
   * @param userPropertyName
   *          the user property name.
   * @param passwordPropertyName  
   *          the password property name.      
   * @return the new mail session to authenticate
   */
  public static Session getAuthenticatedMailSession(final Session mailSession, 
        final String userPropertyName, final String passwordPropertyName) {

    String user = mailSession.getProperty(DEFAULT_USER_PROPERTY);
    if (userPropertyName!=null){
      user = mailSession.getProperty(userPropertyName);
    }
    
    String password = mailSession.getProperty(DEFAULT_PASSWORD_PROPERTY);
    if (passwordPropertyName!=null){
      password = mailSession.getProperty(passwordPropertyName);
    }
    
    return Session.getInstance(mailSession.getProperties(), 
        new MailSessionUtil.UserPasswordAuthenticator(user,password));
  }
  
  /**
   * Get new mail session to authenticate.
   * 
   * the 'mail.user' default value is 'mail.smtp.user'.
   * the 'mail.password' default value is 'mail.smtp.password'.
   *
   * @param mailSession
   *          the original mail session.
    * @return the new mail session to authenticate.
   */
  public static Session getAuthenticatedMailSession(final Session mailSession){
    return getAuthenticatedMailSession(mailSession,null,null);
  }
  
  private static class UserPasswordAuthenticator extends Authenticator {
    
    private String user;
    private String password;
    
    public UserPasswordAuthenticator(String user, String password){
      this.user = user;
      this.password = password;
    }
    
    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
      return new PasswordAuthentication(this.user, this.password);
    }
  }
  
}
