/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.fetch.init.FetchInitTaskDTO
 *  com.jiuqi.bde.common.dto.fetch.init.FetchRegionDTO
 *  com.jiuqi.bde.common.dto.fetch.request.FetchRequestDTO
 *  com.jiuqi.bde.log.fetch.dto.FetchItemLogDTO
 *  com.jiuqi.nr.efdc.pojo.EfdcInfo
 */
package com.jiuqi.gcreport.bde.fetch.impl.service;

import com.jiuqi.bde.common.dto.fetch.init.FetchInitTaskDTO;
import com.jiuqi.bde.common.dto.fetch.init.FetchRegionDTO;
import com.jiuqi.bde.common.dto.fetch.request.FetchRequestDTO;
import com.jiuqi.bde.log.fetch.dto.FetchItemLogDTO;
import com.jiuqi.gcreport.bde.fetch.impl.handler.FetchDataFormDTO;
import com.jiuqi.nr.efdc.pojo.EfdcInfo;

public interface GcFetchDataExecuteService {
    public FetchInitTaskDTO buildFetchInitTask(EfdcInfo var1);

    public void doCalculate(FetchInitTaskDTO var1);

    public FetchRequestDTO buildFetchContext(FetchDataFormDTO var1, FetchRegionDTO var2);

    public FetchItemLogDTO buildFetchLogInfo(EfdcInfo var1);
}

