/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.base.common.jdbc.sqlhandler.impl;

import com.jiuqi.dc.base.common.jdbc.sqlhandler.impl.GaussSqlHandler;

public class OpenGaussSqlHandler
extends GaussSqlHandler {
    @Override
    public String getVirtualTable() {
        return "sys_dummy";
    }

    @Override
    public String toDecimal(String field, int precision, int scale) {
        return "CAST(" + field + " AS NUMERIC)";
    }
}

