package org.productivity.cdyne4j.background;

import java.io.Serializable;
import java.util.Date;

import org.productivity.cdyne4j.cdyne.AbstractCDYNERq;
import org.productivity.cdyne4j.cdyne.AbstractCDYNERs;

/**
 * BackgroundStatus is used by the AbstractBackgroundStatusProcessor and its implementations.
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
public class BackgroundStatus implements Serializable {
	private static final long serialVersionUID = 3888484625434745296L;

	protected final AbstractCDYNERq rq;
	protected final AbstractCDYNERs rs;
	protected final String messageID;
	protected final BackgroundStatusEventHandlerIF handler;
	protected final Date start = new Date();
	
	protected int failsafeCount = 0;
	protected AbstractCDYNERs statusRs = null;
	protected Date lastUpdated = start;
	
	public BackgroundStatus(AbstractCDYNERq rq, AbstractCDYNERs rs, String messageID, BackgroundStatusEventHandlerIF handler) {
		this.rq = rq;
		this.rs = rs;
		this.messageID = messageID;
		this.handler = handler;
	}
	
	public AbstractCDYNERq getRq() {
		return rq;
	}
	public AbstractCDYNERs getRs() {
		return rs;
	}
	public String getMessageID() {
		return messageID;
	}
	public BackgroundStatusEventHandlerIF getHandler() {
		return handler;
	}
	public AbstractCDYNERs getStatusRs() {
		return statusRs;
	}
	public void setStatusRs(AbstractCDYNERs statusRs) {
		this.statusRs = statusRs;
	}
	public int getFailsafeCount() {
		return this.failsafeCount;
	}
	public void incrementFailsafeCount() {
		this.failsafeCount++;
	}
	public Date getStart() {
		return start;
	}
	public Date getLastUpdated() {
		return lastUpdated;
	}
	public void stampLastUpdated() {
		this.lastUpdated = new Date();
	}

	@Override
	public String toString() {
		return "rq=[" + rq.toString() + "] rs=[" + rs.toString() + "] statusRs=[" + statusRs + "]";
	}
}
