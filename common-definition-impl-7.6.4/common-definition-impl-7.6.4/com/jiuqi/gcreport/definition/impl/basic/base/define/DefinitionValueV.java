/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.definition.impl.basic.base.define;

import com.jiuqi.gcreport.definition.impl.anno.DBKeyValuePair;
import com.jiuqi.gcreport.definition.impl.anno.DBValue;
import com.jiuqi.gcreport.definition.impl.anno.intf.IDefaultValueEnum;
import com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionTableV;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;

public class DefinitionValueV
extends DefaultTableEntity {
    private static final long serialVersionUID = 1L;
    private String tableName;

    public DefinitionValueV(IDefaultValueEnum value) {
        this.tableName = value.getTableName();
        this.setId(value.getID());
        this.resetFields(value.getFieldValues());
    }

    public DefinitionValueV(DefinitionTableV table, DBValue value) {
        this.tableName = table.getTableName();
        this.setId(value.id());
        for (DBKeyValuePair kv : value.fields()) {
            this.addFieldValue(kv.key(), kv.value());
        }
    }

    public DefinitionValueV() {
    }

    @Override
    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}

