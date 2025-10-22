/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.metadata.LogicField
 *  com.jiuqi.nr.common.temptable.BaseTempTableDefine
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.np.dataengine.query.account;

import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.nr.common.temptable.BaseTempTableDefine;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;

public class AccountTempTable
extends BaseTempTableDefine {
    public final String TYPE = "NR_ACCOUNT";
    public final String TYPE_TITLE = "\u53f0\u8d26\u8868\u67e5\u8be2\u4e34\u65f6\u8868";
    private List<LogicField> logicFields = null;
    private List<String> pk = null;

    public AccountTempTable() {
    }

    public AccountTempTable(List<ColumnModelDefine> cols) {
        this.logicFields = AccountTempTable.buildTempFields(cols);
        List<String> pkFieldNames = AccountTempTable.pikFieldNames(cols);
        if (CollectionUtils.isEmpty(pkFieldNames)) {
            throw new RuntimeException("\u672a\u627e\u5230\u4e3b\u952e\u5b9a\u4e49,\u65e0\u6cd5\u751f\u4ea7\u6570\u636e\u8868");
        }
        this.pk = pkFieldNames;
    }

    public boolean isDynamic() {
        return true;
    }

    public String getType() {
        return "NR_ACCOUNT";
    }

    public String getTypeTitle() {
        return "\u53f0\u8d26\u8868\u67e5\u8be2\u4e34\u65f6\u8868";
    }

    public List<LogicField> getLogicFields() {
        return this.logicFields;
    }

    public List<String> getPrimaryKeyFields() {
        return this.pk;
    }

    public String getModuleName() {
        return "nr.dataengine";
    }

    public String getDesc() {
        return "\u53f0\u8d26\u5f15\u64ce\u67e5\u8be2\u6570\u636e\u521b\u5efa\u7684\u4e34\u65f6\u8868";
    }

    public static List<LogicField> buildTempFields(List<ColumnModelDefine> cols) {
        ArrayList<LogicField> logicFields = new ArrayList<LogicField>();
        for (ColumnModelDefine col : cols) {
            LogicField logicField = new LogicField();
            logicField.setFieldName(col.getName());
            logicField.setSize(col.getPrecision());
            logicField.setDataType(col.getColumnType().getValue());
            logicFields.add(logicField);
        }
        return logicFields;
    }

    public static List<String> pikFieldNames(List<ColumnModelDefine> cols) {
        return cols.stream().map(ColumnModelDefine::getName).collect(Collectors.toList());
    }
}

