package org.productivity.cdyne4j.cdyne.ip2geo;

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
 * ResolveIPRs implements the response part of: http://cdyne.com/api/ip-location/
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
public class ResolveIPRs extends AbstractCDYNERs {
	private static final long serialVersionUID = -7571467487758026483L;
	
	@XmlRootElement(name="IPInformation",namespace="http://ws.cdyne.com/")
	public static class IPInformation implements Serializable {
		private static final long serialVersionUID = 1L;
		private static JAXBContext JAXB_CONTEXT = null;
		
		static {
			try {
				JAXB_CONTEXT = JAXBContext.newInstance(IPInformation.class);
				
			} catch (JAXBException jaxbe) {
				throw new CDYNERuntimeException(jaxbe);
			}
		}
		
		public static IPInformation parse(Reader reader) throws CDYNEException {
			try {
				Unmarshaller u = JAXB_CONTEXT.createUnmarshaller();
				
				IPInformation response = (IPInformation) u.unmarshal(reader);
				return response;
				
			} catch (JAXBException e) {
				throw new CDYNEException(e);
			}
		}
		
		protected String city = null;
		protected String stateProvince = null;
		protected String country = null;
		protected String organization = null;
		protected String latitude = null;
		protected String longitude = null;
		protected String areaCode = null;
		protected String timeZone = null;
		protected Boolean hasDaylightSavings = null;
		protected Integer certainty = null;
		protected String regionName = null;
		protected String countryCode = null;
		
		@XmlElement(name="City")
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = nullCheck(city);
		}
		@XmlElement(name="StateProvince")
		public String getStateProvince() {
			return stateProvince;
		}
		public void setStateProvince(String stateProvince) {
			this.stateProvince = nullCheck(stateProvince);
		}
		@XmlElement(name="Country")
		public String getCountry() {
			return country;
		}
		public void setCountry(String country) {
			this.country = nullCheck(country);
		}
		@XmlElement(name="Organization")
		public String getOrganization() {
			return organization;
		}
		public void setOrganization(String organization) {
			this.organization = nullCheck(organization);
		}
		@XmlElement(name="Latitude")
		public String getLatitude() {
			return latitude;
		}
		public void setLatitude(String latitude) {
			this.latitude = nullCheck(latitude);
		}
		@XmlElement(name="Longitude")
		public String getLongitude() {
			return longitude;
		}
		public void setLongitude(String longitude) {
			this.longitude = nullCheck(longitude);
		}
		@XmlElement(name="AreaCode")
		public String getAreaCode() {
			return areaCode;
		}
		public void setAreaCode(String areaCode) {
			this.areaCode = nullCheck(areaCode);
		}
		@XmlElement(name="TimeZone")
		public String getTimeZone() {
			return timeZone;
		}
		public void setTimeZone(String timeZone) {
			this.timeZone = nullCheck(timeZone);
		}
		@XmlElement(name="HasDaylightSavings")
		public Boolean getHasDaylightSavings() {
			return hasDaylightSavings;
		}
		public boolean isHasDaylightSavings() {
			return getHasDaylightSavings() != null && getHasDaylightSavings().booleanValue();
		}
		public void setHasDaylightSavings(Boolean hasDaylightSavings) {
			this.hasDaylightSavings = hasDaylightSavings;
		}
		@XmlElement(name="Certainty")
		public Integer getCertainty() {
			return certainty;
		}
		public void setCertainty(Integer certainty) {
			this.certainty = certainty;
		}
		@XmlElement(name="RegionName")
		public String getRegionName() {
			return regionName;
		}
		public void setRegionName(String regionName) {
			this.regionName = nullCheck(regionName);
		}
		@XmlElement(name="CountryCode")
		public String getCountryCode() {
			return countryCode;
		}
		public void setCountryCode(String countryCode) {
			this.countryCode = nullCheck(countryCode);
		}
		
		protected String safe(String in) {
			return in != null ? in : "";
		}
		
		@Override
		public String toString() {
			return
				"city=[" + safe(this.getCity()) + "] " +
				"stateProvince=[" + safe(this.getStateProvince()) + "] " +
				"country=[" + safe(this.getCountry()) + "] " +
				"organization=[" + safe(this.getOrganization()) + "] " +
				"latitude=[" + safe(this.getLatitude()) + "] " +
				"longitude=[" + safe(this.getLongitude()) + "] " +
				"areaCode=[" + safe(this.getAreaCode()) + "] " +
				"timeZone=[" + safe(this.getTimeZone()) + "] " +
				"hasDaylightSavings=[" + (this.isHasDaylightSavings() ? "true" : "false") + "] " +
				"certainty=[" + (this.getCertainty() != null ? this.getCertainty() : "unknonwn" )  + "] " +
				"regionName=[" + safe(this.getRegionName()) + "] " +
				"countryCode=[" + safe(this.getCountryCode()) + "] ";
		}
	}
	
	protected IPInformation ipInformation = null;

	public IPInformation getIpInformation() {
		return ipInformation;
	}

	@Override
	public void parse(Reader reader) throws CDYNEException {
		this.ipInformation = IPInformation.parse(reader);
	}

	@Override
	public boolean isSuccess() {
		return ipInformation != null;
	}

	@Override
	public String getReferenceID() {
		throw new CDYNERuntimeException("getReferenceID(): not implemented for ResolveIPRs");
	}
}
