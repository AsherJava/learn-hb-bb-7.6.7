/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.option.core;

import com.jiuqi.nr.definition.option.core.TaskOptionGroupDTO;
import java.util.List;

public class TaskOptionPageDTO {
    private String title;
    private List<TaskOptionGroupDTO> group;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<TaskOptionGroupDTO> getGroup() {
        return this.group;
    }

    public void setGroup(List<TaskOptionGroupDTO> group) {
        this.group = group;
    }
}

