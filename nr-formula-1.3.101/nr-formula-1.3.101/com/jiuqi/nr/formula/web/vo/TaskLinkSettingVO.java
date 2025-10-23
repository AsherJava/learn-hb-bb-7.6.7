/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.formula.web.vo;

import com.jiuqi.nr.formula.web.vo.TaskLinkEntityBaseVO;
import com.jiuqi.nr.formula.web.vo.TaskLinkVO;
import java.util.List;

public class TaskLinkSettingVO {
    private List<TaskLinkEntityBaseVO> currentTaskEntities;
    private List<TaskLinkEntityBaseVO> currentTaskDimEntities;
    private List<TaskLinkVO> taskLinks;

    public TaskLinkSettingVO() {
    }

    public TaskLinkSettingVO(List<TaskLinkVO> taskLinks) {
        this.taskLinks = taskLinks;
    }

    public List<TaskLinkEntityBaseVO> getCurrentTaskEntities() {
        return this.currentTaskEntities;
    }

    public void setCurrentTaskEntities(List<TaskLinkEntityBaseVO> currentTaskEntities) {
        this.currentTaskEntities = currentTaskEntities;
    }

    public List<TaskLinkVO> getTaskLinks() {
        return this.taskLinks;
    }

    public void setTaskLinks(List<TaskLinkVO> taskLinks) {
        this.taskLinks = taskLinks;
    }

    public List<TaskLinkEntityBaseVO> getCurrentTaskDimEntities() {
        return this.currentTaskDimEntities;
    }

    public void setCurrentTaskDimEntities(List<TaskLinkEntityBaseVO> currentTaskDimEntities) {
        this.currentTaskDimEntities = currentTaskDimEntities;
    }
}

