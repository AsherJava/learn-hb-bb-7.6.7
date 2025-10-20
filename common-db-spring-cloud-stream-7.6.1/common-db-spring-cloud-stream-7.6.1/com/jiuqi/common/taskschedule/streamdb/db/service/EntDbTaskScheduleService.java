/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.taskschedule.streamdb.db.service;

import com.jiuqi.common.taskschedule.streamdb.db.enums.EntTaskState;
import com.jiuqi.common.taskschedule.streamdb.db.eo.EntTaskInfoEo;
import java.util.List;

public interface EntDbTaskScheduleService {
    public boolean receive(String var1);

    public void publish(EntTaskInfoEo var1);

    public List<String> listFinishMessageId(List<String> var1);

    public void updateTime(List<String> var1);

    public List<EntTaskInfoEo> listAllTimeoutMessage();

    public void retry(EntTaskInfoEo var1);

    public void updateMessageStatus(String var1, EntTaskState var2);

    public List<EntTaskInfoEo> listQueueFirstByQueueNameList(List<String> var1);

    public void deleteHistory();

    public void updateErrorMessage(String var1, String var2);

    public String getMessageBodyFormClob(String var1);
}

