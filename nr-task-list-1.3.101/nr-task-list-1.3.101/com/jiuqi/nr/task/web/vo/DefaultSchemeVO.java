/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.web.vo;

import com.jiuqi.nr.task.web.vo.FormSchemeItemVO;
import com.jiuqi.nr.task.web.vo.TaskItemVO;

public class DefaultSchemeVO
extends FormSchemeItemVO {
    private TaskItemVO taskInformation;

    public TaskItemVO getTaskInformation() {
        return this.taskInformation;
    }

    public void setTaskInformation(TaskItemVO taskInformation) {
        this.taskInformation = taskInformation;
    }
}

