package com.aws.gaming.emitter;

import java.io.FileInputStream;
import java.util.Properties;


public class EmitterConfiguration {
	
	private Properties configFile;

	private static EmitterConfiguration instance;

	private EmitterConfiguration() {
		configFile = new java.util.Properties();
		try {
			configFile.load(new FileInputStream("./emitter.config"));
		} catch (Exception eta) {
			eta.printStackTrace();
		}
	}

	private String getValue(String key) {
		return configFile.getProperty(key);
	}

	public static String getProperty(String key) {
		if (instance == null) instance = new EmitterConfiguration();
			return instance.getValue(key);
	}

}
