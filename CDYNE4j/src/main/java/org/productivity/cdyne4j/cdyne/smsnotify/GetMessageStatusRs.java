package org.productivity.cdyne4j.cdyne.smsnotify;

import java.io.Reader;

import org.productivity.cdyne4j.CDYNEException;
import org.productivity.cdyne4j.cdyne.AbstractCDYNERs;

/**
 * GetMessageStatusRs implements the response side of: https://www.cdyne.com/sms/
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
public class GetMessageStatusRs extends AbstractCDYNERs {
	private static final long serialVersionUID = -3199495594589279997L;
	
	protected SMSResponse smsResponse = null;

	public SMSResponse getSmsResponse() {
		return smsResponse;
	}

	public void setSmsResponse(SMSResponse smsResponse) {
		this.smsResponse = smsResponse;
	}

	@Override
	public boolean isSuccess() {
		return this.smsResponse != null && "NoError".equals(this.smsResponse.getSmsError());
	}
	
	@Override
	public String getReferenceID() {
		if (this.smsResponse != null) {
			return this.smsResponse.getReferenceID();			
			
		} else {
			return null;
		}
	}

	@Override
	public void parse(Reader reader) throws CDYNEException {
		this.smsResponse = SMSResponse.parse(reader);
	}
	
	@Override
	public String toString() {
		return "smsResponse=[" + getSmsResponse() + "]";
	}
}
