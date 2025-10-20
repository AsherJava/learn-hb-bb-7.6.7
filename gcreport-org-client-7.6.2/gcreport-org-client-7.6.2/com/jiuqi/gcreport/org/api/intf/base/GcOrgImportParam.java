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

@ApiModel(value="\u7ec4\u7ec7\u673a\u6784\u67e5\u8be2\u53c2\u6570")
public class GcOrgImportParam
extends GcOrgVerApiParam {
    @ApiModelProperty(value="\u64cd\u4f5c\u8868\u540d", allowEmptyValue=true)
    private String tableName;
    @ApiModelProperty(value="\u4e0a\u7ea7\u5355\u4f4d\u7f16\u7801", allowEmptyValue=true)
    private String orgParentCode;
    @ApiModelProperty(value="\u5206\u9875\u9875\u7801", allowEmptyValue=true)
    private Integer pageNum;
    @ApiModelProperty(value="\u5206\u9875\u6bcf\u9875\u6761\u6570", allowEmptyValue=true)
    private Integer pageSize;
    @ApiModelProperty(value="\u5b58\u5728\u65f6\u66f4\u65b0", allowEmptyValue=true)
    private Boolean executeOnDuplicate;
    @ApiModelProperty(value="\u67e5\u8be2\u6240\u6709\u4e0b\u7ea7", allowEmptyValue=true)
    private Boolean isAll;
    @ApiModelProperty(value="\u4efb\u52a1\u6279\u6b21\u53f7", allowEmptyValue=true)
    private String sn;

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
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

    public Boolean getExecuteOnDuplicate() {
        return this.executeOnDuplicate;
    }

    public void setExecuteOnDuplicate(Boolean executeOnDuplicate) {
        this.executeOnDuplicate = executeOnDuplicate;
    }

    public Boolean getIsAll() {
        return this.isAll;
    }

    public void setIsAll(Boolean isAll) {
        this.isAll = isAll;
    }

    public String getSn() {
        return this.sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }
}

