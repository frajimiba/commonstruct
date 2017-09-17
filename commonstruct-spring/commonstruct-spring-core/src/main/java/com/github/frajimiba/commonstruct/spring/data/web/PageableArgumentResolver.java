package com.github.frajimiba.commonstruct.spring.data.web;

import javax.inject.Inject;

import org.springframework.core.MethodParameter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.context.request.NativeWebRequest;

import com.github.frajimiba.commonstruct.configuration.service.ConfigurationService;
import com.github.frajimiba.commonstruct.data.DataSettings;

public class PageableArgumentResolver extends org.springframework.data.web.PageableArgumentResolver {

  @Inject
  private ConfigurationService<?, ?> configService;

  @Override
  public Object resolveArgument(MethodParameter methodParameter, NativeWebRequest webRequest) {

    if (methodParameter.getParameterType().equals(Pageable.class)) {
      Integer pageSize = (Integer) configService.get(DataSettings.PAGE_SIZE);
      this.setFallbackPageable(new PageRequest(0, pageSize));
      return super.resolveArgument(methodParameter, webRequest);
    }

    return UNRESOLVED;

  }

}