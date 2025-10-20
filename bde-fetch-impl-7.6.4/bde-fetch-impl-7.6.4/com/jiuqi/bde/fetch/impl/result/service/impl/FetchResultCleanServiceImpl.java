/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.config.FetchResultConfig
 *  com.jiuqi.bde.bizmodel.execute.service.FetchDataResultService
 *  com.jiuqi.bi.logging.ILogger
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.bde.fetch.impl.result.service.impl;

import com.jiuqi.bde.bizmodel.execute.config.FetchResultConfig;
import com.jiuqi.bde.bizmodel.execute.service.FetchDataResultService;
import com.jiuqi.bde.fetch.impl.result.entity.FetchResultMappingEO;
import com.jiuqi.bde.fetch.impl.result.enums.FetchResultTableEnum;
import com.jiuqi.bde.fetch.impl.result.enums.FetchResultTableStatusEnum;
import com.jiuqi.bde.fetch.impl.result.service.FetchResultCleanService;
import com.jiuqi.bde.fetch.impl.result.service.FetchResultMappingService;
import com.jiuqi.bde.fetch.impl.result.util.FetchResultTableUtil;
import com.jiuqi.bi.logging.ILogger;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

@Service
public class FetchResultCleanServiceImpl
implements FetchResultCleanService {
    @Autowired
    private FetchResultMappingService fetchResultMappingService;

    @Override
    public void doClean(ILogger logger) {
        for (int i = 1; i <= FetchResultConfig.fetchResultTableNum; ++i) {
            FetchResultMappingEO mappingEO = this.fetchResultMappingService.getMappingEOByRouteNum(i);
            if (FetchResultTableStatusEnum.STOP.equals((Object)mappingEO.getRouteStatus())) continue;
            logger.info("\r\n" + ((FetchResultCleanService)ApplicationContextRegister.getBean(FetchResultCleanService.class)).doCleanEachRoute(mappingEO));
        }
    }

    @Override
    @Transactional(rollbackFor={Exception.class}, propagation=Propagation.REQUIRES_NEW)
    public String doCleanEachRoute(FetchResultMappingEO mappingEO) {
        int routeNum = mappingEO.getRouteNum();
        StopWatch stopWatch = new StopWatch(String.format("\u8def\u7531\u3010%1$s\u3011\u6e05\u7406", routeNum));
        stopWatch.start("\u6807\u8bb0\u8def\u7531\u72b6\u6001\u4e3a\u505c\u7528");
        ((FetchResultMappingService)ApplicationContextRegister.getBean(FetchResultMappingService.class)).changeRouteLock(routeNum);
        stopWatch.stop();
        StringBuilder log = new StringBuilder();
        stopWatch.start("\u7b49\u5f85\u6267\u884c");
        try {
            Thread.sleep(550000L);
        }
        catch (InterruptedException e) {
            log.append(String.format("\u7b49\u5f85\u6267\u884c\u8def\u7531\u7f16\u53f7\u3010%1$d\u3011\u5bf9\u5e94\u7684\u7ed3\u679c\u8868\u51fa\u73b0\u9519\u8bef\uff0c\u8be6\u7ec6\u539f\u56e0\uff1a%2$s\n", mappingEO.getRouteNum(), e.getMessage()));
            this.fetchResultMappingService.changeRouteStart(routeNum);
            Thread.currentThread().interrupt();
            throw new BusinessRuntimeException((Throwable)e);
        }
        stopWatch.stop();
        for (FetchResultTableEnum fetchResultTableEnum : FetchResultTableEnum.values()) {
            try {
                stopWatch.start(String.format("\u6e05\u7406%1$s_%2$d\u8868\u6210\u529f;\n", fetchResultTableEnum.getTableName(), routeNum));
                ((FetchDataResultService)ApplicationContextRegister.getBean(FetchDataResultService.class)).truncateByTableName(FetchResultTableUtil.getTableName(fetchResultTableEnum.getTableName(), routeNum));
                stopWatch.stop();
            }
            catch (Exception e) {
                log.append(String.format("\u6e05\u7406%1$s_%2$d\u8868\u5931\u8d25;\n", fetchResultTableEnum.getTableName(), routeNum));
                stopWatch.stop();
            }
        }
        stopWatch.start("\u6807\u8bb0\u8def\u7531\u72b6\u6001\u4e3a\u542f\u7528");
        ((FetchResultMappingService)ApplicationContextRegister.getBean(FetchResultMappingService.class)).changeRouteStart(routeNum);
        stopWatch.stop();
        return stopWatch.prettyPrint();
    }
}

