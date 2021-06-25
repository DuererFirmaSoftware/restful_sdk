package org.duererfirma.rest.java.httprequest.impl.incoming.list;


import java.util.ArrayList;
import java.util.HashMap;

import org.duererfirma.rest.java.httprequest.HttpRequestResponse;
import org.duererfirma.rest.java.httprequest.JSONRequestResponse;
import org.duererfirma.rest.java.httprequest.RequestType;
import org.duererfirma.rest.java.httprequest.requestPerformers.DefaultJSONObjectRequestPerformer;
import org.json.JSONArray;
import org.json.JSONObject;

public class ListRequestPerformer {

	JSONObject article = null;
	ArrayList<JSONObject> objectList = new ArrayList<>();
	public ListRequestPerformer(String address, String subaddress, RequestType type, String id, String token) {
		HashMap<String, String> params = new HashMap<>();
		params.put("token", token);
		String usedAddress = id != null ? address + "/" + subaddress + "/" + id : address;
		DefaultJSONObjectRequestPerformer performer = new DefaultJSONObjectRequestPerformer(usedAddress, params, type);
		if(!HttpRequestResponse.isBlockedResponse(performer.performRequest()))
		{
			if(id == null) {
				if(performer.getJSONResponse() == JSONRequestResponse.OK)
				{
					addArticlesToList((JSONArray)performer.getObject("result"));
					
				}else if(performer.getJSONResponse() == JSONRequestResponse.PARTIAL)
				{
				addArticlesToList((JSONArray)performer.getObject("result"));
				listFurtherArticles(address, (String) performer.getObject("last"), type, token);
				}
			}else {
				if(performer.getJSONResponse() == JSONRequestResponse.OK)
				{
					article = (JSONObject) performer.getObject("result");
				}
			}
		}
	}
	
	private void addArticlesToList(JSONArray array)
	{
		for(Object obj : array)
		{
			JSONObject jsonObj = (JSONObject) obj;
			if(!objectList.contains(jsonObj))
			{
				objectList.add(jsonObj);
			}
		}
	}
	
	private void listFurtherArticles(String address, String last, RequestType type, String token)
	{
		HashMap<String, String> params = new HashMap<>(); 
		params.put("from", last);
		params.put("token", token);
		DefaultJSONObjectRequestPerformer performer = new DefaultJSONObjectRequestPerformer(address, params, type);
		if(!HttpRequestResponse.isBlockedResponse(performer.performRequest()))
		{
			if(performer.getJSONResponse() == JSONRequestResponse.OK)
			{
				addArticlesToList((JSONArray) performer.getObject("result"));
			}else if(performer.getJSONResponse() == JSONRequestResponse.PARTIAL)
			{
				listFurtherArticles(address, (String) performer.getObject("last"), type, token);
			}
		}
	}
	
	public ArrayList<JSONObject> getArticlesAsJSONObject()
	{
		return objectList;
	}
	
	public JSONObject getArticleAsJSONObject()
	{
		return article;
	}

}
