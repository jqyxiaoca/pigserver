//���������ļ��õ��û�����Ҫ���ʵ����·��
package com.jqy.servlet;

import com.jqy.config.ServletMapping;
import com.jqy.http.HttpRequest;
import com.jqy.http.HttpResponse;
import com.jqy.http.WriteResponse;
import com.jqy.server.MainClass;
import com.jqy.staticfile.ErrorPage;

public class DoDynamic {
	public static void doDynamic(HttpRequest request, HttpResponse response)
			throws Exception {
		String realPath = ServletMapping.p.getProperty(request.getUri());

		if (realPath == null || realPath.trim().equals("")) // �����servlet�����ڣ��������ҳ��
		{
			ErrorPage ep = new ErrorPage();
			ep.ePage(response); // ���404ҳ��
		} else if (realPath.equals("stop")) {
			MainClass.isRun = false;
		} else {
			Servlet servlet = ServletFatory.getServlet(realPath);
			servlet.doService(request, response);
			WriteResponse wr = new WriteResponse();
			wr.returnResponse(response);
		}
	}

	public void setDefaultResponse(HttpResponse response) {
		response.setVersion("HTTP/1.1");
		response.setCode("200");
		response.setDescription("OK");
	}
}
