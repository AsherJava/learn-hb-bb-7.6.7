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
public class QueryTemplateDesignStorage
extends QueryStorage {
    @Override
    protected JTableModel getCreateJTM(String tenantName) {
        JTableModel jtm = new JTableModel(tenantName, "DC_QUERY_TEMPLATE_DESIGN");
        jtm.column("ID").VARCHAR(Integer.valueOf(36)).pkey();
        jtm.column("DESIGNDATA").CLOB();
        return jtm;
    }
}

