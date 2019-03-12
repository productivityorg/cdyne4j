package org.productivity.cdyne4j.cdyne.phonenotify;

import java.util.List;

import org.productivity.cdyne4j.CDYNEException;
import org.productivity.cdyne4j.cdyne.AbstractCDYNERs;

/**
 * NotifyPhoneBasicRq implements the request side of: http://cdyne.com/api/phone/notify/
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
public class NotifyPhoneBasicRq extends NotifyPhoneEnglishBasicRq {
	private static final long serialVersionUID = -2876601270636920983L;

	public static final String SERVICE_NAME = "NotifyPhoneBasic";

	protected String callerID = null;
	protected String callerIDName = null;
	protected Integer voiceID = null;
	
	public NotifyPhoneBasicRq() { }
	
	public NotifyPhoneBasicRq(String phoneNumberToDial, String textToSay) {
		this.phoneNumberToDial = phoneNumberToDial;
		this.textToSay = textToSay;
	}

	public NotifyPhoneBasicRq(String phoneNumberToDial, String textToSay, int voiceID) {
		this.phoneNumberToDial = phoneNumberToDial;
		this.textToSay = textToSay;
		this.voiceID = voiceID;
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
		return NotifyPhoneBasicRs.class;
	}

	public String getCallerID() {
		return callerID;
	}

	public void setCallerID(String callerID) {
		this.callerID = callerID;
	}

	public String getCallerIDName() {
		return callerIDName;
	}

	public void setCallerIDName(String callerIDName) {
		this.callerIDName = callerIDName;
	}

	public Integer getVoiceID() {
		return voiceID;
	}

	public void setVoiceID(Integer voiceID) {
		this.voiceID = voiceID;
	}
	
	@Override
	public List<NameValue> params() throws CDYNEException {
		List<NameValue> list = super.params();
		
		if (this.voiceID != null) {
			list.add(new NameValue("VoiceID",this.voiceID));
		}
		if (this.callerID != null) {
			list.add(new NameValue("CallerID",this.callerID));
		}
		if (this.callerIDName != null) {
			list.add(new NameValue("CallerIDName",this.callerIDName));
		}

		return list;
	}
	
	@Override
	public String getServiceName() {
		return SERVICE_NAME;
	}
}
