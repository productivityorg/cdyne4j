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
 * GetResponseCodesRs implements the response side of: http://cdyne.com/api/phone/notify/
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
public class GetResponseCodesRs extends AbstractCDYNERs {
	private static final long serialVersionUID = -5068117437949144484L;

	@XmlRootElement(name="Response")
	public static final class Response implements Serializable {
		private static final long serialVersionUID = 1L;
		
		protected Integer responseCode = null;
		protected String responseText = null;
		
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
	}
	
	@XmlRootElement(name="ArrayOfResponse",namespace="http://ws.cdyne.com/NotifyWS/")
	public static final class ArrayOfResponse implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private static JAXBContext JAXB_CONTEXT = null;

		static {
			try {
				JAXB_CONTEXT = JAXBContext.newInstance(ArrayOfResponse.class);
				
			} catch (JAXBException jaxbe) {
				throw new CDYNERuntimeException(jaxbe);
			}
		}

		protected List<Response> responses = null;
		
		@XmlElement(name="Response")
		public List<Response> getResponses() {
			return this.responses;
		}
		
		public void setResponses(List<Response> responses) {
			this.responses = responses;
		}

		public static ArrayOfResponse parse(Reader reader) throws CDYNEException {
			try {
				Unmarshaller u = JAXB_CONTEXT.createUnmarshaller();
				
				ArrayOfResponse response = (ArrayOfResponse) u.unmarshal(reader);
				return response;
				
			} catch (JAXBException e) {
				throw new CDYNEException(e);
			}
		}
	}	
	
	protected List<Response> responses = null;
	
	public List<Response> getResponses() {
		return this.responses;
	}
	
	@Override
	public boolean isSuccess() {
		return this.responses != null && this.responses.size() > 0;
	}

	@Override
	public String getReferenceID() {
		throw new CDYNERuntimeException("getReferenceID(): not implemented for GetResponseCodes");
	}

	@Override
	public void parse(Reader reader) throws CDYNEException {
		ArrayOfResponse a = ArrayOfResponse.parse(reader);
		
		this.responses = a.getResponses();
	}
}
