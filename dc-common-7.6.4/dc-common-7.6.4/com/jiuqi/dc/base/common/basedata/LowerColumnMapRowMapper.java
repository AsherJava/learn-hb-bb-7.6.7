/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.ColumnMapRowMapper
 */
package com.jiuqi.dc.base.common.basedata;

import org.springframework.jdbc.core.ColumnMapRowMapper;

public class LowerColumnMapRowMapper
extends ColumnMapRowMapper {
    protected String getColumnKey(String columnName) {
        return columnName.toLowerCase();
    }
}

