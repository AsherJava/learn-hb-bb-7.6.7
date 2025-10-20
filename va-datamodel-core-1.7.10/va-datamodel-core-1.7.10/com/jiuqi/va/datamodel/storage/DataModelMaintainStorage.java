/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.common.JDialectUtil
 *  com.jiuqi.va.mapper.common.JTableModel
 */
package com.jiuqi.va.datamodel.storage;

import com.jiuqi.va.mapper.common.JDialectUtil;
import com.jiuqi.va.mapper.common.JTableModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataModelMaintainStorage {
    private static Logger logger = LoggerFactory.getLogger(DataModelMaintainStorage.class);

    public static void init(String tenantName) {
        JDialectUtil jDialect = JDialectUtil.getInstance();
        try {
            JTableModel jtm = DataModelMaintainStorage.getMaintainCreateJTM(tenantName);
            if (!jDialect.hasTable(jtm)) {
                jDialect.createTable(jtm);
            } else {
                jDialect.updateTable(jtm);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private static JTableModel getMaintainCreateJTM(String tenantName) {
        JTableModel jtm = new JTableModel(tenantName, "DATAMODEL_DEFINE_MAINTAIN");
        jtm.column("ID").VARCHAR(Integer.valueOf(36)).pkey();
        jtm.column("NAME").VARCHAR(Integer.valueOf(100)).notNull();
        jtm.column("TITLE").NVARCHAR(Integer.valueOf(100)).notNull();
        jtm.column("BIZTYPE").VARCHAR(Integer.valueOf(100)).notNull();
        jtm.column("GROUPCODE").VARCHAR(Integer.valueOf(100)).notNull();
        jtm.column("DEFINEDATA").CLOB();
        jtm.column("REMARK").NVARCHAR(Integer.valueOf(400));
        jtm.column("VER").NUMERIC(new Integer[]{19, 6}).notNull().defaultValue("0");
        jtm.column("CREATETIME").TIMESTAMP();
        jtm.column("MODIFYUSER").NVARCHAR(Integer.valueOf(36));
        jtm.column("MODIFYTIME").TIMESTAMP();
        jtm.index("DAMLDEMN_NAME").columns(new String[]{"NAME"}).unique();
        return jtm;
    }
}

