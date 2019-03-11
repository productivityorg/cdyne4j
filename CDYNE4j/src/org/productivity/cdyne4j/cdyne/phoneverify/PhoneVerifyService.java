package org.productivity.cdyne4j.cdyne.phoneverify;

import org.productivity.cdyne4j.CDYNEClient;
import org.productivity.cdyne4j.CDYNEException;
import org.productivity.cdyne4j.CDYNERuntimeException;
import org.productivity.cdyne4j.background.AbstractBackgroundStatusProcessor;
import org.productivity.cdyne4j.cdyne.AbstractCDYNEService;

/**
 * PhoneVerifyService implements: http://cdyne.com/api/phone-verification/
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
public class PhoneVerifyService extends AbstractCDYNEService {
	public CheckPhoneNumberRs send(CheckPhoneNumberRq rq) throws CDYNEException {
		return send(rq,CheckPhoneNumberRs.class);
	}

	public CheckPhoneNumbersRs send(CheckPhoneNumbersRq rq) throws CDYNEException {
		return send(rq,CheckPhoneNumbersRs.class);
	}

	@Override
	public AbstractBackgroundStatusProcessor getBackgroundStatusProcessor() {
		throw new CDYNERuntimeException("getBackgroundStatusProcessor(): PhoneVerifyService does not implement an AbstractBackgroundStatusProcessor");
	}

	@Override
	protected byte getService() {
		return CDYNEClient.PHONE_VERIFY;
	}
}
