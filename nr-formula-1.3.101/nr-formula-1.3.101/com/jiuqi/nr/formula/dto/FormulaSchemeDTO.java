/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.jiuqi.nr.definition.common.FormulaSchemeDisplayMode
 *  com.jiuqi.nr.definition.common.FormulaSchemeType
 */
package com.jiuqi.nr.formula.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jiuqi.nr.definition.common.FormulaSchemeDisplayMode;
import com.jiuqi.nr.definition.common.FormulaSchemeType;
import java.util.Date;

public class FormulaSchemeDTO {
    private String formSchemeKey;
    private String key;
    private String title;
    private FormulaSchemeType formulaSchemeType;
    private String order;
    private Boolean showScheme;
    private Boolean defaultScheme;
    private FormulaSchemeDisplayMode displayMode;
    private String level;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date updateTime;
    private int menuApply = 0;

    public Boolean getDefaultScheme() {
        return this.defaultScheme;
    }

    public void setDefaultScheme(Boolean value) {
        this.defaultScheme = value;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getLevel() {
        return this.level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public FormulaSchemeType getFormulaSchemeType() {
        return this.formulaSchemeType;
    }

    public void setFormulaSchemeType(FormulaSchemeType value) {
        this.formulaSchemeType = value;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String value) {
        this.key = value;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String value) {
        this.order = value;
    }

    public Boolean getShowScheme() {
        return this.showScheme;
    }

    public void setShowScheme(Boolean value) {
        this.showScheme = value;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String value) {
        this.title = value;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date value) {
        this.updateTime = value;
    }

    public FormulaSchemeDisplayMode getDisplayMode() {
        return this.displayMode;
    }

    public void setDisplayMode(FormulaSchemeDisplayMode displayMode) {
        this.displayMode = displayMode;
    }

    public int getMenuApply() {
        return this.menuApply;
    }

    public void setMenuApply(int menuApply) {
        this.menuApply = menuApply;
    }
}

