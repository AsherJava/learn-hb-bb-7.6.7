/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.nrdx.adapter.param.common.NrdxParamNodeType
 */
package com.jiuqi.nr.nrdx.param.dto;

import com.jiuqi.nr.nrdx.adapter.param.common.NrdxParamNodeType;
import com.jiuqi.nr.nrdx.param.dto.ParamDTO;
import java.util.List;

public class TaskParamDTO
extends ParamDTO {
    private List<String> tasKGroup;

    public TaskParamDTO() {
    }

    public TaskParamDTO(List<String> tasKGroup, List<String> paramKeys, NrdxParamNodeType resourceType) {
        super(paramKeys, resourceType);
        this.tasKGroup = tasKGroup;
    }

    public List<String> getTasKGroup() {
        return this.tasKGroup;
    }

    public void setTasKGroup(List<String> tasKGroup) {
        this.tasKGroup = tasKGroup;
    }
}

