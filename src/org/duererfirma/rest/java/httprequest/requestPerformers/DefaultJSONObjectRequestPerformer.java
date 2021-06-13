package org.duererfirma.rest.java.httprequest.requestPerformers;

import org.duererfirma.rest.java.httprequest.HttpRequestPerformer;
import org.duererfirma.rest.java.httprequest.RequestType;
import org.json.JSONObject;

import java.util.HashMap;

public class DefaultJSONObjectRequestPerformer extends HttpRequestPerformer {
    public DefaultJSONObjectRequestPerformer(String address, HashMap<String, String> params, RequestType type) {
        super(address, params, type);
    }
    @Override
    public void parseJSON(String responseContext) {
        JSONObject object = new JSONObject(responseContext);
        for(String key : object.keySet())
        {
            storeObject(object.get(key), key);
        }
        storeObject(object, "whole_object");
    }
}
