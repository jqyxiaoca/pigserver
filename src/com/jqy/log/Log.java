package com.jqy.log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

import com.jqy.config.NormalConfigFactory;

/**
 * 
 * 自己编写的一个log模块，支持控制台输出以及文件输出两种方式
 * 支持INFO,WARN以及ERROR三种级别
 * 文件输出默认会按照天分割，可能会有bug~~~
 * 
 * @author jqy
 *
 */
public class Log implements Runnable {

	public static boolean daySplit = false;
	public static boolean levelSplit = false;
	public static boolean isDebug = true;

	public static long todayTimeStart = 0;
	public static long todayTimeEnd = 0;

	public static String logDir = NormalConfigFactory.getConfig("log.path");

	public static HashMap<String, FileOutputStream> out = new HashMap<String, FileOutputStream>();
	public static LinkedBlockingQueue<LogType> logCache = new LinkedBlockingQueue<LogType>();

	private static void reCreateFile() {
		Set<String> outKeySet = out.keySet();
		for (String outKey : outKeySet) {
			try {
				out.get(outKey).close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		out.clear();
		createFile();
	}

	private static void checkTime(String dateString) {
		try {
			todayTimeStart = sdf.parse(dateString + " " + "00:00:00").getTime();
			todayTimeEnd = todayTimeStart + 24 * 60 * 60 * 1000;
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	private static void createFile() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = sdf.format(date);
		checkTime(dateString);
		if (levelSplit == true) {
			try {
				File fileWARN = new File(logDir + dateString + "_WARN_"
						+ date.getTime());
				File fileINFO = new File(logDir + dateString + "_INFO_"
						+ date.getTime());
				File fileERROR = new File(logDir + dateString + "_ERROR_"
						+ date.getTime());
				fileWARN.createNewFile();
				fileINFO.createNewFile();
				fileERROR.createNewFile();
				out.put("WARN", new FileOutputStream(fileWARN));
				out.put("INFO", new FileOutputStream(fileINFO));
				out.put("ERROR", new FileOutputStream(fileERROR));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			File fileALL = new File(logDir + dateString + "_ALL_"
					+ date.getTime());
			try {
				fileALL.createNewFile();
				out.put("WARN", new FileOutputStream(fileALL));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	static {
		System.out
				.println("-----------------------------我是华丽的分割线-------------------------------------");
		if (daySplit == true) {
			createFile();
		}
	}

	public static void printWARNLog(String info) {
		printLog(WARN, info);
	}

	public static void printERRORLog(String info) {
		printLog(ERROR, info);
	}

	public static void printINFOLog(String info) {
		printLog(INFO, info);
	}

	public static void printDEBUGLog(String info) {
		printLog(DEBUG, info);
	}

	public static void printLog(int level, String info) {
		if (level == DEBUG && isDebug == false) {
			return;
		}
		StringBuffer timeSB = getTime(new StringBuffer());
		StringBuffer levelSB = getLevel(timeSB, level);
		StringBuffer lastSB = levelSB.append(info);
		String log = lastSB.toString();
		System.out.println(log);
		if (daySplit == true) {
			LogType lt = new LogType();
			lt.log = log;
			lt.logType = level;
			try {
				logCache.put(lt);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static final int ERROR = 0;
	public static final int WARN = 1;
	public static final int INFO = 2;
	public static final int DEBUG = 3;

	private static StringBuffer getLevel(StringBuffer sb, int level) {
		if (level == ERROR) {
			sb.append(" → [ERROR]");
		} else if (level == WARN) {
			sb.append(" → [WARN]");
		} else if (level == INFO) {
			sb.append(" → [INFO]");
		} else if (level == DEBUG) {
			sb.append(" → [DEBUG]");
		}
		return sb;
	}

	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

	private static StringBuffer getTime(StringBuffer sb) {
		Date date = new Date(System.currentTimeMillis());
		String dateString = sdf.format(date);
		return sb.append(dateString);
	}

	public void run() {
		if (daySplit == true) {
			if (levelSplit == true) {
				Log.printINFOLog("日志系统正式启动");
				while (true) {
					try {
						LogType lt = logCache.take();
						if (todayTimeEnd < System.currentTimeMillis()) {
							reCreateFile();
						}
						OutputStream os = null;
						if (lt.logType == ERROR) {
							os = out.get("ERROE");
						} else if (lt.logType == WARN) {
							os = out.get("WARN");
						} else if (lt.logType == INFO) {
							os = out.get("INFO");
						}
						os.write(lt.log.getBytes());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else {
				while (true) {
					try {
						LogType lt = logCache.take();
						if (todayTimeEnd < System.currentTimeMillis()) {
							reCreateFile();
						}
						OutputStream os = out.get("ALL");
						os.write(lt.log.getBytes());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}

class LogType {
	int logType = -1;
	String log = "";
}