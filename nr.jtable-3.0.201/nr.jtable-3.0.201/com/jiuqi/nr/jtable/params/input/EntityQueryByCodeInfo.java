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
import com.jiuqi.nr.jtable.params.input.EntityQueryByViewInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="EntityQueryByViewInfo", description="\u4e3b\u4f53\u6570\u636e\u6839\u636e\u89c6\u56fe\u67e5\u8be2\u6761\u4ef6\u53c2\u6570")
public class EntityQueryByCodeInfo
extends EntityQueryByViewInfo
implements INRContext {
    @ApiModelProperty(value="\u5b58\u50a8\u8868code", name="tableCode")
    private String tableCode;
    @ApiModelProperty(value="\u67e5\u8be2\u6307\u6807Code", name="fieldCode")
    private String fieldCode;
    private String contextTaskKey;
    private String contextEntityId;
    private String contextFormSchemeKey;
    private String contextFilterExpression;

    public String getTableCode() {
        return this.tableCode;
    }

    public void setTableCode(String tableCode) {
        this.tableCode = tableCode;
    }

    public String getFieldCode() {
        return this.fieldCode;
    }

    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
    }

    @Override
    public String getContextEntityId() {
        return this.contextEntityId;
    }

    @Override
    public String getContextFilterExpression() {
        return this.contextFilterExpression;
    }
}

