package org.productivity.cdyne4j.cdyne.smsnotify;

import java.io.Reader;
import java.util.List;

import org.productivity.cdyne4j.CDYNEException;
import org.productivity.cdyne4j.CDYNERuntimeException;
import org.productivity.cdyne4j.cdyne.AbstractCDYNERs;
import org.productivity.cdyne4j.cdyne.smsnotify.SMSIncomingMessage.ArrayOfSMSIncomingMessage;

/**
 * GetUnreadIncomingMessagesRs implements the response side of: https://www.cdyne.com/sms/
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
public class GetUnreadIncomingMessagesRs extends AbstractCDYNERs {
	private static final long serialVersionUID = 2500777901095853977L;
	
	protected ArrayOfSMSIncomingMessage smsIncomingMessages = null;

	public List<SMSIncomingMessage> getSmsIncomingMessages() {
		return smsIncomingMessages.getSmsIncomingMessages();
	}

	@Override
	public boolean isSuccess() {
		return this.smsIncomingMessages != null;
	}
	
	@Override
	public String getReferenceID() {
		throw new CDYNERuntimeException("getReferenceID(): not yet implemented");		
	}

	@Override
	public void parse(Reader reader) throws CDYNEException {
		this.smsIncomingMessages = ArrayOfSMSIncomingMessage.parse(reader);
	}

}
