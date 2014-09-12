package com.jqy.servlet;

import com.jqy.http.HttpRequest;
import com.jqy.http.HttpResponse;

//HttpServlet的标准模板，用户只需要继承该抽象类，并根据http请求的类型实现对应的方法即可
//目前版本仅仅支持get以及简单的post
public abstract class HttpServlet implements Servlet {

	public void init() {
		// TODO Auto-generated method stub

	}

	public void doService(HttpRequest request, HttpResponse response) {
		// TODO Auto-generated method stub
		if ("GET".equals(request.getMethod())) {
			this.doGet(request, response);
		} else if ("POST".equals(request.getMethod())) {
			this.doPost(request, response);
		}

	}

	public void destory() {
		// TODO Auto-generated method stub

	}

	public abstract void doGet(HttpRequest request, HttpResponse response);

	public abstract void doPost(HttpRequest request, HttpResponse response);

}
