package com.jqy.staticfile;

import java.io.IOException;

import com.jqy.http.HttpResponse;
import com.jqy.http.WriteResponse;

public class ErrorPage
{
	public void ePage(HttpResponse response) throws IOException
	{
		WriteResponse wr = new WriteResponse();
		response.setVersion("HTTP/1.1");
		response.setCode("404");
		response.setDescription("error");
		wr.returnResponse(response);	  //�������д��Ӧ�����������С����С�����ͷ
		response.getOs().write("�Բ��𣬳�������".getBytes());
	}
}
