/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.context.infc.impl.NRContext
 */
package com.jiuqi.nr.dataentry.bean;

import com.jiuqi.nr.context.infc.impl.NRContext;
import java.util.List;

public class DataentryFormSchemeParam
extends NRContext {
    private String taskId;
    private List<String> formschemeIds;
    private String taskGroup;
    private List<String> taskIdList;

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public List<String> getFormschemeIds() {
        return this.formschemeIds;
    }

    public void setFormschemeIds(List<String> formschemeIds) {
        this.formschemeIds = formschemeIds;
    }

    public String getTaskGroup() {
        return this.taskGroup;
    }

    public void setTaskGroup(String taskGroup) {
        this.taskGroup = taskGroup;
    }

    public List<String> getTaskIdList() {
        return this.taskIdList;
    }

    public void setTaskIdList(List<String> taskIdList) {
        this.taskIdList = taskIdList;
    }
}

