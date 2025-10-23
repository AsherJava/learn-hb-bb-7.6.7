/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.metadata.LogicField
 *  com.jiuqi.nr.common.temptable.BaseTempTableDefine
 *  com.jiuqi.nr.common.temptable.IndexMeta
 */
package com.jiuqi.nr.datacopy.param;

import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.nr.common.temptable.BaseTempTableDefine;
import com.jiuqi.nr.common.temptable.IndexMeta;
import java.util.ArrayList;
import java.util.List;

public class CopyDataTempTable
extends BaseTempTableDefine {
    private List<LogicField> logicFields;
    private List<String> pks;
    private List<IndexMeta> indexes;

    public boolean isDynamic() {
        return true;
    }

    public String getType() {
        return "NR_DATACOPY";
    }

    public String getTypeTitle() {
        return "\u6570\u636e\u590d\u5236\u4e0d\u5b9a\u5217\u4e34\u65f6\u8868";
    }

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

    public List<IndexMeta> getIndexes() {
        if (this.indexes == null) {
            this.indexes = new ArrayList<IndexMeta>();
            return this.indexes;
        }
        return this.indexes;
    }

    public void setIndexes(List<IndexMeta> indexes) {
        this.indexes = indexes;
    }

    public String getModuleName() {
        return "\u6570\u636e\u590d\u5236\u4e34\u65f6\u8868\u6a21\u5757";
    }

    public String getDesc() {
        return "\u6570\u636e\u590d\u5236\u4e0d\u5b9a\u5217\u4e34\u65f6\u8868";
    }
}

