/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.definition.impl.basic.base.sql.dml;

import com.jiuqi.gcreport.definition.impl.basic.base.sql.dml.DeleteSql;
import com.jiuqi.gcreport.definition.impl.basic.base.sql.dml.EntDmlBatchSql;
import com.jiuqi.gcreport.definition.impl.basic.base.template.intf.EntBatchPreparedStatementSetter;
import com.jiuqi.gcreport.definition.impl.basic.base.template.intf.PsSetterFactory;
import java.util.ArrayList;
import java.util.List;

public class BatchDeleteSql
extends DeleteSql
implements EntDmlBatchSql {
    private int size;
    private List<List<Object>> values;

    public static BatchDeleteSql newInstance(String tableName, int size) {
        return new BatchDeleteSql(tableName, size);
    }

    private BatchDeleteSql(String tableName, int size) {
        super(tableName);
        this.size = size;
        this.values = new ArrayList<List<Object>>();
    }

    @Override
    public EntBatchPreparedStatementSetter getBatchPreStatementSetter() {
        return PsSetterFactory.newBatchSetter(this.values, this.size);
    }

    @Override
    public void addRowValues(List<Object> values) {
        this.values.add(values);
    }
}

