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
package org.connid.bundles.db.table;

import org.identityconnectors.common.StringUtil;
import org.connid.bundles.db.table.util.DatabaseTableSQLUtil;
import org.connid.bundles.db.common.DatabaseFilterTranslator;
import org.connid.bundles.db.common.SQLParam;
import org.identityconnectors.framework.common.objects.Attribute;
import org.identityconnectors.framework.common.objects.AttributeUtil;
import org.identityconnectors.framework.common.objects.ObjectClass;
import org.identityconnectors.framework.common.objects.OperationOptions;

/**
 * Database table filter translator
 * 
 * @version $Revision 1.0$
 * @since 1.0
 */
public class DatabaseTableFilterTranslator extends DatabaseFilterTranslator {

    DatabaseTableConnector connector;

    /**
     * @param connector the database table connector
     * @param oclass
     * @param options
     */
    public DatabaseTableFilterTranslator(DatabaseTableConnector connector, ObjectClass oclass, OperationOptions options) {
        super(oclass, options);
        this.connector = connector;
    }

    /* (non-Javadoc)
     * @see org.identityconnectors.dbcommon.DatabaseFilterTranslator#getDatabaseColumnType(org.identityconnectors.framework.common.objects.Attribute, org.identityconnectors.framework.common.objects.ObjectClass, org.identityconnectors.framework.common.objects.OperationOptions)
     */
    @Override
    protected SQLParam getSQLParam(Attribute attribute, ObjectClass oclass, OperationOptions options) {
        final Object value = AttributeUtil.getSingleValue(attribute);
        final String columnName = connector.getColumnName(attribute.getName());
        if (StringUtil.isNotBlank(columnName)) {
            final Integer columnType = connector.getColumnType(columnName);
            String quoting = ((DatabaseTableConfiguration) this.connector.
                    getConfiguration()).getQuoting();
            String quotedName = DatabaseTableSQLUtil.quoteName(quoting,
                    columnName);
            return new SQLParam(columnName, value, columnType, quotedName);
        } else {
            return null;
        }
    }
}
