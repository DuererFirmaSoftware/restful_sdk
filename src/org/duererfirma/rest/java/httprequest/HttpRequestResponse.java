package org.duererfirma.rest.java.httprequest;

public enum HttpRequestResponse {

	ERROR_PAGE_NOT_FOUND(404),
	ERROR_BAD_REQUEST(400),
	ERROR_FORBIDDEN(403),
	ERROR_UNAUTHORIZED(401),
	OK(200),
	PARTIAL(206),
	INVALID_ENUM(-1);
	
	
	private final int result;
	HttpRequestResponse(int result)
	{
		this.result = result;
	}
	
	public int getResultCode()
	{
		return result;
	}
	
	public static HttpRequestResponse getResponse(int result)
	{
		for(HttpRequestResponse response : HttpRequestResponse.values())
		{
			if(response.getResultCode() == result)
			{
				return response;
			}
		}
			
		return HttpRequestResponse.INVALID_ENUM;
		
	}

	public static boolean isBlockedResponse(HttpRequestResponse response)
	{
		return response == null || response.toString().startsWith("ERROR") || response == HttpRequestResponse.INVALID_ENUM;
	}

	public static HttpRequestResponse getResponse(String result)
	{

		for(HttpRequestResponse response : HttpRequestResponse.values())
		{
			if(response.toString().equals(result))
			{
				return response;
			}
		}

		return HttpRequestResponse.INVALID_ENUM;
	}

	
}
