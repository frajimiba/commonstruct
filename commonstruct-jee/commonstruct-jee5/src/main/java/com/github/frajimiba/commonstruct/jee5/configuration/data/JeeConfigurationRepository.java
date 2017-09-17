package com.github.frajimiba.commonstruct.jee5.configuration.data;

import java.io.Serializable;

import com.github.frajimiba.commonstruct.configuration.BaseConfiguration;
import com.github.frajimiba.commonstruct.configuration.data.ConfigurationRepository;
import com.github.frajimiba.commonstruct.jee5.data.repository.JeeRepository;


public interface JeeConfigurationRepository<E extends BaseConfiguration<PK>, PK extends Serializable>
	extends JeeRepository<E, PK>, ConfigurationRepository<E, PK> {
}
