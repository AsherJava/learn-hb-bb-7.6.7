/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.form.reject.spring.config;

import com.jiuqi.nr.workflow2.form.reject.entity.dao.RejectFormRecordDao;
import com.jiuqi.nr.workflow2.form.reject.entity.dao.RejectOperateRecordDao;
import com.jiuqi.nr.workflow2.form.reject.ext.service.FormRejectExecuteService;
import com.jiuqi.nr.workflow2.form.reject.ext.service.FormRejectExtExecuteService;
import com.jiuqi.nr.workflow2.form.reject.ext.service.FormRejectExtInstancesService;
import com.jiuqi.nr.workflow2.form.reject.ext.service.FormRejectExtQueryService;
import com.jiuqi.nr.workflow2.form.reject.ext.service.IFormRejectJudgeHelper;
import com.jiuqi.nr.workflow2.form.reject.ext.service.StepByStepFormRejectEvent;
import com.jiuqi.nr.workflow2.form.reject.listener.WorkflowSettingChangeListener;
import com.jiuqi.nr.workflow2.form.reject.service.FormRejectQueryService;
import com.jiuqi.nr.workflow2.form.reject.service.FormRejectRunTimeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@ComponentScan(basePackages={"com.jiuqi.nr.workflow2.form.reject.ext.event"})
@Configuration
public class NrWorkflow2FormRejectSpringConf {
    @Bean(value={"com.jiuqi.nr.workflow2.form.reject.listener.WorkflowSettingChangeListener"})
    public WorkflowSettingChangeListener getWorkflowSettingChangeListener() {
        return new WorkflowSettingChangeListener();
    }

    @Bean(value={"com.jiuqi.nr.workflow2.form.reject.ext.service.FormRejectProcessExecuteService"})
    @Primary
    public FormRejectExtExecuteService getFormRejectProcessExecuteService() {
        return new FormRejectExtExecuteService();
    }

    @Bean(value={"com.jiuqi.nr.workflow2.form.reject.ext.service.FormRejectProcessQueryService"})
    @Primary
    public FormRejectExtQueryService getFormRejectProcessQueryService() {
        return new FormRejectExtQueryService();
    }

    @Bean(value={"com.jiuqi.nr.workflow2.form.reject.ext.service.FormRejectInstancesService"})
    @Primary
    public FormRejectExtInstancesService getFormRejectInstancesService() {
        return new FormRejectExtInstancesService();
    }

    @Bean(value={"com.jiuqi.nr.workflow2.form.reject.service.FormRejectRunTimeService"})
    public FormRejectRunTimeService getFormRejectRunTimeService() {
        return new FormRejectRunTimeService();
    }

    @Bean(value={"com.jiuqi.nr.workflow2.form.reject.service.FormRejectQueryService"})
    public FormRejectQueryService getFormRejectQueryService() {
        return new FormRejectQueryService();
    }

    @Bean(value={"com.jiuqi.nr.workflow2.form.reject.entity.dao.RejectFormRecordDao"})
    public RejectFormRecordDao getRejectFormRecordDao() {
        return new RejectFormRecordDao();
    }

    @Bean(value={"com.jiuqi.nr.workflow2.form.reject.entity.dao.RejectOperateRecordDao"})
    public RejectOperateRecordDao getRejectOperateRecordDao() {
        return new RejectOperateRecordDao();
    }

    @Bean
    public IFormRejectJudgeHelper getFormRejectJudgeHelper() {
        return new IFormRejectJudgeHelper();
    }

    @Bean
    public FormRejectExecuteService getFormRejectExecuteService() {
        return new FormRejectExecuteService();
    }

    @Bean
    public StepByStepFormRejectEvent getStepByStepFormRejectEvent() {
        return new StepByStepFormRejectEvent();
    }
}

