/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.metadata.LogicField
 *  com.jiuqi.nr.common.temptable.BaseTempTableDefine
 */
package com.jiuqi.np.dataengine.config;

import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.nr.common.temptable.BaseTempTableDefine;
import java.util.ArrayList;
import java.util.List;

public class TempTableMeta
extends BaseTempTableDefine {
    public final String TYPE = "NR_T_KEY";
    public final String TYPE_TITLE = "\u5f15\u64ce\u5355\u5217\u8868";
    private List<LogicField> logicFields = null;
    private List<String> pk = null;

    public TempTableMeta() {
    }

    public TempTableMeta(int dataType) {
        LogicField logicField = new LogicField();
        logicField.setFieldName("TEMP_KEY");
        logicField.setDataType(dataType);
        switch (dataType) {
            case 3: 
            case 10: {
                logicField.setPrecision(28);
                logicField.setScale(10);
                break;
            }
            case 4: {
                logicField.setPrecision(10);
                logicField.setSize(10);
                break;
            }
            case 1: {
                logicField.setPrecision(1);
                logicField.setScale(0);
                break;
            }
            default: {
                throw new UnsupportedOperationException("\u4e0d\u652f\u6301\u7684\u7c7b\u578b" + dataType);
            }
        }
        this.logicFields = new ArrayList<LogicField>();
        this.logicFields.add(logicField);
        ArrayList<String> pkFieldNames = new ArrayList<String>();
        pkFieldNames.add("TEMP_KEY");
        this.pk = pkFieldNames;
    }

    public boolean isDynamic() {
        return true;
    }

    public String getType() {
        return "NR_T_KEY";
    }

    public String getTypeTitle() {
        return "\u5f15\u64ce\u5355\u5217\u8868";
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
        return "\u5f15\u64ce\u4e34\u65f6\u8868\u5de5\u5177,\u975e\u5b57\u7b26\u7c7b\u578b\u65f6\u7684 OneKeyTable";
    }
}

