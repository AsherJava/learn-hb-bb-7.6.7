/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.plantask.extend.job.PlanTaskRunner
 *  com.jiuqi.common.plantask.extend.job.Runner
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.common.datasync.runner;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.datasync.dto.CommonDataSyncSettingItemDTO;
import com.jiuqi.common.datasync.message.CommonDataSyncMessage;
import com.jiuqi.common.datasync.service.CommonDataSyncService;
import com.jiuqi.common.plantask.extend.job.PlanTaskRunner;
import com.jiuqi.common.plantask.extend.job.Runner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Component(value="commonDataSyncRunner")
@PlanTaskRunner(id="666CF3EE4AE84BB7B53E119B7EC74666", name="commonDataSyncRunner", title="\u6570\u636e\u540c\u6b65\u8ba1\u5212\u4efb\u52a1", settingPage="commonDataSyncConfig")
public class CommonDataSyncRunner
extends Runner {
    public static final String ID = "666CF3EE4AE84BB7B53E119B7EC74666";
    public static final String NAME = "commonDataSyncRunner";
    public static final String TITLE = "\u6570\u636e\u540c\u6b65\u8ba1\u5212\u4efb\u52a1";
    public static final String SETTING_PAGE = "commonDataSyncConfig";
    @Autowired
    private CommonDataSyncService dataSyncService;

    @Transactional(rollbackFor={Exception.class})
    public boolean excute(JobContext jobContext) {
        String runnerParameter = jobContext.getJob().getExtendedConfig();
        if (ObjectUtils.isEmpty(runnerParameter)) {
            this.appendLog("\u672a\u914d\u7f6e".concat(TITLE).concat("\u8ba1\u5212\u4efb\u52a1\u53c2\u6570\u3002"));
            return false;
        }
        this.appendLog("\u6267\u884c\u53c2\u6570\u8be6\u60c5\u3002");
        this.appendLog(runnerParameter);
        CommonDataSyncSettingItemDTO dataSyncItem = (CommonDataSyncSettingItemDTO)JsonUtils.readValue((String)runnerParameter, CommonDataSyncSettingItemDTO.class);
        String userName = jobContext.getAuthenticatedUsername();
        String type = dataSyncItem.getType();
        if (ObjectUtils.isEmpty(type)) {
            this.appendLog("\u6570\u636e\u540c\u6b65\u7c7b\u578b\u4e0d\u5141\u8bb8\u4e3a\u7a7a\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458\u3002");
            return false;
        }
        CommonDataSyncMessage dataSyncMessage = new CommonDataSyncMessage(dataSyncItem);
        dataSyncMessage.setUsername(userName);
        this.dataSyncService.dataSync(dataSyncMessage);
        return true;
    }
}

