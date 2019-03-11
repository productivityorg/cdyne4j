package org.productivity.cdyne4j.cdyne.phonenotify;

import java.io.Reader;
import java.io.Serializable;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.productivity.cdyne4j.CDYNEException;
import org.productivity.cdyne4j.CDYNERuntimeException;
import org.productivity.cdyne4j.cdyne.AbstractCDYNERs;

/**
 * NotifyPhoneBasicRs implements the response side of: http://cdyne.com/api/phone/notify/
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

public class NotifyPhoneBasicRs extends AbstractCDYNERs {
	private static final long serialVersionUID = -579747397243120491L;

	@XmlRootElement(name="NotifyReturn",namespace="http://ws.cdyne.com/NotifyWS/")
	public static final class NotifyReturn implements Serializable {
		private static final long serialVersionUID = 1L;
		private static JAXBContext JAXB_CONTEXT = null;

		static {
			try {
				JAXB_CONTEXT = JAXBContext.newInstance(NotifyReturn.class);
				
			} catch (JAXBException jaxbe) {
				throw new CDYNERuntimeException(jaxbe);
			}
		}

		private Integer responseCode = null;
		private String responseText = null;
		private Boolean callAnswered = null;
		private String queueID = null;
		private Integer tryCount = null;
		private Boolean demo = null;
		private Integer duration = null;
		private String startTime = null;
		private String endTime = null;
		private Integer minuteRate = null;
		private Boolean callComplete = null;

		@XmlElement(name="ResponseCode")
		public Integer getResponseCode() {
			return responseCode;
		}
		public void setResponseCode(Integer responseCode) {
			this.responseCode = responseCode;
		}
		@XmlElement(name="ResponseText")
		public String getResponseText() {
			return responseText;
		}
		public void setResponseText(String responseText) {
			this.responseText = responseText;
		}
		@XmlElement(name="CallAnswered")
		public Boolean isCallAnswered() {
			return callAnswered;
		}
		public void setCallAnswered(Boolean callAnswered) {
			this.callAnswered = callAnswered;
		}
		@XmlElement(name="QueueID")
		public String getQueueID() {
			return queueID;
		}
		public void setQueueID(String queueID) {
			this.queueID = queueID;
		}
		@XmlElement(name="TryCount")
		public Integer getTryCount() {
			return tryCount;
		}
		public void setTryCount(Integer tryCount) {
			this.tryCount = tryCount;
		}
		@XmlElement(name="Demo")
		public Boolean isDemo() {
			return demo;
		}
		public void setDemo(Boolean demo) {
			this.demo = demo;
		}
		@XmlElement(name="Duration")
		public Integer getDuration() {
			return duration;
		}
		public void setDuration(Integer duration) {
			this.duration = duration;
		}
		@XmlElement(name="StartTime")
		public String getStartTime() {
			return startTime;
		}
		public void setStartTime(String startTime) {
			this.startTime = startTime;
		}
		@XmlElement(name="EndTime")
		public String getEndTime() {
			return endTime;
		}
		public void setEndTime(String endTime) {
			this.endTime = endTime;
		}
		@XmlElement(name="MinuteRate")
		public Integer getMinuteRate() {
			return minuteRate;
		}
		public void setMinuteRate(Integer minuteRate) {
			this.minuteRate = minuteRate;
		}
		@XmlElement(name="CallComplete")
		public Boolean isCallComplete() {
			return callComplete;
		}
		public void setCallComplete(Boolean callComplete) {
			this.callComplete = callComplete;
		}
		public static NotifyReturn parse(Reader reader) throws CDYNEException {
			try {
				Unmarshaller u = JAXB_CONTEXT.createUnmarshaller();
				
				NotifyReturn response = (NotifyReturn) u.unmarshal(reader);
				return response;
				
			} catch (JAXBException e) {
				throw new CDYNEException(e);
			}
		}
	}
	
	protected NotifyReturn notifyReturn = null;
	
	public NotifyReturn getNotifyReturn() {
		return notifyReturn;
	}

	public void setNotifyReturn(NotifyReturn notifyReturn) {
		this.notifyReturn = notifyReturn;
	}
	
	@Override
	public boolean isSuccess() {
		return this.notifyReturn.getQueueID() != null;
	}
	
	@Override
	public String getReferenceID() {
		return this.notifyReturn.getQueueID();	
	}

	@Override
	public void parse(Reader reader) throws CDYNEException {
		this.notifyReturn = NotifyReturn.parse(reader);
	}
}
