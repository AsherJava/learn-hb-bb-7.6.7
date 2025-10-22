/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.attachment.input;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;

@ApiModel(value="\u6839\u636e\u62a5\u8868\u65b9\u6848\u6216\u62a5\u8868\u67e5\u8be2\u9644\u4ef6\u8bf7\u6c42\u4fe1\u606f", description="\u6839\u636e\u62a5\u8868\u65b9\u6848\u6216\u62a5\u8868\u67e5\u8be2\u9644\u4ef6\u8bf7\u6c42\u4fe1\u606f")
public class AttachmentQueryInfo {
    @ApiModelProperty(value="\u4efb\u52a1key", name="task", required=true)
    private String task;
    @ApiModelProperty(value="\u6570\u636e\u65b9\u6848key", name="dataSchemeKey", required=true)
    private String dataSchemeKey;
    @ApiModelProperty(value="\u7ef4\u5ea6\u4fe1\u606f", name="dimensionCollection", required=true)
    private DimensionCollection dimensionCollection;
    @ApiModelProperty(value="\u62a5\u8868\u65b9\u6848key", name="formscheme", required=true)
    private String formscheme;
    @ApiModelProperty(value="\u62a5\u8868key\uff08\u4e0d\u4f20\u4e3a\u6240\u6709\u62a5\u8868\uff09", name="formKey")
    private List<String> formKey;
    @ApiModelProperty(value="\u662f\u5426\u5206\u9875", name="isPage")
    private boolean isPage = true;
    @ApiModelProperty(value="\u6bcf\u9875\u6761\u6570", name="pageSize")
    private Integer pageSize;
    @ApiModelProperty(value="\u5f53\u524d\u9875", name="currentPage")
    private Integer currentPage;
    @ApiModelProperty(value="\u6392\u5e8f\u89c4\u5219\uff08\u6b63\u53d9[asc]\u6216\u5012\u53d9[desc]\uff09", name="order")
    private String order;
    @ApiModelProperty(value="\u6392\u5e8f\u4f9d\u636e\uff08\u540d\u79f0[title]\u3001\u5927\u5c0f[size]\u6216\u521b\u5efa\u65f6\u95f4[createtime]\uff09", name="sortBy")
    private String sortBy;
    @ApiModelProperty(value="\u641c\u7d22\u4fe1\u606f", name="searchInfo")
    private String searchInfo;

    public String getTask() {
        return this.task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }

    public DimensionCollection getDimensionCollection() {
        return this.dimensionCollection;
    }

    public void setDimensionCollection(DimensionCollection dimensionCollection) {
        this.dimensionCollection = dimensionCollection;
    }

    public String getFormscheme() {
        return this.formscheme;
    }

    public void setFormscheme(String formscheme) {
        this.formscheme = formscheme;
    }

    public List<String> getFormKey() {
        return this.formKey;
    }

    public void setFormKey(List<String> formKey) {
        this.formKey = formKey;
    }

    public boolean isPage() {
        return this.isPage;
    }

    public void setPage(boolean page) {
        this.isPage = page;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getCurrentPage() {
        return this.currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getSortBy() {
        return this.sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSearchInfo() {
        return this.searchInfo;
    }

    public void setSearchInfo(String searchInfo) {
        this.searchInfo = searchInfo;
    }
}

