/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 */
package com.jiuqi.nr.datascheme.internal.service;

import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.datascheme.internal.dto.DataTableDTO;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public interface DataTableService {
    public DataTableDTO getDataTable(String var1);

    public DataTableDTO getDataTableByCode(String var1);

    public List<DataTableDTO> getLatestDataTableByScheme(String var1);

    public Instant getLatestDataTableUpdateTime(String var1);

    public List<DataTableDTO> getDataTables(List<String> var1);

    public List<DataTableDTO> getAllDataTable(String var1);

    public List<DataTableDTO> getDataTableByGroup(String var1);

    public List<DataTableDTO> getDataTableByScheme(String var1);

    default public List<DataTableDTO> getAllDataTableBySchemeAndTypes(String schemeKey, DataTableType ... types) {
        List<DataTableDTO> dataTables = this.getDataTableByScheme(schemeKey);
        if (null == types || 0 == types.length) {
            return dataTables;
        }
        int tableTypes = Arrays.stream(types).mapToInt(DataTableType::getValue).reduce(0, (a, b) -> a | b);
        return dataTables.stream().filter(t -> 0 != (t.getDataTableType().getValue() & tableTypes)).collect(Collectors.toList());
    }

    public List<DataTableDTO> searchBy(String var1, String var2, int var3);

    public List<DataTableDTO> searchBy(List<String> var1, String var2, int var3);
}

