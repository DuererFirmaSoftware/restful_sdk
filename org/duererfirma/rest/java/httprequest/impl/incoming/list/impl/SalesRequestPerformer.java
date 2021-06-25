package org.duererfirma.rest.java.httprequest.impl.incoming.list.impl;

import org.duererfirma.rest.java.httprequest.RequestType;
import org.duererfirma.rest.java.httprequest.impl.incoming.list.ListRequestPerformer;


public class SalesRequestPerformer extends ListRequestPerformer {

	public SalesRequestPerformer(String address, RequestType type, String id, String token) {
		super(address, "sales", type, id, token);
	}
	
}
