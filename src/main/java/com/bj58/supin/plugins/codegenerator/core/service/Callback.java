package com.bj58.supin.plugins.codegenerator.core.service;

import com.bj58.supin.plugins.codegenerator.core.ConfigContext;
import org.apache.velocity.VelocityContext;

public interface Callback {
	public void write(ConfigContext configContext,VelocityContext context);
}
