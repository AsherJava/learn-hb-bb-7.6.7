/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 *  javax.validation.constraints.NotNull
 */
package com.jiuqi.gcreport.org.api.intf.base;

import com.jiuqi.gcreport.org.api.enums.GenerateWay;
import com.jiuqi.gcreport.org.api.intf.base.GcOrgVerApiParam;
import com.jiuqi.gcreport.org.api.vo.OrgTypeVO;
import com.jiuqi.gcreport.org.api.vo.OrgVersionVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;

@ApiModel(value="\u7ec4\u7ec7\u673a\u6784\u7c7b\u578b\u590d\u5236\u53c2\u6570")
public class GcOrgTypeCopyApiParam
extends GcOrgVerApiParam {
    @ApiModelProperty(value="\u65b0\u5efa\u7ec4\u7ec7\u673a\u6784\u7c7b\u578b\u5bf9\u8c61", allowEmptyValue=true)
    @NotNull
    private OrgTypeVO orgTypeVo;
    @ApiModelProperty(value="\u8fc7\u6ee4\u516c\u5f0f", allowEmptyValue=true)
    private String filterFormula;
    @ApiModelProperty(value="\u590d\u5236\u7684\u76ee\u6807\u7248\u672c", allowEmptyValue=true)
    private OrgVersionVO orgVersion;
    @ApiModelProperty(value="\u673a\u6784\u751f\u6210\u65b9\u5f0f", allowEmptyValue=false)
    private GenerateWay generateWay;
    @ApiModelProperty(value="\u4efb\u52a1\u6279\u6b21\u53f7", allowEmptyValue=true)
    private String sn;

    public OrgTypeVO getOrgTypeVo() {
        return this.orgTypeVo;
    }

    public void setOrgTypeVo(OrgTypeVO orgTypeVo) {
        this.orgTypeVo = orgTypeVo;
    }

    public String getFilterFormula() {
        return this.filterFormula;
    }

    public void setFilterFormula(String filterFormula) {
        this.filterFormula = filterFormula;
    }

    public OrgVersionVO getOrgVersion() {
        return this.orgVersion;
    }

    public void setOrgVersion(OrgVersionVO orgVersion) {
        this.orgVersion = orgVersion;
    }

    public String getSn() {
        return this.sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public GenerateWay getGenerateWay() {
        return this.generateWay;
    }

    public void setGenerateWay(GenerateWay generateWay) {
        this.generateWay = generateWay;
    }
}

