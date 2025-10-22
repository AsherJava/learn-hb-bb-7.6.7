/*
 * Decompiled with CFR 0.152.
 */
package nr.single.data.bean;

import java.io.Serializable;

public class CheckParam
implements Serializable {
    private static final long serialVersionUID = -296199024921797132L;
    private String taskKey;
    private String formSchemeKey;
    private String periodCode;

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getPeriodCode() {
        return this.periodCode;
    }

    public void setPeriodCode(String periodCode) {
        this.periodCode = periodCode;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }
}

