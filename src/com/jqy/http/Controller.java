//�ж�������Դ�Ǿ�̬��Դ���Ƕ�̬��Դ
//�жϵ��߼����������ļ��ж��ھ�̬�ļ���������ʽ
package com.jqy.http;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jqy.config.NormalConfigFactory;
import com.jqy.servlet.DoDynamic;
import com.jqy.staticfile.DoStatic;

public class Controller {

	static String[] staticTag = NormalConfigFactory.getConfig(
			"static.file.prefix").split(" ");

	// �жϵ�ǰ�Ǿ�̬���Ƕ�̬�����󣬲�������Ӧ��ȥ����
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
