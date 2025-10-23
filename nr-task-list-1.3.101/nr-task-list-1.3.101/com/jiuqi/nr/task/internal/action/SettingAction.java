/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.task.api.common.ComponentDefine
 *  com.jiuqi.nr.task.api.face.ITaskAction
 */
package com.jiuqi.nr.task.internal.action;

import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.task.api.common.ComponentDefine;
import com.jiuqi.nr.task.api.face.ITaskAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SettingAction
implements ITaskAction {
    @Autowired
    private IRunTimeViewController runTimeViewController;

    public String code() {
        return "TASK_SETTING";
    }

    public String title() {
        return "\u8bbe\u7f6e";
    }

    public double order() {
        return 4.0;
    }

    public ComponentDefine component() {
        ComponentDefine componentDefine = new ComponentDefine();
        componentDefine.setComponentName("taskOption");
        componentDefine.setEntry("taskOption");
        componentDefine.setProductLine("@nr");
        return componentDefine;
    }
}

