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

@ApiModel(value="\u641c\u7d22\u4e0a\u4e0b\u6587", description="\u641c\u7d22\u4e0a\u4e0b\u6587")
public class SearchContext {
    @ApiModelProperty(value="\u6570\u636e\u65b9\u6848", name="dataSchemeKey", required=true)
    private String dataSchemeKey;
    @ApiModelProperty(value="\u4efb\u52a1key", name="taskKey", required=true)
    private String taskKey;
    @ApiModelProperty(value="\u62a5\u8868\u65b9\u6848key", name="formscheme", required=true)
    private String formscheme;
    @ApiModelProperty(value="\u7ef4\u5ea6\u4fe1\u606f", name="dimensionCollection", required=true)
    private DimensionCollection dimensionCollection;
    @ApiModelProperty(value="\u62a5\u8868key", name="formKey")
    private String formKey;
    @ApiModelProperty(value="\u6307\u6807key", name="fieldKey")
    private String fieldKey;
    @ApiModelProperty(value="\u9644\u4ef6\u7c7b\u578b\uff08\u540e\u7f00\u540d\uff09", name="type")
    private List<String> types;
    @ApiModelProperty(value="\u9644\u4ef6\u7c7b\u522b", name="category")
    private String category;
    @ApiModelProperty(value="\u641c\u7d22\u5173\u952e\u5b57", name="searchInfo")
    private String searchInfo;
    @ApiModelProperty(value="\u672a\u88ab\u5f15\u7528", name="notReferences")
    private boolean notReferences = false;
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
    @ApiModelProperty(value="\u9644\u4ef6\u5206\u7ec4key", name="groupKey")
    private String groupKey;

    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getFormscheme() {
        return this.formscheme;
    }

    public void setFormscheme(String formscheme) {
        this.formscheme = formscheme;
    }

    public DimensionCollection getDimensionCollection() {
        return this.dimensionCollection;
    }

    public void setDimensionCollection(DimensionCollection dimensionCollection) {
        this.dimensionCollection = dimensionCollection;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getFieldKey() {
        return this.fieldKey;
    }

    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
    }

    public List<String> getTypes() {
        return this.types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSearchInfo() {
        return this.searchInfo;
    }

    public void setSearchInfo(String searchInfo) {
        this.searchInfo = searchInfo;
    }

    public boolean isNotReferences() {
        return this.notReferences;
    }

    public void setNotReferences(boolean notReferences) {
        this.notReferences = notReferences;
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

    public String getGroupKey() {
        return this.groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }
}

