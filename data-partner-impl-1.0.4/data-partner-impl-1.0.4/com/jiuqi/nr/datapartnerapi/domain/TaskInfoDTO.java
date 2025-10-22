/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datapartnerapi.domain.FormSchemaInfo
 *  com.jiuqi.nr.datapartnerapi.domain.TaskInfo
 */
package com.jiuqi.nr.datapartnerapi.domain;

import com.jiuqi.nr.datapartnerapi.domain.FormSchemaInfo;
import com.jiuqi.nr.datapartnerapi.domain.TaskInfo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TaskInfoDTO
implements TaskInfo,
Serializable {
    private static final long serialVersionUID = 1L;
    private String taskKey;
    private String taskCode;
    private String taskTitle;
    private String dataTime;
    private String dataSchemeKey;
    private String dataSchemeTitle;
    private final List<String> entityIds = new ArrayList<String>();
    private long timestamp;
    private final List<FormSchemaInfo> formSchemas = new ArrayList<FormSchemaInfo>();

    public TaskInfoDTO(String taskKey, String taskCode, String taskTitle) {
        this.taskKey = taskKey;
        this.taskCode = taskCode;
        this.taskTitle = taskTitle;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getTaskCode() {
        return this.taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public String getTaskTitle() {
        return this.taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getDataTime() {
        return this.dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }

    public String getDataSchemeTitle() {
        return this.dataSchemeTitle;
    }

    public void setDataSchemeTitle(String dataSchemeTitle) {
        this.dataSchemeTitle = dataSchemeTitle;
    }

    public List<String> getEntityIds() {
        return this.entityIds;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public List<FormSchemaInfo> getFormSchemas() {
        return this.formSchemas;
    }
}

