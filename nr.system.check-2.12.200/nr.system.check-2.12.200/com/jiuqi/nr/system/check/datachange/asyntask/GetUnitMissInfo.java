/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.bi.core.jobs.realtime.RealTimeJob
 *  com.jiuqi.np.asynctask.NpRealTimeTaskExecutor
 *  com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor
 *  com.jiuqi.np.blob.util.BeanUtil
 */
package com.jiuqi.nr.system.check.datachange.asyntask;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.np.asynctask.NpRealTimeTaskExecutor;
import com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor;
import com.jiuqi.np.blob.util.BeanUtil;
import com.jiuqi.nr.system.check.datachange.bean.DataSchemeRepairRecord;
import com.jiuqi.nr.system.check.datachange.service.DataSchemeRepairRecordService;
import com.jiuqi.nr.system.check.datachange.service.UnitMissInfoService;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RealTimeJob(group="ASYNCTASK_GETUNITMISSINFO", groupTitle="\u83b7\u53d6\u5355\u4f4d\u4ee3\u7801\u4e0d\u5728\u7ec4\u7ec7\u673a\u6784\u4e2d\u7684\u5355\u4f4d\u4fe1\u606f", subject="\u62a5\u8868", tags={"\u957f\u4efb\u52a1"})
public class GetUnitMissInfo
extends NpRealTimeTaskExecutor {
    private static final Logger logger = LoggerFactory.getLogger(GetUnitMissInfo.class);

    public void executeWithNpContext(JobContext jobContext) {
        DataSchemeRepairRecordService dataSchemeRepairRecordService = (DataSchemeRepairRecordService)BeanUtil.getBean(DataSchemeRepairRecordService.class);
        UnitMissInfoService unitMissInfoService = (UnitMissInfoService)BeanUtil.getBean(UnitMissInfoService.class);
        String taskId = jobContext.getInstanceId();
        AbstractRealTimeJob job = jobContext.getRealTimeJob();
        Map params = job.getParams();
        RealTimeTaskMonitor asyncTaskMonitor = new RealTimeTaskMonitor(taskId, "ASYNCTASK_GETUNITMISSINFO", jobContext);
        try {
            String dataSchemeKey;
            if (Objects.nonNull(params) && Objects.nonNull(params.get("NR_ARGS")) && Objects.nonNull(dataSchemeKey = (String)params.get("NR_ARGS"))) {
                DataSchemeRepairRecord dataSchemeRepairRecord = dataSchemeRepairRecordService.getDataSchemeRepairRecord(dataSchemeKey, "UNIT_EXIST");
                if (dataSchemeRepairRecord != null) {
                    dataSchemeRepairRecord.setStartTime(new Date());
                    dataSchemeRepairRecord.setEndTime(null);
                    dataSchemeRepairRecordService.updateRepairRecord(dataSchemeRepairRecord);
                } else {
                    DataSchemeRepairRecord repairRecord = new DataSchemeRepairRecord();
                    repairRecord.setRepairType("UNIT_EXIST");
                    repairRecord.setDataSchemeKey(dataSchemeKey);
                    dataSchemeRepairRecordService.insertRepairRecord(repairRecord);
                }
                unitMissInfoService.getMissUnitInfo(dataSchemeKey);
                dataSchemeRepairRecordService.repairComplete(dataSchemeKey, "UNIT_EXIST");
            }
            asyncTaskMonitor.finish("\u83b7\u53d6\u5355\u4f4d\u4ee3\u7801\u4e0d\u5728\u7ec4\u7ec7\u673a\u6784\u4e2d\u7684\u5355\u4f4d\u4fe1\u606f\u5b8c\u6210\u3002", null);
        }
        catch (Exception e) {
            logger.error("\u83b7\u53d6\u5355\u4f4d\u4ee3\u7801\u4e0d\u5728\u7ec4\u7ec7\u673a\u6784\u4e2d\u7684\u5355\u4f4d\u4fe1\u606f\u5931\u8d25\uff01", e);
            asyncTaskMonitor.error("\u83b7\u53d6\u5355\u4f4d\u4ee3\u7801\u4e0d\u5728\u7ec4\u7ec7\u673a\u6784\u4e2d\u7684\u5355\u4f4d\u4fe1\u606f\u5931\u8d25\uff01", (Throwable)e);
        }
    }
}

