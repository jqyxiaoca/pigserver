//判断请求资源是静态资源还是动态资源
//判断的逻辑在于配置文件中对于静态文件的正则表达式
package com.jqy.http;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jqy.config.NormalConfigFactory;
import com.jqy.servlet.DoDynamic;
import com.jqy.staticfile.DoStatic;

public class Controller {

	static String[] staticTag = NormalConfigFactory.getConfig(
			"static.file.prefix").split(" ");

	// 判断当前是静态还是动态的请求，并交给响应的去处理
	public void mapping(HttpRequest request, HttpResponse response)
			throws Exception {
		String uri = request.getUri();

		for (String tag : staticTag) {
			Pattern pattern = Pattern.compile(tag);
			Matcher matcher = pattern.matcher(uri);

			if (matcher.find()) {
				DoStatic.doStatic(request, response);
				return;
			}
		}
		
		DoDynamic.doDynamic(request, response);
	}
}
