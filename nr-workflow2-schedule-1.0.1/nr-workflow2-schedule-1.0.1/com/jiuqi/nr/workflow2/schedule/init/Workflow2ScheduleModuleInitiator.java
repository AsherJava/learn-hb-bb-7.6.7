/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.sf.models.ModuleInitiator
 *  javax.servlet.ServletContext
 */
package com.jiuqi.nr.workflow2.schedule.init;

import com.jiuqi.nr.workflow2.schedule.init.StartupPlanTaskInit;
import com.jiuqi.nvwa.sf.models.ModuleInitiator;
import javax.servlet.ServletContext;
import org.springframework.stereotype.Component;

@Component
public class Workflow2ScheduleModuleInitiator
implements ModuleInitiator {
    @Deprecated
    public void init(ServletContext context) throws Exception {
    }

    @Deprecated
    public void initWhenStarted(ServletContext context) throws Exception {
        new StartupPlanTaskInit().initTaskGroup();
    }
}

