/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.controller.vo;

import com.jiuqi.nr.task.form.field.dto.DataFieldSettingDTO;
import com.jiuqi.nr.task.form.table.dto.DataTableDTO;
import java.util.HashMap;
import java.util.Map;

public class FindFieldVO {
    Map<String, DataTableDTO> tables = new HashMap<String, DataTableDTO>();
    Map<String, Map<String, DataFieldSettingDTO>> fields = new HashMap<String, Map<String, DataFieldSettingDTO>>();

    public Map<String, DataTableDTO> getTables() {
        return this.tables;
    }

    public void setTables(Map<String, DataTableDTO> tables) {
        this.tables = tables;
    }

    public Map<String, Map<String, DataFieldSettingDTO>> getFields() {
        return this.fields;
    }

    public void setFields(Map<String, Map<String, DataFieldSettingDTO>> fields) {
        this.fields = fields;
    }

    public void addTable(DataTableDTO dataTableDTO) {
        this.tables.put(dataTableDTO.getCode(), dataTableDTO);
    }

    public void addField(String tableCode, DataFieldSettingDTO dataFieldSettingDTO) {
        Map map = this.fields.computeIfAbsent(tableCode, k -> new HashMap());
        map.put(dataFieldSettingDTO.getCode(), dataFieldSettingDTO);
    }
}

