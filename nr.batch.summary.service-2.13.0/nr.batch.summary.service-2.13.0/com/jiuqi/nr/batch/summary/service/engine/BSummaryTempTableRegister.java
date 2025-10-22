/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.metadata.LogicField
 *  com.jiuqi.nr.common.temptable.BaseTempTableDefine
 */
package com.jiuqi.nr.batch.summary.service.engine;

import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.nr.common.temptable.BaseTempTableDefine;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class BSummaryTempTableRegister
extends BaseTempTableDefine {
    private final String TYPE_TITLE = "\u6279\u91cf\u6c47\u603b\u7ef4\u5ea6\u4e34\u65f6\u8868";
    private List<LogicField> logicFields;
    private List<String> primaryKeyFields;

    public List<LogicField> getLogicFields() {
        return this.logicFields;
    }

    public void setLogicFields(List<LogicField> logicFields) {
        this.logicFields = logicFields;
    }

    public List<String> getPrimaryKeyFields() {
        return this.primaryKeyFields;
    }

    public void setPrimaryKeyFields(List<String> primaryKeyFields) {
        this.primaryKeyFields = primaryKeyFields;
    }

    public boolean isDynamic() {
        return true;
    }

    public String getType() {
        return "B_SUMMARY";
    }

    public String getTypeTitle() {
        return "\u6279\u91cf\u6c47\u603b\u7ef4\u5ea6\u4e34\u65f6\u8868";
    }

    public String getModuleName() {
        return "\u6279\u91cf\u6c47\u603b\u7ef4\u5ea6\u4e34\u65f6\u8868";
    }

    public String getDesc() {
        return "\u6279\u91cf\u6c47\u603b\u7ef4\u5ea6\u4e34\u65f6\u8868";
    }
}

