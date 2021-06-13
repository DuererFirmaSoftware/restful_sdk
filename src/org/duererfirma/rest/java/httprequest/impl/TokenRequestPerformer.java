package org.duererfirma.rest.java.httprequest.impl;

import org.duererfirma.rest.java.httprequest.HttpRequestResponse;
import org.duererfirma.rest.java.httprequest.JSONRequestResponse;
import org.duererfirma.rest.java.httprequest.RequestType;
import org.duererfirma.rest.java.httprequest.requestPerformers.DefaultJSONObjectRequestPerformer;

import java.util.HashMap;
import java.util.Objects;

public class TokenRequestPerformer {

    String token = null;

    public TokenRequestPerformer(String username, String password, String host, boolean attachPathToURL)
    {
        HashMap<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
        String path = attachPathToURL ? host + "/token/acquire" : host;
        DefaultJSONObjectRequestPerformer performer = new DefaultJSONObjectRequestPerformer(path, params, RequestType.GET);
        if(!HttpRequestResponse.isBlockedResponse(performer.performRequest()))
        {
            JSONRequestResponse response = performer.getJSONResponse();
            if(response == JSONRequestResponse.OK)
            {
                token = (String) performer.getObject("token");
            }
        }
    }


    public String getToken()
    {
        Objects.requireNonNull(token, "No Token");
        return token;
    }

}
