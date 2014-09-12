package com.jqy.servlet;

import com.jqy.http.HttpRequest;
import com.jqy.http.HttpResponse;

//servlet的接口，提供了标准生命周期的几个函数
public interface Servlet
{
	//当servlet生成时被调用，注意每个servlet都是单例的，只有第一次访问的时候才会生成
	public void init();
	//主业务逻辑函数
	public void doService(HttpRequest request,HttpResponse response);
	//当servlet被销毁时调用，通常是服务器停止服务
	public void destory();
	
}
