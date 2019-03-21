package org.productivity.cdyne4j.networkclient.httpclient45;

import java.io.IOException;
import java.util.Properties;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.productivity.cdyne4j.CDYNEException;

/**
 * CDYNEHttpClient45NetworkClient implements the Apache Commons HttpClient 4.5+ series.
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
public class CDYNEHttpClient45NetworkClient extends AbstractCDYNEHttpClient45NetworkClient {
	public CDYNEHttpClient45NetworkClient() {
		super();
	}
	
	public CDYNEHttpClient45NetworkClient(Properties properties) {
		super(properties);
	}

	public CDYNEHttpClient45NetworkClient(CloseableHttpClient httpClient) {
		super();
		
		this.httpClient = httpClient;
		this.initialized = true;
	}

	public CDYNEHttpClient45NetworkClient(Properties properties, CloseableHttpClient httpClient) {
		super(properties);
		
		this.httpClient = httpClient;
		this.initialized = true;
	}

	protected CloseableHttpClient httpClient = null;

	@Override
	public void initialize() throws CDYNEException {
		if (this.httpClient != null) {
			return;
		}
		
		synchronized(this) {
			if (this.httpClient != null) {
				return;
			}
			
			HttpClientBuilder clientBuilder = HttpClients.custom();
	
			setupClient(clientBuilder);
			setupClientProxy(clientBuilder);
			
			RequestConfig.Builder requestConfigBuilder = RequestConfig.custom();
			clientBuilder.setDefaultRequestConfig(requestConfigBuilder.build());
			
			setupRequestConfig(requestConfigBuilder);
			
			this.httpClient = clientBuilder.build();
			
			this.initialized = true;
		}
	}

	@Override
	protected CloseableHttpClient getHttpClient() {
		return this.httpClient;
	}

	@Override
	public void shutdown() {
		try {
			this.httpClient.close();
			
		} catch (IOException ioe) {
			//
		}
	}
}
