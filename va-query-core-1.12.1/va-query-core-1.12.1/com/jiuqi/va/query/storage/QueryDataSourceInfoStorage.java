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
public class QueryDataSourceInfoStorage
extends QueryStorage {
    @Override
    protected JTableModel getCreateJTM(String tenantName) {
        JTableModel jtm = new JTableModel(tenantName, "DC_QUERY_DATASOURCEINFO");
        jtm.column("ID").VARCHAR(Integer.valueOf(36)).pkey();
        jtm.column("CODE").VARCHAR(Integer.valueOf(60)).notNull();
        jtm.column("URL").VARCHAR(Integer.valueOf(260));
        jtm.column("CONNECTIONTYPE").VARCHAR(Integer.valueOf(10));
        jtm.column("DRIVER").VARCHAR(Integer.valueOf(60)).notNull();
        jtm.column("USERNAME").VARCHAR(Integer.valueOf(30)).notNull();
        jtm.column("PWD").VARCHAR(Integer.valueOf(200)).notNull();
        jtm.column("NAME").VARCHAR(Integer.valueOf(30)).notNull();
        jtm.column("UPDATETIME").TIMESTAMP();
        jtm.column("DATABASETYPE").VARCHAR(Integer.valueOf(20));
        jtm.column("IP").VARCHAR(Integer.valueOf(20));
        jtm.column("PORT").VARCHAR(Integer.valueOf(10));
        jtm.column("DATABASENAME").VARCHAR(Integer.valueOf(50));
        jtm.column("ENABLETEMPTABLE").INTEGER(new Integer[]{1});
        jtm.column("INPARAMVALUEMAXCOUNT").INTEGER(new Integer[]{10});
        jtm.column("DATABASEPARAM").VARCHAR(Integer.valueOf(200));
        return jtm;
    }
}

