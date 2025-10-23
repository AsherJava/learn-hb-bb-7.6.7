/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.formula.web.vo;

import com.jiuqi.nr.formula.dto.TaskLinkDimMappingDTO;

public class TaskLinkDimMappingVO
extends TaskLinkDimMappingDTO {
    private String dimName;
    private String dimDataName;

    public TaskLinkDimMappingVO() {
    }

    public TaskLinkDimMappingVO(TaskLinkDimMappingDTO dto) {
        this.setDimKey(dto.getDimKey());
        this.setDimData(dto.getDimData());
    }

    public String getDimName() {
        return this.dimName;
    }

    public void setDimName(String dimName) {
        this.dimName = dimName;
    }

    public String getDimDataName() {
        return this.dimDataName;
    }

    public void setDimDataName(String dimDataName) {
        this.dimDataName = dimDataName;
    }
}

