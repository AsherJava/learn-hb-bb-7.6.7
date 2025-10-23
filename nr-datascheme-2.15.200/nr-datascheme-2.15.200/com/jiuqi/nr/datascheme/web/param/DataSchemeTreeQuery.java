/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.core.INode
 *  com.jiuqi.nr.datascheme.api.core.NodeType
 *  com.jiuqi.nr.datascheme.api.type.DataGroupKind
 *  com.jiuqi.nr.datascheme.api.type.DataSchemeType
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.datascheme.web.param;

import com.jiuqi.nr.datascheme.api.core.INode;
import com.jiuqi.nr.datascheme.api.core.NodeType;
import com.jiuqi.nr.datascheme.api.type.DataGroupKind;
import com.jiuqi.nr.datascheme.api.type.DataSchemeType;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;

public class DataSchemeTreeQuery<E extends INode> {
    @ApiModelProperty(value="\u6570\u636e\u65b9\u6848\u4e3b\u952e,\u4e3a\u7a7a\u4ee3\u8868\u67e5\u8be2\u65b9\u6848\u5206\u7ec4\u6811\u5f62", allowEmptyValue=true)
    private String dataSchemeKey;
    @ApiModelProperty(value="\u8fc7\u6ee4\u5b57\u7b26\u4e32", allowEmptyValue=true)
    private String filter;
    private List<String> filters;
    @ApiModelProperty(value="\u8981\u83b7\u53d6\u6570\u636e\u7684\u4e0a\u7ea7\u8282\u70b9", allowEmptyValue=true)
    private E dataSchemeNode;
    @ApiModelProperty(value="\u5c55\u793a\u5230\u54ea\u4e00\u5c42\u7ea7", allowEmptyValue=true)
    private NodeType nodeType;
    @ApiModelProperty(value="\u662f\u5426\u662f\u590d\u9009\u6811")
    private boolean checkbox;
    @ApiModelProperty(value="\u641c\u7d22\u7c7b\u578b", allowEmptyValue=true)
    private NodeType[] searchType;
    @ApiModelProperty(value="\u8fc7\u6ee4\u4e0d\u5c5e\u4e8e\u6b64\u7ef4\u5ea6\u7684\u65b9\u6848", allowEmptyValue=true)
    private String dimKey;
    @ApiModelProperty(value="\u8fc7\u6ee4\u6570\u636e\u65b9\u6848\u7c7b\u578b", allowEmptyValue=true)
    private DataSchemeType dataSchemeType;
    @ApiModelProperty(value="\u8fc7\u6ee4\u5206\u7ec4\u7c7b\u578b", allowEmptyValue=true)
    private DataGroupKind dataGroupKind;
    @ApiModelProperty(value="\u6570\u636e\u65b9\u6848\u5173\u8054\u6307\u6807\u4f53\u7cfb\u7248\u672c\u65f6\u671f", allowEmptyValue=true)
    private String period;
    @ApiModelProperty(value="\u7cbe\u786e\u641c\u7d22", allowEmptyValue=true)
    private Boolean precise = false;

    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }

    public String getFilter() {
        return this.filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public E getDataSchemeNode() {
        return this.dataSchemeNode;
    }

    public void setDataSchemeNode(E dataSchemeNode) {
        this.dataSchemeNode = dataSchemeNode;
    }

    public NodeType getNodeType() {
        return this.nodeType;
    }

    public void setNodeType(NodeType nodeType) {
        this.nodeType = nodeType;
    }

    public boolean isCheckbox() {
        return this.checkbox;
    }

    public void setCheckbox(boolean checkbox) {
        this.checkbox = checkbox;
    }

    public NodeType[] getSearchType() {
        return this.searchType;
    }

    public void setSearchType(NodeType[] searchType) {
        this.searchType = searchType;
    }

    public String getDimKey() {
        return this.dimKey;
    }

    public void setDimKey(String dimKey) {
        this.dimKey = dimKey;
    }

    public DataSchemeType getDataSchemeType() {
        return this.dataSchemeType;
    }

    public void setDataSchemeType(DataSchemeType dataSchemeType) {
        this.dataSchemeType = dataSchemeType;
    }

    public DataGroupKind getDataGroupKind() {
        return this.dataGroupKind;
    }

    public void setDataGroupKind(DataGroupKind dataGroupKind) {
        this.dataGroupKind = dataGroupKind;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public Boolean isPrecise() {
        return this.precise;
    }

    public void setPrecise(Boolean precise) {
        this.precise = precise;
    }

    public List<String> getFilters() {
        return this.filters;
    }

    public void setFilters(List<String> filters) {
        this.filters = filters;
    }
}

