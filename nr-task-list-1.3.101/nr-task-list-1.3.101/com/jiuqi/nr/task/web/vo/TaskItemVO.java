/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.web.vo;

import com.jiuqi.nr.task.dto.TaskActionDTO;
import com.jiuqi.nr.task.dto.TaskItemDTO;
import java.util.List;

public class TaskItemVO
extends TaskItemDTO {
    private String dateTime;
    private List<TaskActionDTO> actions;

    public String getDateTime() {
        return this.dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public List<TaskActionDTO> getActions() {
        return this.actions;
    }

    public void setActions(List<TaskActionDTO> actions) {
        this.actions = actions;
    }
}

