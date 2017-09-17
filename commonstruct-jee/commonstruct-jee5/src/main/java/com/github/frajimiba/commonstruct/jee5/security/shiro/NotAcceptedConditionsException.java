package com.github.frajimiba.commonstruct.jee5.security.shiro;

import org.apache.shiro.authc.AuthenticationException;

public class NotAcceptedConditionsException extends AuthenticationException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1350539046444882908L;

	/**
     * Creates a new CredentialsException.
     */
    public NotAcceptedConditionsException() {
        super();
    }

    /**
     * Constructs a new NotAcceptedConditionsException.
     *
     * @param message the reason for the exception
     */
    public NotAcceptedConditionsException(String message) {
        super(message);
    }

    /**
     * Constructs a new NotAcceptedConditionsException.
     *
     * @param cause the underlying Throwable that caused this exception to be thrown.
     */
    public NotAcceptedConditionsException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new NotAcceptedConditionsException.
     *
     * @param message the reason for the exception
     * @param cause   the underlying Throwable that caused this exception to be thrown.
     */
    public NotAcceptedConditionsException(String message, Throwable cause) {
        super(message, cause);
    }
}
