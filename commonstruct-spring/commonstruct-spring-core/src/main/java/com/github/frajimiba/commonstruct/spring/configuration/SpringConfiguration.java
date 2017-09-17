package com.github.frajimiba.commonstruct.spring.configuration;

import java.io.Serializable;
import javax.persistence.MappedSuperclass;

import org.hibernate.envers.Audited;

import com.github.frajimiba.commonstruct.configuration.Configuration;
import com.github.frajimiba.commonstruct.spring.domain.SpringEntity;

@MappedSuperclass
@Audited
public abstract class SpringConfiguration<PK extends Serializable> extends SpringEntity<PK>
    implements Configuration<PK> {

  private static final long serialVersionUID = 3865509692589377128L;

  private String key;
  private String value;
  private String type;

  public SpringConfiguration() {
  }

  public SpringConfiguration(String key, String value, String type) {
    this.key = key;
    this.value = value;
    this.type = type;
  }

  @Override
  public String getKey() {
    return this.key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  @Override
  public String getValue() {
    return this.value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  @Override
  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

}