package org.productivity.cdyne4j.cdyne.phoneverify;

import java.util.ArrayList;
import java.util.List;

import org.productivity.cdyne4j.CDYNEException;
import org.productivity.cdyne4j.cdyne.AbstractCDYNERq;
import org.productivity.cdyne4j.cdyne.AbstractCDYNERs;

/**
 * CheckPhoneNumbersRq handles the request side of: https://www.cdyne.com/phone-verify/
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
public class CheckPhoneNumbersRq extends AbstractCDYNERq {
	private static final long serialVersionUID = 8792173201636445811L;

	public static final String SERVICE_NAME = "CheckPhoneNumbers";

	public static final String PHONE_NUMBERS_PARAM = "PhoneNumbers";
	public static final String LICENSE_KEY_PARAM = "LicenseKey";

	protected List<String> phoneNumbers = null;

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
		return CheckPhoneNumberRs.class;
	}

	public List<String> getPhoneNumbers() {
		return phoneNumbers;
	}

	public void setPhoneNumbers(List<String> phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
	}

	public void setPhoneNumbers(String... phoneNumbers) {
		if (phoneNumbers != null && phoneNumbers.length > 0) {
			this.phoneNumbers = new ArrayList<>();
			for(String n: phoneNumbers) {
				if (n != null && !n.isEmpty()) {
					this.phoneNumbers.add(n);
				}
			}
		}
	}

	@Override
	public List<NameValue> params() throws CDYNEException {
		if (this.phoneNumbers == null || this.phoneNumbers.isEmpty()) {
			throw new CDYNEException("params(): input list is null/empty");
		}
		
		List<NameValue> list = new ArrayList<>();
		
		for(String phoneNumber: this.phoneNumbers) {
			checkParam(PHONE_NUMBERS_PARAM,phoneNumber); list.add(new NameValue(PHONE_NUMBERS_PARAM,phoneNumber)); 
		}
		
		checkParam(LICENSE_KEY_PARAM,this.licenseKey); list.add(new NameValue(LICENSE_KEY_PARAM,this.licenseKey));
		
		return list;
	}
	
	@Override
	public String getServiceName() {
		return SERVICE_NAME;
	}
}
