/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.fasterxml.jackson.annotation.JsonFormat$Shape
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.jiuqi.nr.definition.common.FormulaSchemeDisplayMode
 *  com.jiuqi.nr.definition.common.FormulaSchemeType
 *  com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 */
package com.jiuqi.nr.param.transfer.definition.dto.formula;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.nr.definition.common.FormulaSchemeDisplayMode;
import com.jiuqi.nr.definition.common.FormulaSchemeType;
import com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown=true)
public class FormulaSchemeInfoDTO {
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

    public static FormulaSchemeInfoDTO valueOf(FormulaSchemeDefine formulaSchemeDefine) {
        if (formulaSchemeDefine == null) {
            return null;
        }
        FormulaSchemeInfoDTO formulaScheme = new FormulaSchemeInfoDTO();
        formulaScheme.setDefaultScheme(formulaSchemeDefine.isDefault());
        formulaScheme.setKey(formulaSchemeDefine.getKey());
        formulaScheme.setOrder(formulaSchemeDefine.getOrder());
        formulaScheme.setFormSchemeKey(formulaSchemeDefine.getFormSchemeKey());
        formulaScheme.setOwnerLevelAndId(formulaSchemeDefine.getOwnerLevelAndId());
        formulaScheme.setTitle(formulaSchemeDefine.getTitle());
        formulaScheme.setUpdateTime(formulaSchemeDefine.getUpdateTime());
        formulaScheme.setVersion(formulaSchemeDefine.getVersion());
        formulaScheme.setShowScheme(formulaSchemeDefine.isShow());
        formulaScheme.setFormulaSchemeType(formulaSchemeDefine.getFormulaSchemeType());
        formulaScheme.setDescription(formulaSchemeDefine.getDescription());
        formulaScheme.setDisplayMode(formulaSchemeDefine.getDisplayMode());
        return formulaScheme;
    }
}

