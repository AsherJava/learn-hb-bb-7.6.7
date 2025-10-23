/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.api.save;

import com.jiuqi.nr.task.api.save.StepStatus;
import java.io.Serializable;

public class StepDetail
implements Serializable {
    private StepStatus status;
    private String title;
    private String content;
    private String desc;

    public StepDetail(StepStatus status, String title, String content) {
        this.status = status;
        this.title = title;
        this.content = content;
    }

    public StepStatus getStatus() {
        return this.status;
    }

    public String getTitle() {
        return this.title;
    }

    public String getContent() {
        return this.content;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setStatus(StepStatus status) {
        this.status = status;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

