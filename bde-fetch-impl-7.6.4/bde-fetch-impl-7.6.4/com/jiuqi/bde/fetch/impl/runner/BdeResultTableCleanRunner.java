/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.config.FetchResultConfig
 *  com.jiuqi.bde.bizmodel.execute.service.FetchDataResultService
 *  com.jiuqi.common.plantask.extend.job.Runner
 */
package com.jiuqi.bde.fetch.impl.runner;

import com.jiuqi.bde.bizmodel.execute.config.FetchResultConfig;
import com.jiuqi.bde.bizmodel.execute.service.FetchDataResultService;
import com.jiuqi.bde.fetch.impl.result.entity.FetchResultMappingEO;
import com.jiuqi.bde.fetch.impl.result.enums.FetchResultTableEnum;
import com.jiuqi.bde.fetch.impl.result.enums.FetchResultTableStatusEnum;
import com.jiuqi.bde.fetch.impl.result.service.FetchResultMappingService;
import com.jiuqi.bde.fetch.impl.result.util.FetchResultTableUtil;
import com.jiuqi.common.plantask.extend.job.Runner;
import org.springframework.beans.factory.annotation.Autowired;

public class BdeResultTableCleanRunner
extends Runner {
    public static final String ID = "57C211E6FD940770026177D168BFA0C0";
    public static final String NAME = "ResultTableCleanRunner";
    public static final String TITLE = "\u7ed3\u679c\u8868\u6e05\u9664\u8ba1\u5212\u4efb\u52a1";
    @Autowired
    private FetchDataResultService fetchResultService;
    @Autowired
    private FetchResultMappingService fetchResultMappingService;

    @Autowired
    protected boolean excute(String runnerParameter) {
        StringBuilder log = new StringBuilder();
        for (int i = 1; i <= FetchResultConfig.fetchResultTableNum; ++i) {
            FetchResultMappingEO mappingEO = this.fetchResultMappingService.getMappingEOByRouteNum(i);
            if (FetchResultTableStatusEnum.STOP.equals((Object)mappingEO.getRouteStatus())) continue;
            this.fetchResultMappingService.changeRouteLock(i);
            this.cleanTableByName(i, log);
            this.fetchResultMappingService.changeRouteStart(i);
        }
        this.appendLog(log.toString());
        return true;
    }

    private void cleanTableByName(int index, StringBuilder log) {
        boolean isClean = true;
        Long startTime = System.currentTimeMillis();
        try {
            if (System.currentTimeMillis() - startTime < 60000L) {
                isClean = true;
            }
        }
        catch (Exception e) {
            log.append(String.format("\u6e05\u7406%1$d\u7684\u7ed3\u679c\u8868\u5931\u8d25;\n", index));
        }
        if (isClean) {
            for (FetchResultTableEnum fetchResultTableEnum : FetchResultTableEnum.values()) {
                try {
                    this.fetchResultService.truncateByTableName(FetchResultTableUtil.getTableName(fetchResultTableEnum.getTableName(), index));
                    log.append(String.format("\u6e05\u7406%1$s_%2$d\u8868\u6210\u529f;\n", fetchResultTableEnum.getTableName(), index));
                }
                catch (Exception e) {
                    log.append(String.format("\u6e05\u7406%1$s_%2$d\u8868\u5931\u8d25;\n", fetchResultTableEnum.getTableName(), index));
                }
            }
        } else {
            log.append(String.format("\u6e05\u7406%1$d\u7684\u7ed3\u679c\u8868\u5931\u8d25\n", index));
        }
    }
}

