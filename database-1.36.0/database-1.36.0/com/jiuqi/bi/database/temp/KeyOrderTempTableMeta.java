/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database.temp;

import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.bi.database.temp.AbstractTempTableMeta;
import com.jiuqi.bi.database.temp.ITempTableMeta;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KeyOrderTempTableMeta
extends AbstractTempTableMeta
implements ITempTableMeta {
    public static final String KEY_COLUMN = "TEMP_KEY";
    public static final String ORDER_COLUMN = "TEMP_ORDER";

    @Override
    public String getType() {
        return "KeyOrder";
    }

    @Override
    public String getTypeTitle() {
        return "\u5e26\u952e\u5217\u4e0e\u987a\u5e8f\u53f7\u7684\u4e34\u65f6\u8868";
    }

    @Override
    public List<LogicField> getLogicFields() {
        ArrayList<LogicField> fields = new ArrayList<LogicField>();
        fields.add(this.createStringField(KEY_COLUMN, 50));
        fields.add(this.createIntegerField(ORDER_COLUMN, 10));
        return fields;
    }

    @Override
    public List<String> getPrimaryKeyFields() {
        return Collections.singletonList(KEY_COLUMN);
    }
}

