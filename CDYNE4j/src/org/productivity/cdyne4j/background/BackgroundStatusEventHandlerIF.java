package org.productivity.cdyne4j.background;

import java.io.Serializable;

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
public interface BackgroundStatusEventHandlerIF extends Serializable {
	public void event(BackgroundStatus status);
	public void error(BackgroundStatus status, String text);
	public void exception(BackgroundStatus status, Exception e);
	
	public boolean isDebug();
	public void debug(BackgroundStatus status, String text);
}
