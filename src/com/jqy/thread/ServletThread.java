package com.jqy.thread;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.jqy.http.Controller;
import com.jqy.http.HttpRequest;
import com.jqy.http.HttpResponse;
import com.jqy.http.URLParse;

public class ServletThread extends Thread {
	private Socket socket;

	public ServletThread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		InputStream is;
		OutputStream os;
		try {
			is = socket.getInputStream();
			os = socket.getOutputStream();

			URLParse p = new URLParse();
			HttpRequest request = p.parse(is); // ����http����
			Controller map = new Controller(); // �ж�������Դ�Ǿ�̬��Դ���Ƕ�̬��Դ
			HttpResponse response = new HttpResponse(os);

			do {
				request.setForward(false);
				map.mapping(request, response);
			} while (request.isForward());
			
			is.close();
			os.close();
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
