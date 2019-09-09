package org.productivity.cdyne4j.test.networkclient.mock;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.Properties;

import org.productivity.cdyne4j.CDYNEException;
import org.productivity.cdyne4j.CDYNERuntimeException;
import org.productivity.cdyne4j.cdyne.AbstractCDYNERq;
import org.productivity.cdyne4j.cdyne.AbstractCDYNERs;
import org.productivity.cdyne4j.cdyne.ip2geo.ResolveIPRq;
import org.productivity.cdyne4j.cdyne.phonenotify.GetResponseCodesRq;
import org.productivity.cdyne4j.cdyne.phonenotify.GetVoicesRq;
import org.productivity.cdyne4j.cdyne.phonenotify.NotifyPhoneBasicRq;
import org.productivity.cdyne4j.cdyne.phonenotify.NotifyPhoneBasicWithTransferRq;
import org.productivity.cdyne4j.cdyne.phonenotify.NotifyPhoneBasicWithTryCountRq;
import org.productivity.cdyne4j.cdyne.phoneverify.CheckPhoneNumberRq;
import org.productivity.cdyne4j.cdyne.phoneverify.CheckPhoneNumbersRq;
import org.productivity.cdyne4j.cdyne.smsnotify.GetMessageStatusByReferenceIDRq;
import org.productivity.cdyne4j.cdyne.smsnotify.GetMessageStatusRq;
import org.productivity.cdyne4j.cdyne.smsnotify.GetUnreadIncomingMessagesRq;
import org.productivity.cdyne4j.cdyne.smsnotify.SimpleSMSSendRq;
import org.productivity.cdyne4j.cdyne.smsnotify.SimpleSMSSendWithPostbackRq;
import org.productivity.cdyne4j.cdyne.smsnotify.advanced.AdvancedSMSSendRq;
import org.productivity.cdyne4j.networkclient.AbstractCDYNENetworkClient;
import org.productivity.cdyne4j.networkclient.AbstractCDYNENetworkClientSession;

/**
 * MockCDYNENetworkClient implements a mock testing network client for CDYNE4j.
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
public class MockCDYNENetworkClient extends AbstractCDYNENetworkClient {
	private static String BASE = "org/productivity/cdyne4j/test/networkclient/mock/data";
	
	private class Session extends AbstractCDYNENetworkClientSession {
		private static final long serialVersionUID = 1L;
	}

	public static String read(String file) {
		String filePath = BASE + "/" + file;
		
		InputStream is = MockCDYNENetworkClient.class.getClassLoader().getResourceAsStream(filePath);
		
		if (is != null) {
			byte[] buffer = new byte[1024];
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			
			try {
				int len = is.read(buffer);
				while (len > 0) {
					baos.write(buffer,0,len);
					
					len = is.read(buffer);
				}
				return baos.toString("UTF-8");
				
			} catch (IOException ioe) {
				throw new CDYNERuntimeException(ioe);
				
			} finally {
				try { is.close(); } catch (Exception e) { throw new CDYNERuntimeException(e); }
			}
			
		} else {
			throw new CDYNERuntimeException("No data found at \"" + filePath + "\"");
		}
	}

	@Override
	public void initialize() throws CDYNEException {
		this.initialized = true;
	}
	
	@Override
	public Properties getProperties() {
		return loadDefaultProperties();
	}

	@Override
	public AbstractCDYNENetworkClientSession open() throws CDYNEException {
		return new Session();
	}

	@Override
	public <T extends AbstractCDYNERs> T send(AbstractCDYNENetworkClientSession session, byte service, AbstractCDYNERq rq, Class<T> responseClass, boolean useHttpFallback, boolean debug) throws CDYNEException {
		Properties properties = getProperties();
		
		createRequestUrl(service,properties,rq,true);
		shouldUseHttpFallback(service,properties);
		
		T response = null;
		
		try {
			response = responseClass.newInstance();
			
		} catch (Exception e) {
			throw new CDYNERuntimeException(e);
		}
		
		if (rq instanceof GetVoicesRq) {
			String xml = read("GetVoicesRs.xml");
			response.parse(new StringReader(xml));
			return response;
			
		} else if (rq instanceof ResolveIPRq) {
			String xml = read("ResolveIPRs.xml");
			response.parse(new StringReader(xml));
			return response;

		} else if (rq instanceof GetResponseCodesRq) {
			String xml = read("GetResponseCodesRs.xml");
			response.parse(new StringReader(xml));
			return response;
			
		} else if (rq instanceof CheckPhoneNumberRq) {
			String xml = read("CheckPhoneNumberRs.xml");
			response.parse(new StringReader(xml));
			return response;
			
		} else if (rq instanceof CheckPhoneNumbersRq) {
			String xml = read("CheckPhoneNumbersRs.xml");
			response.parse(new StringReader(xml));
			return response;
			
		} else if (rq instanceof SimpleSMSSendRq || rq instanceof SimpleSMSSendWithPostbackRq) {
			String xml = read("SimpleSMSSendRs.xml");
			response.parse(new StringReader(xml));
			return response;
			
		} else if (rq instanceof GetMessageStatusRq || rq instanceof GetMessageStatusByReferenceIDRq) {
			String xml = read("GetMessageStatusRs.xml");
			response.parse(new StringReader(xml));
			return response;
			
		} else if (rq instanceof GetUnreadIncomingMessagesRq) {
			String xml = read("GetUnreadIncomingMessagesRs.xml");
			response.parse(new StringReader(xml));
			return response;
			
		} else if (rq instanceof AdvancedSMSSendRq) {
			String xml = read("AdvancedSMSSendRs.xml");
			response.parse(new StringReader(xml));
			return response;
			
		} else if (rq instanceof NotifyPhoneBasicRq || rq instanceof NotifyPhoneBasicWithTransferRq || rq instanceof NotifyPhoneBasicWithTryCountRq) {
			String xml = read("NotifyPhoneBasicRs.xml");
			response.parse(new StringReader(xml));
			return response;
		}
		
		throw new CDYNERuntimeException("Unimplemented test case");
	}

	@Override
	public void close(AbstractCDYNENetworkClientSession session) throws CDYNEException {
		//
	}

	@Override
	public void shutdown() {
		//
	}
}
