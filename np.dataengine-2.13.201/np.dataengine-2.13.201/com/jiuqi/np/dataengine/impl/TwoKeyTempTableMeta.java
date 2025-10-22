/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.metadata.LogicField
 *  com.jiuqi.bi.database.temp.ITempTableMeta
 *  com.jiuqi.nr.common.temptable.BaseTempTableDefine
 */
package com.jiuqi.np.dataengine.impl;

import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.bi.database.temp.ITempTableMeta;
import com.jiuqi.nr.common.temptable.BaseTempTableDefine;
import java.util.ArrayList;
import java.util.List;

public class TwoKeyTempTableMeta
extends BaseTempTableDefine
implements ITempTableMeta {
    public static final String KEY1_COLUMN = "TEMP_KEY1";
    public static final String KEY2_COLUMN = "TEMP_KEY2";
    public static final String TYPE = "twoKey";

    public List<LogicField> getLogicFields() {
        ArrayList<LogicField> fields = new ArrayList<LogicField>();
        fields.add(this.createStringField(KEY1_COLUMN, 50));
        fields.add(this.createStringField(KEY2_COLUMN, 50));
        return fields;
    }

    public List<String> getPrimaryKeyFields() {
        ArrayList<String> primaryKeyFields = new ArrayList<String>();
        primaryKeyFields.add(KEY1_COLUMN);
        primaryKeyFields.add(KEY2_COLUMN);
        return primaryKeyFields;
    }

    public boolean isDynamic() {
        return false;
    }

    public String getType() {
        return TYPE;
    }

    public String getTypeTitle() {
        return "\u53cc\u952e\u5217\u4e34\u65f6\u8868";
    }

    public String getModuleName() {
        return "nr.dataengine";
    }

    public String getDesc() {
        return "\u5f15\u64ce\u4e34\u65f6\u8868\u5de5\u5177,\u53cc\u952e\u5217\u4e34\u65f6\u8868";
    }
}

