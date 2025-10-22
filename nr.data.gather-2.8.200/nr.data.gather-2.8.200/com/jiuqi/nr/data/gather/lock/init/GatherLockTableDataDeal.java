/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.sf.models.ModuleInitiator
 *  javax.servlet.ServletContext
 */
package com.jiuqi.nr.data.gather.lock.init;

import com.jiuqi.nr.data.gather.lock.service.GatherLockServiceImpl;
import com.jiuqi.nvwa.sf.models.ModuleInitiator;
import javax.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GatherLockTableDataDeal
implements ModuleInitiator {
    @Autowired
    private GatherLockServiceImpl gatherLockService;

    public void init(ServletContext context) throws Exception {
        this.gatherLockService.cleanUpDataByServer();
    }

    public void initWhenStarted(ServletContext context) throws Exception {
    }
}

