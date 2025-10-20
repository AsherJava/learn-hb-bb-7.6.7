/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database.temp;

import com.jiuqi.bi.database.metadata.LogicField;
import java.util.List;

public interface ITempTableMeta {
    public String getType();

    default public String getTypeTitle() {
        return this.getType();
    }

    public List<LogicField> getLogicFields();

    public List<String> getPrimaryKeyFields();
}

