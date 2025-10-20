/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.intermediatelibrary.job;

import com.jiuqi.gcreport.intermediatelibrary.job.IntermediateLibraryFactory;
import com.jiuqi.gcreport.intermediatelibrary.job.IntermediateLibraryJob;
import com.jiuqi.gcreport.intermediatelibrary.service.IntermediateLibraryBulkService;
import java.util.Map;
import org.springframework.beans.BeansException;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class IntermediateLibraryRegister
implements ApplicationContextAware,
ApplicationRunner {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (IntermediateLibraryRegister.applicationContext == null) {
            IntermediateLibraryRegister.applicationContext = applicationContext;
        }
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Map<String, Object> runnerList = applicationContext.getBeansWithAnnotation(IntermediateLibraryJob.class);
        runnerList.keySet().forEach(runner -> {
            IntermediateLibraryJob taskRunner = applicationContext.findAnnotationOnBean((String)runner, IntermediateLibraryJob.class);
            if (taskRunner != null) {
                IntermediateLibraryBulkService service = (IntermediateLibraryBulkService)applicationContext.getBean(taskRunner.classPath());
                IntermediateLibraryFactory.getInstance().setIntermediateLibraryBulkService(service);
            }
        });
    }
}

