/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.reportnotice.job;

import com.jiuqi.gcreport.reportnotice.factory.ReportNoticeFactory;
import com.jiuqi.gcreport.reportnotice.job.ReportNoticeJob;
import com.jiuqi.gcreport.reportnotice.service.ReportNoticeService;
import java.util.Map;
import org.springframework.beans.BeansException;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ReportNoticeRegister
implements ApplicationContextAware,
ApplicationRunner {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (ReportNoticeRegister.applicationContext == null) {
            ReportNoticeRegister.applicationContext = applicationContext;
        }
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Map<String, Object> runnerList = applicationContext.getBeansWithAnnotation(ReportNoticeJob.class);
        runnerList.keySet().forEach(runner -> {
            ReportNoticeJob taskRunner = applicationContext.findAnnotationOnBean((String)runner, ReportNoticeJob.class);
            if (taskRunner != null) {
                ReportNoticeService runnerExecutor = (ReportNoticeService)applicationContext.getBean(taskRunner.classPath());
                ReportNoticeFactory.getInstance().setCode2Class(taskRunner.code(), runnerExecutor);
            }
        });
    }
}

