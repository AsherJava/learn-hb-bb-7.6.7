/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.base.client.orgcomb.vo;

import com.jiuqi.dc.base.client.orgcomb.dto.OrgCombGroupDTO;
import java.util.List;

public class OrgCombGroupTreeNodeVO {
    private String id;
    private String title;
    private String nodeType;
    private String remark;
    private String groupId;
    private List<OrgCombGroupDTO> children;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNodeType() {
        return this.nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getGroupId() {
        return this.groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public List<OrgCombGroupDTO> getChildren() {
        return this.children;
    }

    public void setChildren(List<OrgCombGroupDTO> children) {
        this.children = children;
    }
}

