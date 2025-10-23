/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.schedule.dao;

import com.jiuqi.nr.workflow2.schedule.dao.WFSTriggerEntity;
import java.util.List;

public interface IWFSTriggerPlanDao {
    public static final String TABLE_NAME = "NR_WF_STARTUP_TRIGGER";
    public static final String F_TASK_KEY = "EXEC_TASK_KEY";
    public static final String F_PERIOD = "EXEC_PERIOD";
    public static final String F_START_TIME = "EXEC_START_TIME";
    public static final String F_END_TIME = "EXEC_END_TIME";
    public static final String F_ACTUAL_TIME = "EXEC_ACTUAL_TIME";
    public static final String F_STATUS = "EXEC_STATUS";
    public static final String F_COUNT = "EXEC_COUNT";

    public int[] insertRows(List<WFSTriggerEntity> var1);

    public int updateRow(List<WFSTriggerEntity> var1);

    public int updateRow(WFSTriggerEntity var1);

    public int deleteRows(String var1);

    public WFSTriggerEntity queryRowByTaskAndPeriod(String var1, String var2);

    public List<WFSTriggerEntity> queryRowsByTask(String var1);
}

