package org.productivity.cdyne4j.networkclient;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Properties;

import org.productivity.cdyne4j.CDYNEClient;
import org.productivity.cdyne4j.CDYNEException;
import org.productivity.cdyne4j.CDYNERuntimeException;
import org.productivity.cdyne4j.cdyne.AbstractCDYNERq;
import org.productivity.cdyne4j.cdyne.AbstractCDYNERq.NameValue;
import org.productivity.cdyne4j.cdyne.AbstractCDYNERs;

/**
 * AbstractCDYNENetworkClient specifies how connections to CDYNE.com are managed.
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
public abstract class AbstractCDYNENetworkClient {
	public static final String DEFAULT_CDYNE_PROPERTIES_CLASSPATH = "org/productivity/cdyne4j/config/cdyne.properties";

	public abstract void initialize() throws CDYNEException;

	public abstract Properties getProperties();
	public abstract AbstractCDYNENetworkClientSession open() throws CDYNEException;
	public abstract <T extends AbstractCDYNERs> T send(AbstractCDYNENetworkClientSession session, byte service, AbstractCDYNERq rq, Class<T> responseClass, boolean useHttpFallback, boolean debug) throws CDYNEException;
	public abstract void close(AbstractCDYNENetworkClientSession session) throws CDYNEException;
	public abstract void shutdown();
	
	protected boolean initialized = false;
	
	public boolean isInitialized() {
		return this.initialized;
	}
	
	protected String lookupServiceName(byte service) {
		switch(service) {
			case(CDYNEClient.SMS_NOTIFY): return "sms_notify";
			case(CDYNEClient.PHONE_NOTIFY): return "phone_notify";
			case(CDYNEClient.PHONE_VERIFY): return "phone_verify";
			case(CDYNEClient.IP2GEO): return "ip2geo";
		}
		
		throw new CDYNERuntimeException("lookupServiceName(.): service \"" + service + "\" invalid");
	}

	protected Properties loadDefaultProperties() {
		Properties properties = new Properties();
		
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(DEFAULT_CDYNE_PROPERTIES_CLASSPATH);
		
		if (is == null) {
			throw new CDYNERuntimeException("CDYNEStockNetworkClient(): default cdyne.properties file is missing");			
		}
		
		try {
			properties.load(is);
			
		} catch (IOException ioe) {
			throw new CDYNERuntimeException(ioe);
		}
		
		return properties;
	}

	protected boolean shouldUseHttpFallback(byte service, Properties properties) {
		String value = properties.getProperty(lookupServiceName(service) + "_http_fallback");
		
		return value != null && Boolean.parseBoolean(value);
	}
	
	protected URL createRequestUrl(byte service, Properties properties, AbstractCDYNERq rq, boolean useHttps) throws CDYNEException {
		StringBuilder urlBuilder = new StringBuilder();
		urlBuilder.append(properties.getProperty(lookupServiceName(service) + "_" + (useHttps ? "https" : "http") + "_base_url"));
		urlBuilder.append(rq.getServiceName());

		if (rq.getRequestType() == AbstractCDYNERq.RQ_TYPE_PARAMS) {
			try {
				urlBuilder.append("?");
				
				List<NameValue> list = rq.params();

				for(int i=0; i<list.size(); i++) {
					NameValue nv = list.get(i);
					
					String n = URLEncoder.encode(nv.name,"UTF-8");
					String v = URLEncoder.encode(nv.value,"UTF-8");
					
					urlBuilder.append(n + "=" + v);
					if (i < (list.size()-1)) {
						urlBuilder.append("&");
					}
				}
				
			} catch (Exception e) {
				throw new CDYNEException(e);
			}
		}
		
		try {
			return new URL(urlBuilder.toString());
			
		} catch (MalformedURLException murle) {
			throw new CDYNEException(murle);
		}
	}
}
