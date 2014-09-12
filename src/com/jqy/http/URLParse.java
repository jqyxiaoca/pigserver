//http请求解析
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
		
		String requestLine = br.readLine();	 //解析请求行
		getLines(request,requestLine);
		
		List<String> list = new ArrayList<String>();   //解析请求头，存放在hashmap:headers中
		String header = "";
		while(!"".equals(header = br.readLine()))
		{
			list.add(header);
		}
		getHeaders(request,list);
		
		if("GET".equals(request.getMethod()))   //解析请求参数，存放在hashmap:parameters中
		{
			getGetArgs(request);
		}
		else if(request.getMethod().equals("POST"))
		{
			getPostArgs(request,is);
		}

		return request;
	}
	
	public void getLines(HttpRequest request,String line)	//解析请求行，得到请求方法，路径，http协议版本号
	{
		String[] lines = line.split(" ");
		request.setMethod(lines[0]);
		request.setUri(lines[1]);
		request.setVersion(lines[2]);
	}
	
	public void getHeaders(HttpRequest request,List<String> list)	//解析请求头，存放在hashmap:headers中
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
	
	public void getGetArgs(HttpRequest request)	//请求方式是GET时，解析请求参数;形式如：localhost:7000?a=3&b=4
	{
		String[] uris = request.getUri().split("\\?");
		if(uris.length > 1)	  //如果uri后面跟参数了，那么前半部分是真正的uri，问号之后的是参数
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
	
	public void getPostArgs(HttpRequest request,InputStream is) throws IOException	//请求方式是POST时，解析请求参数
	{
		int length = (Integer)request.getHeaders().get("Content-Length");	//请求主体的长度
		byte[] buffer = new byte[length];
		int count = 0;
		int sum = 0;
		while(-1 != (count = is.read(buffer, 0, length)))	//把post中的请求数据都放在buffer中
		{
			sum += count;
		}
		String[] args = buffer.toString().split("&");	//解析buffer中的请求数据，放入hashmap:parameters中
		for(String arg:args)
		{
			String key = arg.split("=")[0];
			String value = arg.split("=")[1];
			request.getParameters().put(key, value);
		}
	}
}
