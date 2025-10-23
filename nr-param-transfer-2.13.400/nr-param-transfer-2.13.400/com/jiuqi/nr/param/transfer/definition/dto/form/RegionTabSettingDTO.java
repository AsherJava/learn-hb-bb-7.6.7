/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.jiuqi.nr.definition.facade.DesignRegionTabSettingDefine
 *  com.jiuqi.nr.definition.facade.RegionTabSettingDefine
 *  com.jiuqi.nr.definition.internal.impl.RegionTabSettingData
 */
package com.jiuqi.nr.param.transfer.definition.dto.form;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.nr.definition.facade.DesignRegionTabSettingDefine;
import com.jiuqi.nr.definition.facade.RegionTabSettingDefine;
import com.jiuqi.nr.definition.internal.impl.RegionTabSettingData;

@JsonIgnoreProperties(ignoreUnknown=true)
public class RegionTabSettingDTO {
    private String id;
    private String title;
    private String displayCondition;
    private String filterCondition;
    private String bindingExpression;
    private String order;
    private int rowNum;
    private int languageType = 1;

    public int getLanguageType() {
        return this.languageType;
    }

    public void setLanguageType(int languageType) {
        this.languageType = languageType;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDisplayCondition() {
        return this.displayCondition;
    }

    public void setDisplayCondition(String displayCondition) {
        this.displayCondition = displayCondition;
    }

    public String getFilterCondition() {
        return this.filterCondition;
    }

    public void setFilterCondition(String filterCondition) {
        this.filterCondition = filterCondition;
    }

    public String getBindingExpression() {
        return this.bindingExpression;
    }

    public void setBindingExpression(String bindingExpression) {
        this.bindingExpression = bindingExpression;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public int getRowNum() {
        return this.rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    public DesignRegionTabSettingDefine value2Define() {
        RegionTabSettingData setting = new RegionTabSettingData();
        setting.setId(this.getId());
        setting.setTitle(this.getTitle());
        setting.setDisplayCondition(this.getDisplayCondition());
        setting.setFilterCondition(this.getFilterCondition());
        setting.setBindingExpression(this.getBindingExpression());
        setting.setOrder(this.getOrder());
        setting.setRowNum(this.getRowNum());
        return setting;
    }

    public static RegionTabSettingDTO valueOf(RegionTabSettingDefine setting) {
        if (setting == null) {
            return null;
        }
        RegionTabSettingDTO tabSetting = new RegionTabSettingDTO();
        tabSetting.setId(setting.getId());
        tabSetting.setTitle(setting.getTitle());
        tabSetting.setDisplayCondition(setting.getDisplayCondition());
        tabSetting.setFilterCondition(setting.getFilterCondition());
        tabSetting.setBindingExpression(setting.getBindingExpression());
        tabSetting.setOrder(setting.getOrder());
        tabSetting.setRowNum(setting.getRowNum());
        return tabSetting;
    }
}

