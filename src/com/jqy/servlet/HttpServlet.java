package com.jqy.servlet;

import com.jqy.http.HttpRequest;
import com.jqy.http.HttpResponse;

//HttpServlet�ı�׼ģ�壬�û�ֻ��Ҫ�̳иó����࣬������http���������ʵ�ֶ�Ӧ�ķ�������
//Ŀǰ�汾����֧��get�Լ��򵥵�post
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
