/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.core.bridge.AbstractJobBridge
 *  com.jiuqi.bi.core.jobs.core.bridge.JobBridgeFactory
 *  com.jiuqi.nr.definition.deploy.service.IParamDeployStatusService
 *  com.jiuqi.nr.definition.service.IParamCacheManagerService
 *  com.jiuqi.nvwa.sf.models.ModuleInitiator
 *  javax.servlet.ServletContext
 */
package com.jiuqi.nr.definition.starter.init;

import com.jiuqi.bi.core.jobs.core.bridge.AbstractJobBridge;
import com.jiuqi.bi.core.jobs.core.bridge.JobBridgeFactory;
import com.jiuqi.nr.definition.deploy.service.IParamDeployStatusService;
import com.jiuqi.nr.definition.service.IParamCacheManagerService;
import com.jiuqi.nvwa.sf.models.ModuleInitiator;
import javax.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NrDefinitionServiceInit
implements ModuleInitiator {
    @Autowired
    private IParamDeployStatusService paramDeployStatusService;
    @Autowired
    private IParamCacheManagerService paramCacheManagerService;

    public void init(ServletContext context) throws Exception {
        AbstractJobBridge jobBridge2;
        try {
            jobBridge2 = JobBridgeFactory.getInstance().getDefaultBridge();
            jobBridge2.unscheduleJob("rta_clear_history_job", "rta_clear_history_job");
        }
        catch (Exception jobBridge2) {
            // empty catch block
        }
        try {
            jobBridge2 = JobBridgeFactory.getInstance().getDefaultBridge();
            jobBridge2.unscheduleJob("rta_visits_minute_job", "rta_visits_minute_job");
        }
        catch (Exception jobBridge3) {
            // empty catch block
        }
        try {
            jobBridge2 = JobBridgeFactory.getInstance().getDefaultBridge();
            jobBridge2.unscheduleJob("rta_params_job", "rta_params_job");
        }
        catch (Exception jobBridge4) {
            // empty catch block
        }
        try {
            jobBridge2 = JobBridgeFactory.getInstance().getDefaultBridge();
            jobBridge2.unscheduleJob("rta_visits_day_job", "rta_visits_day_job");
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public void initWhenStarted(ServletContext context) throws Exception {
        this.paramDeployStatusService.fixDeployStatus();
        this.paramCacheManagerService.init();
    }
}

