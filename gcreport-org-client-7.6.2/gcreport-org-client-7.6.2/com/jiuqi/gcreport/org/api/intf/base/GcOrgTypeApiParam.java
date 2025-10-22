/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.gcreport.org.api.intf.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="\u7ec4\u7ec7\u673a\u6784\u7c7b\u578b\u67e5\u8be2\u53c2\u6570")
public class GcOrgTypeApiParam {
    @ApiModelProperty(value="\u7ec4\u7ec7\u673a\u6784\u7c7b\u578b", allowEmptyValue=true)
    private String orgType;

    public String getOrgType() {
        return this.orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }
}

