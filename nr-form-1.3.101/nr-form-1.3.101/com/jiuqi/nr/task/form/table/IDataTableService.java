/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.table;

import com.jiuqi.nr.task.form.table.dto.DataTableDTO;
import java.util.List;

public interface IDataTableService {
    public List<DataTableDTO> listDataTablesByForm(String var1);

    public List<DataTableDTO> listDataTables(List<String> var1);

    public DataTableDTO getTable(String var1);

    public void saveTableSetting(List<DataTableDTO> var1);

    public void insertReverseModeTables(List<DataTableDTO> var1);
}

