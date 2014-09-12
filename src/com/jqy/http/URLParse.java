//http�������
package com.jqy.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class URLParse
{
	public HttpRequest parse(InputStream is) throws IOException
	{
		HttpRequest request = new HttpRequest();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		
		String requestLine = br.readLine();	 //����������
		getLines(request,requestLine);
		
		List<String> list = new ArrayList<String>();   //��������ͷ�������hashmap:headers��
		String header = "";
		while(!"".equals(header = br.readLine()))
		{
			list.add(header);
		}
		getHeaders(request,list);
		
		if("GET".equals(request.getMethod()))   //������������������hashmap:parameters��
		{
			getGetArgs(request);
		}
		else if(request.getMethod().equals("POST"))
		{
			getPostArgs(request,is);
		}

		return request;
	}
	
	public void getLines(HttpRequest request,String line)	//���������У��õ����󷽷���·����httpЭ��汾��
	{
		String[] lines = line.split(" ");
		request.setMethod(lines[0]);
		request.setUri(lines[1]);
		request.setVersion(lines[2]);
	}
	
	public void getHeaders(HttpRequest request,List<String> list)	//��������ͷ�������hashmap:headers��
	{
		int size = list.size();
		for(int i=0;i<size;i++)
		{
			String header = list.get(i);
			String key = header.substring(0, header.indexOf(":"));
			String value = header.substring(header.indexOf(":")+1);
			request.getHeaders().put(key.trim(), value.trim());
		}
	}
	
	public void getGetArgs(HttpRequest request)	//����ʽ��GETʱ�������������;��ʽ�磺localhost:7000?a=3&b=4
	{
		String[] uris = request.getUri().split("\\?");
		if(uris.length > 1)	  //���uri����������ˣ���ôǰ�벿����������uri���ʺ�֮����ǲ���
		{
			request.setUri(uris[0]);
			String[] args = uris[1].split("&");
			for(String s : args)
			{
				String key = s.split("=")[0];
				String value = s.split("=")[1];
				request.getParameters().put(key, value);
			}
		}
	}
	
	public void getPostArgs(HttpRequest request,InputStream is) throws IOException	//����ʽ��POSTʱ�������������
	{
		int length = (Integer)request.getHeaders().get("Content-Length");	//��������ĳ���
		byte[] buffer = new byte[length];
		int count = 0;
		int sum = 0;
		while(-1 != (count = is.read(buffer, 0, length)))	//��post�е��������ݶ�����buffer��
		{
			sum += count;
		}
		String[] args = buffer.toString().split("&");	//����buffer�е��������ݣ�����hashmap:parameters��
		for(String arg:args)
		{
			String key = arg.split("=")[0];
			String value = arg.split("=")[1];
			request.getParameters().put(key, value);
		}
	}
}
