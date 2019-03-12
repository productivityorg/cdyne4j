package org.productivity.cdyne4j.cdyne.phoneverify;

import java.util.ArrayList;
import java.util.List;

import org.productivity.cdyne4j.CDYNEException;
import org.productivity.cdyne4j.cdyne.AbstractCDYNERq;
import org.productivity.cdyne4j.cdyne.AbstractCDYNERs;

/**
 * CheckPhoneNumberRq handles the request side of: https://www.cdyne.com/phone-verify/
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
public class CheckPhoneNumberRq extends AbstractCDYNERq {
	private static final long serialVersionUID = -775438895516212680L;
	
	public static final String SERVICE_NAME = "CheckPhoneNumber";

	public static final String PHONE_NUMBER_PARAM = "PhoneNumber";
	public static final String LICENSE_KEY_PARAM = "LicenseKey";
	
	protected String phoneNumber = null;

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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public List<NameValue> params() throws CDYNEException {
		List<NameValue> list = new ArrayList<>();
		
		checkParam(PHONE_NUMBER_PARAM,this.phoneNumber); list.add(new NameValue(PHONE_NUMBER_PARAM,this.phoneNumber)); 
		checkParam(LICENSE_KEY_PARAM,this.licenseKey); list.add(new NameValue(LICENSE_KEY_PARAM,this.licenseKey));
		
		return list;
	}
	
	@Override
	public String getServiceName() {
		return SERVICE_NAME;
	}
}
