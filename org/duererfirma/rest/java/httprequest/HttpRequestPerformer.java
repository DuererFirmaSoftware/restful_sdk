package org.duererfirma.rest.java.httprequest;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class HttpRequestPerformer {
	
	
	protected String address;
	protected HashMap<String, String> params;
	protected RequestType type;
	protected HashMap<String, Object> jsonObjects = new HashMap<>();
	protected HttpRequestResponse response;
	
	public HttpRequestPerformer(String address, HashMap<String, String> params, RequestType type)
	{
		this.address = address;
		this.params = params;
		this.type = type;
	}




	public HttpRequestResponse performRequest()
	{
		try {
		StringBuilder targetAddress = new StringBuilder(address);
		if(params != null && !params.isEmpty()) {
			targetAddress.append("?");
			for(String str : params.keySet())
			{
				String key = URLEncoder.encode(str, StandardCharsets.UTF_8);
				String value = URLEncoder.encode(params.get(str), StandardCharsets.UTF_8);
				targetAddress.append(key).append("=").append(value).append("&");
			}
		}
		
		if(targetAddress.toString().endsWith("&"))
		targetAddress = new StringBuilder(targetAddress.substring(0, targetAddress.length() - 1));
		
		URL url = new URL(targetAddress.toString());
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod(type.toString());
		con.setConnectTimeout(5000);
		con.setReadTimeout(5000);
		try {
			HttpRequestResponse response = HttpRequestResponse.getResponse(con.getResponseCode());
			if (!response.toString().startsWith("ERROR") && response.getResultCode() > 0)
				onSuccessfulRequest(con, response);
			this.response = response;
			return response;
		}catch (ConnectException e)
		{
			return HttpRequestResponse.INVALID_ENUM;
		}
		}catch(Exception e)
		{
			return HttpRequestResponse.INVALID_ENUM;
		}
		
	}

	public void onSuccessfulRequest(HttpURLConnection con, HttpRequestResponse response) {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line;
			StringBuilder responseContext = new StringBuilder();
			while ((line = reader.readLine()) != null) {
				responseContext.append(line);
			}
			reader.close();
			parseJSON(responseContext.toString());
		} catch (IOException ignored) {

		} finally {
			con.disconnect();
		}
	}


	public void parseJSON(String responseContext)
	{
		System.out.println("This Method is empty per default. Please use Classes extending HttpRequestPerformer to parse JSON Content.");
	}

	public void storeObject(Object obj, String key)
	{
		jsonObjects.put(key, obj);
	}

	public JSONRequestResponse getJSONResponse()
	{
		return JSONRequestResponse.getResponse((String) getObject("status"));
	}

	public HttpRequestResponse getHttpResponse()
	{
		return response;
	}

	public Object getObject(String key)
	{
		return jsonObjects.get(key);
	}
}
	


