package org.productivity.cdyne4j.cdyne.phonenotify;

import java.util.List;

import org.productivity.cdyne4j.CDYNEException;
import org.productivity.cdyne4j.cdyne.AbstractCDYNERs;

/**
 * NotifyPhoneAdvanced implements: http://cdyne.com/api/phone/notify/
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
public class NotifyPhoneAdvanced extends NotifyPhoneBasicWithTransferRq {
	private static final long serialVersionUID = -2876601270636920983L;

	public static final String SERVICE_NAME = "NotifyPhoneAdvanced";

	protected Integer tryCount = null;
	protected Integer nextTryInSeconds = null;
	protected String utcScheduledDateTime = null;
	protected Integer ttsRate = null;
	protected Integer ttsVolume = null;
	protected String statusChangePostUrl = null;
	protected String maxCallLength = null;
	
	public NotifyPhoneAdvanced() { }
	
	public NotifyPhoneAdvanced(String phoneNumberToDial, String textToSay) {
		this.phoneNumberToDial = phoneNumberToDial;
		this.textToSay = textToSay;
	}

	public NotifyPhoneAdvanced(String phoneNumberToDial, String textToSay, int voiceID) {
		this.phoneNumberToDial = phoneNumberToDial;
		this.textToSay = textToSay;
		this.voiceID = voiceID;
	}

	public Integer getTryCount() {
		return tryCount;
	}

	public void setTryCount(Integer tryCount) {
		this.tryCount = tryCount;
	}

	public Integer getNextTryInSeconds() {
		return nextTryInSeconds;
	}

	public void setNextTryInSeconds(Integer nextTryInSeconds) {
		this.nextTryInSeconds = nextTryInSeconds;
	}

	public String getUtcScheduledDateTime() {
		return utcScheduledDateTime;
	}

	public void setUtcScheduledDateTime(String utcScheduledDateTime) {
		this.utcScheduledDateTime = utcScheduledDateTime;
	}

	public Integer getTtsRate() {
		return ttsRate;
	}

	public void setTtsRate(Integer ttsRate) {
		this.ttsRate = ttsRate;
	}

	public Integer getTtsVolume() {
		return ttsVolume;
	}

	public void setTtsVolume(Integer ttsVolume) {
		this.ttsVolume = ttsVolume;
	}

	public String getStatusChangePostUrl() {
		return statusChangePostUrl;
	}

	public void setStatusChangePostUrl(String statusChangePostUrl) {
		this.statusChangePostUrl = statusChangePostUrl;
	}

	@Override
	public byte getRequestType() {
		return RQ_TYPE_PARAMS;
	}

	@Override
	public byte getResponseType() {
		return RS_TYPE_XML;
	}

	@Override
	public Class<? extends AbstractCDYNERs> getResponseClass() {
		return NotifyPhoneBasicRs.class;
	}

	public String getCallerID() {
		return callerID;
	}

	public void setCallerID(String callerID) {
		this.callerID = callerID;
	}

	public String getCallerIDName() {
		return callerIDName;
	}

	public void setCallerIDName(String callerIDName) {
		this.callerIDName = callerIDName;
	}

	public Integer getVoiceID() {
		return voiceID;
	}

	public void setVoiceID(Integer voiceID) {
		this.voiceID = voiceID;
	}
	
	@Override
	public List<NameValue> params() throws CDYNEException {
		List<NameValue> list = super.params();

		if (this.tryCount != null) {
			list.add(new NameValue("TryCount",this.tryCount));
		}
		if (this.nextTryInSeconds != null) {
			list.add(new NameValue("NextTryInSeconds",this.nextTryInSeconds));
		}
		if (this.utcScheduledDateTime != null) {
			list.add(new NameValue("UTCScheduledDateTime",this.utcScheduledDateTime));
		}
		if (this.ttsRate != null) {
			list.add(new NameValue("TTSrate",this.ttsRate));
		}
		if (this.ttsVolume != null) {
			list.add(new NameValue("TTSvolume",this.ttsVolume));
		}
		if (this.statusChangePostUrl != null) {
			list.add(new NameValue("StatusChangePostUrl",this.statusChangePostUrl));
		}
		if (this.maxCallLength != null) {
			list.add(new NameValue("MaxCallLength",this.maxCallLength));
		}

		return list;
	}
	
	@Override
	public String getServiceName() {
		return SERVICE_NAME;
	}
}
