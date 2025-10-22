/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 */
package com.jiuqi.nr.common.asynctask.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.np.asynctask.TaskState;
import java.io.Serializable;

public class AsyncTaskInfo
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private TaskState state;
    @JsonInclude(value=JsonInclude.Include.NON_NULL)
    private Integer location;
    private Double process;
    private String result;
    private transient Object detail;
    private String type;
    private String url;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TaskState getState() {
        return this.state;
    }

    public void setState(TaskState state) {
        this.state = state;
    }

    public Integer getLocation() {
        return this.location;
    }

    public void setLocation(Integer location) {
        this.location = location;
    }

    public Double getProcess() {
        return this.process;
    }

    public void setProcess(Double process) {
        this.process = process;
    }

    public String getResult() {
        return this.result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Object getDetail() {
        return this.detail;
    }

    public void setDetail(Object detail) {
        this.detail = detail;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

