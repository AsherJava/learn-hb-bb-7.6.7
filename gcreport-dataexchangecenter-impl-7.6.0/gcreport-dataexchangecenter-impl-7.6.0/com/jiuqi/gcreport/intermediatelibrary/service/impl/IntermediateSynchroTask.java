/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.asynctask.NpAsyncTaskExecutor
 *  javax.annotation.Resource
 */
package com.jiuqi.gcreport.intermediatelibrary.service.impl;

import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.intermediatelibrary.entity.ILExtractCondition;
import com.jiuqi.gcreport.intermediatelibrary.service.IntermediateProgrammeService;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.NpAsyncTaskExecutor;
import java.util.ArrayList;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class IntermediateSynchroTask
implements NpAsyncTaskExecutor {
    @Lazy
    @Resource
    private IntermediateProgrammeService intermediateProgrammeService;
    private static final Logger logger = LoggerFactory.getLogger(IntermediateSynchroTask.class);

    public void execute(Object args, AsyncTaskMonitor asyncTaskMonitor) {
        long startTime = System.currentTimeMillis();
        ILExtractCondition iLExtractCondition = (ILExtractCondition)JsonUtils.readValue((String)String.valueOf(args), ILExtractCondition.class);
        iLExtractCondition.setAsyncTaskMonitor(asyncTaskMonitor);
        try {
            this.intermediateProgrammeService.synchroProgramme(iLExtractCondition);
            if (asyncTaskMonitor.isCancel()) {
                String retStr = "\u4efb\u52a1\u53d6\u6d88";
                asyncTaskMonitor.canceled(retStr, (Object)retStr);
            } else {
                ArrayList<String> intermediateMessages = new ArrayList<String>(iLExtractCondition.getLibraryMessages());
                asyncTaskMonitor.finish("\u540c\u6b65\u5143\u6570\u636e\u4fe1\u606f\u6210\u529f\u3002", intermediateMessages);
            }
            asyncTaskMonitor.finish(GcI18nUtil.getMessage((String)"gc.intermediate.sync.success"), null);
            logger.info("\u5171\u6267\u884c:" + (System.currentTimeMillis() - startTime) / 1000L + "\u79d2");
        }
        catch (Exception e) {
            asyncTaskMonitor.error(GcI18nUtil.getMessage((String)"gc.intermediate.sync.error") + e.getMessage(), (Throwable)e);
            logger.error("\u5171\u6267\u884c:" + (System.currentTimeMillis() - startTime) / 1000L + "\u79d2", e);
        }
    }

    public String getTaskPoolType() {
        return "GC_ASYNC_TASK_INTERMEDIATE_SYNC";
    }
}

