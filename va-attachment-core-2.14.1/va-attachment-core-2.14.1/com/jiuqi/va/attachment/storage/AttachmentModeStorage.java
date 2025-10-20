/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.common.JDialectUtil
 *  com.jiuqi.va.mapper.common.JTableException
 *  com.jiuqi.va.mapper.common.JTableModel
 */
package com.jiuqi.va.attachment.storage;

import com.jiuqi.va.mapper.common.JDialectUtil;
import com.jiuqi.va.mapper.common.JTableException;
import com.jiuqi.va.mapper.common.JTableModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AttachmentModeStorage {
    private static final Logger LOGGER = LoggerFactory.getLogger(AttachmentModeStorage.class);

    public static void init(String tenantName) {
        JDialectUtil jDialect = JDialectUtil.getInstance();
        try {
            JTableModel jtm = AttachmentModeStorage.getCreateJTM(tenantName);
            if (!jDialect.hasTable(jtm)) {
                jDialect.createTable(jtm);
            } else {
                jDialect.updateTable(jtm);
            }
        }
        catch (JTableException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private static JTableModel getCreateJTM(String tenantName) {
        JTableModel jtm = new JTableModel(tenantName, "ATT_MODE");
        jtm.column("ID").VARCHAR(Integer.valueOf(36)).pkey();
        jtm.column("NAME").NVARCHAR(Integer.valueOf(100));
        jtm.column("TITLE").NVARCHAR(Integer.valueOf(100));
        jtm.column("SCHEMENAME").NVARCHAR(Integer.valueOf(100));
        jtm.column("ATTPATH").NVARCHAR(Integer.valueOf(500));
        jtm.column("ATTTYPE").NVARCHAR(Integer.valueOf(100));
        jtm.column("ATTSIZE").NUMERIC(new Integer[]{10, 2});
        jtm.column("ATTNUM").INTEGER(new Integer[]{10});
        jtm.column("VER").NUMERIC(new Integer[]{19, 2});
        jtm.column("STARTFLAG").INTEGER(new Integer[]{1});
        jtm.column("CREATETIME").TIMESTAMP();
        jtm.column("CREATEUSER").VARCHAR(Integer.valueOf(36));
        jtm.column("MODIFYTIME").TIMESTAMP();
        jtm.column("MODIFYUSER").VARCHAR(Integer.valueOf(36));
        jtm.column("CONFIG").CLOB();
        jtm.column("DEFAULTFLAG").INTEGER(new Integer[]{1});
        jtm.index("ATT_MODE_NAME").columns(new String[]{"NAME"}).unique();
        jtm.index("ATT_MODE_DEFAULTFLAG").columns(new String[]{"DEFAULTFLAG"}).unique();
        return jtm;
    }
}

