/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.metadata.LogicField
 *  com.jiuqi.bi.database.temp.ITempTableMeta
 */
package com.jiuqi.nr.common.temptable.dynamic;

import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.bi.database.temp.ITempTableMeta;
import java.util.Collections;
import java.util.List;

public class NRDynamicTempTableMeta
implements ITempTableMeta {
    public static final String type = "NR_DYNAMIC_TEMP_TABLE";
    public static final String typeTitle = "\u62a5\u8868-\u52a8\u6001\u4e34\u65f6\u8868";
    private final ITempTableMeta meta;
    private final List<LogicField> columns;
    private final String primaryKey;

    public NRDynamicTempTableMeta(ITempTableMeta meta, List<LogicField> columns, String primaryKey) {
        this.meta = meta;
        this.columns = columns;
        this.primaryKey = primaryKey;
    }

    public String getTypeTitle() {
        return typeTitle;
    }

    public String getType() {
        return type;
    }

    public ITempTableMeta getMeta() {
        return this.meta;
    }

    public List<LogicField> getLogicFields() {
        return this.columns;
    }

    public List<String> getPrimaryKeyFields() {
        return Collections.singletonList(this.primaryKey);
    }
}

