/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.action.env.GcActionItemEnv
 *  com.jiuqi.gcreport.common.action.impl.AbstractGcActionItem
 */
package com.jiuqi.gcreport.reportdatasync.action;

import com.jiuqi.gcreport.common.action.env.GcActionItemEnv;
import com.jiuqi.gcreport.common.action.impl.AbstractGcActionItem;
import com.jiuqi.gcreport.reportdatasync.service.ReportDataSyncServerListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MultilevelDataUploadAction
extends AbstractGcActionItem {
    @Autowired
    private ReportDataSyncServerListService serverListService;

    protected MultilevelDataUploadAction() {
        super("multilevelDataUploadAction", "\u591a\u7ea7\u90e8\u7f72\u6570\u636e\u4e0a\u4f20", "gcreportdatasync-\u591a\u7ea7\u90e8\u7f72\u6570\u636e\u4e0a\u4f20", "#icon-16_GJ_A_GC_zidongduizhang");
    }

    public Object execute(GcActionItemEnv actionItemEnv) {
        return null;
    }
}

