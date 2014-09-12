package com.jqy.staticfile;

import java.io.IOException;

import com.jqy.http.HttpResponse;
import com.jqy.http.WriteResponse;

public class SuccessPage
{
	public void sPage(HttpResponse response) throws Exception
	{
		WriteResponse wr = new WriteResponse();
		response.setVersion("HTTP/1.1");
		response.setCode("200");
		response.setDescription("OK");
		wr.returnResponse(response);
	}
}
