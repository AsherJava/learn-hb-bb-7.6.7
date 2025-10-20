/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.meta.OperateType
 *  com.jiuqi.va.mapper.domain.PageDTO
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.va.bizmeta.domain.metainfo;

import com.jiuqi.va.domain.meta.OperateType;
import com.jiuqi.va.mapper.domain.PageDTO;
import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.List;
import java.util.UUID;

public class MetaInfoPageDTO
extends TenantDO
implements PageDTO {
    private boolean pagination;
    private int offset;
    private int limit;
    private UUID groupId;
    private String module;
    private String metaType;
    private String userName;
    private List<String> groupNames;
    private String param;
    private String uniqueCode;
    private OperateType operateType;

    public boolean isPagination() {
        return this.pagination;
    }

    public int getOffset() {
        return this.offset;
    }

    public int getLimit() {
        return this.limit;
    }

    public void setPagination(boolean pagination) {
        this.pagination = pagination;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public UUID getGroupId() {
        return this.groupId;
    }

    public void setGroupId(UUID groupId) {
        this.groupId = groupId;
    }

    public String getModule() {
        return this.module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getParam() {
        return this.param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getMetaType() {
        return this.metaType;
    }

    public void setMetaType(String metaType) {
        this.metaType = metaType;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<String> getGroupNames() {
        return this.groupNames;
    }

    public void setGroupNames(List<String> groupNames) {
        this.groupNames = groupNames;
    }

    public OperateType getOperateType() {
        return this.operateType;
    }

    public void setOperateType(OperateType operateType) {
        this.operateType = operateType;
    }

    public String getUniqueCode() {
        return this.uniqueCode;
    }

    public void setUniqueCode(String uniqueCode) {
        this.uniqueCode = uniqueCode;
    }
}

