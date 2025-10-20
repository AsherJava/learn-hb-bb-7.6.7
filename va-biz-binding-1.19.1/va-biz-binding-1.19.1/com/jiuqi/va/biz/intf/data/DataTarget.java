/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.data;

import com.jiuqi.va.biz.intf.data.DataTargetType;
import java.util.UUID;

public interface DataTarget {
    public DataTargetType getTargetType();

    public String getTableName();

    public String getFieldName();

    public int getRowIndex();

    public UUID getRowID();
}

