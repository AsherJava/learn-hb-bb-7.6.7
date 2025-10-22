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

public class MdCodeTempTableInfo {
    private final List<LogicField> logicFields = new ArrayList<LogicField>();
    private final List<String> primaryKeyFields = new ArrayList<String>();

    public MdCodeTempTableInfo() {
        LogicField mdCode = new LogicField();
        mdCode.setFieldName("MDCODE");
        mdCode.setSize(50);
        mdCode.setDataType(6);
        this.logicFields.add(mdCode);
        this.primaryKeyFields.add("MDCODE");
    }

    public List<LogicField> getLogicFields() {
        return this.logicFields;
    }

    public List<String> getPrimaryKeyFields() {
        return this.primaryKeyFields;
    }
}

