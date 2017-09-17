package com.github.frajimiba.commonstruct.healthcheck;

import com.github.frajimiba.commonstruct.ws.Service;

public interface ServiceHealthCheck extends HealthCheck {
	Service getService();
}