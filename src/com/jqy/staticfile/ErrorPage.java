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
		wr.returnResponse(response);	  //向浏览器写响应，包括请求行、空行、请求头
		response.getOs().write("对不起，出错啦！".getBytes());
	}
}
