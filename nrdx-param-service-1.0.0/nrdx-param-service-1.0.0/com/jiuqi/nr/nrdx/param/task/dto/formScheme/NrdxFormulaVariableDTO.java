/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.fasterxml.jackson.annotation.JsonFormat$Shape
 *  com.jiuqi.nr.definition.facade.FormulaVariDefine
 *  com.jiuqi.nr.definition.internal.impl.DesignFormulaVariableDefineImpl
 */
package com.jiuqi.nr.nrdx.param.task.dto.formScheme;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jiuqi.nr.definition.facade.FormulaVariDefine;
import com.jiuqi.nr.definition.internal.impl.DesignFormulaVariableDefineImpl;
import java.util.Date;

public class NrdxFormulaVariableDTO {
    private String key;
    private String code;
    private String title;
    private int type;
    private String formSchemeKey;
    private int valueType;
    private String order;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH-mm-ss.sss zzz")
    private Date updateTime;
    private String version;
    private String ownerLevelAndId;
    private int length;
    private int initType;
    private String initValue;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public int getValueType() {
        return this.valueType;
    }

    public void setValueType(int valueType) {
        this.valueType = valueType;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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

    public int getLength() {
        return this.length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getInitType() {
        return this.initType;
    }

    public void setInitType(int initType) {
        this.initType = initType;
    }

    public String getInitValue() {
        return this.initValue;
    }

    public void setInitValue(String initValue) {
        this.initValue = initValue;
    }

    public static NrdxFormulaVariableDTO valueOf(FormulaVariDefine formulaVariDefine) {
        if (formulaVariDefine == null) {
            return null;
        }
        NrdxFormulaVariableDTO nrdxFormulaVariableDTO = new NrdxFormulaVariableDTO();
        nrdxFormulaVariableDTO.setKey(formulaVariDefine.getKey());
        nrdxFormulaVariableDTO.setCode(formulaVariDefine.getCode());
        nrdxFormulaVariableDTO.setInitValue(formulaVariDefine.getInitValue());
        nrdxFormulaVariableDTO.setLength(formulaVariDefine.getLength());
        nrdxFormulaVariableDTO.setOrder(formulaVariDefine.getOrder());
        nrdxFormulaVariableDTO.setFormSchemeKey(formulaVariDefine.getFormSchemeKey());
        nrdxFormulaVariableDTO.setOwnerLevelAndId(formulaVariDefine.getOwnerLevelAndId());
        nrdxFormulaVariableDTO.setInitType(formulaVariDefine.getInitType());
        nrdxFormulaVariableDTO.setTitle(formulaVariDefine.getTitle());
        nrdxFormulaVariableDTO.setType(formulaVariDefine.getType());
        nrdxFormulaVariableDTO.setValueType(formulaVariDefine.getValueType());
        return nrdxFormulaVariableDTO;
    }

    public FormulaVariDefine value2Define() {
        DesignFormulaVariableDefineImpl formulaVariableDefine = new DesignFormulaVariableDefineImpl();
        formulaVariableDefine.setKey(this.getKey());
        formulaVariableDefine.setCode(this.getCode());
        formulaVariableDefine.setInitValue(this.getInitValue());
        formulaVariableDefine.setLength(this.getLength());
        formulaVariableDefine.setOrder(this.getOrder());
        formulaVariableDefine.setFormSchemeKey(this.getFormSchemeKey());
        formulaVariableDefine.setOwnerLevelAndId(this.getOwnerLevelAndId());
        formulaVariableDefine.setInitType(this.getInitType());
        formulaVariableDefine.setTitle(this.getTitle());
        formulaVariableDefine.setType(this.getType());
        formulaVariableDefine.setValueType(this.getValueType());
        return formulaVariableDefine;
    }
}

