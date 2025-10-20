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

public class OrgCategoryStorage {
    private static Logger logger = LoggerFactory.getLogger(OrgCategoryStorage.class);

    public static void init(String tenantName) {
        JDialectUtil jDialect = JDialectUtil.getInstance();
        try {
            JTableModel jtm = OrgCategoryStorage.getCreateJTM(tenantName);
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

    private static JTableModel getCreateJTM(String tenantName) {
        JTableModel jtm = new JTableModel(tenantName, "MD_ORG_CATEGORY");
        jtm.column("ID").VARCHAR(Integer.valueOf(36)).pkey();
        jtm.column("NAME").VARCHAR(Integer.valueOf(60));
        jtm.column("TITLE").NVARCHAR(Integer.valueOf(100));
        jtm.column("CREATETIME").TIMESTAMP();
        jtm.column("ORDERNUM").NUMERIC(new Integer[]{19, 6});
        jtm.column("REMARK").NVARCHAR(Integer.valueOf(400));
        jtm.column("VERSIONFLAG").INTEGER(new Integer[]{1});
        jtm.column("EXTINFO").CLOB();
        jtm.index("MD_ORG_CATEGORY_NAME").columns(new String[]{"NAME"}).unique();
        return jtm;
    }

    public static void initDetailTable(String name, String tenantName) {
        JDialectUtil jDialect = JDialectUtil.getInstance();
        String tableName = name + "_SUBLIST";
        JTableModel jtm = new JTableModel(tenantName, tableName);
        try {
            OrgCategoryStorage.setCreateBaseDataDetail(jtm, tableName);
            if (!jDialect.hasTable(jtm)) {
                jDialect.createTable(jtm);
            }
        }
        catch (JTableException e) {
            e.printStackTrace();
        }
    }

    public static void setCreateBaseDataDetail(JTableModel jtm, String tableName) {
        jtm.column("ID").VARCHAR(Integer.valueOf(36)).pkey();
        jtm.column("MASTERID").VARCHAR(Integer.valueOf(36));
        jtm.column("FIELDNAME").VARCHAR(Integer.valueOf(60));
        jtm.column("FIELDVALUE").VARCHAR(Integer.valueOf(200));
        jtm.column("ORDERNUM").NUMERIC(new Integer[]{19, 6});
        jtm.index(tableName + "_MSTFID").columns(new String[]{"MASTERID", "FIELDNAME"});
    }
}

