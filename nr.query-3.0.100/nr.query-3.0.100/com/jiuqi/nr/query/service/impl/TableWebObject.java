/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 *  com.fasterxml.jackson.databind.annotation.JsonSerialize
 *  com.jiuqi.np.definition.common.TableKind
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.nr.query.service.impl;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jiuqi.np.definition.common.TableKind;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.query.service.impl.BaseWebObject;
import com.jiuqi.nr.query.service.impl.TableJsonDeserializer;
import com.jiuqi.nr.query.service.impl.TableJsonSerializer;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;

@JsonSerialize(using=TableJsonSerializer.class)
@JsonDeserialize(using=TableJsonDeserializer.class)
public class TableWebObject
extends BaseWebObject {
    public static final String WEB_TABLE_GROUPKEY = "groupKey";
    public static final String WEB_TABLE_RELEASE = "isRelease";
    public static final String WEB_TABLE_FIELDS = "fields";
    public static final String WEB_TABLE_TABLENAME = "tableName";
    public static final String WEB_TABLE_TABLEKIND = "tableKind";
    public static final String WEB_TABLE_VIEWKEY = "viewKey";
    public static final String WEB_TABLE_DICTTREESTRUCT = "dictTreeStruct";
    public static final String WEB_TABLE_SHOWCODE = "showCode";
    public static final String WEB_TABLE_OPENAUTHORITY = "openAuthority";
    public static final String WEB_TABLE_ALIAS = "alias";
    public static final String WEB_TABLE_REFERENTITYKEY = "referEntityKey";
    public static final String WEB_TABLE_REFERENTVIEWKEY = "referEntViewKey";
    public static final String WEB_TABLE_EDITABLE = "editable";
    public static final String WEB_TABLE_READABLE = "readable";
    private String groupKey;
    private boolean isRelease;
    private String tableName;
    private TableKind tableKind;
    private String dictTreeStruct;
    private String alias;
    private String referEntityKey;
    private String referEntViewKey;
    private boolean editable;
    private boolean readable;
    private IRuntimeDataSchemeService iRuntimeDataSchemeService = (IRuntimeDataSchemeService)BeanUtil.getBean(IRuntimeDataSchemeService.class);

    public String getGroupKey() {
        return this.groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    public boolean isRelease() {
        return this.isRelease;
    }

    public void setRelease(boolean isRelease) {
        this.isRelease = isRelease;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public TableKind getTableKind() {
        return this.tableKind;
    }

    public void setTableKind(TableKind tableKind) {
        this.tableKind = tableKind;
    }

    public String getDictTreeStruct() {
        return this.dictTreeStruct;
    }

    public void setDictTreeStruct(String dictTreeStruct) {
        this.dictTreeStruct = dictTreeStruct;
    }

    public String getAlias() {
        return this.alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getReferEntityKey() {
        return this.referEntityKey;
    }

    public void setReferEntityKey(String referEntityKey) {
        this.referEntityKey = referEntityKey;
    }

    public String getReferEntViewKey() {
        return this.referEntViewKey;
    }

    public void setReferEntViewKey(String referEntViewKey) {
        this.referEntViewKey = referEntViewKey;
    }

    public boolean isEditable() {
        return this.editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public boolean isReadable() {
        return this.readable;
    }

    public void setReadable(boolean readable) {
        this.readable = readable;
    }

    public static TableWebObject buildDesignTable(TableDefine desTb) {
        IEntityMetaService iEntityMetaService = (IEntityMetaService)BeanUtil.getBean(IEntityMetaService.class);
        TableModelDefine tableModel = iEntityMetaService.getTableModel(desTb.getKey());
        TableWebObject tbObj = new TableWebObject();
        tbObj.setKey(desTb.getKey());
        tbObj.setCode(desTb.getCode());
        tbObj.setTitle(desTb.getTitle());
        tbObj.setOrder(desTb.getOrder());
        tbObj.setGroupKey(tableModel.getOwner());
        tbObj.setTableName(tableModel.getName());
        tbObj.setTableKind(desTb.getKind());
        return tbObj;
    }
}

