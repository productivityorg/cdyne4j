package org.productivity.cdyne4j.cdyne.smsnotify;

import java.io.Reader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.productivity.cdyne4j.CDYNEException;
import org.productivity.cdyne4j.CDYNERuntimeException;

/**
 * SMSIncomingMessage implements: https://www.cdyne.com/sms/
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
@XmlRootElement(name="SMSIncomingMessage") 
public class SMSIncomingMessage implements Serializable {
	private static final long serialVersionUID = -7819173726600564571L;
	
	public static JAXBContext SMS_INCOMING_MESSAGE_JAXB_CONTEXT = null;
	
	@XmlRootElement(name="ArrayOfSMSIncomingMessage",namespace="http://sms2.cdyne.com")
	public static final class ArrayOfSMSIncomingMessage implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private static JAXBContext ARRAY_OF_SMS_INCOMING_MESSAGE_JAXB_CONTEXT = null;
		
		static {
			try {
				ARRAY_OF_SMS_INCOMING_MESSAGE_JAXB_CONTEXT = JAXBContext.newInstance(ArrayOfSMSIncomingMessage.class);
				
			} catch (JAXBException jaxbe) {
				throw new CDYNERuntimeException(jaxbe);
			}
		}
		
		protected List<SMSIncomingMessage> smsIncomingMessages = null;

		@XmlElement(name="SMSIncomingMessage")
		public List<SMSIncomingMessage> getSmsIncomingMessages() {
			if (smsIncomingMessages == null) {
				smsIncomingMessages = new ArrayList<SMSIncomingMessage>();
			}
			
			return smsIncomingMessages;
		}

		public void setSmsResponses(List<SMSIncomingMessage> smsIncomingMessages) {
			this.smsIncomingMessages = smsIncomingMessages;
		}
		
		public static ArrayOfSMSIncomingMessage parse(Reader reader) throws CDYNEException {
			try {
				Unmarshaller u = ARRAY_OF_SMS_INCOMING_MESSAGE_JAXB_CONTEXT.createUnmarshaller();
				
				ArrayOfSMSIncomingMessage response = (ArrayOfSMSIncomingMessage) u.unmarshal(reader);
				return response;
				
			} catch (JAXBException e) {
				throw new CDYNEException(e);
			}
		}
	}
	
	static {
		try {
			SMS_INCOMING_MESSAGE_JAXB_CONTEXT = JAXBContext.newInstance(SMSIncomingMessage.class);
			
		} catch (Exception e) {
			throw new CDYNERuntimeException(e);
		}
	}

	protected String fromPhoneNumber = null;
	protected String incomingMessageID = null;
	protected String matchedMessageID = null;
	protected String message = null;
	protected String responseReceiveDate = null;
	protected String toPhoneNumber = null;

	protected static SMSIncomingMessage parse(Reader reader) throws CDYNEException {
		try {
			Unmarshaller u = SMS_INCOMING_MESSAGE_JAXB_CONTEXT.createUnmarshaller();
			SMSIncomingMessage response = (SMSIncomingMessage) u.unmarshal(reader);
			return response;
			
		} catch (JAXBException e) {
			throw new CDYNEException(e);
		}
	}
	
	@XmlElement(name="FromPhoneNumber")
	public String getFromPhoneNumber() {
		return fromPhoneNumber;
	}

	public void setFromPhoneNumber(String fromPhoneNumber) {
		this.fromPhoneNumber = fromPhoneNumber;
	}

	@XmlElement(name="IncomingMessageID")
	public String getIncomingMessageID() {
		return incomingMessageID;
	}

	public void setIncomingMessageID(String incomingMessageID) {
		this.incomingMessageID = incomingMessageID;
	}

	@XmlElement(name="MatchedMessageID")
	public String getMatchedMessageID() {
		return matchedMessageID;
	}

	public void setMatchedMessageID(String matchedMessageID) {
		this.matchedMessageID = matchedMessageID;
	}

	@XmlElement(name="Message")
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@XmlElement(name="ResponseReceiveDate")
	public String getResponseReceiveDate() {
		return responseReceiveDate;
	}

	public void setResponseReceiveDate(String responseReceiveDate) {
		this.responseReceiveDate = responseReceiveDate;
	}

	@XmlElement(name="ToPhoneNumber")
	public String getToPhoneNumber() {
		return toPhoneNumber;
	}

	public void setToPhoneNumber(String toPhoneNumber) {
		this.toPhoneNumber = toPhoneNumber;
	}

	@Override
	public String toString() {
		return
			"FromPhoneNumber=" + this.fromPhoneNumber + " " +
			"IncomingMessageID=" + this.incomingMessageID + " " +
			"MatchedMessageID=" + this.matchedMessageID + " " +
			"Message=" + this.message + " " +
			"ToPhoneNumber=" + this.toPhoneNumber;
	}
}
