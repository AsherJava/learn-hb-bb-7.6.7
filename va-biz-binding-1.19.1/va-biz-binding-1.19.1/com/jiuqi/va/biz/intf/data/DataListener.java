/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.data;

import com.jiuqi.va.biz.intf.data.DataField;
import com.jiuqi.va.biz.intf.data.DataRow;
import com.jiuqi.va.biz.intf.data.DataTable;

public interface DataListener {
    default public void afterSetValue(DataTable table, DataRow row, DataField field) {
    }

    default public void afterAddRow(DataTable table, DataRow row) {
    }

    default public void afterDelRow(DataTable table, DataRow row) {
    }

    default public void afterReload(DataTable table) {
    }
}

