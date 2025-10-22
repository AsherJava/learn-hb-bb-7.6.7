/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.metadata.LogicField
 *  com.jiuqi.nr.common.temptable.BaseTempTableDefine
 */
package com.jiuqi.nr.data.engine.gather.util;

import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.nr.common.temptable.BaseTempTableDefine;
import java.util.ArrayList;
import java.util.List;

public class GatherTempTable
extends BaseTempTableDefine {
    private List<LogicField> logicFields;
    private List<String> pks;

    public List<LogicField> getLogicFields() {
        if (this.logicFields == null) {
            this.logicFields = new ArrayList<LogicField>();
            return this.logicFields;
        }
        return this.logicFields;
    }

    public void setLogicFields(List<LogicField> logicFields) {
        this.logicFields = logicFields;
    }

    public List<String> getPrimaryKeyFields() {
        if (this.pks == null) {
            this.pks = new ArrayList<String>();
            return this.pks;
        }
        return this.pks;
    }

    public void setPrimaryKeyFields(List<String> pks) {
        this.pks = pks;
    }

    public boolean isDynamic() {
        return true;
    }

    public String getType() {
        return "NR_GT";
    }

    public String getTypeTitle() {
        return "\u6c47\u603b\u8fc7\u7a0b\u4e2d\u4e0d\u5b9a\u5217\u4e34\u65f6\u8868";
    }

    public String getModuleName() {
        return "\u6c47\u603b\u4e34\u65f6\u8868\u6a21\u5757";
    }

    public String getDesc() {
        return "\u6c47\u603b\u8fc7\u7a0b\u4e2d\u4e0d\u5b9a\u5217\u4e34\u65f6\u8868";
    }
}

