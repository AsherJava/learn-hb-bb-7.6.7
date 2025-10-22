/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.gcreport.org.api.intf.base;

import com.jiuqi.gcreport.org.api.intf.base.GcOrgTypeApiParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="\u7ec4\u7ec7\u673a\u6784\u7248\u672c\u67e5\u8be2\u53c2\u6570")
public class GcOrgVerApiParam
extends GcOrgTypeApiParam {
    @ApiModelProperty(value="\u7ec4\u7ec7\u673a\u6784\u7248\u672c\u4ee3\u7801", allowEmptyValue=true)
    private String orgVerCode;
    @ApiModelProperty(value="\u7ec4\u7ec7\u673a\u6784\u7248\u672c\u540d\u79f0", allowEmptyValue=true)
    private String orgVerName;

    public String getOrgVerCode() {
        return this.orgVerCode;
    }

    public void setOrgVerCode(String orgVerCode) {
        this.orgVerCode = orgVerCode;
    }

    public String getOrgVerName() {
        return this.orgVerName;
    }

    public void setOrgVerName(String orgVerName) {
        this.orgVerName = orgVerName;
    }
}

