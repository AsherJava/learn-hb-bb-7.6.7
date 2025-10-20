/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.common.JDialectUtil
 *  com.jiuqi.va.mapper.common.JTableException
 *  com.jiuqi.va.mapper.common.JTableModel
 */
package com.jiuqi.va.openapi.storage;

import com.jiuqi.va.mapper.common.JDialectUtil;
import com.jiuqi.va.mapper.common.JTableException;
import com.jiuqi.va.mapper.common.JTableModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OpenApiStorage {
    private static Logger logger = LoggerFactory.getLogger(OpenApiStorage.class);

    public static void init(String tenantName) {
        JDialectUtil jDialect = JDialectUtil.getInstance();
        try {
            JTableModel jtm = OpenApiStorage.getCreateJTM4Register(tenantName);
            if (!jDialect.hasTable(jtm)) {
                jDialect.createTable(jtm);
            } else {
                jDialect.updateTable(jtm);
            }
            jtm = OpenApiStorage.getCreateJTM4Auth(tenantName);
            if (!jDialect.hasTable(jtm)) {
                jDialect.createTable(jtm);
            } else {
                jDialect.updateTable(jtm);
            }
        }
        catch (JTableException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private static JTableModel getCreateJTM4Register(String tenantName) {
        JTableModel jtm = new JTableModel(tenantName, "OPENAPI_REGISTER");
        jtm.column("ID").VARCHAR(Integer.valueOf(36)).pkey();
        jtm.column("NAME").VARCHAR(Integer.valueOf(100));
        jtm.column("TITLE").NVARCHAR(Integer.valueOf(100));
        jtm.column("GROUPTITLE").NVARCHAR(Integer.valueOf(100));
        jtm.index("OPENAPI_REGISTER_NAME").columns(new String[]{"NAME"}).unique();
        return jtm;
    }

    private static JTableModel getCreateJTM4Auth(String tenantName) {
        JTableModel jtm = new JTableModel(tenantName, "OPENAPI_AUTH");
        jtm.column("ID").VARCHAR(Integer.valueOf(36)).pkey();
        jtm.column("CLIENTID").VARCHAR(Integer.valueOf(50));
        jtm.column("CLIENTTITLE").NVARCHAR(Integer.valueOf(50));
        jtm.column("RANDOMCODE").VARCHAR(Integer.valueOf(20));
        jtm.column("OPENID").VARCHAR(Integer.valueOf(200));
        jtm.column("EXPIRETIME").NUMERIC(new Integer[]{19, 0});
        jtm.column("STOPFLAG").INTEGER(new Integer[]{1});
        jtm.column("AUTHDATA").CLOB();
        jtm.column("REMARK").NVARCHAR(Integer.valueOf(100));
        jtm.index("OPENAPI_AUTH_CLID").columns(new String[]{"CLIENTID"}).unique();
        return jtm;
    }
}

