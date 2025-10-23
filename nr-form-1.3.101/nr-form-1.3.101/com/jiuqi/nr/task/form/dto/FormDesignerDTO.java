/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.dto;

import com.jiuqi.nr.task.form.dto.ConditionStyleDTO;
import com.jiuqi.nr.task.form.ext.dto.ConfigDTO;
import com.jiuqi.nr.task.form.field.dto.DataFieldSettingDTO;
import com.jiuqi.nr.task.form.form.dto.FormDTO;
import com.jiuqi.nr.task.form.formstyle.dto.FormStyleDTO;
import com.jiuqi.nr.task.form.link.dto.DataLinkSettingDTO;
import com.jiuqi.nr.task.form.link.dto.FilterTemplateDTO;
import com.jiuqi.nr.task.form.link.dto.LinkMappingDTO;
import com.jiuqi.nr.task.form.region.dto.DataRegionSettingDTO;
import com.jiuqi.nr.task.form.table.dto.DataTableDTO;
import java.util.List;
import org.springframework.util.StringUtils;

public class FormDesignerDTO {
    private FormDTO form;
    private FormStyleDTO formStyle;
    private List<DataRegionSettingDTO> regionSetting;
    private List<DataLinkSettingDTO> dataLinkSetting;
    private List<DataFieldSettingDTO> dataFieldSetting;
    private List<LinkMappingDTO> dataLinkMapping;
    private List<ConditionStyleDTO> conditionStyle;
    private List<ConfigDTO> componentConfigs;
    private List<DataTableDTO> tableSetting;
    private Boolean updateFormula;
    private Boolean updatePrintTemplate;
    private String progressId;
    private List<FilterTemplateDTO> filterTemplates;

    public FormDTO getForm() {
        return this.form;
    }

    public void setForm(FormDTO form) {
        this.form = form;
    }

    public FormStyleDTO getFormStyle() {
        return this.formStyle;
    }

    public void setFormStyle(FormStyleDTO formStyle) {
        this.formStyle = formStyle;
    }

    public List<DataRegionSettingDTO> getRegionSetting() {
        return this.regionSetting;
    }

    public void setRegionSetting(List<DataRegionSettingDTO> regionSetting) {
        this.regionSetting = regionSetting;
    }

    public List<DataLinkSettingDTO> getDataLinkSetting() {
        return this.dataLinkSetting;
    }

    public void setDataLinkSetting(List<DataLinkSettingDTO> dataLinkSetting) {
        this.dataLinkSetting = dataLinkSetting;
    }

    public List<LinkMappingDTO> getDataLinkMapping() {
        return this.dataLinkMapping;
    }

    public void setDataLinkMapping(List<LinkMappingDTO> dataLinkMapping) {
        this.dataLinkMapping = dataLinkMapping;
    }

    public List<ConditionStyleDTO> getConditionStyle() {
        return this.conditionStyle;
    }

    public void setConditionStyle(List<ConditionStyleDTO> conditionStyle) {
        this.conditionStyle = conditionStyle;
    }

    public List<DataFieldSettingDTO> getDataFieldSetting() {
        return this.dataFieldSetting;
    }

    public void setDataFieldSetting(List<DataFieldSettingDTO> dataFieldSetting) {
        this.dataFieldSetting = dataFieldSetting;
    }

    public List<ConfigDTO> getComponentConfigs() {
        return this.componentConfigs;
    }

    public void setComponentConfigs(List<ConfigDTO> componentConfigs) {
        this.componentConfigs = componentConfigs;
    }

    public List<DataTableDTO> getTableSetting() {
        return this.tableSetting;
    }

    public void setTableSetting(List<DataTableDTO> tableSetting) {
        this.tableSetting = tableSetting;
    }

    public Boolean getUpdateFormula() {
        return this.updateFormula;
    }

    public boolean isUpdateFormula() {
        return this.updateFormula != null && this.updateFormula != false && StringUtils.hasLength(this.progressId);
    }

    public void setUpdateFormula(Boolean updateFormula) {
        this.updateFormula = updateFormula;
    }

    public String getProgressId() {
        return this.progressId;
    }

    public void setProgressId(String progressId) {
        this.progressId = progressId;
    }

    public List<FilterTemplateDTO> getFilterTemplates() {
        return this.filterTemplates;
    }

    public void setFilterTemplates(List<FilterTemplateDTO> filterTemplates) {
        this.filterTemplates = filterTemplates;
    }

    public boolean isUpdatePrintTemplate() {
        return this.updatePrintTemplate != null && this.updatePrintTemplate != false;
    }

    public void setUpdatePrintTemplate(Boolean updatePrintTemplate) {
        this.updatePrintTemplate = updatePrintTemplate;
    }
}

