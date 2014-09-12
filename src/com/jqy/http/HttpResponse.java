package com.jqy.http;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse
{
	//HTTP1.1 200 OK
	private String version;	 //httpЭ��汾��
	private String code;	//״̬��
	private String description; //״̬����
	private Map<String,String> headers = new HashMap<String,String>();
	private OutputStream os;	//���ڽ���Ӧд�ظ��û�
	private ByteArrayOutputStream bout = new ByteArrayOutputStream();	//���ڴ洢Ҫ��ȡ��Դ������

	public HttpResponse(OutputStream os)
	{
		this.os = os;
	}
	
	public void sendRedirect(HttpResponse response,String location) throws Exception
	{
		setVersion("HTTP/1.1");
		setCode("302");
		setDescription("Found");
		headers.put("Content-Type","text/html");
		headers.put("Location", location);
		setHeaders(headers);
	}
	
	public String getVersion()
	{
		return version;
	}
	public void setVersion(String version)
	{
		this.version = version;
	}
	public String getCode()
	{
		return code;
	}
	public void setCode(String code)
	{
		this.code = code;
	}
	public String getDescription()
	{
		return description;
	}
	public void setDescription(String description)
	{
		this.description = description;
	}
	public Map<String, String> getHeaders()
	{
		return headers;
	}
	public void setHeaders(Map<String, String> headers)
	{
		this.headers = headers;
	}
	public OutputStream getOs()
	{
		return os;
	}
	public void setOs(OutputStream os)
	{
		this.os = os;
	}
	public ByteArrayOutputStream getBout()
	{
		return bout;
	}
	public void setBout(ByteArrayOutputStream bout)
	{
		this.bout = bout;
	}
}
