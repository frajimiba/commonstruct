package com.github.frajimiba.commonstruct.spring.security.service;

import java.io.Serializable;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.github.frajimiba.commonstruct.security.service.UserService;
import com.github.frajimiba.commonstruct.spring.domain.service.SpringDomainService;
import com.github.frajimiba.commonstruct.spring.security.auth.SpringUser;
import com.github.frajimiba.commonstruct.spring.security.data.SpringUserRepository;

public interface SpringUserService<T extends SpringUser<ID>, ID extends Serializable>
    extends SpringDomainService<T, ID>, UserService<T, ID>, UserDetailsService {

  @Override
  SpringUserRepository<T, ID> getRepository();

  @Override
  SpringUserDetail loadUserByUsername(String principal) throws UsernameNotFoundException;

}
