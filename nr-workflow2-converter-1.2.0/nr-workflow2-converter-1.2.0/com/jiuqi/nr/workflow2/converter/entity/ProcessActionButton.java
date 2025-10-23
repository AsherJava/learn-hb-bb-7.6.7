/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserActionEvent
 */
package com.jiuqi.nr.workflow2.converter.entity;

import com.jiuqi.nr.workflow2.converter.entity.IProcessActionButton;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserActionEvent;
import java.util.List;
import java.util.Map;

public class ProcessActionButton
implements IProcessActionButton {
    private String taskId;
    private String code;
    private String title;
    private String icon;
    private boolean enable;
    private List<IUserActionEvent> previousEvents;
    private List<IUserActionEvent> postEvents;
    private Map<String, Object> properties;

    @Override
    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public boolean isEnable() {
        return this.enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    @Override
    public List<IUserActionEvent> getPreviousEvents() {
        return this.previousEvents;
    }

    public void setPreviousEvents(List<IUserActionEvent> previousEvents) {
        this.previousEvents = previousEvents;
    }

    @Override
    public List<IUserActionEvent> getPostEvents() {
        return this.postEvents;
    }

    public void setPostEvents(List<IUserActionEvent> postEvents) {
        this.postEvents = postEvents;
    }

    @Override
    public Map<String, Object> getProperties() {
        return this.properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }
}

