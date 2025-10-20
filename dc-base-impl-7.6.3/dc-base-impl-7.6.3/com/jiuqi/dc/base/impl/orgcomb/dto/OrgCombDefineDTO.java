/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.common.intf.impl.DcTenantDTO
 */
package com.jiuqi.dc.base.impl.orgcomb.dto;

import com.jiuqi.dc.base.common.intf.impl.DcTenantDTO;
import java.util.List;

public class OrgCombDefineDTO
extends DcTenantDTO {
    private static final long serialVersionUID = 2663978130547071429L;
    private String groupId;
    private String unitCode;
    private List<String> itemDefineIdList;
    private Integer page;
    private Integer pageSize;

    public String getGroupId() {
        return this.groupId;
    }

    public String getUnitCode() {
        return this.unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public List<String> getItemDefineIdList() {
        return this.itemDefineIdList;
    }

    public void setItemDefineIdList(List<String> itemDefineIdList) {
        this.itemDefineIdList = itemDefineIdList;
    }

    public Integer getPage() {
        return this.page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}

