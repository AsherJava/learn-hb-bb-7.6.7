/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.service.FetchDataResultService
 *  com.jiuqi.bde.common.constant.MemoryBalanceTypeEnum
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.plantask.extend.job.PlanTaskRunner
 *  com.jiuqi.common.plantask.extend.job.Runner
 *  com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionItemValue
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  com.jiuqi.nvwa.systemoption.vo.SystemOptionSave
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.bde.plugin.common.runner;

import com.jiuqi.bde.bizmodel.execute.service.FetchDataResultService;
import com.jiuqi.bde.common.constant.MemoryBalanceTypeEnum;
import com.jiuqi.bde.plugin.common.cache.memcache.memtable.service.MemoryTableDataService;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.plantask.extend.job.PlanTaskRunner;
import com.jiuqi.common.plantask.extend.job.Runner;
import com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionItemValue;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import com.jiuqi.nvwa.systemoption.vo.SystemOptionSave;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@PlanTaskRunner(id="699a4b48a5d0fe2a8259e096cc570657", name="HotSpotDataCleanRunner", title="\u70ed\u70b9\u6570\u636e\u6e05\u9664\u8ba1\u5212\u4efb\u52a1", group="BDE/\u6570\u636e\u6e05\u7406")
@Component
public class BdeHotSpotDataCleanRunner
extends Runner {
    public static final String ID = "699a4b48a5d0fe2a8259e096cc570657";
    public static final String NAME = "HotSpotDataCleanRunner";
    public static final String TITLE = "\u70ed\u70b9\u6570\u636e\u6e05\u9664\u8ba1\u5212\u4efb\u52a1";
    @Autowired
    private INvwaSystemOptionService optionService;
    @Autowired
    private MemoryTableDataService memoryTableDataService;
    @Autowired
    private FetchDataResultService fetchResultService;

    protected boolean excute(String runnerParameter) {
        StringBuilder log = new StringBuilder();
        String expirationStr = this.optionService.findValueById("BDE_TABLEDATA_EXPIRATION");
        int cacheValidTime = 1000 * (StringUtils.isEmpty((String)expirationStr) ? 0 : Integer.parseInt(expirationStr));
        SystemOptionSave systemOptionSave = new SystemOptionSave();
        systemOptionSave.setKey("bde_sysoption");
        ArrayList<SystemOptionItemValue> itemValues = new ArrayList<SystemOptionItemValue>();
        SystemOptionItemValue systemOptionItemValue = new SystemOptionItemValue("BDE_TABLEDATA_EXPIRATION", "0");
        itemValues.add(systemOptionItemValue);
        systemOptionSave.setItemValues(itemValues);
        this.optionService.save(systemOptionSave);
        try {
            Thread.sleep(60000L);
        }
        catch (InterruptedException e) {
            log.append("\u70ed\u70b9\u6570\u636e\u6e05\u7406\u5931\u8d25\n");
            Thread.currentThread().interrupt();
        }
        for (MemoryBalanceTypeEnum memoryBalanceTypeEnum : MemoryBalanceTypeEnum.values()) {
            try {
                this.cleanTableByName(memoryBalanceTypeEnum.getCode(), cacheValidTime, log);
            }
            catch (Exception e) {
                log.append(String.format("\u6e05\u7406-%1$s-\u5931\u8d25;\n", memoryBalanceTypeEnum.getName()));
            }
        }
        SystemOptionSave systemOptionSave1 = new SystemOptionSave();
        systemOptionSave1.setKey("bde_sysoption");
        ArrayList<SystemOptionItemValue> itemValueList = new ArrayList<SystemOptionItemValue>();
        SystemOptionItemValue systemOptionItemValue1 = new SystemOptionItemValue("BDE_TABLEDATA_EXPIRATION", expirationStr == null ? "0" : expirationStr);
        itemValueList.add(systemOptionItemValue1);
        systemOptionSave1.setItemValues(itemValueList);
        this.optionService.save(systemOptionSave1);
        this.appendLog(String.valueOf(log));
        return true;
    }

    @Transactional(rollbackFor={Exception.class})
    public void cleanTableByName(String tableName, int cacheValidTime, StringBuilder log) {
        if (this.memoryTableDataService.countDataByTableName(tableName, System.currentTimeMillis() - (long)cacheValidTime) != 0) {
            log.append(String.format("\u6e05\u7406-%1$s-\u5931\u8d25,\u8868\u4e2d\u5b58\u5728\u672a\u8fc7\u671f\u6570\u636e; \n", tableName));
            return;
        }
        this.fetchResultService.truncateByTableName(tableName);
        log.append(String.format("\u6e05\u7406-%1$s-\u6210\u529f; \n", tableName));
    }
}

