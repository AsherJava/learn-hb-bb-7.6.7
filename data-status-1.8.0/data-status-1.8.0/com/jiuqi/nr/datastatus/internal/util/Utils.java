/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.controller.ITaskOptionController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 */
package com.jiuqi.nr.datastatus.internal.util;

import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.controller.ITaskOptionController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Utils {
    @Autowired
    private ITaskOptionController taskOptionController;
    @Autowired
    private IRunTimeViewController runTimeViewController;

    public boolean taskOpenStatus(String taskKey) {
        String checkStatusMessage = this.taskOptionController.getValue(taskKey, "DATAENTRY_STATUS");
        return checkStatusMessage != null && (checkStatusMessage.contains("0") || checkStatusMessage.contains("1") || checkStatusMessage.contains("2"));
    }

    public boolean fsOpenStatus(String formSchemeKey) {
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        if (formScheme != null) {
            return this.taskOpenStatus(formScheme.getTaskKey());
        }
        return false;
    }
}

