/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.common.TableDictType
 *  com.jiuqi.nvwa.definition.common.TableModelKind
 *  com.jiuqi.nvwa.definition.common.TableModelType
 */
package com.jiuqi.budget.domain;

import com.jiuqi.nvwa.definition.common.TableDictType;
import com.jiuqi.nvwa.definition.common.TableModelKind;
import com.jiuqi.nvwa.definition.common.TableModelType;
import java.util.Date;

public class BudTableModelDefineVO {
    private String id;
    private String catalogID;
    private String code;
    private String name;
    private String title;
    private String desc;
    private TableModelType type;
    private TableDictType dictType;
    private String keys;
    private String bizKeys;
    private String owner;
    private String option;
    private TableModelKind kind;
    private Date createTime;
    private Date updateTime;
    private boolean supportI18n;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCatalogID() {
        return this.catalogID;
    }

    public void setCatalogID(String catalogID) {
        this.catalogID = catalogID;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public TableModelType getType() {
        return this.type;
    }

    public void setType(TableModelType type) {
        this.type = type;
    }

    public TableDictType getDictType() {
        return this.dictType;
    }

    public void setDictType(TableDictType dictType) {
        this.dictType = dictType;
    }

    public String getKeys() {
        return this.keys;
    }

    public void setKeys(String keys) {
        this.keys = keys;
    }

    public String getBizKeys() {
        return this.bizKeys;
    }

    public void setBizKeys(String bizKeys) {
        this.bizKeys = bizKeys;
    }

    public String getOwner() {
        return this.owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOption() {
        return this.option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public TableModelKind getKind() {
        return this.kind;
    }

    public void setKind(TableModelKind kind) {
        this.kind = kind;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public boolean isSupportI18n() {
        return this.supportI18n;
    }

    public void setSupportI18n(boolean supportI18n) {
        this.supportI18n = supportI18n;
    }
}

