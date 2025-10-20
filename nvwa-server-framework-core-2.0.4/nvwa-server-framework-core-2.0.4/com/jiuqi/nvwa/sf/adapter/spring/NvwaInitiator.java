/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.application.ApplicationInitialization
 *  com.jiuqi.np.core.application.NpApplication
 */
package com.jiuqi.nvwa.sf.adapter.spring;

import com.jiuqi.np.core.application.ApplicationInitialization;
import com.jiuqi.np.core.application.NpApplication;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NvwaInitiator {
    @Autowired(required=false)
    private List<ApplicationInitialization> applicationInitializations;
    @Autowired
    private NpApplication npApplication;
    private static final Logger LOGGER = LoggerFactory.getLogger(NvwaInitiator.class);

    public void onApplicationReady() {
        this.npApplication.runAsTenant("__default_tenant__", () -> {
            if (this.applicationInitializations != null) {
                for (ApplicationInitialization initialization : this.applicationInitializations) {
                    if (initialization.getClass().getName().equals("module.manager.init.ModuleManagerBean")) continue;
                    initialization.init(false);
                }
                LOGGER.info("\u521d\u59cb\u5316\u7cfb\u7edf\u5b8c\u6210\u3002");
            }
        });
    }
}

