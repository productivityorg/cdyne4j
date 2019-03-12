package org.productivity.cdyne4j.cdyne.smsnotify;

import java.util.ArrayList;
import java.util.List;

import org.productivity.cdyne4j.CDYNEException;
import org.productivity.cdyne4j.cdyne.AbstractCDYNERq;
import org.productivity.cdyne4j.cdyne.AbstractCDYNERs;

/**
 * GetMessageStatusRq implements the request side of: https://www.cdyne.com/sms/
 * 
 * <pre>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 *   
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * </pre>
 * 
 * @author Justin Yunke <justin-github@yunke.us>
 */
public class GetMessageStatusRq extends AbstractCDYNERq {
	private static final long serialVersionUID = 4360934840546735796L;
	
	protected String messageID = null;
	
	public GetMessageStatusRq() { }
	
	public GetMessageStatusRq(String messageID) {
		this.messageID = messageID;
	}
	
	public String getMessageID() {
		return this.messageID;
	}

	public void setMessageID(String messageID) {
		this.messageID = messageID;
	}

	@Override
	public byte getRequestType() {
		return RQ_TYPE_PARAMS;
	}

	@Override
	public byte getResponseType() {
		return RS_TYPE_XML;
	}

	@Override
	public Class<? extends AbstractCDYNERs> getResponseClass() {
		return GetMessageStatusRs.class;
	}

	@Override
	public List<NameValue> params() throws CDYNEException {
		List<NameValue> list = new ArrayList<>();
		
		list.add(new NameValue("MessageID",this.messageID));
		list.add(new NameValue("LicenseKey",this.licenseKey));
		
		return list;
	}
	
	@Override
	public String getServiceName() {
		return "GetMessageStatus";
	}
}
