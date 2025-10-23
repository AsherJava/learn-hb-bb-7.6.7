/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserActionEvent
 */
package com.jiuqi.nr.workflow2.converter.entity;

import com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserActionEvent;
import java.util.List;
import java.util.Map;

public interface IProcessActionButton {
    public String getTaskId();

    public String getCode();

    public String getTitle();

    public String getIcon();

    public boolean isEnable();

    public List<IUserActionEvent> getPreviousEvents();

    public List<IUserActionEvent> getPostEvents();

    public Map<String, Object> getProperties();
}

