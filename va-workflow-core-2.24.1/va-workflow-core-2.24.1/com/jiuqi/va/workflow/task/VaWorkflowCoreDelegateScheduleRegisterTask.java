/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.schedule.ScheduleDO
 *  com.jiuqi.va.domain.task.ScheduleRegisterTask
 */
package com.jiuqi.va.workflow.task;

import com.jiuqi.va.domain.schedule.ScheduleDO;
import com.jiuqi.va.domain.task.ScheduleRegisterTask;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class VaWorkflowCoreDelegateScheduleRegisterTask
implements ScheduleRegisterTask {
    public List<ScheduleDO> getSchedules() {
        ArrayList<ScheduleDO> schedules = new ArrayList<ScheduleDO>();
        schedules.add(this.initSchedule("VaWorkflowCoreDelegateSchedule", "\u81ea\u52a8\u53d1\u9001\u548c\u6536\u56de\u59d4\u6258\u5f85\u529e", "\u5de5\u4f5c\u6d41\u6a21\u5757", "VA_WORKFLOW_DELEGATE_SCHEDULE"));
        return schedules;
    }
}

