/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.sf.models.ModuleInitiator
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  javax.servlet.ServletContext
 */
package com.jiuqi.nr.analysisreport.init;

import com.jiuqi.nr.analysisreport.support.AbstractExprParser;
import com.jiuqi.nr.analysisreport.utils.AnaUtils;
import com.jiuqi.nr.analysisreport.utils.TaskExecutorUtils;
import com.jiuqi.nvwa.sf.models.ModuleInitiator;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import java.util.List;
import javax.servlet.ServletContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class NrAnalysisReportInit
implements ModuleInitiator {
    @Lazy
    @Autowired
    private INvwaSystemOptionService systemOptionService;
    @Lazy
    @Autowired(required=false)
    private List<AbstractExprParser> exprParsers;
    private Logger logger = LoggerFactory.getLogger(NrAnalysisReportInit.class);

    public void init(ServletContext context) throws Exception {
    }

    public void initWhenStarted(ServletContext context) throws Exception {
        if (!CollectionUtils.isEmpty(this.exprParsers)) {
            for (AbstractExprParser exprParser : this.exprParsers) {
                try {
                    String varMaxThreadOptionItem = AnaUtils.getVarMaxThreadOptionItem(exprParser.getName());
                    int maxThreadNum = Integer.valueOf(this.systemOptionService.get("com.jiuqi.nr.analysisreport", varMaxThreadOptionItem));
                    ThreadPoolTaskExecutor threadPoolTaskExecutor = TaskExecutorUtils.initThreadPoolTaskExecutor(maxThreadNum, exprParser.getName());
                    exprParser.setThreadPoolTaskExecutor(threadPoolTaskExecutor);
                }
                catch (Exception e) {
                    this.logger.error(e.getMessage() + "\u521d\u59cb\u5316\u5206\u6790\u62a5\u544a\u53d8\u91cf\u7ebf\u7a0b\u6c60\u5931\u8d25", e);
                }
            }
        }
    }
}

