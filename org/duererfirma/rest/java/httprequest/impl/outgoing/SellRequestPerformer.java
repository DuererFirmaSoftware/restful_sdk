package org.duererfirma.rest.java.httprequest.impl.outgoing;

import java.util.HashMap;

import org.duererfirma.rest.java.httprequest.HttpRequestResponse;
import org.duererfirma.rest.java.httprequest.JSONRequestResponse;
import org.duererfirma.rest.java.httprequest.requestPerformers.DefaultPostRequestPerformer;
import org.json.JSONObject;

public class SellRequestPerformer {

	
	private boolean wasSuccessful = false;
	private String error = "Webpage not available";
	
	public SellRequestPerformer(String address, HashMap<String, Integer> articlesSold, String token)
	{
		new SellRequestPerformer(address, null, token, 0, null);
	}

	
	public SellRequestPerformer(String address, HashMap<String, Integer> articlesSold, String token, double discount, String comment)
	{
		HashMap<String, String> params = new HashMap<>();
		params.put("token", token);
		
		
		/*
		 * {"articles": {"<id>": "<count>", ...}, "comment": <comment>, "discount": <discount>}
		 */
		
		JSONObject object = new JSONObject();
		JSONObject articles = new JSONObject();
		for(String str : articlesSold.keySet())
		{
			if(articles.get(str) == null)
			{
				articles.put(str, articlesSold.get(str));
			}else {
				articles.put(str, articles.getInt(str) + articlesSold.get(str));
			}
		}
		
		object.put("articles", articles);
		if(comment != null)
		{
			object.put("comment", comment);
		}
		
		if(discount > 0)
		{
			object.put("discount", discount);
		}
		
		DefaultPostRequestPerformer performer = new DefaultPostRequestPerformer(address + "/sales/new", params, object.toString());
		
		HttpRequestResponse response = performer.performRequest();
		
		if(HttpRequestResponse.isBlockedResponse(response))
		{
			return;
		}
		
		JSONRequestResponse jsonResponse = performer.getJSONResponse();
		if(!jsonResponse.isBlockedResponse())
		{
			if(performer.getObject("error") != null)
			{
				error = (String) performer.getObject("error");
			}
		}else {
			wasSuccessful = true;
		}
		
		
	}
	
	public String getError()
	{
		return error;
	}
	
	public boolean wasSuccessful()
	{
		return wasSuccessful;
	}
	
}
