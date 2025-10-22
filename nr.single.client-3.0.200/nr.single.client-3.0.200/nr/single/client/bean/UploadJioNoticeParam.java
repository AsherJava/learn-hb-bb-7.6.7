/*
 * Decompiled with CFR 0.152.
 */
package nr.single.client.bean;

import java.util.ArrayList;
import java.util.List;

public class UploadJioNoticeParam {
    private boolean success;
    private String message;
    private String taskKey;
    private String formSchemeKey;
    private String configKey;
    private String orgDefineCode;
    private List<String> periodCodes;
    private int state;

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getOrgDefineCode() {
        return this.orgDefineCode;
    }

    public void setOrgDefineCode(String orgDefineCode) {
        this.orgDefineCode = orgDefineCode;
    }

    public List<String> getPeriodCodes() {
        if (this.periodCodes == null) {
            this.periodCodes = new ArrayList<String>();
        }
        return this.periodCodes;
    }

    public void setPeriodCodes(List<String> periodCodes) {
        this.periodCodes = periodCodes;
    }

    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getConfigKey() {
        return this.configKey;
    }

    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }
}

