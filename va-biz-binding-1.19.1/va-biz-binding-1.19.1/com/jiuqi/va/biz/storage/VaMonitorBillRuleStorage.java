/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.common.JDialectUtil
 *  com.jiuqi.va.mapper.common.JTableException
 *  com.jiuqi.va.mapper.common.JTableModel
 */
package com.jiuqi.va.biz.storage;

import com.jiuqi.va.mapper.common.JDialectUtil;
import com.jiuqi.va.mapper.common.JTableException;
import com.jiuqi.va.mapper.common.JTableModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VaMonitorBillRuleStorage {
    private static final Logger logger = LoggerFactory.getLogger(VaMonitorBillRuleStorage.class);

    public static void init(String tenantName) {
        JDialectUtil jDialect = JDialectUtil.getInstance();
        try {
            JTableModel jtm = VaMonitorBillRuleStorage.getCreateJTM(tenantName);
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
        JTableModel jtm = new JTableModel(tenantName, "VA_MONITOR_BILL_RULE");
        jtm.column("ID").VARCHAR(Integer.valueOf(36)).pkey();
        jtm.column("HOSTNAME").VARCHAR(Integer.valueOf(60)).comment("\u670d\u52a1\u8282\u70b9\u6807\u8bc6");
        jtm.column("DEFINECODE").VARCHAR(Integer.valueOf(60)).comment("\u5355\u636e\u5b9a\u4e49\u6807\u8bc6");
        jtm.column("RULEID").VARCHAR(Integer.valueOf(60)).comment("\u5355\u636e\u89c4\u5219\u6807\u8bc6");
        jtm.column("RULENAME").VARCHAR(Integer.valueOf(60)).comment("\u5355\u636e\u89c4\u5219\u540d\u79f0");
        jtm.column("MPERIOD").TIMESTAMP().comment("\u76d1\u63a7\u65f6\u671f");
        jtm.column("UPDATETIME").TIMESTAMP().comment("\u66f4\u65b0\u65f6\u95f4");
        jtm.column("EXECUTECOUNT").INTEGER(new Integer[]{10}).comment("\u6267\u884c\u6b21\u6570");
        jtm.column("TOTALTIME").LONG().comment("\u6267\u884c\u603b\u65f6\u95f4\uff08ms\uff09");
        jtm.column("MAXTIME").LONG().comment("\u6700\u5927\u7528\u65f6\uff08ms\uff09");
        jtm.column("MINTIME").LONG().comment("\u6700\u5c0f\u7528\u65f6\uff08ms\uff09");
        jtm.column("AVGTIME").LONG().comment("\u5e73\u5747\u7528\u65f6\uff08ms\uff09");
        jtm.column("PERIOD").INTEGER(new Integer[]{10}).comment("\u5468\u671f");
        jtm.index("IDX_H_D_R_M").columns(new String[]{"HOSTNAME", "DEFINECODE", "RULEID", "MPERIOD"}).unique();
        return jtm;
    }
}

