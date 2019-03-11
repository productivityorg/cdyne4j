package org.productivity.cdyne4j.cdyne.phoneverify;

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
import org.productivity.cdyne4j.cdyne.phoneverify.CheckPhoneNumberRs.PhoneReturn;

/**
 * CheckPhoneNumbersRs handles the response side of: https://www.cdyne.com/phone-verify/
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
public class CheckPhoneNumbersRs extends AbstractCDYNERs {
	private static final long serialVersionUID = -4557135864381450428L;

	@XmlRootElement(name="ArrayOfPhoneReturn",namespace="http://ws.cdyne.com/PhoneVerify/query")
	public static class ArrayOfPhoneReturn implements Serializable {
		private static final long serialVersionUID = 1L;
		private static JAXBContext JAXB_CONTEXT = null;

		static {
			try {
				JAXB_CONTEXT = JAXBContext.newInstance(ArrayOfPhoneReturn.class);
				
			} catch (JAXBException jaxbe) {
				throw new CDYNERuntimeException(jaxbe);
			}
		}
		
		public static ArrayOfPhoneReturn parse(Reader reader) throws CDYNEException {
			try {
				Unmarshaller u = JAXB_CONTEXT.createUnmarshaller();
				
				ArrayOfPhoneReturn response = (ArrayOfPhoneReturn) u.unmarshal(reader);
				return response;
				
			} catch (JAXBException e) {
				throw new CDYNEException(e);
			}
		}
		
		protected List<PhoneReturn> phoneReturn = null;
		
		@XmlElement(name="PhoneReturn")
		public List<PhoneReturn> getPhoneReturn() {
			return phoneReturn;
		}

		public void setPhoneReturn(List<PhoneReturn> phoneReturn) {
			this.phoneReturn = phoneReturn;
		}

		@Override
		public String toString() {
			if (this.phoneReturn != null && !this.phoneReturn.isEmpty()) {
				StringBuffer sb = new StringBuffer();
				
				for(PhoneReturn pr: this.phoneReturn) {
					sb.append(pr);
					sb.append(System.getProperty("line.separator"));
				}
				
				return sb.toString();
				
			} else {
				return "null/empty";
			}
		}
	}
	
	protected ArrayOfPhoneReturn arrayOfPhoneReturn = null;

	public ArrayOfPhoneReturn getArrayOfPhoneReturn() {
		return arrayOfPhoneReturn;
	}

	public List<PhoneReturn> getPhoneReturns() {
		ArrayOfPhoneReturn a = getArrayOfPhoneReturn();
		
		if (a != null) {
			List<PhoneReturn> r = a.getPhoneReturn();
			
			if (r != null && !r.isEmpty()) {
				return r;
			}
		}
		
		return null;
	}

	@Override
	public void parse(Reader reader) throws CDYNEException {
		this.arrayOfPhoneReturn = ArrayOfPhoneReturn.parse(reader);
	}

	@Override
	public boolean isSuccess() {
		return this.arrayOfPhoneReturn != null && this.arrayOfPhoneReturn.getPhoneReturn() != null && !this.arrayOfPhoneReturn.getPhoneReturn().isEmpty();
	}

	@Override
	public String getReferenceID() {
		throw new CDYNERuntimeException("getReferenceID(): not implemented for CheckPhoneNumbersRs");
	}
}
