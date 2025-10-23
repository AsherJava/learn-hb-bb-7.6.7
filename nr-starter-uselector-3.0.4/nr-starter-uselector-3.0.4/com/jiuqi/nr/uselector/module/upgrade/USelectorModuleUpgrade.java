/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.sf.models.ModuleInitiator
 *  javax.servlet.ServletContext
 */
package com.jiuqi.nr.uselector.module.upgrade;

import com.jiuqi.nvwa.sf.models.ModuleInitiator;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;

public class USelectorModuleUpgrade
implements ModuleInitiator {
    public void init(ServletContext context) throws Exception {
        for (ModuleInitiator initiator : this.getModuleInitiator()) {
            initiator.init(context);
        }
    }

    public void initWhenStarted(ServletContext context) throws Exception {
        for (ModuleInitiator initiator : this.getModuleInitiator()) {
            initiator.initWhenStarted(context);
        }
    }

    private List<ModuleInitiator> getModuleInitiator() {
        return new ArrayList<ModuleInitiator>();
    }
}

