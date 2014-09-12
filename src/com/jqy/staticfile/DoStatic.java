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
		String defaultPath = NormalConfigFactory.getConfig("static.file.rootPath");	//��̬�ļ���Ĭ��·��
		File file = new File(defaultPath + request.getUri());
		
		if(file.exists())	//����ļ�����
		{
			
			SuccessPage sp = new SuccessPage();
			sp.sPage(response);
			
			FileInputStream fis = new FileInputStream(file);
			int size = (int)file.length();
			byte[] buffer = new byte[size];
			int count = 0;
			while(-1 != (count = fis.read(buffer, 0, buffer.length)))
			{
				response.getOs().write(buffer, 0, count);	//�������д��Ӧ����
			}
			response.getOs().flush();
		}
		else	//����ļ�������
		{
			ErrorPage ep = new ErrorPage();
			ep.ePage(response);
		}
	}
}
