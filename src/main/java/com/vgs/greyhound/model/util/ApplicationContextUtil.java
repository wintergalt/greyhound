package com.vgs.greyhound.model.util;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.vgs.greyhound.model.facade.GreyhoundFacade;

public class ApplicationContextUtil {

	private static ConfigurableApplicationContext context;

	static {
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
		context.registerShutdownHook();
	}

	public static GreyhoundFacade getGreyhoundFacade() {
		return (GreyhoundFacade) context.getBean("greyhoundFacade");
	}

	public static void closeApplicationContext() {
		context.close();
	}

}
