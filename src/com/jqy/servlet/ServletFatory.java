package com.jqy.servlet;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

//����ÿ��servlet���ǵ�����
public class ServletFatory {
	// ����concurrentHashMap�����Ӷ��̼߳�����Ч��
	static ConcurrentHashMap<String, Servlet> servletPool = new ConcurrentHashMap<String, Servlet>();

	public static Servlet getServlet(String uri) throws InstantiationException,
			Exception {
		Servlet servlet = servletPool.get(uri);
		if (servlet == null) {
			Class classname = Class.forName(uri);
			servlet = (Servlet) classname.newInstance();
			servlet.init();
			servletPool.put(uri, servlet);
		}
		return servlet;
	}

	public static void stop() {
		Collection<Servlet> ss = servletPool.values();
		for (Servlet s : ss) {
			s.destory();
		}
	}
}
