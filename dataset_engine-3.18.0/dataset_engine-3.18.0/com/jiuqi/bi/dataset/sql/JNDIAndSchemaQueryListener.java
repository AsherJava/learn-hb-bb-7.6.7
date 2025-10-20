/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.sql.ISQLQueryListener
 *  com.jiuqi.bi.syntax.sql.ISQLQueryListener$ParamInfo
 */
package com.jiuqi.bi.dataset.sql;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.sql.ISQLQueryListener;

public class JNDIAndSchemaQueryListener
implements ISQLQueryListener {
    private static final String PARAMNAME_JNDI = "sys_jndi";
    private static final String PARAMNAME_SCHEMA = "sys_schema";
    private String jndi;
    private String schema;

    public JNDIAndSchemaQueryListener(String jndi, String schema) {
        this.jndi = jndi;
        this.schema = schema;
    }

    public ISQLQueryListener.ParamInfo findParam(String paramName) throws SyntaxException {
        if (PARAMNAME_JNDI.equalsIgnoreCase(paramName)) {
            ISQLQueryListener.ParamInfo pi = new ISQLQueryListener.ParamInfo(paramName, 6, (Object)this.jndi);
            return pi;
        }
        if (PARAMNAME_SCHEMA.equalsIgnoreCase(paramName)) {
            ISQLQueryListener.ParamInfo pi = new ISQLQueryListener.ParamInfo(paramName, 6, (Object)this.schema);
            return pi;
        }
        return null;
    }
}

