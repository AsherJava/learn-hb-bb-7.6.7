/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.bi.core.jobs.realtime.RealTimeJob
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.NpRealTimeTaskExecutor
 *  com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.blob.util.BeanUtil
 */
package com.jiuqi.nr.system.check.datachange.asyntask;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.NpRealTimeTaskExecutor;
import com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.blob.util.BeanUtil;
import com.jiuqi.nr.system.check.datachange.bean.DataSchemeRepairRecord;
import com.jiuqi.nr.system.check.datachange.bean.RepairParam;
import com.jiuqi.nr.system.check.datachange.provider.org.OrgDataUpper;
import com.jiuqi.nr.system.check.datachange.service.DataChangeService;
import com.jiuqi.nr.system.check.datachange.service.DataSchemeRepairRecordService;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RealTimeJob(group="ASYNCTASK_UNITCODEUPPER", groupTitle="\u5355\u4f4d\u4ee3\u7801\u8f6c\u5927\u5199", subject="\u62a5\u8868", tags={"\u957f\u4efb\u52a1"})
public class DoUnitCodeUpperCase
extends NpRealTimeTaskExecutor {
    private static final Logger logger = LoggerFactory.getLogger(DoUnitCodeUpperCase.class);

    public void executeWithNpContext(JobContext jobContext) {
        OrgDataUpper orgDataUpper = (OrgDataUpper)BeanUtil.getBean(OrgDataUpper.class);
        DataSchemeRepairRecordService dataSchemeRepairRecordService = (DataSchemeRepairRecordService)BeanUtil.getBean(DataSchemeRepairRecordService.class);
        DataChangeService dataChangeService = (DataChangeService)BeanUtil.getBean(DataChangeService.class);
        String taskId = jobContext.getInstanceId();
        AbstractRealTimeJob job = jobContext.getRealTimeJob();
        Map params = job.getParams();
        RealTimeTaskMonitor asyncTaskMonitor = new RealTimeTaskMonitor(taskId, "ASYNCTASK_UNITCODEUPPER", jobContext);
        try {
            if (Objects.nonNull(params) && Objects.nonNull(params.get("NR_ARGS"))) {
                RepairParam param = (RepairParam)SimpleParamConverter.SerializationUtils.deserialize((String)((String)params.get("NR_ARGS")));
                if (StringUtils.isNotEmpty((String)param.getDataSchemeKey())) {
                    DataSchemeRepairRecord dataSchemeRepairRecord = dataSchemeRepairRecordService.getDataSchemeRepairRecord(param.getDataSchemeKey(), "UNIT_CODE_UPPER");
                    if (dataSchemeRepairRecord != null) {
                        dataSchemeRepairRecord.setStartTime(new Date());
                        dataSchemeRepairRecord.setEndTime(null);
                        dataSchemeRepairRecordService.updateRepairRecord(dataSchemeRepairRecord);
                    } else {
                        DataSchemeRepairRecord repairRecord = new DataSchemeRepairRecord();
                        repairRecord.setRepairType("UNIT_CODE_UPPER");
                        repairRecord.setDataSchemeKey(param.getDataSchemeKey());
                        dataSchemeRepairRecordService.insertRepairRecord(repairRecord);
                    }
                    if (param.isRepairOrg()) {
                        orgDataUpper.doUpper();
                    }
                    dataChangeService.doDataChange(param);
                    dataSchemeRepairRecordService.repairComplete(param.getDataSchemeKey(), "UNIT_CODE_UPPER");
                } else if (param.isRepairOrg()) {
                    orgDataUpper.doUpper();
                }
                asyncTaskMonitor.finish(param.getDataSchemeKey() + "\u5355\u4f4d\u4ee3\u7801\u8f6c\u5927\u5199\u5b8c\u6210!", null);
            }
        }
        catch (Exception e) {
            logger.error("\u5355\u4f4d\u4ee3\u7801\u8f6c\u5927\u5199\u5931\u8d25\uff01", e);
            asyncTaskMonitor.error("\u5355\u4f4d\u4ee3\u7801\u8f6c\u5927\u5199\u5931\u8d25\uff01", (Throwable)e);
        }
    }
}

