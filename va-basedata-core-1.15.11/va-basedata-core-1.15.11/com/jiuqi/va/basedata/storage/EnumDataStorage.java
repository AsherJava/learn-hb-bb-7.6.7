/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.common.JDialectUtil
 *  com.jiuqi.va.mapper.common.JTableException
 *  com.jiuqi.va.mapper.common.JTableModel
 */
package com.jiuqi.va.basedata.storage;

import com.jiuqi.va.mapper.common.JDialectUtil;
import com.jiuqi.va.mapper.common.JTableException;
import com.jiuqi.va.mapper.common.JTableModel;

public class EnumDataStorage {
    public static void init(String tenantName) {
        JDialectUtil jDialect = JDialectUtil.getInstance();
        try {
            JTableModel jtm = EnumDataStorage.getCreateJTM(tenantName);
            if (!jDialect.hasTable(jtm)) {
                jDialect.createTable(jtm);
            } else {
                jDialect.updateTable(jtm);
            }
        }
        catch (JTableException e) {
            e.printStackTrace();
        }
    }

    public static JTableModel getCreateJTM(String tenantName) {
        JTableModel jtm = new JTableModel(tenantName, "ENUMDATA_INFO");
        jtm.column("ID").VARCHAR(Integer.valueOf(36)).pkey();
        jtm.column("VER").NUMERIC(new Integer[]{19, 0}).defaultValue("0");
        jtm.column("TITLE").NVARCHAR(Integer.valueOf(100));
        jtm.column("VAL").VARCHAR(Integer.valueOf(100)).notNull();
        jtm.column("BIZTYPE").VARCHAR(Integer.valueOf(100)).notNull();
        jtm.column("DESCRIPTION").NVARCHAR(Integer.valueOf(100));
        jtm.column("ORDERNUM").NUMERIC(new Integer[]{19, 6});
        jtm.column("STATUS").INTEGER(new Integer[]{1});
        jtm.column("REMARK").NVARCHAR(Integer.valueOf(400));
        jtm.index("EMDA_CODE").columns(new String[]{"BIZTYPE", "VAL"}).unique();
        jtm.index("EMDA_BIPESTUS").columns(new String[]{"BIZTYPE", "STATUS"});
        return jtm;
    }
}

