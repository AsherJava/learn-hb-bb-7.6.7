/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.task.StorageSyncTask
 */
package com.jiuqi.va.basedata.task;

import com.jiuqi.va.basedata.service.BaseDataDefineService;
import com.jiuqi.va.basedata.service.impl.help.BaseDataCacheService;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.task.StorageSyncTask;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VaBasedataCacheInitTask
implements StorageSyncTask {
    private static Logger logger = LoggerFactory.getLogger(VaBasedataCacheInitTask.class);
    @Autowired
    private BaseDataDefineService defineService;
    @Autowired
    private BaseDataCacheService dataCacheService;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void execute() {
        String tenantName = ShiroUtil.getTenantName();
        BaseDataDefineDTO defineParam = new BaseDataDefineDTO();
        defineParam.setTenantName(tenantName);
        PageVO<BaseDataDefineDO> defineList = this.defineService.list(defineParam);
        if (defineList != null && defineList.getTotal() > 0) {
            ExecutorService executorService = Executors.newWorkStealingPool();
            try {
                ArrayList<CompletableFuture<Void>> futures = new ArrayList<CompletableFuture<Void>>();
                for (BaseDataDefineDO defineDO : defineList.getRows()) {
                    if (defineDO.getCachedisabled() == null || defineDO.getCachedisabled() != 9) continue;
                    CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                        BaseDataDTO tempParam = new BaseDataDTO();
                        tempParam.setTenantName(tenantName);
                        tempParam.setTableName(defineDO.getName());
                        this.dataCacheService.initCache(tempParam);
                        logger.info(Thread.currentThread().getName() + ": The [" + defineDO.getName() + "] cache is initialized.");
                    }, executorService);
                    futures.add(future);
                }
                CompletableFuture<Void> allFutures = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
                allFutures.join();
            }
            catch (Throwable throwable) {
            }
            finally {
                executorService.shutdown();
            }
        }
    }

    public boolean needCompareVersion() {
        return false;
    }
}

