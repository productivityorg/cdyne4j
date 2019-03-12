package org.productivity.cdyne4j.cdyne.phoneverify;

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
 * CheckPhoneNumberRs handles the response side of: https://www.cdyne.com/phone-verify/
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
public class CheckPhoneNumberRs extends AbstractCDYNERs {
	private static final long serialVersionUID = -4557135864381450428L;

	@XmlRootElement(name="PhoneReturn",namespace="http://ws.cdyne.com/PhoneVerify/query")
	public static class PhoneReturn implements Serializable {
		private static final long serialVersionUID = 1L;
		private static JAXBContext JAXB_CONTEXT = null;

		static {
			try {
				JAXB_CONTEXT = JAXBContext.newInstance(PhoneReturn.class);
				
			} catch (JAXBException jaxbe) {
				throw new CDYNERuntimeException(jaxbe);
			}
		}
		
		protected String company = null;
		protected boolean valid = false;
		protected String use = null;
		protected String state = null;
		protected String rc = null;
		protected String ocn = null;
		protected String originalNumber = null;
		protected String cleanNumber = null;
		protected String switchName = null;
		protected String switchType = null;
		protected String country = null;
		protected String clli = null;
		protected String prefixType = null;
		protected String lata = null;
		protected String sms = null;
		protected String email = null;
		protected String assignDate = null;
		protected String telecomCity = null;
		protected String telecomCounty = null;
		protected String telecomState = null;
		protected String telecomZip = null;
		protected String timeZone = null;
		protected String latitude = null;
		protected String longitude = null;
		protected boolean wireless = false;
		protected String lrn = null;

		@XmlElement(name="Company")
		public String getCompany() {
			return company;
		}

		public void setCompany(String company) {
			this.company = nullCheck(company);
		}
		
		@XmlElement(name="Valid")
		public boolean isValid() {
			return valid;
		}

		public void setValid(boolean valid) {
			this.valid = valid;
		}

		@XmlElement(name="Use")
		public String getUse() {
			return use;
		}

		public void setUse(String use) {
			this.use = nullCheck(use);
		}

		@XmlElement(name="State")
		public String getState() {
			return state;
		}

		public void setState(String state) {
			this.state = nullCheck(state);
		}

		@XmlElement(name="RC")
		public String getRc() {
			return rc;
		}

		public void setRc(String rc) {
			this.rc = nullCheck(rc);
		}

		@XmlElement(name="OCN")
		public String getOcn() {
			return ocn;
		}

		public void setOcn(String ocn) {
			this.ocn = nullCheck(ocn);
		}

		@XmlElement(name="OriginalNumber")
		public String getOriginalNumber() {
			return originalNumber;
		}

		public void setOriginalNumber(String originalNumber) {
			this.originalNumber = nullCheck(originalNumber);
		}

		@XmlElement(name="CleanNumber")
		public String getCleanNumber() {
			return cleanNumber;
		}

		public void setCleanNumber(String cleanNumber) {
			this.cleanNumber = nullCheck(cleanNumber);
		}

		@XmlElement(name="SwitchName")
		public String getSwitchName() {
			return switchName;
		}

		public void setSwitchName(String switchName) {
			this.switchName = nullCheck(switchName);
		}

		@XmlElement(name="SwitchType")
		public String getSwitchType() {
			return switchType;
		}

		public void setSwitchType(String switchType) {
			this.switchType = nullCheck(switchType);
		}

		@XmlElement(name="Country")
		public String getCountry() {
			return country;
		}

		public void setCountry(String country) {
			this.country = nullCheck(country);
		}

		@XmlElement(name="CLLI")
		public String getClli() {
			return clli;
		}

		public void setClli(String clli) {
			this.clli = nullCheck(clli);
		}

		@XmlElement(name="PrefixType")
		public String getPrefixType() {
			return prefixType;
		}

		public void setPrefixType(String prefixType) {
			this.prefixType = nullCheck(prefixType);
		}

		@XmlElement(name="LATA")
		public String getLata() {
			return lata;
		}

		public void setLata(String lata) {
			this.lata = nullCheck(lata);
		}

		@XmlElement(name="sms")
		public String getSms() {
			return sms;
		}

		public void setSms(String sms) {
			this.sms = nullCheck(sms);
		}

		@XmlElement(name="Email")
		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = nullCheck(email);
		}

		@XmlElement(name="AssignDate")
		public String getAssignDate() {
			return assignDate;
		}

		public void setAssignDate(String assignDate) {
			this.assignDate = nullCheck(assignDate);
		}

		@XmlElement(name="TelecomCity")
		public String getTelecomCity() {
			return telecomCity;
		}

		public void setTelecomCity(String telecomCity) {
			this.telecomCity = nullCheck(telecomCity);
		}

		@XmlElement(name="TelecomCounty")
		public String getTelecomCounty() {
			return telecomCounty;
		}

		public void setTelecomCounty(String telecomCounty) {
			this.telecomCounty = nullCheck(telecomCounty);
		}

		@XmlElement(name="TelecomState")
		public String getTelecomState() {
			return telecomState;
		}

		public void setTelecomState(String telecomState) {
			this.telecomState = nullCheck(telecomState);
		}

		@XmlElement(name="TelecomZip")
		public String getTelecomZip() {
			return telecomZip;
		}

		public void setTelecomZip(String telecomZip) {
			this.telecomZip = nullCheck(telecomZip);
		}

		@XmlElement(name="TimeZone")
		public String getTimeZone() {
			return timeZone;
		}

		public void setTimeZone(String timeZone) {
			this.timeZone = nullCheck(timeZone);
		}

