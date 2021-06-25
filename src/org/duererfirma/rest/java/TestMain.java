package org.duererfirma.rest.java;


import org.duererfirma.rest.java.httprequest.HttpRequestResponse;
import org.duererfirma.rest.java.httprequest.JSONRequestResponse;
import org.duererfirma.rest.java.httprequest.RequestType;
import org.duererfirma.rest.java.httprequest.impl.incoming.TokenRequestPerformer;
import org.duererfirma.rest.java.httprequest.requestPerformers.DefaultJSONObjectRequestPerformer;

public class TestMain {

	public static void main(String[] args) {
		testTokenResponse("http://localhost", "test", "test");
	}
	private static void testResponse(String host)
	{
		DefaultJSONObjectRequestPerformer performer = new DefaultJSONObjectRequestPerformer(host, null, RequestType.GET);
		if(!HttpRequestResponse.isBlockedResponse(performer.performRequest()))
		{
			JSONRequestResponse response = performer.getJSONResponse();
			if(response == JSONRequestResponse.PARTIAL)
			{
				System.out.println("Awaiting more Content...");
			}else if(response == JSONRequestResponse.OK)
			{
				System.out.println("Request finished.");
			}else{
				System.out.println("Illegal Status");
			}
		}
	}

	private static void testTokenResponse(String host, String username, String password)
	{
		TokenRequestPerformer performer = new TokenRequestPerformer(username, password, host, true);
		System.out.println(performer.getToken());
	}

}
