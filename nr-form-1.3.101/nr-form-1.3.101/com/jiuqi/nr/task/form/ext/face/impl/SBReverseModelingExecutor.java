/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 */
package com.jiuqi.nr.task.form.ext.face.impl;

import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.task.form.controller.dto.ReverseModeParam;
import com.jiuqi.nr.task.form.controller.dto.ReverseModeRegionDTO;
import com.jiuqi.nr.task.form.service.impl.FloatReverseModelingExecutor;
import com.jiuqi.nr.task.form.table.dto.DataTableDTO;

public class SBReverseModelingExecutor
extends FloatReverseModelingExecutor {
    @Override
    public DataTableDTO createTable(ReverseModeParam param, ReverseModeRegionDTO region) {
        DataTableDTO table = super.createTable(param, region);
        table.setDataTableType(DataTableType.ACCOUNT.getValue());
        return table;
    }
}

