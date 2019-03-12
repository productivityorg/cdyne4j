package org.productivity.cdyne4j.background;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.productivity.cdyne4j.CDYNERuntimeException;
import org.productivity.cdyne4j.cdyne.AbstractCDYNERq;
import org.productivity.cdyne4j.cdyne.AbstractCDYNERs;

/**
 * AbstractBackgroundStatusProcessor manages asynchronous activity within the CDYNE4j Java implementation.
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
public abstract class AbstractBackgroundStatusProcessor {
	protected ScheduledExecutorService scheduleService = null;
	protected AbstractBackgroundStatusQueueRunnable queueRunnable = null;
	
	public abstract static class AbstractBackgroundStatusQueueRunnable implements Runnable {
		protected Queue<BackgroundStatus> queue = null;
		
		public Queue<BackgroundStatus> getQueue() {
			return this.queue;
		}
		
		public AbstractBackgroundStatusQueueRunnable() {
			this.queue = new ConcurrentLinkedQueue<BackgroundStatus>();
		}
	}
	
	public AbstractBackgroundStatusProcessor(AbstractBackgroundStatusQueueRunnable queueRunnable, int periodSeconds) {
		if (queueRunnable == null) {
			throw new CDYNERuntimeException("AbstractBackgroundStatusProcessor(..): cannot create with queueRunnable=null");
		}
		
		this.queueRunnable = queueRunnable;
		
		this.scheduleService = Executors.newSingleThreadScheduledExecutor();
		this.scheduleService.scheduleAtFixedRate(queueRunnable, 1, periodSeconds, TimeUnit.SECONDS);
	}
	
	public void addBackgroundStatusRequest(AbstractCDYNERq rq, AbstractCDYNERs rs, String messageID, BackgroundStatusEventHandlerIF handler) {
		this.queueRunnable.getQueue().add(new BackgroundStatus(rq,rs,messageID,handler));
	}
	
	public boolean isQueueEmpty() {
		return this.queueRunnable.getQueue().isEmpty();
	}
	
	public void waitUntilQueueIsEmpty() {
		while(!isQueueEmpty()) {
			try {
				Thread.sleep(250);
				
			} catch (InterruptedException ie) {
				break;
			}
		}
	}
	
	public void shutdown() {
		if (this.scheduleService != null) {
			this.scheduleService.shutdown();
		}
	}
}
