/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.dto;

import com.jiuqi.nr.task.form.dto.DataSchemeDefineDTO;
import com.jiuqi.nr.task.form.dto.EntityFieldDTO;
import com.jiuqi.nr.task.form.ext.dto.ConfigDTO;
import com.jiuqi.nr.task.form.field.dto.DataFieldSettingDTO;
import com.jiuqi.nr.task.form.form.dto.FormDTO;
import com.jiuqi.nr.task.form.formstyle.dto.FormStyleDTO;
import com.jiuqi.nr.task.form.link.dto.DataLinkSettingDTO;
import com.jiuqi.nr.task.form.region.dto.DataRegionSettingDTO;
import com.jiuqi.nr.task.form.table.dto.DataTableDTO;
import java.util.List;
import java.util.Set;

public class SimpleFormDesignerDTO {
    private FormDTO form;
    private String formSchemeKey;
    private String taskKey;
    private String dataSchemeKey;
    private DataSchemeDefineDTO dataScheme;
    private FormStyleDTO formStyle;
    private List<DataLinkSettingDTO> dataLinks;
    private Set<DataFieldSettingDTO> dataFields;
    private List<DataRegionSettingDTO> dataRegions;
    private List<ConfigDTO> componentConfigs;
    private List<DataTableDTO> dataTables;
    private List<EntityFieldDTO> entityFields;

    public FormDTO getForm() {
        return this.form;
    }

    public void setForm(FormDTO form) {
        this.form = form;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }

    public FormStyleDTO getFormStyle() {
        return this.formStyle;
    }

    public void setFormStyle(FormStyleDTO formStyle) {
        this.formStyle = formStyle;
    }

    public List<DataLinkSettingDTO> getDataLinks() {
        return this.dataLinks;
    }

    public void setDataLinks(List<DataLinkSettingDTO> dataLinks) {
        this.dataLinks = dataLinks;
    }

    public Set<DataFieldSettingDTO> getDataFields() {
        return this.dataFields;
    }

    public void setDataFields(Set<DataFieldSettingDTO> dataFields) {
        this.dataFields = dataFields;
    }

    public List<DataRegionSettingDTO> getDataRegions() {
        return this.dataRegions;
    }

    public void setDataRegions(List<DataRegionSettingDTO> dataRegions) {
        this.dataRegions = dataRegions;
    }

    public List<ConfigDTO> getComponentConfigs() {
        return this.componentConfigs;
    }

    public void setComponentConfigs(List<ConfigDTO> componentConfigs) {
        this.componentConfigs = componentConfigs;
    }

    public List<DataTableDTO> getDataTables() {
        return this.dataTables;
    }

    public void setDataTables(List<DataTableDTO> dataTables) {
        this.dataTables = dataTables;
    }

    public List<EntityFieldDTO> getEntityFields() {
        return this.entityFields;
    }

    public void setEntityFields(List<EntityFieldDTO> entityFields) {
        this.entityFields = entityFields;
    }

    public DataSchemeDefineDTO getDataScheme() {
        return this.dataScheme;
    }

    public void setDataScheme(DataSchemeDefineDTO dataScheme) {
        this.dataScheme = dataScheme;
    }
}

