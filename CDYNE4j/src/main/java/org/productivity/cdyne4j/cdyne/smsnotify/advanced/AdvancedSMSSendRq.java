package org.productivity.cdyne4j.cdyne.smsnotify.advanced;

import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

import org.productivity.cdyne4j.CDYNEException;
import org.productivity.cdyne4j.CDYNERuntimeException;
import org.productivity.cdyne4j.cdyne.AbstractCDYNERq;
import org.productivity.cdyne4j.cdyne.AbstractCDYNERs;

/**
 * AdvancedSMSSendRq implements the request for: https://www.cdyne.com/sms/
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
@XmlRootElement(name="SMSAdvancedRequest")
@XmlType(propOrder={"concatenate","unicode","requests"})
public class AdvancedSMSSendRq extends AbstractCDYNERq {
	private static final long serialVersionUID = 707142698013844508L;
	
	public static JAXBContext JAXB_CONTEXT = null;
	
	static {
		try {
			JAXB_CONTEXT = JAXBContext.newInstance(AdvancedSMSSendRq.class);
			
		} catch (Exception e) {
			throw new CDYNERuntimeException(e);
		}
	}
	
	public static final class MicrosoftString {
		public MicrosoftString(String string) {
			this.string = string;
		}
		
		@XmlValue
		private String string = null;
		
		@XmlAttribute(name="xmlns")
		private String xmlns = "http://schemas.microsoft.com/2003/10/Serialization/Arrays";
	}

	@XmlRootElement
	public static final class SMSRequest {
		protected String assignedDID = null;
		protected String referenceID = null;
		protected String scheduledDateTime = null;
		protected String statusPostBackURL = null;
		protected List<MicrosoftString> phoneNumbers = null;
		protected String message = null;

		public SMSRequest() { }
		
		public SMSRequest(List<String> phoneNumbers, String message) {
			List<MicrosoftString> _phoneNumbers = new ArrayList<>();
			for(String phoneNumber: phoneNumbers) {
				_phoneNumbers.add(new MicrosoftString(phoneNumber));
			}
			
			this.phoneNumbers = _phoneNumbers;
			this.message = message;
		}

		public SMSRequest(String phoneNumber, String message) {
			this.phoneNumbers = new ArrayList<>();
			this.phoneNumbers.add(new MicrosoftString(phoneNumber));
			this.message = message;
		}
		
		@XmlAttribute(name="xmlns")
		public String requestXmlns = "http://sms2.cdyne.com";

		@XmlElement(name="AssignedDID")
		public String getAssignedDID() {
			return assignedDID;
		}

		public void setAssignedDID(String assignedDID) {
			this.assignedDID = assignedDID;
		}

		@XmlElement(name="ReferenceID")
		public String getReferenceID() {
			return referenceID;
		}

		public void setReferenceID(String referenceID) {
			this.referenceID = referenceID;
		}

		@XmlElement(name="ScheduledDateTime")
		public String getScheduledDateTime() {
			return scheduledDateTime;
		}

		public void setScheduledDateTime(String scheduledDateTime) {
			this.scheduledDateTime = scheduledDateTime;
		}

		@XmlElement(name="StatusPostBackURL")
		public String getStatusPostBackURL() {
			return statusPostBackURL;
		}

		public void setStatusPostBackURL(String statusPostBackURL) {
			this.statusPostBackURL = statusPostBackURL;
		}

		@XmlElementWrapper(name="PhoneNumbers")
		@XmlElement(name="string")
		public List<MicrosoftString> getPhoneNumbers() {
			return phoneNumbers;
		}

		public void setPhoneNumbers(List<MicrosoftString> phoneNumbers) {
			this.phoneNumbers = phoneNumbers;
		}

		@XmlElement(name="Message")
		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
	}
	
	protected boolean concatenate = true;
	protected boolean unicode = false;
	protected transient String commonReferenceID = null;
	
	protected List<SMSRequest> requests = null;
	
	public AdvancedSMSSendRq() { }

	public AdvancedSMSSendRq(String phoneNumber, String message) {
		SMSRequest r = new SMSRequest(phoneNumber,message);
		getRequests().add(r);
	}
	
	@XmlAttribute(name="xmlns")
	public String getXmlns() {
		return "http://schemas.datacontract.org/2004/07/SmsWS";
	}
	
	@XmlElement(name="Concatenate")
	public boolean isConcatenate() {
		return concatenate;
	}

	public void setConcatenate(boolean concatenate) {
		this.concatenate = concatenate;
	}

	@XmlElement(name="IsUnicode")
	public boolean isUnicode() {
		return unicode;
	}

	public void setUnicode(boolean unicode) {
		this.unicode = unicode;
	}
	
	@XmlElementWrapper(name="SMSRequests")
	@XmlElement(name="SMSRequest")
	public List<SMSRequest> getRequests() {
		if (this.requests == null) {
			this.requests = new ArrayList<>();
		}
		
		return requests;
	}

	public void setRequests(List<SMSRequest> requests) {
		this.requests = requests;
	}

	@Override
	public void serializeXml(Writer writer) throws CDYNEException {
		serializeXml(this,writer,JAXB_CONTEXT);
	}
	
	public void setCommonReferenceID(String commonReferenceID) {
		this.commonReferenceID = commonReferenceID;
	}
	
	@XmlTransient
	public String getCommonReferenceID() {
		return this.commonReferenceID;
	}
	
	@Override
	public byte getRequestType() {
		return RQ_TYPE_XML;
	}
	
	@Override
	public byte getResponseType() {
		return RS_TYPE_XML;
	}
	
	@Override
	public Class<? extends AbstractCDYNERs> getResponseClass() {
		return AdvancedSMSSendRs.class;
	}

	@Override
	public String getServiceName() {
		return "AdvancedSMSsend";
	}
}
