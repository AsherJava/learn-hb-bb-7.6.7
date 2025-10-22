/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.metadata.LogicField
 */
package com.jiuqi.nr.io.sb.bean;

import com.jiuqi.bi.database.metadata.LogicField;
import java.util.ArrayList;
import java.util.List;

public class SbIdTempTableInfo {
    private final List<LogicField> logicFields = new ArrayList<LogicField>();
    private final List<String> primaryKeyFields = new ArrayList<String>();

    public SbIdTempTableInfo() {
        LogicField sbId = new LogicField();
        sbId.setFieldName("SBID");
        sbId.setSize(50);
        sbId.setDataType(6);
        this.logicFields.add(sbId);
        this.primaryKeyFields.add("SBID");
    }

    public List<LogicField> getLogicFields() {
        return this.logicFields;
    }

    public List<String> getPrimaryKeyFields() {
        return this.primaryKeyFields;
    }
}

