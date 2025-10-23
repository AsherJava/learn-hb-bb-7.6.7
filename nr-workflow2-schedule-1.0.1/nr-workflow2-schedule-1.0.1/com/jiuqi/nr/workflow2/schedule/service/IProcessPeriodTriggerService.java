/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  org.quartz.Trigger
 */
package com.jiuqi.nr.workflow2.schedule.service;

import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.workflow2.schedule.enumeration.WFSOffsetType;
import java.text.ParseException;
import java.time.LocalTime;
import org.quartz.Trigger;

public interface IProcessPeriodTriggerService {
    public Trigger buildTrigger(TaskDefine var1, IPeriodEntity var2, String var3, LocalTime var4) throws ParseException;

    public Trigger buildTrigger(TaskDefine var1, IPeriodEntity var2, String var3, WFSOffsetType var4, int var5, LocalTime var6) throws ParseException;

    public Trigger buildStartupTrigger(TaskDefine var1, String var2) throws ParseException;
}

