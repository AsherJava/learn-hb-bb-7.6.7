/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.common.JTableModel
 */
package com.jiuqi.va.query.storage;

import com.jiuqi.va.mapper.common.JTableModel;
import com.jiuqi.va.query.storage.QueryStorage;
import org.springframework.stereotype.Component;

@Component
public class QueryMonitorStorage
extends QueryStorage {
    @Override
    protected JTableModel getCreateJTM(String tenantName) {
        JTableModel jtm = new JTableModel(tenantName, "VA_MONITOR_QUERY");
        jtm.addColumn("ID").VARCHAR(Integer.valueOf(36)).pkey().comment("\u4e3b\u952eID");
        jtm.addColumn("HOSTNAME").VARCHAR(Integer.valueOf(60)).notNull().comment("\u670d\u52a1\u5668IP");
        jtm.addColumn("CODE").VARCHAR(Integer.valueOf(60)).notNull().comment("\u76d1\u63a7\u4ee3\u7801");
        jtm.addColumn("BIZNAME").VARCHAR(Integer.valueOf(60)).comment("\u4e1a\u52a1\u6807\u8bc6");
        jtm.addColumn("ARGS").CLOB().comment("\u65b9\u6cd5\u53c2\u6570(\u6700\u5927\u65f6\u95f4\u7684\u53c2\u6570)");
        jtm.addColumn("MPERIOD").TIMESTAMP().notNull().comment("\u76d1\u63a7\u65f6\u671f");
        jtm.addColumn("EXECUTECOUNT").INTEGER(new Integer[]{10}).comment("\u6267\u884c\u6b21\u6570");
        jtm.addColumn("TOTALTIME").LONG().comment("\u603b\u5171\u7528\u65f6");
        jtm.addColumn("AVGTIME").LONG().comment("\u5e73\u5747\u7528\u65f6");
        jtm.addColumn("MAXTIME").LONG().comment("\u6700\u5927\u7528\u65f6");
        jtm.addColumn("MINTIME").LONG().comment("\u6700\u5c0f\u7528\u65f6");
        jtm.addColumn("CREATETIME").TIMESTAMP().comment("\u521b\u5efa\u65f6\u95f4");
        jtm.addColumn("EVENTTIME").TIMESTAMP().comment("\u4e8b\u4ef6\u65f6\u95f4");
        jtm.index("IDX_UNIQUE_H_C_B_M").columns(new String[]{"HOSTNAME", "CODE", "BIZNAME", "MPERIOD"}).unique();
        return jtm;
    }
}

