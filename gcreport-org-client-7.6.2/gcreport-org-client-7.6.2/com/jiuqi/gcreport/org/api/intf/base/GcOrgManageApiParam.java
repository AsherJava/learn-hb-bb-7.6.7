/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.gcreport.org.api.intf.base;

import com.jiuqi.gcreport.org.api.intf.base.GcOrgVerApiParam;
import com.jiuqi.gcreport.org.api.vo.field.GcOrgFieldVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;

@ApiModel(value="\u7ec4\u7ec7\u673a\u6784\u5b57\u6bb5\u67e5\u8be2\u53c2\u6570")
public class GcOrgManageApiParam
extends GcOrgVerApiParam {
    @ApiModelProperty(value="\u7528\u6237\u8fc7\u6ee4\u6761\u4ef6", allowEmptyValue=true)
    private String tableName;
    @ApiModelProperty(value="\u5b57\u6bb5\u5bf9\u8c61", allowEmptyValue=true)
    private List<GcOrgFieldVO> fields;

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<GcOrgFieldVO> getFields() {
        return this.fields;
    }

    public void setFields(List<GcOrgFieldVO> fields) {
        this.fields = fields;
    }
}

