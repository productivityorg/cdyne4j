package org.productivity.cdyne4j;

import org.productivity.cdyne4j.cdyne.AbstractCDYNEService.CDYNEServiceProfiles;
import org.productivity.cdyne4j.cdyne.ip2geo.IP2GeoService;
import org.productivity.cdyne4j.cdyne.phonenotify.PhoneNotifyService;
import org.productivity.cdyne4j.cdyne.phoneverify.PhoneVerifyService;
import org.productivity.cdyne4j.cdyne.smsnotify.SMSNotifyService;
import org.productivity.cdyne4j.networkclient.AbstractCDYNENetworkClient;
import org.productivity.cdyne4j.networkclient.stock.CDYNEStockNetworkClient;

/**
 * CDYNEClient is the starting point/singleton class for the CDYNE4j Java implementation.
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
public class CDYNEClient {
	public final static byte DEMOGRAPHICS					= 0;
	public final static byte PHONE_VERIFY					= 1;
	public final static byte POSTAL_ADDRESS_VERIFICATION	= 2;
	public final static byte IP2GEO							= 3;
	public final static byte PHONE_NOTIFY					= 4;
	public final static byte SMS_NOTIFY						= 5;
	
	public final static String[] SERVICES = new String[] {
			"DEMOGRAPHICS", "PHONE_VERIFICATION", "POSTAL_ADDRESS_VERIFICATION", 
			"IP2GEO", "PHONE_NOTIFY", "SMS_NOTIFY"
	};

	private final static class CDYNEClientHolder {
		private final static CDYNEClient INSTANCE = new CDYNEClient();
	}
	
	public static CDYNEClient getInstance() {
		return CDYNEClientHolder.INSTANCE;
	}
	
	private AbstractCDYNENetworkClient networkClient = null;
	private boolean debug = false;
	
	private final CDYNEServiceProfiles[] serviceProfiles = new CDYNEServiceProfiles[SERVICES.length];
	
	private CDYNEClient() {
		for(int i=0; i<SERVICES.length; i++) {
			serviceProfiles[i] = new CDYNEServiceProfiles();
		}
	}
		
	/**
	 * @return Returns the network client currently in use.
	 * @throws CDYNEException
	 */
	public AbstractCDYNENetworkClient getNetworkClient() throws CDYNEException {
		if (this.networkClient == null) {
			this.networkClient = new CDYNEStockNetworkClient();
		}
		
		if (!this.networkClient.isInitialized()) {
			synchronized(this.networkClient) {
				if (!this.networkClient.isInitialized()) {
					this.networkClient.initialize();
				}
			}
		}
		
		return this.networkClient;
	}
	
	/**
	 * Allows consumer to override the default network client implementation.
	 * 
	 * @param networkClient - An implementation of AbstractCDYNENetworkClient.
	 */
	public void setNetworkClient(AbstractCDYNENetworkClient networkClient) {
		if (networkClient == null) {
			throw new CDYNERuntimeException("setNetworkClient(.): provided client cannot be null");
		}
		
		this.networkClient = networkClient;
	}
	
	/**
	 * @return Returns whether this client is in debug mode.
	 */
	public boolean isDebug() {
		return this.debug;
	}
	
	/**
	 * Setting this value to "true" enables additional debugging within the client.
	 * 
	 * @param debug - debug mode
	 */
	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	/**
	 * @param profileName - a consumer-specified profileName.
	 * @return Returns a specific instance of the PhoneNotifyService functionality.
	 */
	public PhoneNotifyService getPhoneNotify(String profileName) {
		final CDYNEServiceProfiles serviceProfiles = this.serviceProfiles[PHONE_NOTIFY];
		return serviceProfiles.getService(profileName,PhoneNotifyService.class);
	}
	
	/**
	 * @return Returns the default instance of the PhoneNotifyService functionality.
	 */
	public PhoneNotifyService getPhoneNotify() {
		return getPhoneNotify(CDYNEServiceProfiles.DEFAULT_PROFILE_NAME);
	}

	/**
	 * @param profileName - a consumer-specified profileName.
	 * @return Returns a specific instance of the SMSNotifyService functionality.
	 */
	public SMSNotifyService getSMSNotify(String profileName) {
		final CDYNEServiceProfiles serviceProfiles = this.serviceProfiles[SMS_NOTIFY];
		return serviceProfiles.getService(CDYNEServiceProfiles.DEFAULT_PROFILE_NAME,SMSNotifyService.class);
	}

	/**
	 * @return Returns the default instance of the SMSNotifyService functionality.
	 */
	public SMSNotifyService getSMSNotify() {
		return getSMSNotify(CDYNEServiceProfiles.DEFAULT_PROFILE_NAME);
	}

	/**
	 * @param profileName - a consumer-specified profileName.
	 * @return Returns a specific instance of the PhoneVerifyService functionality.
	 */
	public PhoneVerifyService getPhoneVerify(String profileName) {
		final CDYNEServiceProfiles serviceProfiles = this.serviceProfiles[PHONE_VERIFY];
		return serviceProfiles.getService(CDYNEServiceProfiles.DEFAULT_PROFILE_NAME,PhoneVerifyService.class);
	}

	/**
	 * @return Returns the default instance of the PhoneVerifyService functionality.
	 */
	public PhoneVerifyService getPhoneVerify() {
		return getPhoneVerify(CDYNEServiceProfiles.DEFAULT_PROFILE_NAME);
	}

	/**
	 * @param profileName - a consumer-specified profileName.
	 * @return Returns a specific instance of the IP2Geo functionality.
	 */
	public IP2GeoService getIP2Geo(String profileName) {
		final CDYNEServiceProfiles serviceProfiles = this.serviceProfiles[IP2GEO];
		return serviceProfiles.getService(CDYNEServiceProfiles.DEFAULT_PROFILE_NAME,IP2GeoService.class);
	}

	/**
	 * @return Returns the default instance of the IP2Geo functionality.
	 */
	public IP2GeoService getIP2Geo() {
		return getIP2Geo(CDYNEServiceProfiles.DEFAULT_PROFILE_NAME);
	}

	/**
	 * Gracefully shuts down the CDYNE4j service by sending the network client a shutdown() call and then
	 * shutting down all of the individual services.
	 */
	public void shutdown() {
		if (this.networkClient != null) {
			try {
				this.networkClient.shutdown();
				
			} catch (Exception e) {
				//
			}
		}
		
		for(CDYNEServiceProfiles serviceProfile: this.serviceProfiles) {
			serviceProfile.shutdown();
		}
	}
}
