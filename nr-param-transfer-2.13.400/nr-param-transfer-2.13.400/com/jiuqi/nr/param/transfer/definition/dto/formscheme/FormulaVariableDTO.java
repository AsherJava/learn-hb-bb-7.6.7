/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.fasterxml.jackson.annotation.JsonFormat$Shape
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.jiuqi.nr.definition.facade.FormulaVariDefine
 *  com.jiuqi.nr.definition.internal.impl.DesignFormDefineBigDataUtil
 *  com.jiuqi.nr.definition.internal.impl.DesignFormulaVariableDefineImpl
 */
package com.jiuqi.nr.param.transfer.definition.dto.formscheme;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.nr.definition.facade.FormulaVariDefine;
import com.jiuqi.nr.definition.internal.impl.DesignFormDefineBigDataUtil;
import com.jiuqi.nr.definition.internal.impl.DesignFormulaVariableDefineImpl;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown=true)
public class FormulaVariableDTO {
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
    private byte[] initValue;
    private String initValue2;

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

    public byte[] getInitValue() {
        return this.initValue;
    }

    public void setInitValue(byte[] initValue) {
        this.initValue = initValue;
    }

    public String getInitValue2() {
        return this.initValue2;
    }

    public void setInitValue2(String initValue2) {
        this.initValue2 = initValue2;
    }

    public static FormulaVariableDTO valueOf(FormulaVariDefine formulaVariDefine) {
        if (formulaVariDefine == null) {
            return null;
        }
        FormulaVariableDTO variableDTO = new FormulaVariableDTO();
        variableDTO.setKey(formulaVariDefine.getKey());
        variableDTO.setCode(formulaVariDefine.getCode());
        variableDTO.setInitValue2(formulaVariDefine.getInitValue());
        variableDTO.setLength(formulaVariDefine.getLength());
        variableDTO.setOrder(formulaVariDefine.getOrder());
        variableDTO.setFormSchemeKey(formulaVariDefine.getFormSchemeKey());
        variableDTO.setOwnerLevelAndId(formulaVariDefine.getOwnerLevelAndId());
        variableDTO.setInitType(formulaVariDefine.getInitType());
        variableDTO.setTitle(formulaVariDefine.getTitle());
        variableDTO.setType(formulaVariDefine.getType());
        variableDTO.setValueType(formulaVariDefine.getValueType());
        return variableDTO;
    }

    public FormulaVariDefine value2Define() {
        DesignFormulaVariableDefineImpl formulaVariableDefine = new DesignFormulaVariableDefineImpl();
        formulaVariableDefine.setKey(this.getKey());
        formulaVariableDefine.setCode(this.getCode());
        if (this.getInitValue() != null) {
            formulaVariableDefine.setInitValue(DesignFormDefineBigDataUtil.bytesToString((byte[])this.getInitValue()));
        } else {
            formulaVariableDefine.setInitValue(this.getInitValue2());
        }
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

