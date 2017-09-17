package com.github.frajimiba.commonstruct.jee5.security.data;

import org.apache.shiro.authc.credential.CredentialsMatcher;

public interface JeeCredentialsMatcher extends CredentialsMatcher {
	CredentialsHashConfig getCredentialsHashConfig();
}