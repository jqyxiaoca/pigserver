package com.jqy.servlet;

import com.jqy.http.HttpRequest;
import com.jqy.http.HttpResponse;

//servlet�Ľӿڣ��ṩ�˱�׼�������ڵļ�������
public interface Servlet
{
	//��servlet����ʱ�����ã�ע��ÿ��servlet���ǵ����ģ�ֻ�е�һ�η��ʵ�ʱ��Ż�����
	public void init();
	//��ҵ���߼�����
	public void doService(HttpRequest request,HttpResponse response);
	//��servlet������ʱ���ã�ͨ���Ƿ�����ֹͣ����
	public void destory();
	
}
