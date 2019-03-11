package org.productivity.cdyne4j.cdyne.smsnotify;

import java.util.UUID;

import org.productivity.cdyne4j.CDYNEClient;
import org.productivity.cdyne4j.CDYNEException;
import org.productivity.cdyne4j.background.AbstractBackgroundStatusProcessor;
import org.productivity.cdyne4j.background.BackgroundStatusEventHandlerIF;
import org.productivity.cdyne4j.cdyne.AbstractCDYNEService;
import org.productivity.cdyne4j.cdyne.smsnotify.advanced.AdvancedSMSSendRq;
import org.productivity.cdyne4j.cdyne.smsnotify.advanced.AdvancedSMSSendRs;
import org.productivity.cdyne4j.cdyne.smsnotify.advanced.AdvancedSMSSendRq.SMSRequest;

/**
 * SMSNotifyService implements: http://cdyne.com/api/phone/sms/
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
public class SMSNotifyService extends AbstractCDYNEService {
	protected SMSNotifyBackgroundStatusProcessor backgroundStatusProcessor = null;
	
	public GetUnreadIncomingMessagesRs getUnreadIncomingMessages() throws CDYNEException {
		GetUnreadIncomingMessagesRq rq = new GetUnreadIncomingMessagesRq();
		return send(rq,GetUnreadIncomingMessagesRs.class);
	}

	public GetUnreadIncomingMessagesRs send(GetUnreadIncomingMessagesRq rq) throws CDYNEException {
		return send(rq,GetUnreadIncomingMessagesRs.class);
	}

	public SimpleSMSSendRs send(SimpleSMSSendRq rq) throws CDYNEException {
		return send(rq,SimpleSMSSendRs.class);
	}
	
	@Override
	public AbstractBackgroundStatusProcessor getBackgroundStatusProcessor() {
		return this.backgroundStatusProcessor;
	}
	
	public synchronized void createStatusProcessor() {
		if (this.backgroundStatusProcessor != null) {
			return;
		}
		
		this.backgroundStatusProcessor = new SMSNotifyBackgroundStatusProcessor();
	}

	public SimpleSMSSendRs send(SimpleSMSSendRq rq, BackgroundStatusEventHandlerIF handler) throws CDYNEException {
		SimpleSMSSendRs rs = send(rq,SimpleSMSSendRs.class);
		
		if (rs != null) {
			if (this.backgroundStatusProcessor == null) {
				createStatusProcessor();
			}
			
			this.backgroundStatusProcessor.addBackgroundStatusRequest(rq,rs,rs.getSmsResponse().getMessageID(),handler);
		}
		
		return rs;
	}
	
	public SimpleSMSSendRs send(SimpleSMSSendWithPostbackRq rq) throws CDYNEException {
		return send(rq,SimpleSMSSendRs.class);
	}
	
	public AdvancedSMSSendRs send(AdvancedSMSSendRq rq, BackgroundStatusEventHandlerIF handler) throws CDYNEException {
		if (rq.getRequests() != null && rq.getRequests().size() > 0) {
			String commonReferenceID = rq.getCommonReferenceID();
			
			for(SMSRequest r: rq.getRequests()) {
				if (r.getReferenceID() == null) {
					if (commonReferenceID == null) {
						commonReferenceID = UUID.randomUUID().toString();
						rq.setCommonReferenceID(commonReferenceID);
					}
					
					r.setReferenceID(commonReferenceID);
				}
			}
		}
		AdvancedSMSSendRs rs = send(rq,AdvancedSMSSendRs.class);
		
		if (handler != null && rs != null && rs.getSmsResponses() != null && !rs.getSmsResponses().isEmpty()) {
			if (this.backgroundStatusProcessor == null) {
				createStatusProcessor();
			}
			
			for(SMSResponse s: rs.getSmsResponses()) {
				this.backgroundStatusProcessor.addBackgroundStatusRequest(rq,rs,s.getMessageID(),handler);
			}
		}
		
		return rs;
	}

	public AdvancedSMSSendRs send(AdvancedSMSSendRq rq) throws CDYNEException {
		BackgroundStatusEventHandlerIF handler = null;
		
		return send(rq,handler);
	}
	
	public GetMessageStatusRs send(GetMessageStatusRq rq) throws CDYNEException {
		return send(rq,GetMessageStatusRs.class);
	}

	public GetMessageStatusRs send(GetMessageStatusByReferenceIDRq rq) throws CDYNEException {
		return send(rq,GetMessageStatusRs.class);
	}

	@Override
	protected byte getService() {
		return CDYNEClient.SMS_NOTIFY;
	}
}
