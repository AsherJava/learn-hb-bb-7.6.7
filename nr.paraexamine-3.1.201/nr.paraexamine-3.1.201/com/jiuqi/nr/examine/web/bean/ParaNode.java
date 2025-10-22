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
import java.util.ArrayList;
import java.util.List;

@ApiModel(value="\u53c2\u6570\u68c0\u67e5\u9519\u8bef\u53c2\u6570\u8def\u5f84\u7c7b", description="\u53c2\u6570\u68c0\u67e5\u9519\u8bef\u53c2\u6570\u8def\u5f84\u7c7b")
public class ParaNode {
    @ApiModelProperty(value="\u53c2\u6570\u7c7b\u578b")
    public int paraType;
    @ApiModelProperty(value="\u53c2\u6570\u6807\u9898")
    public String title;
    @ApiModelProperty(value="\u5b50\u53c2\u6570")
    public List<ParaNode> children;

    public void addChild(ParaNode childNode) {
        if (childNode != null) {
            if (this.children == null) {
                this.children = new ArrayList<ParaNode>();
            }
            this.children.add(childNode);
        }
    }

    public void addChild(List<ParaNode> childNode) {
        if (childNode != null) {
            if (this.children == null) {
                this.children = new ArrayList<ParaNode>();
            }
            this.children.addAll(childNode);
        }
    }
}

