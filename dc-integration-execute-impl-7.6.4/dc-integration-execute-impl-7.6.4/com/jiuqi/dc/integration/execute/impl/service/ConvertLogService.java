/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.integration.execute.client.dto.ConvertLogDTO
 *  com.jiuqi.dc.integration.execute.client.vo.ConvertLogVO
 */
package com.jiuqi.dc.integration.execute.impl.service;

import com.jiuqi.dc.integration.execute.client.dto.ConvertLogDTO;
import com.jiuqi.dc.integration.execute.client.vo.ConvertLogVO;
import com.jiuqi.dc.integration.execute.impl.domain.ConvertLogDO;
import java.util.List;

public interface ConvertLogService {
    public void insertLog(ConvertLogDO var1);

    public void batchInsertLog(List<ConvertLogDO> var1);

    public ConvertLogDTO queryDataConvertLog(String var1, int var2, int var3);

    public ConvertLogVO getExecuteById(ConvertLogVO var1);

    public Boolean batchDeleteById(List<String> var1);

    public void updateExecuteById(String var1);

    public void updateRunnerIdById(String var1, String var2);

    public void updateMessageAndState(String var1, String var2, int var3);
}

