/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.bi.core.jobs.realtime.RealTimeJob
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.internal.BeanUtil
 */
package com.jiuqi.nr.io.tz.job;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.io.tz.TzParams;
import com.jiuqi.nr.io.tz.job.JobLoggerMonitor;
import com.jiuqi.nr.io.tz.service.TzBatchImportService;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RealTimeJob(group="COM.JIUQI.NR.IO.DATA_IMPORT_JOB", groupTitle="\u53f0\u8d26\u6570\u636e\u5bfc\u5165", hasProgress=false)
public class TzImportRealTimeInstanceJob
extends AbstractRealTimeJob {
    private static final long serialVersionUID = 6517289510911556447L;
    private static final Logger log = LoggerFactory.getLogger(TzImportRealTimeInstanceJob.class);

    public void execute(JobContext context) {
        Map params = this.getParams();
        log.info("\u5904\u7406etl\u53f0\u8d26\u6570\u636e:\u4f20\u5165\u53c2\u6570\u4e3a\uff1a" + params);
        String datatime = (String)params.get("P_DATATIME");
        String sourceData = (String)params.get("P_SOURCE_DATA");
        String destForm = (String)params.get("P_DEST_TABLE");
        String fullOrAdd = (String)params.get("P_FULL_OR_ADD");
        IRuntimeDataSchemeService dataSchemeService = (IRuntimeDataSchemeService)BeanUtil.getBean(IRuntimeDataSchemeService.class);
        DataTable table = dataSchemeService.getDataTableByCode(destForm);
        if (table == null) {
            log.info("\u672a\u627e\u5230\u76ee\u6807\u8868{}\uff0c\u9000\u51fa\u5bfc\u5165", (Object)destForm);
            return;
        }
        TzParams tzParams = new TzParams(null, datatime, null, sourceData, table.getKey(), fullOrAdd);
        TzBatchImportService bean = (TzBatchImportService)BeanUtil.getBean(TzBatchImportService.class);
        bean.batchImport(tzParams, new JobLoggerMonitor(context));
    }
}

