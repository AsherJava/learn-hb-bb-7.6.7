/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.sf.models.ModuleInitiator
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  javax.servlet.ServletContext
 */
package com.jiuqi.dc.base.common.init;

import com.jiuqi.dc.base.common.definition.service.ITableCheckService;
import com.jiuqi.dc.base.common.event.StartReadyEvent;
import com.jiuqi.dc.base.common.event.SyncCacheEvent;
import com.jiuqi.dc.base.common.event.TempTableHandleEvent;
import com.jiuqi.nvwa.sf.models.ModuleInitiator;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import javax.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DatacenterModuleInitiator
implements ModuleInitiator {
    @Value(value="${acct.sync-table:true}")
    private boolean syncTable;
    @Value(value="${jiuqi.gcreport.env.inittablecheck:false}")
    private boolean checkTable;
    @Autowired
    private ITableCheckService tableCheckService;

    public void init(ServletContext context) throws Exception {
    }

    public void initWhenStarted(ServletContext context) throws Exception {
        ApplicationContextRegister.getApplicationContext().publishEvent(new TempTableHandleEvent(this));
        if (this.checkTable) {
            this.tableCheckService.tableCheck(null, Boolean.TRUE);
        }
        ApplicationContextRegister.getApplicationContext().publishEvent(new SyncCacheEvent((Object)this, "__default_tenant__"));
        ApplicationContextRegister.getApplicationContext().publishEvent(new StartReadyEvent(this));
    }
}

