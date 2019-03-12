package org.productivity.cdyne4j.cdyne.smsnotify;

import java.io.Reader;
import java.io.Serializable;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.productivity.cdyne4j.CDYNEException;
import org.productivity.cdyne4j.CDYNERuntimeException;
import org.productivity.cdyne4j.cdyne.AbstractCDYNERs;

/**
 * SMSResponse implements a response object for: https://www.cdyne.com/sms/
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
@XmlRootElement(name="SMSResponse",namespace="http://sms2.cdyne.com")
public class SMSResponse implements Serializable {
	private static final long serialVersionUID = 3325837681576283896L;
	
	public static JAXBContext SMS_RESPONSE_JAXB_CONTEXT = null;

	@XmlRootElement(name="ArrayOfSMSResponse",namespace="http://sms2.cdyne.com")
	public static final class ArrayOfSMSResponse {
		private static JAXBContext JAXB_CONTEXT = null;
		
		static {
			try {
				JAXB_CONTEXT = JAXBContext.newInstance(ArrayOfSMSResponse.class);
				
			} catch (JAXBException jaxbe) {
				throw new CDYNERuntimeException(jaxbe);
			}
		}
		
		protected List<SMSResponse> smsResponses = null;

		@XmlElement(name="SMSResponse")
		public List<SMSResponse> getSmsResponses() {
			return smsResponses;
		}

		public void setSmsResponses(List<SMSResponse> smsResponses) {
			this.smsResponses = smsResponses;
		}
		
		public static ArrayOfSMSResponse parse(Reader reader) throws CDYNEException {
			try {
				Unmarshaller u = JAXB_CONTEXT.createUnmarshaller();
				
				ArrayOfSMSResponse response = (ArrayOfSMSResponse) u.unmarshal(reader);
				return response;
				
			} catch (JAXBException e) {
				throw new CDYNEException(e);
			}
		}
	}
	
	static {
		try {
			SMS_RESPONSE_JAXB_CONTEXT = JAXBContext.newInstance(SMSResponse.class);
			
		} catch (Exception e) {
			throw new CDYNERuntimeException(e);
		}
	}

	protected boolean cancelled = false;
	protected String messageID = null;
	protected boolean queued = false;
	protected String referenceID = null;
	protected String smsError = null;
	protected String smsIncomingMessages = null;
	protected boolean sent = false;
	protected String sentDateTime = null;

	@XmlElement(name="Cancelled")
	public boolean isCancelled() {
		return cancelled;
	}

	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	@XmlElement(name="MessageID")
	public String getMessageID() {
		return messageID;
	}

	public void setMessageID(String messageID) {
		this.messageID = nullCheck(messageID);
	}

	@XmlElement(name="Queued")
	public boolean isQueued() {
		return queued;
	}

	public void setQueued(boolean queued) {
		this.queued = queued;
	}

	@XmlElement(name="ReferenceID")
	public String getReferenceID() {
		return referenceID;
	}

	public void setReferenceID(String referenceID) {
		this.referenceID = nullCheck(referenceID);
	}

	@XmlElement(name="SMSError")
	public String getSmsError() {
		return smsError;
	}

	public void setSmsError(String smsError) {
		this.smsError = nullCheck(smsError);
	}

	@XmlElement(name="SMSIncomingMessages")
	public String getSmsIncomingMessages() {
		return smsIncomingMessages;
	}

	public void setSmsIncomingMessages(String smsIncomingMessages) {
		this.smsIncomingMessages = nullCheck(smsIncomingMessages);
	}

	@XmlElement(name="Sent")
	public boolean isSent() {
		return sent;
	}

	public void setSent(boolean sent) {
		this.sent = sent;
	}

	@XmlElement(name="SentDateTime")
	public String getSentDateTime() {
		return sentDateTime;
	}

	public void setSentDateTime(String sentDateTime) {
		this.sentDateTime = nullCheck(sentDateTime);
	}
	
	protected static String nullCheck(String in) {
		return AbstractCDYNERs.nullCheck(in);
	}
	
	protected static SMSResponse parse(Reader reader) throws CDYNEException {
		try {
			Unmarshaller u = SMS_RESPONSE_JAXB_CONTEXT.createUnmarshaller();
			SMSResponse response = (SMSResponse) u.unmarshal(reader);
			return response;
			
		} catch (JAXBException e) {
			throw new CDYNEException(e);
		}
	}
	
	@Override
	public String toString() {
		return
			"MessageID=" + this.messageID + " " +
			"Cancelled=" + this.cancelled + " " +
			"Queued=" + this.queued + " " +
			"ReferenceID=" + this.referenceID + " " +
			"SMSError=" + this.smsError + " " +
			"SMSIncomingMessages=" + this.smsIncomingMessages + " " +
			"Sent=" + this.sent + " " +
			"SentDateTime=" + this.sentDateTime;
	}
}
