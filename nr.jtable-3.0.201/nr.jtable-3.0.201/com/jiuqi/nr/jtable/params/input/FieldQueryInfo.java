/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.context.infc.INRContext
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.jtable.params.input;

import com.jiuqi.nr.context.infc.INRContext;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.UUID;

@ApiModel(value="FieldQueryInfo", description="\u6307\u6807\u67e5\u8be2\u53c2\u6570")
public class FieldQueryInfo
implements INRContext {
    @ApiModelProperty(value="jtable\u73af\u5883\u4fe1\u606f", name="context", required=true)
    private JtableContext context;
    @ApiModelProperty(value="\u62a5\u8868key", name="formKeys")
    private String formKeys;
    @ApiModelProperty(value="\u5b58\u50a8\u8868tablekey", name="tableKey")
    private UUID tableKey;
    @ApiModelProperty(value="\u6307\u6807key", name="fieldKey")
    private UUID fieldKey;
    @ApiModelProperty(value="\u6307\u6807\u6a21\u7cca\u641c\u7d22", name="search")
    private String search;
    private String contextTaskKey;
    private String contextEntityId;
    private String contextFormSchemeKey;
    private String contextFilterExpression;

    public JtableContext getContext() {
        return this.context;
    }

    public void setContext(JtableContext context) {
        this.context = context;
    }

    public String getFormKeys() {
        return this.formKeys;
    }

    public void setFormKeys(String formKeys) {
        this.formKeys = formKeys;
    }

    public UUID getTableKey() {
        return this.tableKey;
    }

    public void setTableKey(UUID tableKey) {
        this.tableKey = tableKey;
    }

    public UUID getFieldKey() {
        return this.fieldKey;
    }

    public void setFieldKey(UUID fieldKey) {
        this.fieldKey = fieldKey;
    }

    public String getSearch() {
        return this.search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getContextEntityId() {
        return this.contextEntityId;
    }

    public String getContextFilterExpression() {
        return this.contextFilterExpression;
    }
}

