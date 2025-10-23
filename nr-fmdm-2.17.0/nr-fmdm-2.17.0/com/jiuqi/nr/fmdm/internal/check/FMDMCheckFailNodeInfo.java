/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.fmdm.internal.check;

import com.jiuqi.nr.fmdm.internal.check.CheckNodeInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;

@ApiModel(value="FMDMCheckFailNodeInfo", description="\u5c01\u9762\u68c0\u67e5\u7ed3\u679c")
public class FMDMCheckFailNodeInfo {
    @ApiModelProperty(value="\u5c5e\u6027\u540d\u79f0", name="fieldTitle")
    private String fieldTitle;
    @ApiModelProperty(value="\u5c5e\u6027\u6807\u8bc6", name="fieldCode")
    private String fieldCode;
    @ApiModelProperty(value="\u63d0\u793a\u8bed\u8282\u70b9\u5217\u8868", name="nodes")
    private List<CheckNodeInfo> nodes = new ArrayList<CheckNodeInfo>();

    public String getFieldTitle() {
        return this.fieldTitle;
    }

    public void setFieldTitle(String fieldTitle) {
        this.fieldTitle = fieldTitle;
    }

    public String getFieldCode() {
        return this.fieldCode;
    }

    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
    }

    public List<CheckNodeInfo> getNodes() {
        return this.nodes;
    }

    public void setNodes(List<CheckNodeInfo> nodes) {
        this.nodes = nodes;
    }

    public void addNode(CheckNodeInfo nodeInfo) {
        if (this.nodes == null) {
            this.nodes = new ArrayList<CheckNodeInfo>();
        }
        this.nodes.add(nodeInfo);
    }
}

