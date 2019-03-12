package org.productivity.cdyne4j.cdyne.phonenotify;

import java.util.ArrayList;
import java.util.List;

import org.productivity.cdyne4j.CDYNEException;
import org.productivity.cdyne4j.cdyne.AbstractCDYNERq;
import org.productivity.cdyne4j.cdyne.AbstractCDYNERs;

/**
 * NotifyPhoneEnglishBasicRq request the response side of: http://cdyne.com/api/phone/notify/
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
public class NotifyPhoneEnglishBasicRq extends AbstractCDYNERq {
	private static final long serialVersionUID = -2876601270636920983L;

	public static final String SERVICE_NAME = "NotifyPhoneEnglishBasic";
	
	public static final String PHONE_NUMBER_TO_DIAL_PARAM = "PhoneNumberToDial";
	public static final String TEXT_TO_SAY_PARAM = "TextToSay";
	public static final String LICENSE_KEY_PARAM = "LicenseKey";

	protected String phoneNumberToDial = null;
	protected String textToSay = null;
	
	public NotifyPhoneEnglishBasicRq() { }
	
	public NotifyPhoneEnglishBasicRq(String phoneNumberToDial, String textToSay) {
		this.phoneNumberToDial = phoneNumberToDial;
		this.textToSay = textToSay;
	}

	@Override
	public byte getRequestType() {
		return RQ_TYPE_PARAMS;
	}

	@Override
	public byte getResponseType() {
		return RS_TYPE_PARAMS;
	}

	@Override
	public Class<? extends AbstractCDYNERs> getResponseClass() {
		return NotifyPhoneBasicRs.class;
	}

	public String getPhoneNumberToDial() {
		return phoneNumberToDial;
	}

	public void setPhoneNumberToDial(String phoneNumberToDial) {
		this.phoneNumberToDial = phoneNumberToDial;
	}

	public String getTextToSay() {
		return textToSay;
	}

	public void setTextToSay(String textToSay) {
		this.textToSay = textToSay;
	}

	@Override
	public List<NameValue> params() throws CDYNEException {
		List<NameValue> list = new ArrayList<>();
		
		checkParam(PHONE_NUMBER_TO_DIAL_PARAM,this.phoneNumberToDial);	list.add(new NameValue(PHONE_NUMBER_TO_DIAL_PARAM,this.phoneNumberToDial));
		checkParam(TEXT_TO_SAY_PARAM,this.textToSay);					list.add(new NameValue(TEXT_TO_SAY_PARAM,this.textToSay));
		checkParam(LICENSE_KEY_PARAM,this.licenseKey);					list.add(new NameValue(LICENSE_KEY_PARAM,this.licenseKey));
		
		return list;
	}
	
	@Override
	public String getServiceName() {
		return SERVICE_NAME;
	}
}
