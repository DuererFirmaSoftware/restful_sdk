package org.duererfirma.rest.java.httprequest.requestPerformers;

import java.util.HashMap;

import org.duererfirma.rest.java.httprequest.HttpRequestPerformer;
import org.duererfirma.rest.java.httprequest.RequestType;
import org.json.JSONArray;

public class DefaultJSONArrayRequestPerformer extends HttpRequestPerformer {
    public DefaultJSONArrayRequestPerformer(String address, HashMap<String, String> params, RequestType type) {
        super(address, params, type);
    }

    @Override
    public void parseJSON(String responseContext) {
        JSONArray object = new JSONArray(responseContext);
        for(int i = 0; i < object.length(); i++)
        {
            storeObject(object.get(i), i + "");
        }
        storeObject(object, "whole_object");
    }
}
