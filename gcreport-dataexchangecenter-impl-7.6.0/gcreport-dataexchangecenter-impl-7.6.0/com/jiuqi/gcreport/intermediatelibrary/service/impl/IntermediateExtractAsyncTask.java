/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.asynctask.NpAsyncTaskExecutor
 */
package com.jiuqi.gcreport.intermediatelibrary.service.impl;

import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.intermediatelibrary.entity.ILExtractCondition;
import com.jiuqi.gcreport.intermediatelibrary.service.IntermediateLibraryService;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.NpAsyncTaskExecutor;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class IntermediateExtractAsyncTask
implements NpAsyncTaskExecutor {
    @Lazy
    @Autowired
    private IntermediateLibraryService intermediateLibraryService;
    private static final Logger logger = LoggerFactory.getLogger(IntermediateExtractAsyncTask.class);

    public void execute(Object args, AsyncTaskMonitor asyncTaskMonitor) {
        long startTime = System.currentTimeMillis();
        try {
            ILExtractCondition iLExtractCondition = this.intermediateLibraryService.getAsyncData(args, asyncTaskMonitor);
            iLExtractCondition.setPushType(false);
            this.intermediateLibraryService.programmeHandle(iLExtractCondition);
            if (asyncTaskMonitor.isCancel()) {
                String retStr = "\u4efb\u52a1\u53d6\u6d88";
                asyncTaskMonitor.canceled(retStr, (Object)retStr);
            } else {
                ArrayList<String> intermediateMessages = new ArrayList<String>(iLExtractCondition.getLibraryMessages());
                String loggerStr = (String)intermediateMessages.get(intermediateMessages.size() - 1);
                intermediateMessages = new ArrayList(intermediateMessages.subList(0, intermediateMessages.size() - 1));
                intermediateMessages.add(0, loggerStr);
                asyncTaskMonitor.finish(GcI18nUtil.getMessage((String)"gc.intermediate.extract.success"), intermediateMessages);
            }
            logger.info("\u5171\u6267\u884c:" + (System.currentTimeMillis() - startTime) / 1000L + "\u79d2");
        }
        catch (Exception e) {
            asyncTaskMonitor.error(GcI18nUtil.getMessage((String)"gc.intermediate.extract.error") + e.getMessage(), (Throwable)e);
            logger.error("\u5171\u6267\u884c:" + (System.currentTimeMillis() - startTime) / 1000L + "\u79d2", e);
        }
    }

    public String getTaskPoolType() {
        return "GC_ASYNC_TASK_INTERMEDIATE_EXTRACT";
    }
}

