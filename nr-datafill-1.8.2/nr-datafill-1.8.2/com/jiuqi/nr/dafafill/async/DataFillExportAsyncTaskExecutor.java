/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.realtime.RealTimeJob
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.asynctask.NpRealTimeTaskExecutor
 *  com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 */
package com.jiuqi.nr.dafafill.async;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.NpRealTimeTaskExecutor;
import com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.dafafill.model.DataFillDataQueryInfo;
import com.jiuqi.nr.dafafill.model.ExportInfo;
import com.jiuqi.nr.dafafill.service.IDataFillExportService;
import java.util.Collection;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

@RealTimeJob(group="AT_DATAFILLEXPORT", groupTitle="\u81ea\u5b9a\u4e49\u5f55\u5165\u5bfc\u51fa\u8868\u683c")
public class DataFillExportAsyncTaskExecutor
extends NpRealTimeTaskExecutor {
    private static final long serialVersionUID = 1L;
    private static Logger logger = LoggerFactory.getLogger(DataFillExportAsyncTaskExecutor.class);

    public String getTaskPoolType() {
        return "AT_DATAFILLEXPORT";
    }

    public void execute(JobContext jobContext) {
        ApplicationContext applicationContext = SpringBeanUtils.getApplicationContext();
        Collection<IDataFillExportService> dataFillExportServices = applicationContext.getBeansOfType(IDataFillExportService.class).values();
        RealTimeTaskMonitor monitor = new RealTimeTaskMonitor(jobContext.getInstanceId(), this.getTaskPoolType(), jobContext);
        Map args = jobContext.getRealTimeJob().getParams();
        String arg = (String)args.get("NR_ARGS");
        Object deserialize = SimpleParamConverter.SerializationUtils.deserialize((String)arg);
        if (deserialize instanceof DataFillDataQueryInfo) {
            DataFillDataQueryInfo queryInfo = (DataFillDataQueryInfo)deserialize;
            try {
                ExportInfo exportInfo = queryInfo.getExportInfo();
                boolean hasExportType = false;
                for (IDataFillExportService iDataFillExportService : dataFillExportServices) {
                    if (!iDataFillExportService.accept(exportInfo.getExportType())) continue;
                    iDataFillExportService.export(queryInfo, (AsyncTaskMonitor)monitor);
                    hasExportType = true;
                    break;
                }
                if (!hasExportType) {
                    String str = "\u5bfc\u51fa\uff1a" + (Object)((Object)exportInfo.getExportType()) + ";\u6682\u4e0d\u652f\u6301\uff01";
                    monitor.canceled(str, (Object)str);
                }
            }
            catch (Exception e) {
                monitor.error(e.getLocalizedMessage(), (Throwable)e);
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
        }
    }
}

