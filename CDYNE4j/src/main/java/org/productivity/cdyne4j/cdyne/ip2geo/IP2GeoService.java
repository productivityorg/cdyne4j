package org.productivity.cdyne4j.cdyne.ip2geo;

import org.productivity.cdyne4j.CDYNEClient;
import org.productivity.cdyne4j.CDYNEException;
import org.productivity.cdyne4j.CDYNERuntimeException;
import org.productivity.cdyne4j.background.AbstractBackgroundStatusProcessor;
import org.productivity.cdyne4j.cdyne.AbstractCDYNEService;

/**
 * IP2GeoService implements: http://cdyne.com/api/ip-location/
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
public class IP2GeoService extends AbstractCDYNEService {
	public ResolveIPRs send(ResolveIPRq rq) throws CDYNEException {
		return send(rq,ResolveIPRs.class);
	}
	
	@Override
	public AbstractBackgroundStatusProcessor getBackgroundStatusProcessor() {
		throw new CDYNERuntimeException("getBackgroundStatusProcessor(): IP2GeoService does not implement an AbstractBackgroundStatusProcessor");
	}

	@Override
	protected byte getService() {
		return CDYNEClient.IP2GEO;
	}
}
