package org.duererfirma.rest.java.httprequest.impl.incoming.list.impl;

import org.duererfirma.rest.java.httprequest.RequestType;
import org.duererfirma.rest.java.httprequest.impl.incoming.list.ListRequestPerformer;
public class ArticlesRequestPerformer extends ListRequestPerformer {

	public ArticlesRequestPerformer(String address, RequestType type, String id, String token) {
		super(address, "articles", type, id, token);
	}

	
}