		@XmlElement(name="Lat")
		public String getLatitude() {
			return latitude;
		}

		public void setLatitude(String latitude) {
			this.latitude = nullCheck(latitude);
		}

		@XmlElement(name="Long")
		public String getLongitude() {
			return longitude;
		}

		public void setLongitude(String longitude) {
			this.longitude = nullCheck(longitude);
		}

		@XmlElement(name="Wireless")
		public boolean isWireless() {
			return wireless;
		}

		public void setWireless(boolean wireless) {
			this.wireless = wireless;
		}

		@XmlElement(name="LRN")
		public String getLrn() {
			return lrn;
		}

		public void setLrn(String lrn) {
			this.lrn = nullCheck(lrn);
		}

		public static PhoneReturn parse(Reader reader) throws CDYNEException {
			try {
				Unmarshaller u = JAXB_CONTEXT.createUnmarshaller();
				
				PhoneReturn response = (PhoneReturn) u.unmarshal(reader);
				return response;
				
			} catch (JAXBException e) {
				throw new CDYNEException(e);
			}
		}
		
		protected String safe(String in) {
			return in != null ? in : "";
		}
		
		@Override
		public String toString() {
			return
				"company=[" + safe(this.getCompany()) + "] " +
				"valid=[" + (this.isValid() ? "true" : "false") + "] " +
				"use=[" + safe(this.getUse()) + "] " +
				"state=[" + safe(this.getState()) + "] " +
				"rc=[" + safe(this.getRc()) + "] " +
				"ocn=[" + safe(this.getOcn()) + "] " +
				"originalNumber=[" + safe(this.getOriginalNumber()) + "] " +
				"cleanNumber=[" + safe(this.getCleanNumber()) + "] " +
				"switchName=[" + safe(this.getSwitchName()) + "] " +
				"switchType=[" + safe(this.getSwitchType()) + "] " +
				"country=[" + safe(this.getCountry()) + "] " +
				"clli=[" + safe(this.getClli()) + "] " +
				"prefixType=[" + safe(this.getPrefixType()) + "] " +
				"lata=[" + safe(this.getLata()) + "] " +
				"sms=[" + safe(this.getSms()) + "] " +
				"email=[" + safe(this.getEmail()) + "] " +
				"assignDate=[" + safe(this.getAssignDate()) + "] " +
				"telecomCity=[" + safe(this.getTelecomCity()) + "] " +
				"telecomCounty=[" + safe(this.getTelecomCounty()) + "] " +
				"telecomState=[" + safe(this.getTelecomState()) + "] " +
				"telecomZip=[" + safe(this.getTelecomZip()) + "] " +
				"timeZone=[" + safe(this.getTimeZone()) + "] " +
				"latitude=[" + safe(this.getLatitude()) + "] " +
				"longitude=[" + safe(this.getLongitude()) + "] " +
				"wireless=[" + (this.isWireless() ? "true" : "false") + "] " +
				"lrn=[" + safe(this.getLrn()) + "]";
		}

		public static String getTabDelimitedHeaders() {
			return
				"Company\t" +
				"Valid\t" +
				"Use\t" +
				"State\t" +
				"RC\t" +
				"OCN\t" +
				"Original Number\t" +
				"Clean Number\t" +
				"Switch Name\t" +
				"Switch Type\t" +
				"Country\t" +
				"CLLI\t" +
				"Prefix Type\t" +
				"LATA\t" +
				"SMS\t" +
				"E-Mail\t" +
				"Assign Date\t" +
				"Telecom City\t" +
				"Telecom County\t" +
				"Telecom State\t" +
				"Telecom ZIP\t" +
				"Time Zone\t" +
				"Latitude\t" +
				"Longitude\t" +
				"Wireless\t" +
				"LRN";
		}

		public String toTabDelimited() {
			return
				safe(this.getCompany()) + "\t" +
				(this.isValid() ? "true" : "false") + "\t" +
				safe(this.getUse()) + "\t" +
				safe(this.getState()) + "\t" +
				safe(this.getRc()) + "\t" +
				safe(this.getOcn()) + "\t" +
				safe(this.getOriginalNumber()) + "\t" +
				safe(this.getCleanNumber()) + "\t" +
				safe(this.getSwitchName()) + "\t" +
				safe(this.getSwitchType()) + "\t" +
				safe(this.getCountry()) + "\t" +
				safe(this.getClli()) + "\t" +
				safe(this.getPrefixType()) + "\t" +
				safe(this.getLata()) + "\t" +
				safe(this.getSms()) + "\t" +
				safe(this.getEmail()) + "\t" +
				safe(this.getAssignDate()) + "\t" +
				safe(this.getTelecomCity()) + "\t" +
				safe(this.getTelecomCounty()) + "\t" +
				safe(this.getTelecomState()) + "\t" +
				safe(this.getTelecomZip()) + "\t" +
				safe(this.getTimeZone()) + "\t" +
				safe(this.getLatitude()) + "\t" +
				safe(this.getLongitude()) + "\t" +
				(this.isWireless() ? "true" : "false") + "\t" +
				safe(this.getLrn());
		}
	}
	
	protected PhoneReturn phoneReturn = null;

	public PhoneReturn getPhoneReturn() {
		return phoneReturn;
	}

	@Override
	public void parse(Reader reader) throws CDYNEException {
		this.phoneReturn = PhoneReturn.parse(reader);
	}

	@Override
	public boolean isSuccess() {
		return this.phoneReturn != null;
	}

	@Override
	public String getReferenceID() {
		throw new CDYNERuntimeException("getReferenceID(): not implemented for CheckPhoneNumberRs");
	}
}
