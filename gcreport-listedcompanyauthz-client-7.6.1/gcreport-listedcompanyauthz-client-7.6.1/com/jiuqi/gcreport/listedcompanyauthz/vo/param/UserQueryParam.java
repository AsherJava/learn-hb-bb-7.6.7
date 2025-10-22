/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.gcreport.listedcompanyauthz.vo.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="\u7528\u6237\u67e5\u8be2\u53c2\u6570")
public class UserQueryParam {
    @ApiModelProperty(value="\u516c\u53f8\u4ee3\u7801\uff08\u591a\u5355\u4f4d\u7528,\u5206\u5272\uff09", allowEmptyValue=true, example="A001,A002")
    private String orgCodes;
    @ApiModelProperty(value="\u7528\u6237\u767b\u9646\u540d(\u516c\u53f8\u4ee3\u7801\u4e0d\u4e3a\u7a7a\u5219\u5728\u516c\u53f8\u5bf9\u5e94\u7684\u517c\u7ba1\u7528\u6237\u4e2d\u8fc7\u6ee4\u5f53\u524d\u67e5\u8be2\u7528\u6237,\u516c\u53f8\u4ee3\u7801\u4e3a\u7a7a\u6839\u636e\u7528\u6237\u767b\u9646\u540d\u67e5\u8be2\u7528\u6237)", allowEmptyValue=true, example="jq")
    private String username;

    public String getOrgCodes() {
        return this.orgCodes;
    }

    public void setOrgCodes(String orgCodes) {
        this.orgCodes = orgCodes;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

