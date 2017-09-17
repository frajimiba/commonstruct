package com.github.frajimiba.commonstruct.spring.security.auth;

import javax.persistence.MappedSuperclass;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.github.frajimiba.commonstruct.security.auth.UserProfile;

/**
 * UserProfile for JPA entity.
 *
 * @author Francisco José Jiménez
 * @param <PK>
 *          the generic type
 */
@MappedSuperclass
public abstract class SpringUserProfile implements UserProfile {

  /**
   * Serial Version UID.
   */
  private static final long serialVersionUID = -3084967160214827451L;

  /** The locale. */
  private String locale;

  /** The theme. */
  private String theme;

  /**
   * Sets the theme.
   *
   * @param theme
   *          the new theme
   */
  public void setTheme(String theme) {
    this.theme = theme;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getLocale() {
    return locale;
  }

  /**
   * Sets the locale.
   *
   * @param locale
   *          the new locale
   */
  public void setLocale(String locale) {
    this.locale = locale;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getTheme() {
    return theme;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE, false, false);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object obj) {
    boolean result = false;
    if (obj instanceof SpringUserProfile) {
      if (this == obj) {
        result = true;
      } else {
        SpringUserProfile that = (SpringUserProfile) obj;
        EqualsBuilder eqBuilder = new EqualsBuilder();
        eqBuilder.append(this.locale, that.locale);
        eqBuilder.append(this.theme, that.theme);
        result = eqBuilder.isEquals();
      }
    }
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    HashCodeBuilder hcBuilder = new HashCodeBuilder();
    hcBuilder.append(this.locale);
    hcBuilder.append(this.theme);
    return hcBuilder.toHashCode();
  }

}
