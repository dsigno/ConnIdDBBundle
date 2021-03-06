/**
 * ====================
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2008-2009 Sun Microsystems, Inc. All rights reserved.
 * Copyright 2011-2013 Tirasa. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License("CDDL") (the "License"). You may not use this file
 * except in compliance with the License.
 *
 * You can obtain a copy of the License at https://oss.oracle.com/licenses/CDDL
 * See the License for the specific language governing permissions and limitations
 * under the License.
 *
 * When distributing the Covered Code, include this CDDL Header Notice in each file
 * and include the License file at https://oss.oracle.com/licenses/CDDL.
 * If applicable, add the following below this CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 * ====================
 */
package org.connid.bundles.db.common;

import java.util.Hashtable;

import org.identityconnectors.common.StringUtil;
import org.identityconnectors.framework.common.objects.ConnectorMessages;

/**
 * Common utility methods regarding JNDI
 * @author kitko
 *
 */
public abstract class JNDIUtil {
    /** */
	private JNDIUtil() {
        //empty
    }
	/**	 */
	public static final String INVALID_JNDI_ENTRY = "invalid.jndi.entry"; 
	
	/**
	 * Parses arrays of string as entries of properties. Each entry must be in form
	 * <code>key=value</code>. We use <strong>=</strong> as only separator and treat only first occurrence of =.
	 * Blank entries are skipped.
	 * @param entries could be null or size = 0
	 * @param messages the error messages from the configuration resource bundle
	 * @return hashtable of given entries
	 * @throws IllegalArgumentException when there is any error in format of any entry
	 */
	public static Hashtable<String,String> arrayToHashtable(String[] entries, ConnectorMessages messages){
		Hashtable<String, String> result = new Hashtable<String, String>();
        if (entries != null) {
            for (String entry : entries) {
                if (StringUtil.isNotBlank(entry)) {
                    int firstEq = entry.indexOf('=');
                    if (firstEq == -1) {
                        throwFormatException(messages, entry, "Invalid value in JNDI entry");
                    }
                    if (firstEq == 0) {
                        throwFormatException(messages, entry, "First character cannot be =");
                    }
                    final String key = entry.substring(0, firstEq);
                    final String value = firstEq == entry.length() - 1 ? null : entry.substring(firstEq + 1);
                    result.put(key, value);
                }
            }
        }
		return result;
	}
	
	private static void throwFormatException(ConnectorMessages messages,String entry,String defaultMsg){
		String msg = null;
		if(messages == null){
			msg = defaultMsg + " : " + entry;
		}
		else{
			msg = messages.format(INVALID_JNDI_ENTRY,INVALID_JNDI_ENTRY + " : " + entry,entry);
		}
		throw new IllegalArgumentException(msg);
	}
	

}
