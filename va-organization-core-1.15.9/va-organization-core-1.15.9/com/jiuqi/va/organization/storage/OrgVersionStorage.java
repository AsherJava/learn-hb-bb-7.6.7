/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.common.JDialectUtil
 *  com.jiuqi.va.mapper.common.JTableException
 *  com.jiuqi.va.mapper.common.JTableModel
 */
package com.jiuqi.va.organization.storage;

import com.jiuqi.va.mapper.common.JDialectUtil;
import com.jiuqi.va.mapper.common.JTableException;
import com.jiuqi.va.mapper.common.JTableModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrgVersionStorage {
    private static Logger logger = LoggerFactory.getLogger(OrgVersionStorage.class);

    public static void init(String tenantName) {
        JDialectUtil jDialect = JDialectUtil.getInstance();
        try {
            JTableModel jtm = OrgVersionStorage.getCreateJTM(tenantName);
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

    public static JTableModel getCreateJTM(String tenantName) {
        JTableModel jtm = new JTableModel(tenantName, "MD_ORG_VERSION");
        jtm.column("ID").VARCHAR(Integer.valueOf(36)).pkey();
        jtm.column("TITLE").NVARCHAR(Integer.valueOf(100)).notNull();
        jtm.column("CATEGORYNAME").VARCHAR(Integer.valueOf(36)).notNull();
        jtm.column("VALIDTIME").DATE().notNull();
        jtm.column("INVALIDTIME").DATE().notNull();
        jtm.column("ACTIVEFLAG").INTEGER(new Integer[]{1}).defaultValue("1");
        jtm.index("MD_ORG_VERSION_TITLE").columns(new String[]{"CATEGORYNAME", "TITLE"}).unique();
        return jtm;
    }
}

