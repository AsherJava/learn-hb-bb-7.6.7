/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.compare.bean;

public class TaskPeriodDTO {
    private String taskKey;
    private String fromPeriod;
    private String toPeriod;
    private boolean updateFormSchemePreiod;

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public boolean isUpdateFormSchemePreiod() {
        return this.updateFormSchemePreiod;
    }

    public void setUpdateFormSchemePreiod(boolean updateFormSchemePreiod) {
        this.updateFormSchemePreiod = updateFormSchemePreiod;
    }

    public String getFromPeriod() {
        return this.fromPeriod;
    }

    public void setFromPeriod(String fromPeriod) {
        this.fromPeriod = fromPeriod;
    }

    public String getToPeriod() {
        return this.toPeriod;
    }

    public void setToPeriod(String toPeriod) {
        this.toPeriod = toPeriod;
    }
}

