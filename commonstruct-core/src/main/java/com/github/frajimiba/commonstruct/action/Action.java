package com.github.frajimiba.commonstruct.action;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The Interface Action.
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Action {

  /**
   * Name.
   *
   * @return the string
   */
  String name();

  /**
   * Auditable.
   *
   * @return true, if successful
   */
  boolean auditable() default false;

}
