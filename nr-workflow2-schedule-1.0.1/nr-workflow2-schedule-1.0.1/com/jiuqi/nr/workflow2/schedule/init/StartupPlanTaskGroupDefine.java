/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.jobmanager.entity.PlanTaskGroupEO
 *  com.jiuqi.util.OrderGenerator
 */
package com.jiuqi.nr.workflow2.schedule.init;

import com.jiuqi.nvwa.jobmanager.entity.PlanTaskGroupEO;
import com.jiuqi.util.OrderGenerator;
import java.time.Instant;

public class StartupPlanTaskGroupDefine
extends PlanTaskGroupEO {
    public static final String ID = "workflow2-schedule-startup-job-group";
    public static final String TITLE = "\u6d41\u7a0b\u542f\u52a8\u4efb\u52a1";

    public StartupPlanTaskGroupDefine() {
        this.setId(ID);
        this.setTitle(TITLE);
        this.setModifyTime(Instant.now());
        this.setOrder(OrderGenerator.newOrder());
        this.setParent("0dcf7d08-4309-420c-9f2c-000000000001");
        this.setAddDisable(true);
    }
}

