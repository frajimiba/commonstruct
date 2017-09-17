package com.github.frajimiba.commonstruct.configuration;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

import com.github.frajimiba.commonstruct.domain.BaseEntity;

@MappedSuperclass
public abstract class BaseConfiguration<PK extends Serializable> extends BaseEntity<PK>
    implements Configuration<PK> {

  private static final long serialVersionUID = 3865509692589377128L;

  private String key;
  private String value;
  private String type;

  public BaseConfiguration() {
  }

  public BaseConfiguration(String key, String value, String type) {
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