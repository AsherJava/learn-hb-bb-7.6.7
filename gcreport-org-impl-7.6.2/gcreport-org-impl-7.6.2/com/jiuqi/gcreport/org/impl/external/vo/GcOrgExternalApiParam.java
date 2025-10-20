/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.org.api.enums.GcOrgOperateEnum
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 *  javax.validation.constraints.NotNull
 */
package com.jiuqi.gcreport.org.impl.external.vo;

import com.jiuqi.gcreport.org.api.enums.GcOrgOperateEnum;
import com.jiuqi.gcreport.org.impl.external.vo.GcOrgExternalVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.HashSet;
import javax.validation.constraints.NotNull;

@ApiModel(value="\u7ec4\u7ec7\u673a\u6784\u5916\u90e8\u63a5\u53e3\u53c2\u6570")
public class GcOrgExternalApiParam {
    @ApiModelProperty(value="\u7ec4\u7ec7\u673a\u6784\u5bf9\u8c61", allowEmptyValue=false)
    @NotNull
    private GcOrgExternalVO org;
    @ApiModelProperty(value="\u7ec4\u7ec7\u673a\u6784\u7248\u672c\u4ee3\u7801", allowEmptyValue=true)
    private String orgVerCode;
    @ApiModelProperty(value="\u7ec4\u7ec7\u673a\u6784\u7248\u672c\u4ee3\u7801", allowEmptyValue=false)
    private HashSet<String> orgTypes;
    @ApiModelProperty(value="\u7ec4\u7ec7\u673a\u6784\u64cd\u4f5c\u7c7b\u578b", allowEmptyValue=false)
    private GcOrgOperateEnum operateType;

    public GcOrgExternalVO getOrg() {
        return this.org;
    }

    public void setOrg(GcOrgExternalVO org) {
        this.org = org;
    }

    public String getOrgVerCode() {
        return this.orgVerCode;
    }

    public void setOrgVerCode(String orgVerCode) {
        this.orgVerCode = orgVerCode;
    }

    public HashSet<String> getOrgTypes() {
        return this.orgTypes;
    }

    public void setOrgTypes(HashSet<String> orgTypes) {
        this.orgTypes = orgTypes;
    }

    public GcOrgOperateEnum getOperateType() {
        return this.operateType;
    }

    public void setOperateType(GcOrgOperateEnum operateType) {
        this.operateType = operateType;
    }
}

