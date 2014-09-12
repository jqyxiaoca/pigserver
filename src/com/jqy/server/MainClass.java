package com.jqy.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.jqy.config.NormalConfigFactory;
import com.jqy.log.Log;
import com.jqy.servlet.ServletFatory;
import com.jqy.thread.ServletThread;

public class MainClass {
	// 用来执行http请求的线程池
	static Executor threadPool = null;

	public static boolean isRun = true;

	public static void main(String[] args) {
		int maxConnect = Integer.parseInt(NormalConfigFactory
				.getConfig("server.max_connect"));
		threadPool = Executors.newFixedThreadPool(maxConnect);
		Log.printINFOLog("The server is start.");
		Log.printDEBUGLog("The max connect is " + maxConnect);
		ServerSocket ss;
		try {
			int port = Integer.parseInt(NormalConfigFactory.getConfig("port"));
			Log.printINFOLog("The server bind the port is " + port);
			ss = new ServerSocket(port);
			while (isRun) {
				Socket socket = ss.accept();
				Log.printINFOLog("A client is connected , address is "
						+ socket.getRemoteSocketAddress());
				threadPool.execute(new ServletThread(socket));
			}
			stop();
		} catch (Exception e) {
			Log.printERRORLog("Unknown error, the server is stop.");
			e.printStackTrace();
		}
	}

	public static void stop() {
		ServletFatory.stop();
		Log.printINFOLog("The server is stop.");
	}
}
