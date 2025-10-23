/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.singlequery.multcheck.vo;

import com.jiuqi.nr.singlequery.multcheck.vo.SingleQueryErrorInfo;
import java.util.List;

public class ErrorParam {
    private String task;
    private String description;
    private boolean cover;
    private List<SingleQueryErrorInfo> errors;

    public String getTask() {
        return this.task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCover() {
        return this.cover;
    }

    public void setCover(boolean cover) {
        this.cover = cover;
    }

    public List<SingleQueryErrorInfo> getErrors() {
        return this.errors;
    }

    public void setErrors(List<SingleQueryErrorInfo> errors) {
        this.errors = errors;
    }
}

