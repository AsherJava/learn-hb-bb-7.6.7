/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.formula.web.vo;

import com.jiuqi.nr.formula.web.vo.TaskLinkDimEntityVO;
import com.jiuqi.nr.formula.web.vo.TaskLinkEntityBaseVO;
import java.util.List;

public class TaskLinkEntityQueryVO {
    private String taskId;
    private List<TaskLinkEntityBaseVO> entities;
    private List<TaskLinkDimEntityVO> dimEntities;

    public TaskLinkEntityQueryVO() {
    }

    public TaskLinkEntityQueryVO(String taskId, List<TaskLinkEntityBaseVO> entities, List<TaskLinkDimEntityVO> dimEntities) {
        this.taskId = taskId;
        this.entities = entities;
        this.dimEntities = dimEntities;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public List<TaskLinkEntityBaseVO> getEntities() {
        return this.entities;
    }

    public void setEntities(List<TaskLinkEntityBaseVO> entities) {
        this.entities = entities;
    }

    public List<TaskLinkDimEntityVO> getDimEntities() {
        return this.dimEntities;
    }

    public void setDimEntities(List<TaskLinkDimEntityVO> dimEntities) {
        this.dimEntities = dimEntities;
    }
}

