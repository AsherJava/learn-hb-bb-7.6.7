/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.metadata.LogicField
 *  com.jiuqi.nr.common.temptable.BaseTempTableDefine
 */
package com.jiuqi.nr.integritycheck.utils;

import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.nr.common.temptable.BaseTempTableDefine;
import java.util.ArrayList;
import java.util.List;

public class IntegrityCheckTempTableDefine
extends BaseTempTableDefine {
    private final String TYPE_TITLE = "\u8868\u5b8c\u6574\u6027\u68c0\u67e5\u4e34\u65f6\u8868";
    private List<LogicField> logicFields;
    private List<String> pks;

    public void setLogicFields(List<LogicField> logicFields) {
        this.logicFields = logicFields;
    }

    public List<LogicField> getLogicFields() {
        if (this.logicFields == null) {
            this.logicFields = new ArrayList<LogicField>();
            return this.logicFields;
        }
        return this.logicFields;
    }

    public void setPks(List<String> pks) {
        this.pks = pks;
    }

    public List<String> getPrimaryKeyFields() {
        if (this.pks == null) {
            this.pks = new ArrayList<String>();
            return this.pks;
        }
        return this.pks;
    }

    public boolean isDynamic() {
        return true;
    }

    public String getType() {
        return "NR_I_CHECK";
    }

    public String getTypeTitle() {
        return "\u8868\u5b8c\u6574\u6027\u68c0\u67e5\u4e34\u65f6\u8868";
    }

    public String getModuleName() {
        return "\u8868\u5b8c\u6574\u6027\u68c0\u67e5\u4e34\u65f6\u8868";
    }

    public String getDesc() {
        return "\u8868\u5b8c\u6574\u6027\u68c0\u67e5\u4e34\u65f6\u8868";
    }
}

