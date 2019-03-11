package org.productivity.cdyne4j.cdyne.smsnotify;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.productivity.cdyne4j.CDYNEClient;
import org.productivity.cdyne4j.CDYNEException;
import org.productivity.cdyne4j.background.AbstractBackgroundStatusProcessor;
import org.productivity.cdyne4j.background.BackgroundStatus;

/**
 * SMSNotifyBackgroundStatusProcessor implements: https://www.cdyne.com/sms/
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
public class SMSNotifyBackgroundStatusProcessor extends AbstractBackgroundStatusProcessor {
	// Run the queue at least every 5 seconds (non-concurrent)
	private static final int PERIOD_SECONDS = 5;
	
	// Allow roughly 10 minutes worth of 5-second periods for each BackgrounDstatus, then give up
	private static final int FAILSAFE_MAX = (10 * 60) / PERIOD_SECONDS; 
	
	public SMSNotifyBackgroundStatusProcessor() {
		super(new SMSNotifyBackgroundStatusQueueRunnable(),PERIOD_SECONDS);
	}

	protected static class SMSNotifyBackgroundStatusQueueRunnable extends AbstractBackgroundStatusQueueRunnable {
		protected List<BackgroundStatus> addBackIntoQueueList = new ArrayList<>();
		
		@Override
		public void run() {
			if (queue != null) {
				if (CDYNEClient.getInstance().isDebug()) {
					System.out.println("run(): queue run; size=" + queue.size());
				}
			
				while (!queue.isEmpty()) {
					BackgroundStatus status = queue.remove();
					status.stampLastUpdated();
					if (status.getHandler().isDebug()) { status.getHandler().debug(status,"Queue Remove (1 item) Success"); }
					
					GetMessageStatusRq statusRq = new GetMessageStatusRq();
					statusRq.setLicenseKey(status.getRq().getLicenseKey());
					statusRq.setMessageID(status.getMessageID());
					
					boolean addBackIntoQueue = true;
					
					try {
						status.stampLastUpdated();
						if (status.getHandler().isDebug()) { status.getHandler().debug(status,"Sending GetMessageStatus request to CDYNE"); }
						GetMessageStatusRs statusRs = CDYNEClient.getInstance().getSMSNotify().send(statusRq);
						
						if (statusRs != null) {
							status.setStatusRs(statusRs);
							
							if (statusRs.isSuccess()) {
								status.stampLastUpdated();
								if (status.getHandler().isDebug()) { status.getHandler().debug(status,"Received Successful GetMessageStatus response from CDYNE"); }
								
								if (statusRs.getSmsResponse() != null && (statusRs.getSmsResponse().isSent() || statusRs.getSmsResponse().isCancelled())) {
									addBackIntoQueue = false;
									status.stampLastUpdated();
									status.getHandler().event(status);
								}
								
							} else {
								addBackIntoQueue = false;
								status.stampLastUpdated();
								status.getHandler().error(status,statusRs.getSmsResponse() != null ? statusRs.getSmsResponse().getSmsError() : "UNKNOWN");
							}
							
						} else {
							addBackIntoQueue = false;
							status.stampLastUpdated();
							status.getHandler().error(status,"GetMessageStatusRs is null");						
						}
							
					} catch (Exception e) {
						status.stampLastUpdated();
						if (status.getHandler().isDebug()) { status.getHandler().debug(status,"Received Exception during GetMessageStatus request to CDYNE"); }
						status.stampLastUpdated();
						status.getHandler().exception(status,e);
					}
					
					if (addBackIntoQueue) {
						status.stampLastUpdated();
						if (status.getHandler().isDebug()) { status.getHandler().debug(status,"Adding status back to queue (not completed)"); }
						addBackIntoQueueList.add(status);
						
					} else {
						status.stampLastUpdated();
						if (status.getHandler().isDebug()) { status.getHandler().debug(status,"Not adding status back to queue (completed)"); }
					}
				}
				
				if (!addBackIntoQueueList.isEmpty()) {
					Iterator<BackgroundStatus> i = addBackIntoQueueList.iterator();
					while(i.hasNext()) {
						BackgroundStatus status = i.next();
						
						status.incrementFailsafeCount();
						
						if (status.getFailsafeCount() > FAILSAFE_MAX) {
							status.stampLastUpdated();
							status.getHandler().exception(status,new CDYNEException("run(): failsafe reached; removing from queue"));
							i.remove();
						}
					}
					
					queue.addAll(addBackIntoQueueList);
					addBackIntoQueueList.clear();
				}
			
				if (CDYNEClient.getInstance().isDebug()) {
					System.out.println("run(): queue run complete");
				}
			}
		}
	}
}
