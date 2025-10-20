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

public class OrgActionAuthStorage {
    private static Logger logger = LoggerFactory.getLogger(OrgActionAuthStorage.class);

    public static void init(String tenantName) {
        JDialectUtil jDialect = JDialectUtil.getInstance();
        try {
            JTableModel jtm = OrgActionAuthStorage.getCreateJTM(tenantName);
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
        JTableModel jtm = new JTableModel(tenantName, "AUTH_ORG_ACTION");
        jtm.column("ID").VARCHAR(Integer.valueOf(36)).pkey();
        jtm.column("BIZTYPE").INTEGER(new Integer[]{1}).notNull();
        jtm.column("BIZNAME").VARCHAR(Integer.valueOf(100)).notNull();
        jtm.column("AUTHTYPE").INTEGER(new Integer[]{1}).notNull();
        jtm.column("ORGCATEGORY").VARCHAR(Integer.valueOf(100)).notNull();
        jtm.column("ACTNAME").VARCHAR(Integer.valueOf(100)).notNull();
        jtm.column("AUTHFLAG").INTEGER(new Integer[]{1}).notNull();
        jtm.index("AHORG_BBOAA").columns(new String[]{"BIZTYPE", "BIZNAME", "ORGCATEGORY", "ACTNAME", "AUTHTYPE"}).unique();
        return jtm;
    }
}

