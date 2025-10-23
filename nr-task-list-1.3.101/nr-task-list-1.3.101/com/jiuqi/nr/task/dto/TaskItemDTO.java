/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.dto;

import com.jiuqi.nr.task.dto.FormSchemeStatusDTO;
import com.jiuqi.nr.task.dto.TaskDTO;
import java.util.Date;

public class TaskItemDTO
extends TaskDTO {
    private Date publishTime;
    private FormSchemeStatusDTO status;

    public Date getPublishTime() {
        return this.publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public FormSchemeStatusDTO getStatus() {
        return this.status;
    }

    public void setStatus(FormSchemeStatusDTO status) {
        this.status = status;
    }
}

