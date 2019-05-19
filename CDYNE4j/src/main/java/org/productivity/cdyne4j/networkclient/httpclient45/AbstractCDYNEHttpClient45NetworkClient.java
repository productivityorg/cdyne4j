package org.productivity.cdyne4j.networkclient.httpclient45;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Properties;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.ProxyAuthenticationStrategy;
import org.apache.http.util.EntityUtils;
import org.productivity.cdyne4j.CDYNEException;
import org.productivity.cdyne4j.cdyne.AbstractCDYNERq;
import org.productivity.cdyne4j.cdyne.AbstractCDYNERs;
import org.productivity.cdyne4j.networkclient.AbstractCDYNENetworkClient;
import org.productivity.cdyne4j.networkclient.AbstractCDYNENetworkClientSession;

/**
 * AbstractCDYNEHttpClient45NetworkClient implements the Apache Commons HttpClient 4.5+ series.
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
public abstract class AbstractCDYNEHttpClient45NetworkClient extends AbstractCDYNENetworkClient {
	public static final int DEFAULT_MAX_CONN_PER_ROUTE = 100;
	public static final int DEFAULT_MAX_CONN_TOTAL = 100;
	public static final int DEFAULT_CONNECT_TIMEOUT_MS = 15 * 1000;
	public static final int DEFAULT_SOCKET_TIMEOUT_MS = 30 * 1000;
	
	protected Properties properties = null;
	protected HttpHost httpProxy = null;
			
	public AbstractCDYNEHttpClient45NetworkClient() {
		this.properties = loadDefaultProperties();
	}
	
	public AbstractCDYNEHttpClient45NetworkClient(Properties properties) {
		this.properties = properties;
	}

	@Override
	public Properties getProperties() {
		return this.properties;
	}

	protected void setupClientProxy(HttpClientBuilder builder) throws CDYNEException {
		if (this.properties.containsKey("http_proxy")) {
			URL httpProxyUrl = null;
			
			try {
				httpProxyUrl = new URL((String) this.properties.get("http_proxy"));
				
			} catch (MalformedURLException murle) {
				throw new CDYNEException(murle);
			}
			
			this.httpProxy = new HttpHost(httpProxyUrl.getHost(),httpProxyUrl.getPort());
			builder.setProxy(this.httpProxy);

			String proxyUser = (String) this.properties.get("http_proxy_user");
			String proxyPass = (String) this.properties.get("http_proxy_pass");

			if (proxyUser != null && !proxyUser.isEmpty() && proxyPass != null && !proxyPass.isEmpty()) {
				 CredentialsProvider credsProvider = new BasicCredentialsProvider();
				 credsProvider.setCredentials(
				   new AuthScope(this.httpProxy),
				   new UsernamePasswordCredentials(proxyUser,proxyPass)
				 );
				 
				builder.setDefaultCredentialsProvider(credsProvider);
				builder.setProxyAuthenticationStrategy(new ProxyAuthenticationStrategy());
			}
		}
	}
	
	protected void setupClient(HttpClientBuilder builder) {
		int maxConnPerRoute = DEFAULT_MAX_CONN_PER_ROUTE;
		
		if (this.properties.containsKey("http_maxConnPerRoute")) {
			try {
				maxConnPerRoute = Integer.parseInt(this.properties.getProperty("http_maxConnPerRoute"));
				
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		builder.setMaxConnPerRoute(maxConnPerRoute);
		
		int maxConnTotal = DEFAULT_MAX_CONN_TOTAL;
		
		if (this.properties.containsKey("http_maxConnTotal")) {
			try {
				maxConnPerRoute = Integer.parseInt(this.properties.getProperty("http_maxConnTotal"));
				
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		builder.setMaxConnTotal(maxConnTotal);
	}
	
	protected void setupRequestConfig(RequestConfig.Builder builder) {
		int connectTimeout = DEFAULT_CONNECT_TIMEOUT_MS;
		
		if (this.properties.containsKey("http_connectTimeout")) {
			try {
				connectTimeout = Integer.parseInt(this.properties.getProperty("http_connectTimeout"));
				
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		builder.setConnectTimeout(connectTimeout);

		int socketTimeout = DEFAULT_SOCKET_TIMEOUT_MS;
		
		if (this.properties.containsKey("http_socketTimeout")) {
			try {
				socketTimeout = Integer.parseInt(this.properties.getProperty("http_socketTimeout"));
				
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		builder.setSocketTimeout(socketTimeout);
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
	
	protected abstract CloseableHttpClient getHttpClient();
	
	protected <T extends AbstractCDYNERs> T _send(AbstractCDYNENetworkClientSession session, byte service, AbstractCDYNERq rq, Class<T> responseClass, boolean useHttps, boolean debug) throws CDYNEException {
		try {
			T rs = responseClass.newInstance();
			
			URL url = createRequestUrl(service,this.properties,rq,useHttps);
			URI uri = url.toURI();
			
			if (debug) {
				System.out.println("DEBUG Rq: URL=" + url.toString());
			}
			
			HttpUriRequest request = null;
			
			if (rq.getRequestType() == AbstractCDYNERq.RQ_TYPE_XML) {
				HttpPost post = new HttpPost(uri);
				request = post;

				StringWriter sw = new StringWriter();
				rq.serializeXml(sw);

				String out = sw.toString();

				HttpEntity e = new StringEntity(out,"text/xml");
				post.setEntity(e);
				
				if (debug) {
					System.out.println("DEBUG Rq: " + out);
				}
				
			} else {
				HttpGet get = new HttpGet(uri);
				request = get;
			}
			
			try (CloseableHttpResponse response = getHttpClient().execute(request)) {
				int statusCode = response.getStatusLine().getStatusCode();
				
				if (debug) {
					System.out.println("DEBUG Rs: responseCode=" + statusCode);
				}

				if (statusCode == HttpStatus.SC_OK) {
					HttpEntity responseEntity = response.getEntity();
					
					if (responseEntity != null) {
						try (InputStream is = responseEntity.getContent(); InputStreamReader isr = new InputStreamReader(is)) {
							rs.parse(isr);
							
						} finally {
							EntityUtils.consumeQuietly(responseEntity);
						}
					}
					
				} else {
					throw new CDYNEException(response.getStatusLine().toString());
				}
				
				return rs;
			}
			
		} catch (Exception e) {
			throw new CDYNEException(e);
		}
	}

	@Override
	public void close(AbstractCDYNENetworkClientSession session) throws CDYNEException {
		//
	}
}
