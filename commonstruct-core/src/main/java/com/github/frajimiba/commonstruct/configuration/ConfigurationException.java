package com.github.frajimiba.commonstruct.configuration;

/**
 * A Configuration exception
 * 
 * @author Francisco José Jiménez
 * 
 */
public class ConfigurationException extends RuntimeException {

  private static final long serialVersionUID = -3734340143000041890L;

  /**
   * Default constructor
   */
  public ConfigurationException() {
    super();
  }

  /**
   * Message constructor.
   * @param s The exception message.
   */
  public ConfigurationException(String s) {
    super(s);
  }
  
  /**
   * Throwable constructor.
   * @param throwable The throwable cause.
   */
  public ConfigurationException(Throwable throwable) {
    super(throwable);
  }
  /**
   * Message and Throwable constructor.
   * @param s The exception message.
   * @param throwable The throwable cause.
   */
  public ConfigurationException(String s, Throwable throwable) {
    super(s, throwable);
  }
}