/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.quartz.Trigger
 */
package com.jiuqi.nr.reminder.plan.job;

import com.jiuqi.nr.reminder.plan.CbPlanDTO;
import java.util.List;
import org.quartz.Trigger;

public interface CbTriggerBuilder {
    public List<Trigger> build(CbPlanDTO var1);
}

