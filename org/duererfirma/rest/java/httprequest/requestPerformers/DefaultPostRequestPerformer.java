package org.duererfirma.rest.java.httprequest.requestPerformers;

import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import org.duererfirma.rest.java.httprequest.HttpRequestResponse;
import org.duererfirma.rest.java.httprequest.RequestType;

public class DefaultPostRequestPerformer extends DefaultJSONObjectRequestPerformer {

	protected String jsonString;
	
	public DefaultPostRequestPerformer(String address, HashMap<String, String> params, String jsonString) {
		super(address, params, RequestType.POST);
		this.jsonString = jsonString;
	}
	
	@Override
	public HttpRequestResponse performRequest()
	{
		try {
			StringBuilder targetAddress = new StringBuilder(address);
			if(this.params != null && !params.isEmpty()) {
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
			try(OutputStream out = con.getOutputStream())
			{
				out.write(this.jsonString.getBytes());
			}
			
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

}
