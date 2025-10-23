/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.subsystem.core.model.ISubServerConfigPlugin
 *  com.jiuqi.nvwa.subsystem.core.model.SubServer
 */
package com.jiuqi.nr.transmission.data.plugins;

import com.jiuqi.nvwa.subsystem.core.model.ISubServerConfigPlugin;
import com.jiuqi.nvwa.subsystem.core.model.SubServer;
import org.springframework.stereotype.Component;

@Component
public class TaskListChoosePlugin
implements ISubServerConfigPlugin {
    public static final String PLUGIN_TYPE = "EXT_TASK_LIST";

    public String type() {
        return PLUGIN_TYPE;
    }

    public String title() {
        return "\u591a\u7ea7\u90e8\u7f72\u914d\u7f6e";
    }

    public String pluginName() {
        return "taskListPlug";
    }

    public void configSaved(SubServer subServer, String config) {
    }
}

