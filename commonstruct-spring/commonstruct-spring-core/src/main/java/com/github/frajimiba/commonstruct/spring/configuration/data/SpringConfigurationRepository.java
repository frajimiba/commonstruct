package com.github.frajimiba.commonstruct.spring.configuration.data;

import java.io.Serializable;

import com.github.frajimiba.commonstruct.configuration.data.ConfigurationRepository;
import com.github.frajimiba.commonstruct.spring.configuration.SpringConfiguration;
import com.github.frajimiba.commonstruct.spring.data.SpringRepository;

public interface SpringConfigurationRepository<E extends SpringConfiguration<PK>, PK extends Serializable>
    extends SpringRepository<E, PK>, ConfigurationRepository<E, PK> {

}
