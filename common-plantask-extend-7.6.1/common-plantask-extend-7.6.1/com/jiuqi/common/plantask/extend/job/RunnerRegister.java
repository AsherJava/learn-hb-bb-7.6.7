/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobFactory
 *  com.jiuqi.bi.core.jobs.JobFactoryManager
 *  com.jiuqi.np.core.application.ApplicationInitialization
 */
package com.jiuqi.common.plantask.extend.job;

import com.jiuqi.bi.core.jobs.JobFactory;
import com.jiuqi.bi.core.jobs.JobFactoryManager;
import com.jiuqi.common.plantask.extend.job.PlanTaskRunner;
import com.jiuqi.common.plantask.extend.job.RunnerJobFactory;
import com.jiuqi.np.core.application.ApplicationInitialization;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value=-2147483648)
public class RunnerRegister
implements ApplicationInitialization,
ApplicationContextAware {
    private static final String JOB_FACTORY_SUFFIX = "_job_factory";
    private static ConfigurableApplicationContext applicationContext;
    private static final Logger logger;
    private Set<String> registerTaskIdSet = new HashSet<String>();

    public synchronized void init() {
        Map<String, Object> runnerList = applicationContext.getBeansWithAnnotation(PlanTaskRunner.class);
        if (runnerList == null) {
            return;
        }
        List taskRunnerList = runnerList.keySet().stream().map(runner -> {
            PlanTaskRunner taskRunner = applicationContext.findAnnotationOnBean((String)runner, PlanTaskRunner.class);
            if (taskRunner == null) {
                System.err.println("PlanTaskRunner of Renner is null. \t" + runner);
            }
            return taskRunner;
        }).filter(Objects::nonNull).sorted((o1, o2) -> {
            if (o1.group().equals(o2.group())) {
                return o1.order() - o2.order();
            }
            return o1.group().compareTo(o2.group());
        }).collect(Collectors.toList());
        for (PlanTaskRunner taskRunner : taskRunnerList) {
            if (this.registerTaskIdSet.contains(taskRunner.id())) {
                logger.info(taskRunner.name() + "\u5df2\u7ecf\u6ce8\u518c\u8fc7nvwa-job\uff0c\u8df3\u8fc7\u6ce8\u518c");
                continue;
            }
            RunnerJobFactory runnerJobFactory = new RunnerJobFactory(taskRunner, applicationContext);
            applicationContext.getBeanFactory().registerSingleton(taskRunner.name() + JOB_FACTORY_SUFFIX, (Object)runnerJobFactory);
            JobFactoryManager.getInstance().regJobFactory((JobFactory)runnerJobFactory);
            this.registerTaskIdSet.add(taskRunner.id());
        }
        logger.info("PlanTaskRunner\u6ce8\u518cnvwa-job\u5b8c\u6210");
    }

    public void init(boolean isSysTenant) {
        this.init();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (RunnerRegister.applicationContext == null) {
            RunnerRegister.applicationContext = (ConfigurableApplicationContext)applicationContext;
        }
    }

    static {
        logger = LoggerFactory.getLogger(RunnerRegister.class);
    }
}

