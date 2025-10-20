/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.common.JDialectUtil
 *  com.jiuqi.va.mapper.common.JTableException
 *  com.jiuqi.va.mapper.common.JTableModel
 */
package com.jiuqi.va.bill.storage;

import com.jiuqi.va.mapper.common.JDialectUtil;
import com.jiuqi.va.mapper.common.JTableException;
import com.jiuqi.va.mapper.common.JTableModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MetaInfoExtendBillStorage {
    private static final Logger log = LoggerFactory.getLogger(MetaInfoExtendBillStorage.class);

    public static void init(String tenantName) {
        JDialectUtil jDialect = JDialectUtil.getInstance();
        try {
            JTableModel jtm = MetaInfoExtendBillStorage.getCreateJTM(tenantName);
            if (!jDialect.hasTable(jtm)) {
                jDialect.createTable(jtm);
            } else {
                jDialect.updateTable(jtm);
            }
        }
        catch (JTableException e) {
            log.error("\u8868\u521b\u5efa\u5931\u8d25\uff1aMETA_INFO_EXTEND_BILL " + e.getMessage(), e);
        }
    }

    public static JTableModel getCreateJTM(String tenantName) {
        JTableModel jtm = new JTableModel(tenantName, "META_INFO_EXTEND_BILL");
        jtm.column("ID").VARCHAR(Integer.valueOf(36)).pkey();
        jtm.column("UNIQUECODE").comment("\u5355\u636e\u5b9a\u4e49\u6807\u8bc6").VARCHAR(Integer.valueOf(100));
        jtm.column("TABLENAME").comment("\u8868\u540d").NVARCHAR(Integer.valueOf(100));
        jtm.column("PARENTNAME").comment("\u7236\u7ea7\u8868\u540d").VARCHAR(Integer.valueOf(100));
        jtm.index("UNIQUECODE_INDEX").columns(new String[]{"UNIQUECODE"});
        jtm.index("UNIQUECODE_TABLENAME_INDEX").columns(new String[]{"UNIQUECODE", "TABLENAME"}).unique();
        return jtm;
    }
}

