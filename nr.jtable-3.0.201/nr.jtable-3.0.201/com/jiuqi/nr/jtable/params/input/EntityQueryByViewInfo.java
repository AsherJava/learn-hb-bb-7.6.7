/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.PagerInfo
 *  com.jiuqi.nr.context.infc.INRContext
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.jtable.params.input;

import com.jiuqi.nr.common.params.PagerInfo;
import com.jiuqi.nr.context.infc.INRContext;
import com.jiuqi.nr.jtable.params.input.EntityQueryInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.HashMap;
import java.util.Map;

@ApiModel(value="EntityQueryByViewInfo", description="\u4e3b\u4f53\u6570\u636e\u6839\u636e\u89c6\u56fe\u67e5\u8be2\u6761\u4ef6\u53c2\u6570")
public class EntityQueryByViewInfo
extends EntityQueryInfo
implements INRContext {
    @ApiModelProperty(value="\u7236\u4e3b\u4f53\u89c6\u56fekey\uff08\u5e9f\u5f03\uff09", name="parentViewKey")
    private String parentViewKey;
    @ApiModelProperty(value="\u679a\u4e3e\u6761\u76ee\u7236\u8282\u70b9\u4e3b\u952e", name="parentKey")
    private String parentKey;
    @ApiModelProperty(value="\u662f\u5426\u5206\u9875", name="parentKey")
    private boolean paginate;
    @ApiModelProperty(value="\u5206\u9875\u4fe1\u606f", name="pagerInfo")
    private PagerInfo pagerInfo;
    @ApiModelProperty(value="\u662f\u5426\u67e5\u8be2\u6240\u6709\u4e0b\u7ea7\u8282\u70b9", name="allChildren")
    private boolean allChildren = true;
    @ApiModelProperty(value="\u679a\u4e3e\u6761\u76ee\u8fc7\u6ee4\u5b57\u7b26", name="search")
    private String search;
    @ApiModelProperty(value="\u662f\u5426\u53ea\u67e5\u8be2\u53f6\u5b50\u8282\u70b9", name="searchLeaf")
    private boolean searchLeaf = false;
    @ApiModelProperty(value="\u524d\u7f6e\u679a\u4e3e\u503cmap\uff0ckey\u4e3a\u524d\u7f6e\u679a\u4e3e\u89c6\u56fekey\uff0cvalue\u4e3a\u524d\u7f6e\u679a\u4e3e\u89c6\u56fevalue", name="perEntityValues")
    private Map<String, String> perEntityValues = new HashMap<String, String>();
    @ApiModelProperty(value="\u8fc7\u6ee4\u5b57\u7b26\u662f\u5426\u5168\u8bcd\u5339\u914d", name="matchAll")
    private boolean matchAll = false;
    @ApiModelProperty(value="\u8fc7\u6ee4\u5b57\u7b26\u662f\u5426\u89e3\u6790\u5168\u8def\u5f84", name="fullPath")
    private boolean fullPath = false;
    @ApiModelProperty(value="\u662f\u5426\u8fdb\u884c\u6392\u5e8f", name="sorted")
    private boolean sorted = false;
    private String contextTaskKey;
    private String contextEntityId;
    private String contextFormSchemeKey;
    private String contextFilterExpression;
    private boolean treeToList = false;
    @ApiModelProperty(value="\u662f\u5426\u4f7f\u7528\u9ed8\u8ba4\u641c\u7d22\u6a21\u5f0f", name="useDefaultSearch")
    private boolean useDefaultSearch = true;

    public String getParentViewKey() {
        return this.parentViewKey;
    }

    public void setParentViewKey(String parentViewKey) {
        this.parentViewKey = parentViewKey;
    }

    public String getParentKey() {
        return this.parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

    public boolean isPaginate() {
        return this.paginate;
    }

    public void setPaginate(boolean paginate) {
        this.paginate = paginate;
    }

    public PagerInfo getPagerInfo() {
        return this.pagerInfo;
    }

    public void setPagerInfo(PagerInfo pagerInfo) {
        this.pagerInfo = pagerInfo;
    }

    public boolean isAllChildren() {
        return this.allChildren;
    }

    public void setAllChildren(boolean allChildren) {
        this.allChildren = allChildren;
    }

    public String getSearch() {
        return this.search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public boolean isSearchLeaf() {
        return this.searchLeaf;
    }

    public void setSearchLeaf(boolean searchLeaf) {
        this.searchLeaf = searchLeaf;
    }

    public Map<String, String> getPerEntityValues() {
        return this.perEntityValues;
    }

    public void setPerEntityValues(Map<String, String> perEntityValues) {
        this.perEntityValues = perEntityValues;
    }

    public boolean isMatchAll() {
        return this.matchAll;
    }

    public void setMatchAll(boolean matchAll) {
        this.matchAll = matchAll;
    }

    public boolean isFullPath() {
        return this.fullPath;
    }

    public void setFullPath(boolean fullPath) {
        this.fullPath = fullPath;
    }

    public boolean isSorted() {
        return this.sorted;
    }

    public void setSorted(boolean sorted) {
        this.sorted = sorted;
    }

    public String getContextEntityId() {
        return this.contextEntityId;
    }

    public String getContextFilterExpression() {
        return this.contextFilterExpression;
    }

    public boolean isTreeToList() {
        return this.treeToList;
    }

    public void setTreeToList(boolean treeToList) {
        this.treeToList = treeToList;
    }

    public boolean isUseDefaultSearch() {
        return this.useDefaultSearch;
    }

    public void setUseDefaultSearch(boolean useDefaultSearch) {
        this.useDefaultSearch = useDefaultSearch;
    }
}

