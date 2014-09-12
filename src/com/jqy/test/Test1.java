//≤‚ ‘¿‡
package com.jqy.test;

import java.io.IOException;

import com.jqy.http.HttpRequest;
import com.jqy.http.HttpResponse;
import com.jqy.servlet.HttpServlet;
import com.jqy.servlet.Servlet;

public class Test1 extends HttpServlet
{
	public void doGet(HttpRequest request, HttpResponse response) 
	{
		
		try
		{
			//response.getOs().write("hello".getBytes());
			response.sendRedirect(response, "http://www.baidu.com/index.html");
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void doPost(HttpRequest request, HttpResponse response)
	{
		doGet(request,response);
	}
}
