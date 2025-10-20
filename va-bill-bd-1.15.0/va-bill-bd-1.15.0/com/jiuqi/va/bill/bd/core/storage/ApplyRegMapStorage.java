/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.common.JDialectUtil
 *  com.jiuqi.va.mapper.common.JTableException
 *  com.jiuqi.va.mapper.common.JTableModel
 */
package com.jiuqi.va.bill.bd.core.storage;

import com.jiuqi.va.mapper.common.JDialectUtil;
import com.jiuqi.va.mapper.common.JTableException;
import com.jiuqi.va.mapper.common.JTableModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplyRegMapStorage {
    private static final Logger logger = LoggerFactory.getLogger(ApplyRegMapStorage.class);

    public static void init(String tenantName) {
        JDialectUtil jDialect = JDialectUtil.getInstance();
        try {
            JTableModel jtm = ApplyRegMapStorage.getCreateJTM(tenantName);
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
        JTableModel jtm = new JTableModel(tenantName, "APPLYREG_MP");
        jtm.column("ID").VARCHAR(Integer.valueOf(36)).pkey();
        jtm.column("VER").comment("\u7248\u672c").NUMERIC(new Integer[]{19, 2});
        jtm.column("NAME").comment("\u8868\u6807\u8bc6").VARCHAR(Integer.valueOf(100));
        jtm.column("TITLE").comment("\u8868\u6807\u9898").VARCHAR(Integer.valueOf(100));
        jtm.column("GROUPCODE").comment("\u5206\u7ec4\u6807\u8bc6").VARCHAR(Integer.valueOf(36));
        jtm.column("STARTFLAG").comment("\u542f\u7528\u6807\u8bc6").INTEGER(new Integer[]{1});
        jtm.column("DELETEFLAG").comment("\u5220\u9664\u6807\u8bb0").INTEGER(new Integer[]{1});
        jtm.column("ADAPTCONDITION").comment("\u9002\u5e94\u6761\u4ef6").VARCHAR(Integer.valueOf(255));
        jtm.column("CREATETYPE").comment("\u751f\u5355\u7c7b\u578b").INTEGER(new Integer[]{1});
        jtm.column("CREATETIME").comment("\u521b\u5efa\u65f6\u95f4").DATE();
        jtm.column("WRITEBACKNAME").comment("\u53d8\u66f4\u4f9d\u636e").VARCHAR(Integer.valueOf(100));
        jtm.column("DELETEFLAGNAME").comment("\u5220\u9664\u6807\u8bb0\u5b57\u6bb5").VARCHAR(Integer.valueOf(100));
        jtm.column("CREATEOPPORTUNITY").comment("\u751f\u5355\u65f6\u673a").INTEGER(new Integer[]{1});
        jtm.column("BILLDEFINE").comment("\u76ee\u6807\u8868").VARCHAR(Integer.valueOf(100));
        jtm.column("SRCBILLDEFINE").comment("\u6e90\u8868").VARCHAR(Integer.valueOf(100));
        jtm.column("BILLDEFINECODE").comment("\u76ee\u6807\u8868\u5b9a\u4e49").VARCHAR(Integer.valueOf(100));
        jtm.column("SRCBILLDEFINECODE").comment("\u6e90\u8868\u5b9a\u4e49").VARCHAR(Integer.valueOf(100));
        jtm.column("RELATIONTEMPLATE").comment("\u5173\u8054\u6a21\u677f").VARCHAR(Integer.valueOf(100));
        jtm.column("MAPINFOS").comment("\u5b9a\u4e49\u6570\u636e").CLOB();
        jtm.index("APPLYREG_MP_NAME").columns(new String[]{"NAME"}).unique();
        return jtm;
    }
}

