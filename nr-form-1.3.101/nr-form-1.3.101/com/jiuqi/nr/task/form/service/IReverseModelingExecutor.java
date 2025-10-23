/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.common.DataRegionKind
 */
package com.jiuqi.nr.task.form.service;

import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.task.form.controller.dto.ReverseModeParam;
import com.jiuqi.nr.task.form.controller.dto.ReverseModeRegionDTO;
import com.jiuqi.nr.task.form.field.dto.DataFieldDTO;
import com.jiuqi.nr.task.form.table.dto.DataTableDTO;
import java.util.List;

public interface IReverseModelingExecutor {
    public boolean available(DataRegionKind var1);

    public DataTableDTO createTable(ReverseModeParam var1, ReverseModeRegionDTO var2);

    public <T extends DataFieldDTO> List<T> createField(ReverseModeParam var1, ReverseModeRegionDTO var2);
}

