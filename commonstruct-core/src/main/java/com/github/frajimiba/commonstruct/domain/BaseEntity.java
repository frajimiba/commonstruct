package com.github.frajimiba.commonstruct.domain;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.github.frajimiba.commonstruct.domain.Entity;

@MappedSuperclass
public abstract class BaseEntity<PK extends Serializable> implements Entity<PK> {

  private static final long serialVersionUID = 3133882863063354305L;

  @Version
  private Long version;

  public Long getVersion() {
    return this.version;
  }

  public void setVersion(Long version) {
    this.version = version;
  }

  /**
   * {@inheritDoc}
   */
  @Transient
  public boolean isNew() {
    return null == getId();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    return String.format("%s[id=%d]", getClass().getName(), getId());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object obj) {
    boolean result = false;
    if (obj instanceof BaseEntity) {
      if (this == obj) {
        result = true;
      } else {
    	  BaseEntity<?> that = (BaseEntity<?>) obj;
        result = new EqualsBuilder().append(this.getId(), that.getId()).isEquals();
      }
    }
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(getId()).toHashCode();
  }

}