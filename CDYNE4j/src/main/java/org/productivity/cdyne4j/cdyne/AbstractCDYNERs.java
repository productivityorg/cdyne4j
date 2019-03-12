package org.productivity.cdyne4j.cdyne;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;
import java.io.Serializable;

import org.productivity.cdyne4j.CDYNEException;
import org.productivity.cdyne4j.CDYNERuntimeException;

/**
 * AbstractCDYNERs specifies how responses from CDYNE are handled.
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
public abstract class AbstractCDYNERs implements Serializable {
	private static final long serialVersionUID = 583703255511369713L;
	
	public abstract void parse(Reader reader) throws CDYNEException;
	public abstract boolean isSuccess();
	public abstract String getReferenceID();
	
	protected void dump(Reader reader, PrintStream out) {
		BufferedReader br = new BufferedReader(reader);

		try {
			String line = br.readLine();
			while (line != null) {
				out.println(line);
				
				line = br.readLine();
			}
			
		} catch (IOException ioe) {
			throw new CDYNERuntimeException(ioe);
		}
	}
	
	public static String nullCheck(String in) {
		if (in != null && !in.isEmpty()) {
			return in;
		}
		
		return null;
	}
}
