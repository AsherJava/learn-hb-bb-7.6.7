/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.planpublish.model;

import com.jiuqi.nr.designer.planpublish.model.PlanPublishMessageObj;
import java.util.List;

public class TaskPlanPublishObj {
    private String taskID;
    private String deployTaskID;
    private String languageType;
    private String activedFormId;
    private String ownGroupId;
    private String activedSchemeId;
    private String publishDate;
    private String message;
    private List<PlanPublishMessageObj> messageObjs;
    private boolean deployDataScheme;

    public String getTaskID() {
        return this.taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    public String getDeployTaskID() {
        return this.deployTaskID;
    }

    public void setDeployTaskID(String deployTaskID) {
        this.deployTaskID = deployTaskID;
    }

    public String getLanguageType() {
        return this.languageType;
    }

    public void setLanguageType(String languageType) {
        this.languageType = languageType;
    }

    public String getActivedFormId() {
        return this.activedFormId;
    }

    public void setActivedFormId(String activedFormId) {
        this.activedFormId = activedFormId;
    }

    public String getOwnGroupId() {
        return this.ownGroupId;
    }

    public void setOwnGroupId(String ownGroupId) {
        this.ownGroupId = ownGroupId;
    }

    public String getActivedSchemeId() {
        return this.activedSchemeId;
    }

    public void setActivedSchemeId(String activedSchemeId) {
        this.activedSchemeId = activedSchemeId;
    }

    public String getPublishDate() {
        return this.publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<PlanPublishMessageObj> getMessageObjs() {
        return this.messageObjs;
    }

    public void setMessageObjs(List<PlanPublishMessageObj> messageObjs) {
        this.messageObjs = messageObjs;
    }

    public boolean isDeployDataScheme() {
        return this.deployDataScheme;
    }

    public void setDeployDataScheme(boolean deployDataScheme) {
        this.deployDataScheme = deployDataScheme;
    }
}

