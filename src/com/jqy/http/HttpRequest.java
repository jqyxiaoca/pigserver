package com.jqy.http;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
	private String method; // ���󷽷�
	private String uri; // ·��
	private String version; // httpЭ��汾��
	private Map headers = new HashMap(); // �������ͷ�еļ�ֵ��
	private Map parameters = new HashMap(); // ����û��������Ĳ���

	private boolean isForward;

	@Override
	public String toString() {
		return "HttpRequest [method=" + method + ", uri=" + uri + ", version="
				+ version + "]";
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Map getHeaders() {
		return headers;
	}

	public void setHeaders(Map headers) {
		this.headers = headers;
	}

	public Map getParameters() {
		return parameters;
	}

	public void setParameters(Map parameters) {
		this.parameters = parameters;
	}

	public boolean isForward() {
		return isForward;
	}

	public void setForward(boolean isForward) {
		this.isForward = isForward;
	}

	public void forward(String uri) {
		this.isForward = true;
		this.uri = uri;
	}
}
