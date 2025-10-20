/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 */
package com.jiuqi.va.bizmeta.domain.metagroup;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.va.bizmeta.domain.dimension.MetaGroupDim;
import java.util.List;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class MetaGroupVO {
    private Boolean flag;
    private String message;
    private MetaGroupDim groupInfo;
    private List<MetaGroupDim> groups;

    public List<MetaGroupDim> getGroups() {
        return this.groups;
    }

    public void setGroups(List<MetaGroupDim> groups) {
        this.groups = groups;
    }

    public Boolean isFlag() {
        return this.flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MetaGroupDim getGroupInfo() {
        return this.groupInfo;
    }

    public void setGroupInfo(MetaGroupDim groupInfo) {
        this.groupInfo = groupInfo;
    }
}

