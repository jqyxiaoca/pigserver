package com.jqy.http;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class WriteResponse
{
	public void returnResponse(HttpResponse response) throws IOException
	{
		writeLine(response);
		writeHeaders(response);
		writeHH(response);
	}
	
	public void writeLine(HttpResponse response) throws IOException	//œÏ”¶––
	{
		response.getOs().write((response.getVersion()+" "+response.getCode()+" "+response.getDescription()).getBytes());
		response.getOs().write("\r\n".getBytes());
		response.getOs().flush();
	}
	
	public void writeHH(HttpResponse response) throws IOException	//ø’––
	{
		response.getOs().write("\r\n".getBytes());
		response.getOs().flush();
	}
	
	public void writeHeaders(HttpResponse response) throws IOException
	{
		Map map = response.getHeaders();
		if(map.size() == 0)
			return;
		
		Iterator iter = map.keySet().iterator();
		while(iter.hasNext())
		{
			String key = (String) iter.next();
			System.out.println(key + ": " + map.get(key));
			response.getOs().write((key + ": " + map.get(key)).getBytes());
			response.getOs().write("\r\n".getBytes());
			response.getOs().flush();
		}
		
		
	}
}








