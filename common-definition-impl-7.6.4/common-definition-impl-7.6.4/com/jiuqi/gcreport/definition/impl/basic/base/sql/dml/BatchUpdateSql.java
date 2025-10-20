/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.definition.impl.basic.base.sql.dml;

import com.jiuqi.gcreport.definition.impl.basic.base.sql.dml.EntDmlBatchSql;
import com.jiuqi.gcreport.definition.impl.basic.base.sql.dml.UpdateSql;
import com.jiuqi.gcreport.definition.impl.basic.base.template.intf.EntBatchPreparedStatementSetter;
import com.jiuqi.gcreport.definition.impl.basic.base.template.intf.PsSetterFactory;
import java.util.ArrayList;
import java.util.List;

public class BatchUpdateSql
extends UpdateSql
implements EntDmlBatchSql {
    private int size;
    private List<List<Object>> values;

    public static BatchUpdateSql newInstance(String tableName, int size) {
        return new BatchUpdateSql(tableName, size);
    }

    private BatchUpdateSql(String tableName, int size) {
        super(tableName);
        this.size = size;
        this.values = new ArrayList<List<Object>>();
    }

    @Override
    public void addRowValues(List<Object> values) {
        this.values.add(values);
    }

    @Override
    public final EntBatchPreparedStatementSetter getBatchPreStatementSetter() {
        return PsSetterFactory.newBatchSetter(this.values, this.size);
    }
}

