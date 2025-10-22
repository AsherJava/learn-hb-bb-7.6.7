/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.formsetting.vo;

import com.jiuqi.gcreport.formsetting.vo.DataLinkSettingVO;
import com.jiuqi.gcreport.formsetting.vo.DataRegionSettingVO;
import com.jiuqi.gcreport.formsetting.vo.FieldSettingVO;
import com.jiuqi.gcreport.formsetting.vo.FormSettingVO;
import com.jiuqi.gcreport.formsetting.vo.PageSettingVO;
import java.util.List;

public class SettingVO {
    private FormSettingVO formSetting;
    private DataRegionSettingVO dataRegionSetting;
    private List<DataLinkSettingVO> dataLinks;
    private List<FieldSettingVO> fields;
    private String readOnlyFormula;
    private List<PageSettingVO> pageSettings;
    private String formulaSchemeId;

    public FormSettingVO getFormSetting() {
        return this.formSetting;
    }

    public void setFormSetting(FormSettingVO formSetting) {
        this.formSetting = formSetting;
    }

    public DataRegionSettingVO getDataRegionSetting() {
        return this.dataRegionSetting;
    }

    public void setDataRegionSetting(DataRegionSettingVO dataRegionSetting) {
        this.dataRegionSetting = dataRegionSetting;
    }

    public List<DataLinkSettingVO> getDataLinks() {
        return this.dataLinks;
    }

    public void setDataLinks(List<DataLinkSettingVO> dataLinks) {
        this.dataLinks = dataLinks;
    }

    public List<FieldSettingVO> getFields() {
        return this.fields;
    }

    public void setFields(List<FieldSettingVO> fields) {
        this.fields = fields;
    }

    public String getReadOnlyFormula() {
        return this.readOnlyFormula;
    }

    public void setReadOnlyFormula(String readOnlyFormula) {
        this.readOnlyFormula = readOnlyFormula;
    }

    public List<PageSettingVO> getPageSettings() {
        return this.pageSettings;
    }

    public void setPageSettings(List<PageSettingVO> pageSettings) {
        this.pageSettings = pageSettings;
    }

    public String getFormulaSchemeId() {
        return this.formulaSchemeId;
    }

    public void setFormulaSchemeId(String formulaSchemeId) {
        this.formulaSchemeId = formulaSchemeId;
    }

    public String toString() {
        return "SettingVO [formSetting=" + this.formSetting + ", dataRegionSetting=" + this.dataRegionSetting + ", dataLinks=" + this.dataLinks + ", readOnlyFormula=" + this.readOnlyFormula + ", pageSettings=" + this.pageSettings + ", formulaSchemeId=" + this.formulaSchemeId + "]";
    }
}

