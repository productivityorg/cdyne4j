package org.productivity.cdyne4j.cdyne.phonenotify;

import org.productivity.cdyne4j.cdyne.AbstractCDYNERq;
import org.productivity.cdyne4j.cdyne.AbstractCDYNERs;

/**
 * GetVoicesRq implements the request side of: http://cdyne.com/api/phone/notify/
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
public class GetVoicesRq extends AbstractCDYNERq {
	private static final long serialVersionUID = 8447818193343768780L;

	public static final String SERVICE_NAME = "getVoices";
	
	@Override
	public byte getRequestType() {
		return RQ_TYPE_NONE;
	}

	@Override
	public byte getResponseType() {
		return RS_TYPE_PARAMS;
	}

	@Override
	public Class<? extends AbstractCDYNERs> getResponseClass() {
		return GetVoicesRs.class;
	}

	@Override
	public String getServiceName() {
		return SERVICE_NAME;
	}
}
