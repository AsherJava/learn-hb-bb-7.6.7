/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database.temp;

import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.bi.database.temp.AbstractTempTableMeta;
import com.jiuqi.bi.database.temp.ITempTableMeta;
import java.util.Collections;
import java.util.List;

public class OneKeyTempTableMeta
extends AbstractTempTableMeta
implements ITempTableMeta {
    public static final String KEY_COLUMN = "TEMP_KEY";

    @Override
    public String getType() {
        return "OneKey";
    }

    @Override
    public String getTypeTitle() {
        return "\u53ea\u6709\u5355\u5b57\u7b26\u5217\u7684\u4e34\u65f6\u8868";
    }

    @Override
    public List<LogicField> getLogicFields() {
        LogicField field = this.createStringField(KEY_COLUMN, 50);
        return Collections.singletonList(field);
    }

    @Override
    public List<String> getPrimaryKeyFields() {
        return Collections.singletonList(KEY_COLUMN);
    }
}

