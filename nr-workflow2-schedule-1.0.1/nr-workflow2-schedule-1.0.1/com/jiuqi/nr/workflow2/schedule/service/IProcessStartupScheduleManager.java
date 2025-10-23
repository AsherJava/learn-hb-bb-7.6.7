/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobsException
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  org.quartz.SchedulerException
 */
package com.jiuqi.nr.workflow2.schedule.service;

import com.jiuqi.bi.core.jobs.JobsException;
import com.jiuqi.nr.definition.facade.TaskDefine;
import java.text.ParseException;
import org.quartz.SchedulerException;

public interface IProcessStartupScheduleManager {
    public void updateStartupSchedule(TaskDefine var1) throws ParseException, JobsException;

    public void deleteStartupSchedule(TaskDefine var1) throws SchedulerException, JobsException, ParseException;

    public void removeStartupSchedule(TaskDefine var1) throws SchedulerException, JobsException, ParseException;

    public void immediateStartupSchedule(TaskDefine var1, String var2) throws JobsException, ParseException;

    public void createOrUpdateStartupSchedule(TaskDefine var1, String var2) throws JobsException, ParseException;
}

