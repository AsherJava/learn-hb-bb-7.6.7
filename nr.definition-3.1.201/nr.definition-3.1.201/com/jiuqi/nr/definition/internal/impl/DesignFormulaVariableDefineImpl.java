/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBField
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBTable
 */
package com.jiuqi.nr.definition.internal.impl;

import com.jiuqi.np.definition.internal.anno.DBAnno;
import com.jiuqi.nr.definition.facade.FormulaVariDefine;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;

@DBAnno.DBTable(dbTable="NR_PARAM_FORMULAVARIABLE_DES")
public class DesignFormulaVariableDefineImpl
implements FormulaVariDefine {
    private static final long serialVersionUID = 1L;
    @DBAnno.DBField(dbField="fa_key", isPk=true)
    private String key;
    @DBAnno.DBField(dbField="fa_code")
    private String code;
    @DBAnno.DBField(dbField="fa_title")
    private String title;
    @DBAnno.DBField(dbField="fa_type")
    private int type;
    @DBAnno.DBField(dbField="fa_fs_key", isPk=false)
    private String formSchemeKey;
    @DBAnno.DBField(dbField="fa_valueType")
    private int valueType;
    @DBAnno.DBField(dbField="fa_order", isOrder=true)
    private String order;
    @DBAnno.DBField(dbField="fa_updatetime", tranWith="transTimeStamp", dbType=Timestamp.class, appType=Date.class, autoDate=true)
    private Date updateTime;
    @DBAnno.DBField(dbField="fa_version")
    private String version;
    @DBAnno.DBField(dbField="fa_level")
    private String ownerLevelAndId;
    @DBAnno.DBField(dbField="fa_length")
    private int length;
    @DBAnno.DBField(dbField="fa_initType")
    private int initType;
    @DBAnno.DBField(dbField="fa_initvalue", dbType=Clob.class)
    private String initvalue;

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public int getType() {
        return this.type;
    }

    @Override
    public int getValueType() {
        return this.valueType;
    }

    @Override
    public int getLength() {
        return this.length;
    }

    @Override
    public int getInitType() {
        return this.initType;
    }

    public String getKey() {
        return this.key;
    }

    public String getOrder() {
        return this.order;
    }

    public String getVersion() {
        return this.version;
    }

    public String getOwnerLevelAndId() {
        return this.ownerLevelAndId;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    @Override
    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    @Override
    public String getInitValue() {
        return this.initvalue;
    }

    @Override
    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void setType(int type) {
        this.type = type;
    }

    @Override
    public void setValueType(int valueType) {
        this.valueType = valueType;
    }

    @Override
    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public void setInitType(int initType) {
        this.initType = initType;
    }

    @Override
    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    @Override
    public void setInitValue(String initvalue) {
        this.initvalue = initvalue;
    }

    @Override
    public void setOrder(String order) {
        this.order = order;
    }

    @Override
    public void setOwnerLevelAndId(String ownerLevelAndId) {
        this.ownerLevelAndId = ownerLevelAndId;
    }
}

