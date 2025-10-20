/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.ruler.intf;

import com.jiuqi.va.biz.intf.data.DataField;
import com.jiuqi.va.biz.intf.data.DataRow;
import com.jiuqi.va.biz.intf.data.DataTable;

public interface TriggerEvent {
    public String getTriggerType();

    public DataTable getTable();

    public DataRow getRow();

    public DataField getField();
}

