/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.taskschedule.streamdb.db.dao;

import com.jiuqi.common.taskschedule.streamdb.db.enums.EntTaskState;
import com.jiuqi.common.taskschedule.streamdb.db.eo.EntTaskInfoEo;
import java.util.Date;
import java.util.List;

public interface EntDbTaskScheduleDao {
    public boolean receive(String var1);

    public void publish(EntTaskInfoEo var1);

    public List<String> listFinishMessageId(List<String> var1);

    public void updateTime(List<String> var1);

    public List<EntTaskInfoEo> listAllTimeoutMessage();

    public void retry(EntTaskInfoEo var1);

    public void updateMessageStatus(String var1, EntTaskState var2);

    public List<EntTaskInfoEo> listQueueFirstByQueueNameList(List<String> var1);

    public void insertHistoryTableData(Date var1);

    public void deleteHistoryData(Date var1);

    public void deleteHistoryTableData(List<String> var1);

    public List<String> listIdByUpdateTime(Date var1);

    public void deleteClobByIdList(List<String> var1);

    public void deleteErrorClobByIdList(List<String> var1);

    public void updateErrorMessage(String var1, String var2);

    public String getMessageBodyFormClob(String var1);
}

