/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.dataresource.web.param;

import com.jiuqi.nr.dataresource.NodeType;
import com.jiuqi.nr.dataresource.dto.DataResourceNodeDTO;
import io.swagger.annotations.ApiModelProperty;

public class DataResourceTreeQuery {
    @ApiModelProperty(value="\u6570\u636e\u8d44\u6e90\u6811\u4e3b\u952e,\u4e3a\u7a7a\u4ee3\u8868\u67e5\u8be2\u8d44\u6e90\u6811\u5206\u7ec4\u6811\u5f62", allowEmptyValue=true)
    private String defineKey;
    @ApiModelProperty(value="\u8fc7\u6ee4\u5b57\u7b26\u4e32", allowEmptyValue=true)
    private String filter;
    @ApiModelProperty(value="\u8981\u83b7\u53d6\u6570\u636e\u7684\u4e0a\u7ea7\u8282\u70b9", allowEmptyValue=true)
    private DataResourceNodeDTO dataResourceNode;
    @ApiModelProperty(value="\u5c55\u793a\u5230\u54ea\u4e00\u5c42\u7ea7", allowEmptyValue=true)
    private NodeType nodeType;
    @ApiModelProperty(value="\u641c\u7d22\u7c7b\u578b", allowEmptyValue=true)
    private NodeType[] searchType;

    public String getDefineKey() {
        return this.defineKey;
    }

    public void setDefineKey(String defineKey) {
        this.defineKey = defineKey;
    }

    public String getFilter() {
        return this.filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public DataResourceNodeDTO getDataResourceNode() {
        return this.dataResourceNode;
    }

    public void setDataResourceNode(DataResourceNodeDTO dataResourceNode) {
        this.dataResourceNode = dataResourceNode;
    }

    public NodeType getNodeType() {
        return this.nodeType;
    }

    public void setNodeType(NodeType nodeType) {
        this.nodeType = nodeType;
    }

    public NodeType[] getSearchType() {
        return this.searchType;
    }

    public void setSearchType(NodeType[] searchType) {
        this.searchType = searchType;
    }
}

