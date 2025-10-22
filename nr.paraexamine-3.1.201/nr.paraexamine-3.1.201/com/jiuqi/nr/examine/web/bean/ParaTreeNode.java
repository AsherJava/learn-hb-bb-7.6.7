/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.examine.web.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;

@ApiModel(value="\u53c2\u6570\u6811\u5f62\u8282\u70b9", description="\u53c2\u6570\u6811\u5f62\u8282\u70b9")
public class ParaTreeNode {
    @ApiModelProperty(value="\u6807\u9898")
    private String title;
    @ApiModelProperty(value="\u5b50\u8282\u70b9")
    private List<ParaTreeNode> children;
    @ApiModelProperty(value="\u552f\u4e00\u6807\u8bc6")
    private String key;
    @ApiModelProperty(value="\u53c2\u6570\u7c7b\u578b")
    private int type;
    @ApiModelProperty(value="\u662f\u5426\u5c55\u5f00")
    private boolean expand;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ParaTreeNode> getChildren() {
        return this.children;
    }

    public void setChildren(List<ParaTreeNode> children) {
        this.children = children;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getKey() {
        return this.key;
    }

    public void setExpand(boolean expand) {
        this.expand = expand;
    }

    public boolean isExpand() {
        return this.expand;
    }
}

