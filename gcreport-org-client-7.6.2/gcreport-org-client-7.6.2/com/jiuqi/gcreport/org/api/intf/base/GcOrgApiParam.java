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
import java.util.List;

@ApiModel(value="\u7ec4\u7ec7\u673a\u6784\u67e5\u8be2\u53c2\u6570")
public class GcOrgApiParam
extends GcOrgVerApiParam {
    @ApiModelProperty(value="\u8fc7\u6ee4\u6761\u4ef6", allowEmptyValue=true)
    private String searchText;
    @ApiModelProperty(value="\u7ec4\u7ec7\u673a\u6784\u4ee3\u7801", allowEmptyValue=true)
    private String orgCode;
    @ApiModelProperty(value="\u7236\u7ea7\u7ec4\u7ec7\u673a\u6784\u4ee3\u7801", allowEmptyValue=true)
    private String orgParentCode;
    @ApiModelProperty(value="\u6279\u91cf\u7ec4\u7ec7\u673a\u6784\u4ee3\u7801", allowEmptyValue=true)
    private List<String> orgCodes;
    @ApiModelProperty(value="\u5206\u9875\u9875\u7801", allowEmptyValue=true)
    private Integer pageNum;
    @ApiModelProperty(value="\u5206\u9875\u6bcf\u9875\u6761\u6570", allowEmptyValue=true)
    private Integer pageSize;

    public String getSearchText() {
        return this.searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public String getOrgCode() {
        return this.orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrgParentCode() {
        return this.orgParentCode;
    }

    public void setOrgParentCode(String orgParentCode) {
        this.orgParentCode = orgParentCode;
    }

    public Integer getPageNum() {
        return this.pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public List<String> getOrgCodes() {
        return this.orgCodes;
    }

    public void setOrgCodes(List<String> orgCodes) {
        this.orgCodes = orgCodes;
    }
}

