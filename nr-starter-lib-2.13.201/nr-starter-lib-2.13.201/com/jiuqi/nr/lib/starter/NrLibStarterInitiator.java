/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.temptable.inner.TempTableManagerInit
 *  com.jiuqi.nr.graph.rwlock.executer.DatabaseLockHeartbeat
 *  com.jiuqi.nvwa.sf.models.ModuleInitiator
 *  javax.servlet.ServletContext
 */
package com.jiuqi.nr.lib.starter;

import com.jiuqi.nr.common.temptable.inner.TempTableManagerInit;
import com.jiuqi.nr.graph.rwlock.executer.DatabaseLockHeartbeat;
import com.jiuqi.nvwa.sf.models.ModuleInitiator;
import javax.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NrLibStarterInitiator
implements ModuleInitiator {
    @Autowired
    private DatabaseLockHeartbeat databaseLockHeartbeat;
    @Autowired
    private TempTableManagerInit tempTableManagerInit;

    public void init(ServletContext context) throws Exception {
        this.databaseLockHeartbeat.init(context);
        this.tempTableManagerInit.init();
    }

    public void initWhenStarted(ServletContext context) throws Exception {
        this.databaseLockHeartbeat.initWhenStarted(context);
    }
}

