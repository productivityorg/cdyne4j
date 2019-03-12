package org.productivity.cdyne4j.cdyne;

import java.io.Serializable;
import java.io.Writer;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;

import org.productivity.cdyne4j.CDYNEException;

/**
 * AbstractCDYNERq specifies how requests are sent to CDYNE.
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
public abstract class AbstractCDYNERq implements Serializable {
	private static final long serialVersionUID = 1062960978147260302L;

	public static final byte RQ_TYPE_NONE = 0;
	public static final byte RQ_TYPE_PARAMS = 1;
	public static final byte RQ_TYPE_XML = 2;

	public static final byte RS_TYPE_PARAMS = 0;
	public static final byte RS_TYPE_XML = 1;

	public static final class NameValue implements Serializable {
		private static final long serialVersionUID = 1L;
		
		public NameValue(String name, String value) {
			this.name = name;
			this.value = value;
		}
		
		public NameValue(String name, int value) {
			this.name = name;
			this.value = Integer.toString(value);
		}
		
		public String name = null;
		public String value = null;
	}

	protected String licenseKey = null;

	public String getLicenseKey() {
		return licenseKey;
	}

	@XmlElement(name="LicenseKey")
	public void setLicenseKey(String licenseKey) {
		this.licenseKey = licenseKey;
	}
	
	public abstract byte getRequestType();
	public abstract byte getResponseType();
	public abstract Class<? extends AbstractCDYNERs> getResponseClass();
	public abstract String getServiceName();
	
	public List<NameValue> params() throws CDYNEException {
		throw new CDYNEException("params(): " + this.getClass().getSimpleName() + " does not implement params");
	}
	
	public void serializeXml(Writer writer) throws CDYNEException {
		throw new CDYNEException("params(): " + this.getClass().getSimpleName() + " does not implement serializeXml");
	}
	
	protected void checkParam(String name, String value) throws CDYNEException {
		if (value == null || value.isEmpty()) {
			throw new CDYNEException("checkParam(..): name \"" + name + "\" is required");
		}
	}

	protected void checkParam(String name, Integer value) throws CDYNEException {
		if (value == null) {
			throw new CDYNEException("checkParam(..): name \"" + name + "\" is required");
		}
	}
	
	protected static void serializeXml(Object object, Writer writer, JAXBContext jaxbContext) throws CDYNEException {
		try {
			Marshaller m = jaxbContext.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			m.marshal(object,writer);
			
		} catch (JAXBException jaxbe) {
			throw new CDYNEException(jaxbe);
		}
	}
}
