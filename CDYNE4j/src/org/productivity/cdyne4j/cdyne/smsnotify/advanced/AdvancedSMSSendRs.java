package org.productivity.cdyne4j.cdyne.smsnotify.advanced;

import java.io.Reader;
import java.util.List;

import org.productivity.cdyne4j.CDYNEException;
import org.productivity.cdyne4j.CDYNERuntimeException;
import org.productivity.cdyne4j.cdyne.AbstractCDYNERs;
import org.productivity.cdyne4j.cdyne.smsnotify.SMSResponse;
import org.productivity.cdyne4j.cdyne.smsnotify.SMSResponse.ArrayOfSMSResponse;

/**
 * AdvancedSMSSendRs implements the response for: https://www.cdyne.com/sms/
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
public class AdvancedSMSSendRs extends AbstractCDYNERs {
	private static final long serialVersionUID = 2711831695221517516L;
	
	protected ArrayOfSMSResponse smsResponses = null;
	
	public List<SMSResponse> getSmsResponses() {
		return smsResponses.getSmsResponses();
	}

	@Override
	public boolean isSuccess() {
		return getSmsResponses() != null && !getSmsResponses().isEmpty();
	}
	
	@Override
	public String getReferenceID() {
		throw new CDYNERuntimeException("getReferenceID(): not yet implemented");		
	}

	@Override
	public void parse(Reader reader) throws CDYNEException {
		this.smsResponses = ArrayOfSMSResponse.parse(reader);
	}
}
