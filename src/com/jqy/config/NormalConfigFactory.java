package com.jqy.config;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class NormalConfigFactory {

	public static String getConfig(String key) {
		return p.getProperty(key);
	}

	private static Properties p = new Properties();

	static {
		File file = new File(
				"/jqytomcat/src/config");
		try {
			p.load(new FileInputStream(file));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
