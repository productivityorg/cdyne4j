package org.productivity.cdyne4j.background.debugging;

import org.productivity.cdyne4j.background.BackgroundStatus;
import org.productivity.cdyne4j.background.BackgroundStatusEventHandlerIF;

/**
 * BackgroundStatusEventHandlerIF is used by the AbstractBackgroundStatusProcessor and its implementations to send back status of a request.
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
public class DebuggingBackgroundStatusEventHandler implements BackgroundStatusEventHandlerIF {
	private static final long serialVersionUID = 1488432064674390528L;

	protected boolean debug = true;
	
	@Override
	public void event(BackgroundStatus status) {
		System.out.println("EVENT: " + status.toString());
	}

	@Override
	public void error(BackgroundStatus status, String text) {
		System.out.println("ERROR: " + text + "; " + status);
	}
	
	@Override
	public void exception(BackgroundStatus status, Exception e) {
		System.err.println("EXCEPTION: " + e.toString() + "; " + status.toString());
	}

	@Override
	public boolean isDebug() {
		return this.debug;
	}
	
	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	@Override
	public void debug(BackgroundStatus status, String text) {
		System.out.println("DEBUG: " + text);
		System.out.println("DEBUG:   " + status);
	}
}
