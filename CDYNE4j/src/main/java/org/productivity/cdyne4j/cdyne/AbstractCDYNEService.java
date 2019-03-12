package org.productivity.cdyne4j.cdyne;

import java.util.HashMap;
import java.util.Map;

import org.productivity.cdyne4j.CDYNEClient;
import org.productivity.cdyne4j.CDYNEException;
import org.productivity.cdyne4j.CDYNERuntimeException;
import org.productivity.cdyne4j.background.AbstractBackgroundStatusProcessor;
import org.productivity.cdyne4j.networkclient.AbstractCDYNENetworkClient;
import org.productivity.cdyne4j.networkclient.AbstractCDYNENetworkClientSession;

/**
 * AbstractCDYNEService specifies how individual CDYNE services are handled.
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
public abstract class AbstractCDYNEService {
	public static class CDYNEServiceProfiles {
		public static final String DEFAULT_PROFILE_NAME = "default";		

		protected Map<String,AbstractCDYNEService> services = new HashMap<String,AbstractCDYNEService>();
		
		public <T extends AbstractCDYNEService> T getService(String profileName, Class<T> clazz) {
			if (profileName == null || profileName.isEmpty()) {
				throw new CDYNERuntimeException("getService(..): cannot provide null/empty profile");
			}
			
			AbstractCDYNEService service = this.services.get(profileName.toLowerCase());
			
			if (service == null) {
				try {
					service = (T) clazz.newInstance();
					service.setProfileName(profileName);
					this.services.put(profileName.toLowerCase(),service);
					
				} catch (Exception e) {
					throw new CDYNERuntimeException("getService(..): could not create a new instance of class \"" + clazz.getName() + "\" for profileName \"" + profileName + "\"");
				}
			}
			
			@SuppressWarnings("unchecked")
			T s = (T) service;
			
			return s;
		}
		
		public void shutdown() {
			for(String key: this.services.keySet()) {
				this.services.get(key).shutdown();
			}
		}
	}
	
	protected String profileName = null;
	protected String licenseKey = null;
	
	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}
	
	public void setLicenseKey(String licenseKey) {
		if (licenseKey == null || licenseKey.isEmpty()) {
			throw new CDYNERuntimeException("setLicenseKey(.): cannot provide null/empty licenseKey");
		}
		
		this.licenseKey = licenseKey;
	}
	
	protected abstract byte getService();
	
	protected AbstractCDYNENetworkClient getNetworkClient() {
		try {
			return CDYNEClient.getInstance().getNetworkClient();
			
		} catch (CDYNEException ce) {
			throw new CDYNERuntimeException(ce);
		}
	}
	
	protected boolean isDebug() {
		return CDYNEClient.getInstance().isDebug();
	}
	
	protected void applyLicenseKey(AbstractCDYNERq rq) throws CDYNEException {
		if (this.licenseKey == null) {
			throw new CDYNEException("applyLicenseKey(...): no license key available in profile \"" + this.profileName + "\" for " + CDYNEClient.SERVICES[getService()]);
		}
		
		rq.setLicenseKey(this.licenseKey);
	}
	
	public abstract AbstractBackgroundStatusProcessor getBackgroundStatusProcessor();
	
	protected <T extends AbstractCDYNERs> T send(AbstractCDYNERq rq, Class<T> clazz) throws CDYNEException {
		applyLicenseKey(rq);
		
		T rs = null;
		
		AbstractCDYNENetworkClient networkClient = getNetworkClient();
		AbstractCDYNENetworkClientSession networkSession = null;
		
		try {
			networkSession = networkClient.open();
			rs = networkClient.send(networkSession,getService(),rq,clazz,true,isDebug());
			
		} finally {
			if (networkSession != null) {
				networkClient.close(networkSession);
			}
		}
		
		return (T) rs;
	}	
	
	public void shutdown() {
		try {
			AbstractBackgroundStatusProcessor processor = getBackgroundStatusProcessor();
			
			if (processor != null) {
				processor.shutdown();
			}
			
		} catch (CDYNERuntimeException cre) {
			//
		}
	}
}
