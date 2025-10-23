/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.controller.dto;

import com.jiuqi.nr.task.form.field.dto.DataFieldSettingDTO;
import com.jiuqi.nr.task.form.table.dto.DataTableDTO;
import java.util.ArrayList;
import java.util.List;

public class ReverseModeDTO {
    private List<DataTableDTO> tables;
    private List<DataFieldSettingDTO> fields;

    public ReverseModeDTO() {
        this.tables = new ArrayList<DataTableDTO>();
        this.fields = new ArrayList<DataFieldSettingDTO>();
    }

    public ReverseModeDTO(List<DataTableDTO> tables, List<DataFieldSettingDTO> fields) {
        this.tables = tables;
        this.fields = fields;
    }

    public List<DataTableDTO> getTables() {
        return this.tables;
    }

    public void setTables(List<DataTableDTO> tables) {
        this.tables = tables;
    }

    public List<DataFieldSettingDTO> getFields() {
        return this.fields;
    }

    public void setFields(List<DataFieldSettingDTO> fields) {
        this.fields = fields;
    }
}

