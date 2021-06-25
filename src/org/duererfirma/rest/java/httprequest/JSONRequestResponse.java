package org.duererfirma.rest.java.httprequest;

public enum JSONRequestResponse {
    OK(200),
    PARTIAL(206),
    ERROR(0),
    INVALID_ENUM(-1);


    private final int result;
    JSONRequestResponse(int result)
    {
        this.result = result;
    }

    public int getResultCode()
    {
        return result;
    }

    public static JSONRequestResponse getResponse(int result)
    {
        for(JSONRequestResponse response : JSONRequestResponse.values())
        {
            if(response.getResultCode() == result)
            {
                return response;
            }
        }

        return JSONRequestResponse.INVALID_ENUM;

    }

    public static boolean isBlockedResponse(JSONRequestResponse response)
    {
        return response == null || response.toString().startsWith("ERROR") || response == JSONRequestResponse.INVALID_ENUM;
    }
    
    public boolean isBlockedResponse()
    {
    	return isBlockedResponse(this);
    }

    public static JSONRequestResponse getResponse(String result)
    {

        for(JSONRequestResponse response : JSONRequestResponse.values())
        {
            if(response.toString().equals(result))
            {
                return response;
            }
        }

        return JSONRequestResponse.INVALID_ENUM;
    }
}
