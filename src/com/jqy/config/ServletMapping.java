//º”‘ÿ≈‰÷√Œƒº˛
package com.jqy.config;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import com.jqy.log.Log;

public class ServletMapping
{
	public static Properties p = new Properties();
	static 
	{
		File file = new File(NormalConfigFactory.getConfig("servlet.config.path"));
		try
		{
			p.load(new FileInputStream(file));
			Log.printINFOLog("Servlet config load ok!");
		} catch (Exception e)
		{
			Log.printERRORLog("Servlet config load error, the server is stop.");
			e.printStackTrace();
			System.exit(0);
		}
	}
}
