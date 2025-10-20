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
import com.jiuqi.gcreport.org.api.vo.OrgSplitVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import javax.validation.constraints.NotNull;

@ApiModel(value="\u7ec4\u7ec7\u673a\u6784\u7ef4\u62a4\u5bf9\u8c61")
public class GcOrgSplitedVO
extends GcOrgVerApiParam {
    @ApiModelProperty(value="\u8981\u62c6\u5206\u7684\u7ec4\u7ec7\u673a\u6784\u5bf9\u8c61", allowEmptyValue=true)
    @NotNull
    private List<OrgSplitVO> orgs;
    @ApiModelProperty(value="\u8981\u5904\u7406\u7684\u7ec4\u7ec7\u673a\u6784\u4ee3\u7801", allowEmptyValue=true)
    private List<String> orgCodes;

    public List<OrgSplitVO> getOrgs() {
        return this.orgs;
    }

    public void setOrgs(List<OrgSplitVO> orgs) {
        this.orgs = orgs;
    }

    public List<String> getOrgCodes() {
        return this.orgCodes;
    }

    public void setOrgCodes(List<String> orgCodes) {
        this.orgCodes = orgCodes;
    }
}

