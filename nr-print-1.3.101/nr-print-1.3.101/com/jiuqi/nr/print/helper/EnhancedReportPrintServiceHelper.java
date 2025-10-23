/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.dispatch.core.ITaskContext
 *  com.jiuqi.nvwa.dispatch.core.TaskContextBuilder
 *  com.jiuqi.nvwa.dispatch.core.TaskException
 *  com.jiuqi.nvwa.dispatch.core.submit.ITaskDispatcher
 */
package com.jiuqi.nr.print.helper;

import com.jiuqi.nr.print.service.IPrintDesignExtendService;
import com.jiuqi.nr.print.service.Impl.PrintDesignInvokeServiceImpl;
import com.jiuqi.nvwa.dispatch.core.ITaskContext;
import com.jiuqi.nvwa.dispatch.core.TaskContextBuilder;
import com.jiuqi.nvwa.dispatch.core.TaskException;
import com.jiuqi.nvwa.dispatch.core.submit.ITaskDispatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EnhancedReportPrintServiceHelper {
    public static final String DISPATCH_PRINT_TASKID = "EnhancedReportPrintService";
    private IPrintDesignExtendService defaultReportDesignService = null;
    private static Logger logger = LoggerFactory.getLogger(EnhancedReportPrintServiceHelper.class);
    @Value(value="${jiuqi.nr.print.enhanced:true}")
    private boolean enableEnhancedPrint = true;
    @Autowired
    protected ITaskDispatcher dispatcher;

    protected IPrintDesignExtendService getEhancedReportPrintService() {
        if (this.enableEnhancedPrint) {
            TaskContextBuilder builder = new TaskContextBuilder();
            try {
                ITaskContext taskContext = builder.taskId(DISPATCH_PRINT_TASKID).build();
                return (IPrintDesignExtendService)this.dispatcher.createService(taskContext, PrintDesignInvokeServiceImpl.class);
            }
            catch (TaskException e) {
                logger.error("\u65b0\u62a5\u8868\u6253\u5370\u670d\u52a1\u5668\uff1a\u83b7\u53d6\u5206\u5e03\u5f0f\u6846\u67b6\u6269\u5c55\u6253\u5370\u670d\u52a1\u5668\u5931\u8d25\uff0c\u5c06\u4f7f\u7528\u9ed8\u8ba4\u6253\u5370\u670d\u52a1\u5668. ", e);
            }
        }
        if (null == this.defaultReportDesignService) {
            logger.info("\u65b0\u62a5\u8868\u6253\u5370\u670d\u52a1\u5668\uff1a\u672a\u542f\u7528\u5206\u5e03\u5f0f\u5bbd\u8857\u6269\u5c55\u6216\u8005\u542f\u7528\u5931\u8d25\uff0c\u5c06\u4f7f\u7528\u9ed8\u8ba4\u6253\u5370\u670d\u52a1\u5668.");
            this.defaultReportDesignService = new PrintDesignInvokeServiceImpl();
        }
        return this.defaultReportDesignService;
    }
}

