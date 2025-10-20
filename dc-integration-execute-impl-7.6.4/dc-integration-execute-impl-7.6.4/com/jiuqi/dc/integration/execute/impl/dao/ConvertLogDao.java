/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.integration.execute.client.vo.ConvertLogVO
 *  com.jiuqi.dc.taskscheduling.logquery.client.vo.ExecuteStateVO
 */
package com.jiuqi.dc.integration.execute.impl.dao;

import com.jiuqi.dc.integration.execute.client.vo.ConvertLogVO;
import com.jiuqi.dc.integration.execute.impl.domain.ConvertLogDO;
import com.jiuqi.dc.taskscheduling.logquery.client.vo.ExecuteStateVO;
import java.util.List;

public interface ConvertLogDao {
    public void insert(ConvertLogDO var1);

    public void batchInsert(List<ConvertLogDO> var1);

    public List<ConvertLogVO> queryWithTaskLog(String var1, int var2, int var3);

    public int queryCount(String var1);

    public ExecuteStateVO getExecuteById(String var1);

    public void updateExecuteById(String var1);

    public ConvertLogVO getConvertLogById(String var1);

    public int batchDeleteById(List<String> var1);

    public void updateRunnerIdById(String var1, String var2);

    public void updateMessageAndState(String var1, String var2, int var3);
}

