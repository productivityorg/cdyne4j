package org.productivity.cdyne4j.cdyne.ip2geo;

import java.util.ArrayList;
import java.util.List;

import org.productivity.cdyne4j.CDYNEException;
import org.productivity.cdyne4j.cdyne.AbstractCDYNERq;
import org.productivity.cdyne4j.cdyne.AbstractCDYNERs;

/**
 * ResolveIPRq implements the request part of: http://cdyne.com/api/ip-location/
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
public class ResolveIPRq extends AbstractCDYNERq {
	private static final long serialVersionUID = -7182066645284337564L;
	
	public static final String SERVICE_NAME = "ResolveIP";
	
	public static final String IP_ADDRESS_PARAM = "IPAddress";
	public static final String LICENSE_KEY_PARAM = "LicenseKey";
	
	protected String ipAddress = null;
	
	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
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
	public List<NameValue> params() throws CDYNEException {
		List<NameValue> list = new ArrayList<>();
		
		checkParam(IP_ADDRESS_PARAM,this.ipAddress); list.add(new NameValue(IP_ADDRESS_PARAM,this.ipAddress)); 
		checkParam(LICENSE_KEY_PARAM,this.licenseKey); list.add(new NameValue(LICENSE_KEY_PARAM,this.licenseKey));
		
		return list;
	}

	@Override
	public Class<? extends AbstractCDYNERs> getResponseClass() {
		return ResolveIPRs.class;
	}

	@Override
	public String getServiceName() {
		return SERVICE_NAME;
	}
}
