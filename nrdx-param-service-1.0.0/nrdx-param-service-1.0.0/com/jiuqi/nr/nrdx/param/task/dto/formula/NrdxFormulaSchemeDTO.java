/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.fasterxml.jackson.annotation.JsonFormat$Shape
 *  com.jiuqi.nr.definition.common.FormulaSchemeDisplayMode
 *  com.jiuqi.nr.definition.common.FormulaSchemeType
 *  com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.param.transfer.definition.dto.DesParamLanguageDTO
 *  com.jiuqi.nr.param.transfer.definition.dto.task.FormulaConditionDTO
 */
package com.jiuqi.nr.nrdx.param.task.dto.formula;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jiuqi.nr.definition.common.FormulaSchemeDisplayMode;
import com.jiuqi.nr.definition.common.FormulaSchemeType;
import com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.param.transfer.definition.dto.DesParamLanguageDTO;
import com.jiuqi.nr.param.transfer.definition.dto.task.FormulaConditionDTO;
import java.util.Date;
import java.util.List;

public class NrdxFormulaSchemeDTO {
    private String formSchemeKey;
    private String description;
    private FormulaSchemeDisplayMode displayMode;
    private String key;
    private String title;
    private String order;
    private String version;
    private String ownerLevelAndId;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH-mm-ss.sss zzz")
    private Date updateTime;
    private FormulaSchemeType formulaSchemeType;
    private boolean defaultScheme;
    private boolean showScheme;
    private List<FormulaConditionDTO> formulaConditions;
    private DesParamLanguageDTO desParamLanguageDTO;

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public FormulaSchemeDisplayMode getDisplayMode() {
        return this.displayMode;
    }

    public void setDisplayMode(FormulaSchemeDisplayMode displayMode) {
        this.displayMode = displayMode;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getOwnerLevelAndId() {
        return this.ownerLevelAndId;
    }

    public void setOwnerLevelAndId(String ownerLevelAndId) {
        this.ownerLevelAndId = ownerLevelAndId;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public FormulaSchemeType getFormulaSchemeType() {
        return this.formulaSchemeType;
    }

    public void setFormulaSchemeType(FormulaSchemeType formulaSchemeType) {
        this.formulaSchemeType = formulaSchemeType;
    }

    public boolean isDefaultScheme() {
        return this.defaultScheme;
    }

    public void setDefaultScheme(boolean defaultScheme) {
        this.defaultScheme = defaultScheme;
    }

    public boolean isShowScheme() {
        return this.showScheme;
    }

    public void setShowScheme(boolean showScheme) {
        this.showScheme = showScheme;
    }

    public void value2Define(DesignFormulaSchemeDefine formulaSchemeDefine) {
        formulaSchemeDefine.setDefault(this.isDefaultScheme());
        formulaSchemeDefine.setKey(this.getKey());
        formulaSchemeDefine.setOrder(this.getOrder());
        formulaSchemeDefine.setFormSchemeKey(this.getFormSchemeKey());
        formulaSchemeDefine.setOwnerLevelAndId(this.getOwnerLevelAndId());
        formulaSchemeDefine.setTitle(this.getTitle());
        formulaSchemeDefine.setUpdateTime(this.getUpdateTime());
        formulaSchemeDefine.setVersion(this.getVersion());
        formulaSchemeDefine.setShow(this.isShowScheme());
        formulaSchemeDefine.setFormulaSchemeType(this.getFormulaSchemeType());
        formulaSchemeDefine.setDescription(this.getDescription());
        formulaSchemeDefine.setDisplayMode(this.getDisplayMode());
    }

    public static NrdxFormulaSchemeDTO valueOf(FormulaSchemeDefine formulaSchemeDefine) {
        if (formulaSchemeDefine == null) {
            return null;
        }
        NrdxFormulaSchemeDTO nrdxFormulaSchemeDTO = new NrdxFormulaSchemeDTO();
        nrdxFormulaSchemeDTO.setDefaultScheme(formulaSchemeDefine.isDefault());
        nrdxFormulaSchemeDTO.setKey(formulaSchemeDefine.getKey());
        nrdxFormulaSchemeDTO.setOrder(formulaSchemeDefine.getOrder());
        nrdxFormulaSchemeDTO.setFormSchemeKey(formulaSchemeDefine.getFormSchemeKey());
        nrdxFormulaSchemeDTO.setOwnerLevelAndId(formulaSchemeDefine.getOwnerLevelAndId());
        nrdxFormulaSchemeDTO.setTitle(formulaSchemeDefine.getTitle());
        nrdxFormulaSchemeDTO.setUpdateTime(formulaSchemeDefine.getUpdateTime());
        nrdxFormulaSchemeDTO.setVersion(formulaSchemeDefine.getVersion());
        nrdxFormulaSchemeDTO.setShowScheme(formulaSchemeDefine.isShow());
        nrdxFormulaSchemeDTO.setFormulaSchemeType(formulaSchemeDefine.getFormulaSchemeType());
        nrdxFormulaSchemeDTO.setDescription(formulaSchemeDefine.getDescription());
        nrdxFormulaSchemeDTO.setDisplayMode(formulaSchemeDefine.getDisplayMode());
        return nrdxFormulaSchemeDTO;
    }

    public List<FormulaConditionDTO> getFormulaConditions() {
        return this.formulaConditions;
    }

    public void setFormulaConditions(List<FormulaConditionDTO> formulaConditions) {
        this.formulaConditions = formulaConditions;
    }

    public DesParamLanguageDTO getDesParamLanguageDTO() {
        return this.desParamLanguageDTO;
    }

    public void setDesParamLanguageDTO(DesParamLanguageDTO desParamLanguageDTO) {
        this.desParamLanguageDTO = desParamLanguageDTO;
    }
}

