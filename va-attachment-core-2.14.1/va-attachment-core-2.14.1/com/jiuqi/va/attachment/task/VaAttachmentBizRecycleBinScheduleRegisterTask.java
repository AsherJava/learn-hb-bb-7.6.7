/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.schedule.ScheduleDO
 *  com.jiuqi.va.domain.task.ScheduleRegisterTask
 */
package com.jiuqi.va.attachment.task;

import com.jiuqi.va.domain.schedule.ScheduleDO;
import com.jiuqi.va.domain.task.ScheduleRegisterTask;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class VaAttachmentBizRecycleBinScheduleRegisterTask
implements ScheduleRegisterTask {
    public List<ScheduleDO> getSchedules() {
        ArrayList<ScheduleDO> scheduleDOS = new ArrayList<ScheduleDO>();
        ScheduleDO scheduleDO = this.initSchedule("vaAttachmentRecycleBinSchedule", "\u9644\u4ef6-\u9644\u4ef6\u56de\u6536\u7ad9\u6570\u636e\u6e05\u9664", "\u9644\u4ef6\u6a21\u5757", "VA_ATTACHMENT_RECYCLE_BIN_CLEAR_MQ", 1);
        scheduleDO.setSystemflag(Integer.valueOf(1));
        scheduleDO.setDefaultCron("0 0 5 * * ?");
        scheduleDOS.add(scheduleDO);
        ScheduleDO scheduleDO1 = this.initSchedule("vaAttachmentCleanUseLessSchedule", "\u9644\u4ef6-\u8fd8\u539f\u5220\u9664\u5f85\u786e\u8ba4\u72b6\u6001\u9644\u4ef6", "\u9644\u4ef6\u6a21\u5757", "VA_ATTACHMENT_CLEAN_USE_LESS_MQ", 1);
        scheduleDO1.setSystemflag(Integer.valueOf(1));
        scheduleDO1.setDefaultCron("0 0 6 * * ?");
        scheduleDOS.add(scheduleDO1);
        ScheduleDO scheduleDO2 = this.initSchedule("vaAttachmentConfirmDataExecuteSchedule", "\u9644\u4ef6-\u9644\u4ef6\u786e\u8ba4\u8868\u6570\u636e\u786e\u8ba4", "\u9644\u4ef6\u6a21\u5757", "VA_ATTACHMENT_CONFIRM_DATA_EXECUTE_MQ", 1);
        scheduleDO2.setSystemflag(Integer.valueOf(1));
        scheduleDO2.setDefaultCron("0 0 4 * * ?");
        scheduleDOS.add(scheduleDO2);
        return scheduleDOS;
    }
}

