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

public class AttachmentSchemeStorage {
    private static final Logger LOGGER = LoggerFactory.getLogger(AttachmentSchemeStorage.class);

    public static void init(String tenantName) {
        JDialectUtil jDialect = JDialectUtil.getInstance();
        try {
            JTableModel jtm = AttachmentSchemeStorage.getCreateJTM(tenantName);
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
        JTableModel jtm = new JTableModel(tenantName, "ATT_SCHEME");
        jtm.column("ID").VARCHAR(Integer.valueOf(36)).pkey();
        jtm.column("NAME").NVARCHAR(Integer.valueOf(100));
        jtm.column("TITLE").NVARCHAR(Integer.valueOf(100));
        jtm.column("STORAGEBUCKET").NVARCHAR(Integer.valueOf(100));
        jtm.column("STOREMODE").INTEGER(new Integer[]{3});
        jtm.column("DEGREE").INTEGER(new Integer[]{1});
        jtm.column("STARTFLAG").INTEGER(new Integer[]{1});
        jtm.column("CREATETIME").TIMESTAMP();
        jtm.column("CREATEUSER").VARCHAR(Integer.valueOf(36));
        jtm.column("MODIFYTIME").TIMESTAMP();
        jtm.column("MODIFYUSER").VARCHAR(Integer.valueOf(36));
        jtm.column("VER").NUMERIC(new Integer[]{19, 2});
        jtm.column("CONFIG").CLOB();
        jtm.index("ATT_SCHEME_NAME").columns(new String[]{"NAME"}).unique();
        return jtm;
    }
}

