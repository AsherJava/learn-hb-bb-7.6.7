/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.gcreport.org.api.intf.base;

import com.jiuqi.gcreport.org.api.intf.base.GcOrgApiParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="\u7ec4\u7ec7\u673a\u6784\u67e5\u8be2\u53c2\u6570")
public class GcOrgPublicApiParam
extends GcOrgApiParam {
    @ApiModelProperty(value="\u6743\u9650\u7c7b\u578b", allowEmptyValue=true)
    private String authType;
    @ApiModelProperty(value="\u62a5\u8868\u65b9\u6848", allowEmptyValue=true)
    private String formSchemeKey;
    @ApiModelProperty(value="\u516c\u5f0f", allowEmptyValue=true)
    private String expression;
    @ApiModelProperty(value="\u8c03\u6574\u671f\u6807\u8bc6", allowEmptyValue=true)
    private String adjustPeriodCode;

    public String getAuthType() {
        return this.authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getExpression() {
        return this.expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getAdjustPeriodCode() {
        return this.adjustPeriodCode;
    }

    public void setAdjustPeriodCode(String adjustPeriodCode) {
        this.adjustPeriodCode = adjustPeriodCode;
    }
}

