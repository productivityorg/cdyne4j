package org.productivity.cdyne4j;

/**
 * CDYNEException is an extension of RuntimeException used by the CDYNE4j java implementation.
 * 
 * <p>This class is reserved for atypical issues within the client.</p>
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
public class CDYNERuntimeException extends RuntimeException {
	private static final long serialVersionUID = 7666609448467948479L;

	public CDYNERuntimeException() {
		super();
	}

	public CDYNERuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CDYNERuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public CDYNERuntimeException(String message) {
		super(message);
	}

	public CDYNERuntimeException(Throwable cause) {
		super(cause);
	}
}
