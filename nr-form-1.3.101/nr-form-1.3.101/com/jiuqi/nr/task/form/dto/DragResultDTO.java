/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.dto;

import com.jiuqi.nr.task.form.dto.EntityFieldDTO;
import com.jiuqi.nr.task.form.field.dto.DataFieldDTO;
import com.jiuqi.nr.task.form.table.dto.DataTableDTO;
import java.util.List;

public class DragResultDTO {
    private DataTableDTO table;
    private List<DataFieldDTO> fields;
    private List<EntityFieldDTO> entityFields;

    public DataTableDTO getTable() {
        return this.table;
    }

    public void setTable(DataTableDTO table) {
        this.table = table;
    }

    public List<DataFieldDTO> getFields() {
        return this.fields;
    }

    public void setFields(List<DataFieldDTO> fields) {
        this.fields = fields;
    }

    public List<EntityFieldDTO> getEntityFields() {
        return this.entityFields;
    }

    public void setEntityFields(List<EntityFieldDTO> entityFields) {
        this.entityFields = entityFields;
    }
}

