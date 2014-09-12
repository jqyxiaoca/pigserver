package com.jqy.staticfile;

import java.io.File;
import java.io.FileInputStream;

import com.jqy.config.NormalConfigFactory;
import com.jqy.http.HttpRequest;
import com.jqy.http.HttpResponse;

public class DoStatic
{
	public static void doStatic(HttpRequest request,HttpResponse response) throws Exception
	{
		String defaultPath = NormalConfigFactory.getConfig("static.file.rootPath");	//静态文件的默认路径
		File file = new File(defaultPath + request.getUri());
		
		if(file.exists())	//如果文件存在
		{
			
			SuccessPage sp = new SuccessPage();
			sp.sPage(response);
			
			FileInputStream fis = new FileInputStream(file);
			int size = (int)file.length();
			byte[] buffer = new byte[size];
			int count = 0;
			while(-1 != (count = fis.read(buffer, 0, buffer.length)))
			{
				response.getOs().write(buffer, 0, count);	//向浏览器写响应正文
			}
			response.getOs().flush();
		}
		else	//如果文件不存在
		{
			ErrorPage ep = new ErrorPage();
			ep.ePage(response);
		}
	}
}
