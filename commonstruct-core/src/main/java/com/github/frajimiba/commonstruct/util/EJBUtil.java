package com.github.frajimiba.commonstruct.util;

import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Utility class for EJBs. There's a {@link #lookup(Class)} method which allows
 * you to lookup the current instance of a given EJB class from the JNDI
 * context. This utility class assumes that EJBs are deployed in the WAR as you
 * would do in Java EE 6 Web Profile. For EARs, you'd need to alter the
 * <code>EJB_CONTEXT</code> to add the EJB module name or to add another
 * lookup() method.
 */
public final class EJBUtil {

  /**
   * Default JNDI EJB context.
   */
  private static final String EJB_CONTEXT;

  static {
    try {
      EJB_CONTEXT = "java:comp/env/ejb/";
      new InitialContext().lookup(EJB_CONTEXT);
    } catch (NamingException e1) {
      throw new ExceptionInInitializerError(e1);
    }
  }

  /**
   * Private constructor prevents construction outside of this class.
   */
  private EJBUtil() {
  }

  /**
   * Lookup the current instance of the given EJB class from the JNDI context.
   * If the given class implements a local or remote interface, you must assign
   * the return type to that interface to prevent ClassCastException.
   * No-interface EJB lookups can just be assigned to own type. E.g.
   * <li><code>IfaceEJB ifaceEJB = EJB.lookup(ConcreteEJB.class);</code>
   * <li><code>NoIfaceEJB noIfaceEJB = EJB.lookup(NoIfaceEJB.class);</code>
   * 
   * @param <T>
   *          The EJB type.
   * @param ejbClass
   *          The EJB class.
   * @return The instance of the given EJB class from the JNDI context.
   * @throws IllegalArgumentException
   *           If the given EJB class cannot be found in the JNDI context.
   */
  @SuppressWarnings("unchecked")
  public static <T> T lookup(Class<T> ejbClass) {
    String jndiName = EJB_CONTEXT + ejbClass.getSimpleName();
    try {
      return (T) new InitialContext().lookup(jndiName);
    } catch (NamingException e) {
      throw new IllegalArgumentException(String.format("Cannot find EJB class %s in JNDI %s", ejbClass, jndiName), e);
    }
  }

}