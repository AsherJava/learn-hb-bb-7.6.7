/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.task.api.common.ComponentDefine
 *  com.jiuqi.nr.task.api.common.Constants$ActionType
 *  com.jiuqi.nr.task.api.face.ITaskAction
 */
package com.jiuqi.nr.workflow2.settings.register;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.task.api.common.ComponentDefine;
import com.jiuqi.nr.task.api.common.Constants;
import com.jiuqi.nr.task.api.face.ITaskAction;
import java.util.List;

public class WorkflowSettingsAppActionRegister
implements ITaskAction {
    public String code() {
        return "WORKFLOW_SETTINGS";
    }

    public String title() {
        return "\u6d41\u7a0b\u8bbe\u7f6e";
    }

    public double order() {
        return 5.0;
    }

    public boolean apply(Constants.ActionType type, String key) {
        return type.equals((Object)Constants.ActionType.TASK) && this.isTaskVersion_2_0(key);
    }

    public boolean enable(Constants.ActionType type, String key) {
        IRunTimeViewController runTimeViewController = (IRunTimeViewController)SpringBeanUtils.getBean(IRunTimeViewController.class);
        if (type.equals((Object)Constants.ActionType.TASK)) {
            List formSchemeDefines = runTimeViewController.listFormSchemeByTask(key);
            return this.isTaskVersion_2_0(key) && formSchemeDefines != null && !formSchemeDefines.isEmpty();
        }
        return false;
    }

    public ComponentDefine component() {
        ComponentDefine componentDefine = new ComponentDefine();
        componentDefine.setComponentName("nr-workflow2-settings-app");
        componentDefine.setProductLine("@nr");
        componentDefine.setEntry(null);
        return componentDefine;
    }

    private boolean isTaskVersion_2_0(String taskKey) {
        IDesignTimeViewController designTimeViewController = (IDesignTimeViewController)SpringBeanUtils.getBean(IDesignTimeViewController.class);
        DesignTaskDefine designTaskDefine = designTimeViewController.getTask(taskKey);
        return "2.0".equals(designTaskDefine.getVersion());
    }
}

