/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.dc.base.client.orgcomb.dto;

import com.jiuqi.dc.base.client.orgcomb.vo.OrgCombDefineVO;
import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.List;

public class OrgCombGroupDTO
extends TenantDO {
    private static final long serialVersionUID = -3393503107492748712L;
    private String id;
    private String title;
    private String nodeType;
    private String sortNum;
    private List<OrgCombDefineVO> children;
    private List<String> codes;

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

    public String getSortNum() {
        return this.sortNum;
    }

    public void setSortNum(String sortNum) {
        this.sortNum = sortNum;
    }

    public List<OrgCombDefineVO> getChildren() {
        return this.children;
    }

    public void setChildren(List<OrgCombDefineVO> children) {
        this.children = children;
    }

    public List<String> getCodes() {
        return this.codes;
    }

    public void setCodes(List<String> codes) {
        this.codes = codes;
    }
}

