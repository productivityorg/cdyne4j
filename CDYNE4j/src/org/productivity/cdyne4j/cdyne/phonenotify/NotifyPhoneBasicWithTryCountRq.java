package org.productivity.cdyne4j.cdyne.phonenotify;

import java.util.List;

import org.productivity.cdyne4j.CDYNEException;
import org.productivity.cdyne4j.cdyne.AbstractCDYNERs;

/**
 * NotifyPhoneBasicWithTryCountRq request the response side of: http://cdyne.com/api/phone/notify/
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
public class NotifyPhoneBasicWithTryCountRq extends NotifyPhoneBasicRq {
	private static final long serialVersionUID = -4203771816418178528L;

	public static final String SERVICE_NAME = "NotifyPhoneBasicWithTryCount";
	
	public static final String TRY_COUNT_PARAM = "TryCount";

	protected int tryCount = 1;
	
	public NotifyPhoneBasicWithTryCountRq() {
		super();
	}
	
	public NotifyPhoneBasicWithTryCountRq(String phoneNumberToDial, String textToSay, int tryCount) {
		super(phoneNumberToDial,textToSay);
		
		this.tryCount = tryCount;
	}

	public NotifyPhoneBasicWithTryCountRq(String phoneNumberToDial, String textToSay, int voiceID, int tryCount) {
		super(phoneNumberToDial,textToSay,voiceID);
		
		this.tryCount = tryCount;
	}
	
	public int getTryCount() {
		return this.tryCount;
	}

	public void setTryCount(int tryCount) {
		this.tryCount = tryCount;
	}

	@Override
	public Class<? extends AbstractCDYNERs> getResponseClass() {
		return NotifyPhoneBasicRs.class;
	}

	@Override
	public List<NameValue> params() throws CDYNEException {
		List<NameValue> list = super.params();
		
		checkParam(TRY_COUNT_PARAM,this.tryCount); list.add(new NameValue(TRY_COUNT_PARAM,this.tryCount)); 
		
		return list;
	}
	
	@Override
	public String getServiceName() {
		return SERVICE_NAME;
	}
}
