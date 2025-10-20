/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 *  javax.validation.constraints.NotNull
 */
package com.jiuqi.gcreport.org.api.intf.base;

import com.jiuqi.gcreport.org.api.intf.base.GcOrgVerApiParam;
import com.jiuqi.gcreport.org.api.vo.OrgToJsonVO;
import com.jiuqi.gcreport.org.api.vo.OrgVersionVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import javax.validation.constraints.NotNull;

@ApiModel(value="\u7ec4\u7ec7\u673a\u6784\u7ef4\u62a4\u5bf9\u8c61")
public class GcOrgEditVO
extends GcOrgVerApiParam {
    @ApiModelProperty(value="\u8981\u7ef4\u62a4\u7684\u7ec4\u7ec7\u673a\u6784\u5bf9\u8c61", allowEmptyValue=true)
    @NotNull
    private OrgToJsonVO org;
    @ApiModelProperty(value="\u8981\u5904\u7406\u7684\u7ec4\u7ec7\u673a\u6784\u7f16\u53f7", allowEmptyValue=true)
    private String orgCode;
    @ApiModelProperty(value="\u8981\u5904\u7406\u7684\u7ec4\u7ec7\u673a\u6784\u7248\u672c", allowEmptyValue=true)
    private List<OrgVersionVO> versions;
    private String sn;

    public OrgToJsonVO getOrg() {
        return this.org;
    }

    public void setOrg(OrgToJsonVO org) {
        this.org = org;
    }

    public String getOrgCode() {
        return this.orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public List<OrgVersionVO> getVersions() {
        return this.versions;
    }

    public void setVersions(List<OrgVersionVO> versions) {
        this.versions = versions;
    }

    public String getSn() {
        return this.sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }
}

