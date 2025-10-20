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

public class KeyValueTempTableMeta
extends AbstractTempTableMeta
implements ITempTableMeta {
    public static final String KEY_COLUMN = "TEMP_KEY";
    public static final String VALUE_COLUMN = "TEMP_VALUE";

    @Override
    public String getType() {
        return "KeyValue";
    }

    @Override
    public String getTypeTitle() {
        return "k,v\u7ed3\u6784\u7684\u4e34\u65f6\u8868";
    }

    @Override
    public List<LogicField> getLogicFields() {
        ArrayList<LogicField> fields = new ArrayList<LogicField>();
        fields.add(this.createStringField(KEY_COLUMN, 50));
        fields.add(this.createStringField(VALUE_COLUMN, 50));
        return fields;
    }

    @Override
    public List<String> getPrimaryKeyFields() {
        return Collections.singletonList(KEY_COLUMN);
    }
}

