/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.gcreport.org.api.intf.base;

import com.jiuqi.gcreport.org.api.intf.base.GcOrgVerApiParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="\u7ec4\u7ec7\u673a\u6784\u7ef4\u62a4\u5bf9\u8c61")
public class GcOrgSplitedParam
extends GcOrgVerApiParam {
    @ApiModelProperty(value="\u8981\u67e5\u8be2\u7684\u7ec4\u7ec7\u673a\u6784\u4e0a\u7ea7\u7f16\u53f7", allowEmptyValue=true)
    private String parentCode;

    public String getParentCode() {
        return this.parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }
}

