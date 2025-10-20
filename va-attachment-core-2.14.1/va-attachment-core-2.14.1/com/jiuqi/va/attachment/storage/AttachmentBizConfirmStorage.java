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

public class AttachmentBizConfirmStorage {
    private static final Logger LOGGER = LoggerFactory.getLogger(AttachmentBizConfirmStorage.class);

    public static void init(String tenantName) {
        JDialectUtil jDialect = JDialectUtil.getInstance();
        try {
            JTableModel jtm = AttachmentBizConfirmStorage.getCreateJTM(tenantName);
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
        JTableModel jtm = new JTableModel(tenantName, "BIZATTACHMENT_CONFIRM");
        jtm.column("ID").VARCHAR(Integer.valueOf(36)).pkey();
        jtm.column("QUOTECODE").VARCHAR(Integer.valueOf(60));
        jtm.column("UPDATETIME").TIMESTAMP();
        jtm.column("CONFIRMTIME").TIMESTAMP();
        jtm.column("BIZTYPE").NVARCHAR(Integer.valueOf(60));
        jtm.column("BIZCODE").NVARCHAR(Integer.valueOf(60));
        jtm.column("EXTDATA").NVARCHAR(Integer.valueOf(200));
        jtm.index("BIZ_ATTCON_QUOTECODE").columns(new String[]{"QUOTECODE"}).unique();
        jtm.index("BIZ_ATTCON_TYPE_CODE").columns(new String[]{"BIZTYPE", "BIZCODE"});
        return jtm;
    }
}

