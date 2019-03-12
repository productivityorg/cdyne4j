package org.productivity.cdyne4j.networkclient.stock;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.URL;
import java.util.Properties;

import org.productivity.cdyne4j.CDYNEException;
import org.productivity.cdyne4j.cdyne.AbstractCDYNERq;
import org.productivity.cdyne4j.cdyne.AbstractCDYNERs;
import org.productivity.cdyne4j.networkclient.AbstractCDYNENetworkClient;
import org.productivity.cdyne4j.networkclient.AbstractCDYNENetworkClientSession;

/**
 * CDYNEStockNetworkClient implements the stock Oracle(tm) Java HTTP client.
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
public class CDYNEStockNetworkClient extends AbstractCDYNENetworkClient {
	public static final int RESPONSE_BUFFER_SIZE = 1024;

	private class ProxyAuthenticator extends Authenticator {
		private final String verifyHost;
		private final int verifyPort;
		private final String proxyUser;
		private final String proxyPass;
		
		public ProxyAuthenticator(final String verifyHost, final int verifyPort, final String proxyUser, final String proxyPass) {
			super();
			
			this.verifyHost = verifyHost;
			this.verifyPort = verifyPort;
			this.proxyUser = proxyUser;
			this.proxyPass = proxyPass;
		}
		
		@Override
		protected PasswordAuthentication getPasswordAuthentication() {
			if ("PROXY".equals(this.getRequestorType().name()) && this.verifyHost.equals(this.getRequestingHost()) && this.verifyPort == this.getRequestingPort()) {
				return new PasswordAuthentication(this.proxyUser,this.proxyPass.toCharArray());
				
			} else {
				return super.getPasswordAuthentication();
			}
		}	
	}
	
	protected Properties properties = null;
	protected boolean debug = false;
	
	protected InetSocketAddress httpProxy = null;

	public CDYNEStockNetworkClient() {
		this.properties = loadDefaultProperties();
	}
	
	public CDYNEStockNetworkClient(Properties properties) {
		this.properties = properties;
	}
	
	@Override
	public Properties getProperties() {
		return this.properties;
	}
	
	@Override
	public void initialize() throws CDYNEException {
		if (this.properties.containsKey("http_proxy")) {
			URL httpProxyUrl = null;
			
			try {
				httpProxyUrl = new URL((String) this.properties.get("http_proxy"));
				
			} catch (MalformedURLException murle) {
				throw new CDYNEException(murle);
			}
			
			this.httpProxy = new InetSocketAddress(httpProxyUrl.getHost(),httpProxyUrl.getPort());

			String proxyUser = (String) this.properties.get("http_proxy_user");
			String proxyPass = (String) this.properties.get("http_proxy_pass");

			if (proxyUser != null && !proxyUser.isEmpty() && proxyPass != null && !proxyPass.isEmpty()) {
				Authenticator.setDefault(
					new ProxyAuthenticator(httpProxyUrl.getHost(),httpProxyUrl.getPort(),proxyUser,proxyPass)
				);
			}
		}
		
		this.initialized = true;
	}
	
	@Override
	public AbstractCDYNENetworkClientSession open() throws CDYNEException {
		return null;
	}
	
	@Override
	public <T extends AbstractCDYNERs> T send(AbstractCDYNENetworkClientSession session, byte service, AbstractCDYNERq rq, Class<T> responseClass, boolean useHttpFallback, boolean debug) throws CDYNEException {
		try {
			return _send(session,service,rq,responseClass,true,debug);			
			
		} catch (CDYNEException ce) {
			if (shouldUseHttpFallback(service,properties)) {
				return _send(session,service,rq,responseClass,false,debug);
				
			} else {
				throw ce;
			}
		}		
	}
	
	protected <T extends AbstractCDYNERs> T _send(AbstractCDYNENetworkClientSession session, byte service, AbstractCDYNERq rq, Class<T> responseClass, boolean useHttps, boolean debug) throws CDYNEException {
		try {
			T rs = responseClass.newInstance();
			
			URL url = createRequestUrl(service,this.properties,rq,useHttps);
			if (debug) {
				System.out.println("DEBUG Rq: URL=" + url.toString());
			}
			
			Proxy proxy = null;
			
			if (httpProxy != null && ("http".equals(url.getProtocol()) || "https".equals(url.getProtocol()))) {
				proxy = new Proxy(Proxy.Type.HTTP,httpProxy);
			}
			
			HttpURLConnection c = proxy != null ? (HttpURLConnection) url.openConnection(proxy) : (HttpURLConnection) url.openConnection();
			
			if (rq.getRequestType() == AbstractCDYNERq.RQ_TYPE_XML) {
				c.setRequestMethod("POST");
				c.setRequestProperty("Content-type","text/xml");
				c.setDoOutput(true);
				
				if (debug) {
					StringWriter sw = new StringWriter();
					rq.serializeXml(sw);
					System.out.println("DEBUG Rq: " + sw.toString());
				}
				
				OutputStreamWriter w = new OutputStreamWriter(c.getOutputStream());
				rq.serializeXml(w);
				
			} else {
				c.setRequestMethod("GET");
				c.connect();
			}

			if (debug) {
				System.out.println("DEBUG Rs: responseCode=" + c.getResponseCode());
			}

			if (c.getResponseCode() == 200) {
				InputStream is = c.getInputStream();
				InputStreamReader isr = new InputStreamReader(is);
				
				if (debug) {
					char[] buffer = new char[RESPONSE_BUFFER_SIZE];
					StringBuilder sb = new StringBuilder();
					
					int s = isr.read(buffer);
					while(s > 0) {
						sb.append(buffer,0,s);
						
						s = isr.read(buffer);
					}
					
					String text = sb.toString();
					
					if (debug) {
						System.out.println("DEBUG Rs: " + text);
					}
					
					StringReader sr = new StringReader(text);
					rs.parse(sr);
					
				} else {
					rs.parse(isr);
				}
				
				return rs;
			}
			
			throw new CDYNEException(c.getResponseCode() + " " + c.getResponseMessage());
			
		} catch (Exception e) {
			throw new CDYNEException(e);
		}
	}

	@Override
	public void close(AbstractCDYNENetworkClientSession session) {
		//
	}

	@Override
	public void shutdown() {
		//
	}
}
