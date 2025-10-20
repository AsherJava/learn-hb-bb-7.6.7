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

public class AttachmentBizRecycleBinStorage {
    private static final Logger LOGGER = LoggerFactory.getLogger(AttachmentBizRecycleBinStorage.class);

    public static void init(String tenantName) {
        JDialectUtil jDialect = JDialectUtil.getInstance();
        try {
            JTableModel jtm = AttachmentBizRecycleBinStorage.getCreateJTM(tenantName);
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
        JTableModel jtm = new JTableModel(tenantName, "BIZATTACHMENT_RECYCLE_BIN");
        jtm.column("ID").VARCHAR(Integer.valueOf(36)).pkey();
        jtm.column("QUOTECODE").VARCHAR(Integer.valueOf(60));
        jtm.column("QUOTEID").VARCHAR(Integer.valueOf(36));
        jtm.column("SOURCEINFO").NVARCHAR(Integer.valueOf(200));
        jtm.column("FILEPATH").NVARCHAR(Integer.valueOf(200));
        jtm.column("FILENAME").NVARCHAR(Integer.valueOf(100));
        jtm.column("DELETETIME").TIMESTAMP();
        jtm.column("OPTUSER").VARCHAR(Integer.valueOf(36));
        jtm.column("DELETETYPE").INTEGER(new Integer[]{1});
        jtm.index("BIZ_ATTCON_QUOTEID").columns(new String[]{"QUOTEID"}).unique();
        return jtm;
    }
}

