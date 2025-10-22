/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.JobExecutionException
 *  com.jiuqi.bi.core.jobs.JobExecutor
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 */
package com.jiuqi.nr.enumcheck.job;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.bi.core.jobs.JobExecutor;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.enumcheck.service.IECRClearService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class ECRClearJobExecutor
extends JobExecutor {
    private static final Logger logger = LoggerFactory.getLogger(ECRClearJobExecutor.class);
    @Autowired
    private IECRClearService iecrClearService;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public void execute(JobContext jobContext) throws JobExecutionException {
        String runnerParameter;
        if (this.iecrClearService == null) {
            this.iecrClearService = (IECRClearService)SpringBeanUtils.getBean(IECRClearService.class);
        }
        if (!StringUtils.hasText(runnerParameter = jobContext.getJob().getExtendedConfig())) {
            this.iecrClearService.clearResult(-1);
        } else {
            JobParam jobParam;
            try {
                jobParam = (JobParam)objectMapper.readValue(runnerParameter, JobParam.class);
            }
            catch (JsonProcessingException e) {
                logger.error("\u679a\u4e3e\u5b57\u5178\u68c0\u67e5\u7ed3\u679c\u8868\u6570\u636e\u6e05\u9664\u8ba1\u5212\u4efb\u52a1\u53cd\u5e8f\u5217\u5316\u53c2\u6570\u5f02\u5e38:{}", (Object)e.getMessage(), (Object)e);
                jobContext.getDefaultLogger().error("\u679a\u4e3e\u5b57\u5178\u68c0\u67e5\u7ed3\u679c\u8868\u6570\u636e\u6e05\u9664\u8ba1\u5212\u4efb\u52a1\u53cd\u5e8f\u5217\u5316\u53c2\u6570\u5f02\u5e38:" + e.getMessage(), (Throwable)e);
                throw new JobExecutionException("\u679a\u4e3e\u5b57\u5178\u68c0\u67e5\u7ed3\u679c\u8868\u6570\u636e\u6e05\u9664\u8ba1\u5212\u4efb\u52a1\u53cd\u5e8f\u5217\u5316\u53c2\u6570\u5f02\u5e38:" + e.getMessage(), (Throwable)e);
            }
            if (jobParam == null || jobParam.getCleanDays() < 0) {
                logger.error("\u679a\u4e3e\u5b57\u5178\u68c0\u67e5\u7ed3\u679c\u8868\u6570\u636e\u6e05\u9664\u8ba1\u5212\u4efb\u52a1\u914d\u7f6e\u9519\u8bef:{}", (Object)runnerParameter);
                jobContext.getDefaultLogger().error("\u679a\u4e3e\u5b57\u5178\u68c0\u67e5\u7ed3\u679c\u8868\u6570\u636e\u6e05\u9664\u8ba1\u5212\u4efb\u52a1\u914d\u7f6e\u9519\u8bef:" + runnerParameter);
                throw new JobExecutionException("\u679a\u4e3e\u5b57\u5178\u68c0\u67e5\u7ed3\u679c\u8868\u6570\u636e\u6e05\u9664\u8ba1\u5212\u4efb\u52a1\u914d\u7f6e\u9519\u8bef:" + runnerParameter);
            }
            this.iecrClearService.clearResult(jobParam.cleanDays);
        }
    }

    static class JobParam {
        private int cleanDays = -1;

        JobParam() {
        }

        public int getCleanDays() {
            return this.cleanDays;
        }

        public void setCleanDays(int cleanDays) {
            this.cleanDays = cleanDays;
        }
    }
}

