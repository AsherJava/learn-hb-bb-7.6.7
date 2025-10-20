/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.org.OrgCategoryDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.task.StorageSyncTask
 */
package com.jiuqi.va.organization.task;

import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.org.OrgCategoryDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.task.StorageSyncTask;
import com.jiuqi.va.organization.service.OrgCategoryService;
import com.jiuqi.va.organization.service.impl.help.OrgDataCacheService;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnExpression(value="${nvwa.organization.init-catche-on-started:false}")
public class VaOrgCacheInitTask
implements StorageSyncTask {
    private static Logger logger = LoggerFactory.getLogger(VaOrgCacheInitTask.class);
    @Autowired
    private OrgCategoryService orgCatService;
    @Autowired
    private OrgDataCacheService orgDataCacheService;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void execute() {
        String tenantName = ShiroUtil.getTenantName();
        OrgCategoryDO catgParam = new OrgCategoryDO();
        catgParam.setTenantName(tenantName);
        PageVO<OrgCategoryDO> catList = this.orgCatService.list(catgParam);
        if (catList != null && catList.getTotal() > 0) {
            ExecutorService executorService = Executors.newWorkStealingPool();
            try {
                ArrayList<CompletableFuture<Void>> futures = new ArrayList<CompletableFuture<Void>>();
                for (OrgCategoryDO orgCat : catList.getRows()) {
                    CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                        OrgDTO tempParam = new OrgDTO();
                        tempParam.setTenantName(tenantName);
                        tempParam.setCategoryname(orgCat.getName());
                        this.orgDataCacheService.initCache(tempParam);
                        logger.info(Thread.currentThread().getName() + ": The [" + orgCat.getName() + "] cache is initialized.");
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

