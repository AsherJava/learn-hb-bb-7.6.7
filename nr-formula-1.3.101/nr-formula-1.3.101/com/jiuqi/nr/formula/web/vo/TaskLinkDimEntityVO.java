/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.formula.web.vo;

import com.jiuqi.nr.formula.web.vo.TaskLinkEntityBaseVO;
import java.util.List;

public class TaskLinkDimEntityVO
extends TaskLinkEntityBaseVO {
    private List<TaskLinkEntityBaseVO> data;

    public TaskLinkDimEntityVO() {
    }

    public TaskLinkDimEntityVO(String entityId, String entityTitle, String entityCode) {
        super(entityId, entityTitle, entityCode);
    }

    public List<TaskLinkEntityBaseVO> getData() {
        return this.data;
    }

    public void setData(List<TaskLinkEntityBaseVO> data) {
        this.data = data;
    }
}

