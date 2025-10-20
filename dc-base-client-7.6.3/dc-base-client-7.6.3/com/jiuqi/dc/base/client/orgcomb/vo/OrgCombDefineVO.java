/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.base.client.orgcomb.vo;

import com.jiuqi.dc.base.client.orgcomb.vo.OrgCombItemDefineVO;
import java.util.List;

public class OrgCombDefineVO {
    private String id;
    private Long ver;
    private String code;
    private String name;
    private String remark;
    private String groupId;
    private List<OrgCombItemDefineVO> schemeItems;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getVer() {
        return this.ver;
    }

    public void setVer(Long ver) {
        this.ver = ver;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<OrgCombItemDefineVO> getSchemeItems() {
        return this.schemeItems;
    }

    public void setSchemeItems(List<OrgCombItemDefineVO> schemeItems) {
        this.schemeItems = schemeItems;
    }

    public String getGroupId() {
        return this.groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}

