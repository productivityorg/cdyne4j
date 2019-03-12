package org.productivity.cdyne4j.cdyne.phonenotify;

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
 * GetVoicesRs implements the response side of: http://cdyne.com/api/phone/notify/
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
public class GetVoicesRs extends AbstractCDYNERs {
	private static final long serialVersionUID = 4930854869051423246L;

	@XmlRootElement(name="Voice")
	public static final class Voice implements Serializable {
		private static final long serialVersionUID = 1L;
		
		protected Integer voiceID = null;
		protected String voiceName = null;
		protected String voiceGender = null;
		protected Integer voiceAge = null;
		protected String voiceLanguage = null;
		protected String voiceSummary = null;

		@XmlElement(name="VoiceID")
		public Integer getVoiceID() {
			return voiceID;
		}

		public void setVoiceID(Integer voiceID) {
			this.voiceID = voiceID;
		}

		@XmlElement(name="VoiceName")
		public String getVoiceName() {
			return voiceName;
		}

		public void setVoiceName(String voiceName) {
			this.voiceName = voiceName;
		}

		@XmlElement(name="VoiceGender")
		public String getVoiceGender() {
			return voiceGender;
		}

		public void setVoiceGender(String voiceGender) {
			this.voiceGender = voiceGender;
		}

		@XmlElement(name="VoiceAge")
		public Integer getVoiceAge() {
			return voiceAge;
		}

		public void setVoiceAge(Integer voiceAge) {
			this.voiceAge = voiceAge;
		}

		@XmlElement(name="VoiceLanguage")
		public String getVoiceLanguage() {
			return voiceLanguage;
		}

		public void setVoiceLanguage(String voiceLanguage) {
			this.voiceLanguage = voiceLanguage;
		}

		@XmlElement(name="VoiceSummary")
		public String getVoiceSummary() {
			return voiceSummary;
		}

		public void setVoiceSummary(String voiceSummary) {
			this.voiceSummary = voiceSummary;
		}
	}
	
	@XmlRootElement(name="ArrayOfVoice",namespace="http://ws.cdyne.com/NotifyWS/")
	public static final class ArrayOfVoice implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private static JAXBContext JAXB_CONTEXT = null;

		static {
			try {
				JAXB_CONTEXT = JAXBContext.newInstance(ArrayOfVoice.class);
				
			} catch (JAXBException jaxbe) {
				throw new CDYNERuntimeException(jaxbe);
			}
		}
		
		protected List<Voice> voices = null;
		
		@XmlElement(name="Voice")
		public List<Voice> getVoices() {
			return this.voices;
		}
		
		public void setVoices(List<Voice> voices) {
			this.voices = voices;
		}

		public static ArrayOfVoice parse(Reader reader) throws CDYNEException {
			try {
				Unmarshaller u = JAXB_CONTEXT.createUnmarshaller();
				
				ArrayOfVoice response = (ArrayOfVoice) u.unmarshal(reader);
				return response;
				
			} catch (JAXBException e) {
				throw new CDYNEException(e);
			}
		}
	}	
	
	protected List<Voice> voices = null;
	
	public List<Voice> getVoices() {
		return this.voices;
	}
	
	@Override
	public boolean isSuccess() {
		return this.voices != null && this.voices.size() > 0;
	}

	@Override
	public String getReferenceID() {
		throw new CDYNERuntimeException("getReferenceID(): not implemented for getVoices");
	}

	@Override
	public void parse(Reader reader) throws CDYNEException {
		ArrayOfVoice v = ArrayOfVoice.parse(reader);
		this.voices = v.getVoices();
	}
}
