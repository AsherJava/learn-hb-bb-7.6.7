/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.settings.listener;

public class WorkflowSettingsChangeVerification {
    private boolean supportChange;
    private String unSupportMessage;
    private boolean existsAlarm;
    private String alarmMessage;

    public boolean isSupportChange() {
        return this.supportChange;
    }

    public void setSupportChange(boolean supportChange) {
        this.supportChange = supportChange;
    }

    public String getUnSupportMessage() {
        return this.unSupportMessage;
    }

    public void setUnSupportMessage(String unSupportMessage) {
        this.unSupportMessage = unSupportMessage;
    }

    public boolean isExistsAlarm() {
        return this.existsAlarm;
    }

    public void setExistsAlarm(boolean existsAlarm) {
        this.existsAlarm = existsAlarm;
    }

    public String getAlarmMessage() {
        return this.alarmMessage;
    }

    public void setAlarmMessage(String alarmMessage) {
        this.alarmMessage = alarmMessage;
    }
}

