/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.DeserializationFeature
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.JobExecutionException
 *  com.jiuqi.bi.core.jobs.JobExecutor
 *  com.jiuqi.bi.core.jobs.defaultlog.Logger
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.service.DataSchemeCalcTask
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.service.IZbSchemeBusinessService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodRow
 */
package com.jiuqi.nr.datascheme.internal.job;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.bi.core.jobs.JobExecutor;
import com.jiuqi.bi.core.jobs.defaultlog.Logger;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.service.DataSchemeCalcTask;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.service.IZbSchemeBusinessService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.datascheme.exception.DataSchemeException;
import com.jiuqi.nr.datascheme.internal.job.CalZbJobConfig;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodRow;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class CalZbJobExecutor
extends JobExecutor {
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private IZbSchemeBusinessService zbSchemeBusinessService;

    public void execute(JobContext jobContext) throws JobExecutionException {
        Logger logger = jobContext.getDefaultLogger();
        String extendedConfig = jobContext.getJob().getExtendedConfig();
        if (StringUtils.hasText(extendedConfig)) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                CalZbJobConfig config = (CalZbJobConfig)mapper.readValue(extendedConfig, CalZbJobConfig.class);
                if (!this.checkConfig(config, logger)) {
                    return;
                }
                DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(config.getDataSchemeKey());
                if (dataScheme == null) {
                    logger.warn("\u6570\u636e\u65b9\u6848\u4e0d\u5b58\u5728\u6216\u8005\u672a\u53d1\u5e03");
                    return;
                }
                if (dataScheme.getZbSchemeKey() == null) {
                    logger.warn("\u6570\u636e\u65b9\u6848\u672a\u5173\u8054\u6307\u6807\u4f53\u7cfb");
                    return;
                }
                List dataDimensions = this.runtimeDataSchemeService.getDataSchemeDimension(config.getDataSchemeKey());
                DataDimension dimension = dataDimensions.stream().filter(d -> d.getDimensionType() == DimensionType.PERIOD).findFirst().orElseThrow(() -> new DataSchemeException("\u65f6\u671f\u4e0d\u5b58\u5728"));
                IPeriodProvider periodProvider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(dimension.getDimKey());
                String[] period = this.getPeriod(periodProvider, config);
                logger.info("\u6267\u884c\u5206\u6790\u6307\u6807\u4efb\u52a1\uff1a{0}", new Object[]{this.getLog(dataScheme, period)});
                if (dataDimensions.stream().anyMatch(d -> d.getDimensionType() == DimensionType.DIMENSION)) {
                    logger.info("\u6267\u884c\u5206\u6790\u6307\u6807\u4efb\u52a1\u6210\u529f");
                    return;
                }
                DataSchemeCalcTask dataSchemeCalcTask = new DataSchemeCalcTask(dataScheme.getKey());
                dataSchemeCalcTask.setStartPeriod(period[0]);
                dataSchemeCalcTask.setEndPeriod(period[1]);
                this.zbSchemeBusinessService.executeCalFormula(dataSchemeCalcTask);
                logger.info("\u6267\u884c\u5206\u6790\u6307\u6807\u4efb\u52a1\u6210\u529f");
            }
            catch (Exception e) {
                logger.error("\u6267\u884c\u5206\u6790\u6307\u6807\u4efb\u52a1\u5931\u8d25\uff1a{0}", new Object[]{e.getMessage()});
                throw new JobExecutionException((Throwable)e);
            }
        } else {
            logger.warn("\u5206\u6790\u6307\u6807\u4efb\u52a1\u53c2\u6570\u65e0\u6548\uff0c\u8bf7\u68c0\u67e5\u914d\u7f6e");
        }
    }

    private String getLog(DataScheme dataScheme, String[] period) {
        return "\n[\u6570\u636e\u65b9\u6848=" + dataScheme.getTitle() + ", \u5f00\u59cb\u65f6\u671f=" + period[0] + ", \u7ed3\u675f\u65f6\u671f=" + period[1] + "]";
    }

    private boolean checkConfig(CalZbJobConfig config, Logger logger) {
        if (config == null) {
            logger.warn("\u5206\u6790\u6307\u6807\u4efb\u52a1\u53c2\u6570\u4e3a\u7a7a");
            return false;
        }
        if (config.getDataSchemeKey() == null || config.getExecuteType() == null) {
            logger.warn("\u5206\u6790\u6307\u6807\u4efb\u52a1\u53c2\u6570\u65e0\u6548\uff0c\u8bf7\u68c0\u67e5\u914d\u7f6e");
            return false;
        }
        if (config.getPeriodType() == CalZbJobConfig.PeriodType.SPECIFIC && !StringUtils.hasText(config.getPeriod())) {
            logger.warn("\u5206\u6790\u6307\u6807\u4efb\u52a1\u53c2\u6570\u65e0\u6548\uff0c\u8bf7\u68c0\u67e5\u914d\u7f6e");
            return false;
        }
        return true;
    }

    private String[] getPeriod(IPeriodProvider periodProvider, CalZbJobConfig config) {
        IPeriodRow curPeriod = periodProvider.getCurPeriod();
        if (config.getExecuteType() == CalZbJobConfig.ExecuteType.ALL) {
            return periodProvider.getPeriodCodeRegion();
        }
        switch (config.getPeriodType()) {
            case SPECIFIC: {
                return new String[]{config.getPeriod(), config.getPeriod()};
            }
            case LAST_ISSUE: {
                String period = periodProvider.priorPeriod(curPeriod.getCode());
                return new String[]{period, period};
            }
            case LAST_TWO_ISSUE: {
                String period = periodProvider.priorPeriod(periodProvider.priorPeriod(curPeriod.getCode()));
                return new String[]{period, period};
            }
        }
        return new String[]{curPeriod.getCode(), curPeriod.getCode()};
    }
}

