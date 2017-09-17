package com.github.frajimiba.commonstruct.security.authc.service;

/**
 * The Class AuthenticationServiceException.
 * 
 * @author Francisco José Jiménez
 */
public class AuthenticationServiceException extends RuntimeException {
  /**
   * Serial version.
   */
  private static final long serialVersionUID = -3734340143000041890L;
  /**
   * Default constructor
   */
  public AuthenticationServiceException() {
    super();
  }
  /**
   * Message constructor.
   * @param s The exception message.
   */
  public AuthenticationServiceException(String s) {
    super(s);
  }
  /**
   * Throwable constructor.
   * @param throwable The throwable cause.
   */
  public AuthenticationServiceException(Throwable throwable) {
    super(throwable);
  }
  /**
   * Message and Throwable constructor.
   * @param s The exception message.
   * @param throwable The throwable cause.
   */
  public AuthenticationServiceException(String s, Throwable throwable) {
    super(s, throwable);
  }
}
