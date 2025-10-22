/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.dispatch.core.ITaskContext
 *  com.jiuqi.nvwa.dispatch.core.TaskContextBuilder
 *  com.jiuqi.nvwa.dispatch.core.TaskException
 *  com.jiuqi.nvwa.dispatch.core.submit.ITaskDispatcher
 *  com.jiuqi.xg.print.service.IPrintService
 */
package com.jiuqi.nr.dataentry.print.impl;

import com.jiuqi.nr.dataentry.print.INrEnhancedPrintService;
import com.jiuqi.nr.dataentry.print.impl.NrEnhancedPrintServiceImpl;
import com.jiuqi.nr.dataentry.service.IBatchExportService;
import com.jiuqi.nvwa.dispatch.core.ITaskContext;
import com.jiuqi.nvwa.dispatch.core.TaskContextBuilder;
import com.jiuqi.nvwa.dispatch.core.TaskException;
import com.jiuqi.nvwa.dispatch.core.submit.ITaskDispatcher;
import com.jiuqi.xg.print.service.IPrintService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

public abstract class AbstractNrEnhancedPrintService {
    public static final String DISPATCH_PRINT_TASKID = "nrEnhancedPrintService";
    private INrEnhancedPrintService defaultEnhancedPrintService = null;
    private Logger logger = LoggerFactory.getLogger(AbstractNrEnhancedPrintService.class);
    @Value(value="${jiuqi.nr.print.enhanced:true}")
    private boolean enableEnhancedPrint = true;
    @Autowired
    protected ITaskDispatcher dispatcher;
    @Autowired
    @Qualifier(value="EXPORT_BATCH_EXCEL")
    protected IBatchExportService defaultBatchExportService;
    @Autowired
    @Qualifier(value="defaultPrintService")
    protected IPrintService defaultPrintService;

    protected INrEnhancedPrintService getINrEnhancedPrintService() {
        if (this.enableEnhancedPrint) {
            TaskContextBuilder builder = new TaskContextBuilder();
            try {
                ITaskContext taskContext = builder.taskId(DISPATCH_PRINT_TASKID).build();
                return (INrEnhancedPrintService)this.dispatcher.createService(taskContext, NrEnhancedPrintServiceImpl.class);
            }
            catch (TaskException e) {
                this.logger.error("\u65b0\u62a5\u8868\u6253\u5370\u670d\u52a1\u5668\uff1a\u83b7\u53d6\u5206\u5e03\u5f0f\u6846\u67b6\u6269\u5c55\u6253\u5370\u670d\u52a1\u5668\u5931\u8d25\uff0c\u5c06\u4f7f\u7528\u9ed8\u8ba4\u6253\u5370\u670d\u52a1\u5668. ", e);
            }
        }
        if (null == this.defaultEnhancedPrintService) {
            this.logger.info("\u65b0\u62a5\u8868\u6253\u5370\u670d\u52a1\u5668\uff1a\u672a\u542f\u7528\u5206\u5e03\u5f0f\u5bbd\u8857\u6269\u5c55\u6216\u8005\u542f\u7528\u5931\u8d25\uff0c\u5c06\u4f7f\u7528\u9ed8\u8ba4\u6253\u5370\u670d\u52a1\u5668.");
            this.defaultEnhancedPrintService = new NrEnhancedPrintServiceImpl();
        }
        return this.defaultEnhancedPrintService;
    }
}

