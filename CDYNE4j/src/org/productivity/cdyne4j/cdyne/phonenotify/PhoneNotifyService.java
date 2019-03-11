package org.productivity.cdyne4j.cdyne.phonenotify;

import org.productivity.cdyne4j.CDYNEClient;
import org.productivity.cdyne4j.CDYNEException;
import org.productivity.cdyne4j.CDYNERuntimeException;
import org.productivity.cdyne4j.background.AbstractBackgroundStatusProcessor;
import org.productivity.cdyne4j.cdyne.AbstractCDYNEService;

/**
 * PhoneNotifyService implements: http://cdyne.com/api/phone/notify/
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
public class PhoneNotifyService extends AbstractCDYNEService {
	public NotifyPhoneBasicRs send(NotifyPhoneBasicRq rq) throws CDYNEException {
		return send(rq,NotifyPhoneBasicRs.class);
	}

	public NotifyPhoneBasicRs send(NotifyPhoneEnglishBasicRq rq) throws CDYNEException {
		return send(rq,NotifyPhoneBasicRs.class);
	}

	public NotifyPhoneBasicRs send(NotifyPhoneBasicWithTryCountRq rq) throws CDYNEException {
		return send(rq,NotifyPhoneBasicRs.class);
	}
	
	public NotifyPhoneBasicRs send(NotifyPhoneBasicWithTransferRq rq) throws CDYNEException {
		return send(rq,NotifyPhoneBasicRs.class);
	}
	
	@Override
	public AbstractBackgroundStatusProcessor getBackgroundStatusProcessor() {
		throw new CDYNERuntimeException("getBackgroundStatusProcessor(): PhoneNotifyService does not implement an AbstractBackgroundStatusProcessor");
	}

	public GetVoicesRs getVoices() throws CDYNEException {
		GetVoicesRq rq = new GetVoicesRq();
		
		return send(rq,GetVoicesRs.class);
	}

	public GetResponseCodesRs getResponseCodes() throws CDYNEException {
		GetResponseCodesRq rq = new GetResponseCodesRq();

		return send(rq,GetResponseCodesRs.class);
	}

	@Override
	protected byte getService() {
		return CDYNEClient.PHONE_NOTIFY;
	}
}
