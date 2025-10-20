/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.login.domain.NvwaLoginUserDTO
 */
package com.jiuqi.common.datasync.service;

import com.jiuqi.common.datasync.dto.CommonDataSyncExecutorDTO;
import com.jiuqi.common.datasync.executor.CommonDataSyncExecutor;
import com.jiuqi.common.datasync.feign.CommonDataSyncNvwaFeignClient;
import com.jiuqi.common.datasync.message.CommonDataSyncMessage;
import com.jiuqi.nvwa.login.domain.NvwaLoginUserDTO;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

public interface CommonDataSyncService {
    public void dataSync(CommonDataSyncMessage var1);

    public CommonDataSyncNvwaFeignClient getNvwaFeignClient();

    public NvwaLoginUserDTO initNvwaFeignClientTokenEnv(String var1, String var2, String var3) throws URISyntaxException;

    public List<CommonDataSyncExecutorDTO> getDataSyncExecutorDTOs();

    public Map<String, List<CommonDataSyncExecutorDTO>> getDiscoveryDataSyncExecutors();

    public CommonDataSyncExecutor findDataSyncExecutorByType(String var1);
}

