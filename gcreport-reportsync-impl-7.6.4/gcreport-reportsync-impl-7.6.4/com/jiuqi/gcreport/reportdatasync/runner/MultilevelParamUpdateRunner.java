/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.common.plantask.extend.job.PlanTaskRunner
 *  com.jiuqi.common.plantask.extend.job.Runner
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncReceiveTaskVO
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.gcreport.reportdatasync.runner;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.common.plantask.extend.job.PlanTaskRunner;
import com.jiuqi.common.plantask.extend.job.Runner;
import com.jiuqi.gcreport.reportdatasync.service.ReportDataSyncParamUpdateService;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncReceiveTaskVO;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component(value="multilevelParamUpdateRunner")
@PlanTaskRunner(id="ab99fdbb1280499c821a1520db8cbed8", name="multilevelParamUpdateRunner", title="\u591a\u7ea7\u90e8\u7f72\u53c2\u6570\u81ea\u52a8\u66f4\u65b0\u5e76\u53d1\u5e03", settingPage="")
public class MultilevelParamUpdateRunner
extends Runner {
    private static final Logger logger = LoggerFactory.getLogger(MultilevelParamUpdateRunner.class);
    public static final String ID = "ab99fdbb1280499c821a1520db8cbed8";
    public static final String NAME = "multilevelParamUpdateRunner";
    public static final String TITLE = "\u591a\u7ea7\u90e8\u7f72\u53c2\u6570\u81ea\u52a8\u66f4\u65b0\u5e76\u53d1\u5e03";
    @Autowired
    private ReportDataSyncParamUpdateService paramUpdateService;

    public boolean excute(JobContext jobContext) {
        String userName = jobContext.getJob().getUser();
        if (StringUtils.isEmpty((String)userName)) {
            this.appendLog("\u8ba1\u5212\u4efb\u52a1\u81ea\u52a8\u66f4\u65b0\u53c2\u6570\u627e\u4e0d\u5230\u64cd\u4f5c\u7528\u6237\uff0c\u8bf7\u53bb\u8ba1\u5212\u4efb\u52a1\u4e2d\u914d\u7f6e\u6307\u5b9a\u7528\u6237\u4f5c\u4e3a\u53c2\u6570\u66f4\u65b0\u64cd\u4f5c\u7528\u6237\uff0c\u8be5\u7528\u6237\u9700\u5177\u5907\u53c2\u6570\u66f4\u65b0\u548c\u53d1\u5e03\u7684\u6743\u9650\u3002");
            return false;
        }
        List receiveTaskVOS = this.paramUpdateService.listAllTasks(100000, 1).getList();
        List<Object> needUpdateTaskVOS = new ArrayList<ReportDataSyncReceiveTaskVO>();
        HashMap<String, ReportDataSyncReceiveTaskVO> containTaskMap = new HashMap<String, ReportDataSyncReceiveTaskVO>();
        for (ReportDataSyncReceiveTaskVO reportDataSyncReceiveTaskVO : receiveTaskVOS) {
            if (!StringUtils.isEmpty((String)reportDataSyncReceiveTaskVO.getSyncTime()) || reportDataSyncReceiveTaskVO.getAvailable() == 0) continue;
            String taskId = reportDataSyncReceiveTaskVO.getTaskId();
            if (taskId == null) {
                needUpdateTaskVOS.add(reportDataSyncReceiveTaskVO);
                continue;
            }
            if (!containTaskMap.containsKey(taskId)) {
                containTaskMap.put(taskId, reportDataSyncReceiveTaskVO);
                continue;
            }
            if (reportDataSyncReceiveTaskVO.getSyncVersion().compareTo(((ReportDataSyncReceiveTaskVO)containTaskMap.get(taskId)).getSyncVersion()) <= 0) continue;
            containTaskMap.put(taskId, reportDataSyncReceiveTaskVO);
        }
        for (String string : containTaskMap.keySet()) {
            ReportDataSyncReceiveTaskVO receiveTaskVO = (ReportDataSyncReceiveTaskVO)containTaskMap.get(string);
            needUpdateTaskVOS.add(receiveTaskVO);
        }
        if (CollectionUtils.isEmpty(needUpdateTaskVOS)) {
            this.appendLog("\u672a\u627e\u5230\u9700\u8981\u66f4\u65b0\u7684\u53c2\u6570");
            this.appendLog("\u53c2\u6570\u66f4\u65b0\u5b8c\u6210");
            return true;
        }
        needUpdateTaskVOS = needUpdateTaskVOS.stream().sorted((s1, s2) -> s1.getXfTime().compareTo(s2.getXfTime())).collect(Collectors.toList());
        for (ReportDataSyncReceiveTaskVO reportDataSyncReceiveTaskVO : needUpdateTaskVOS) {
            try {
                this.paramUpdateService.updateReportParamsData(reportDataSyncReceiveTaskVO.getId(), true);
                this.appendLog("\u540d\u79f0\u4e3a\uff1a" + reportDataSyncReceiveTaskVO + ",id\u4e3a\uff1a" + reportDataSyncReceiveTaskVO.getId() + "\u7684\u53c2\u6570\u5305\u66f4\u65b0\u6210\u529f");
            }
            catch (Exception e) {
                this.appendLog("\u540d\u79f0\u4e3a\uff1a" + reportDataSyncReceiveTaskVO + ",id\u4e3a\uff1a" + reportDataSyncReceiveTaskVO.getId() + "\u7684\u53c2\u6570\u5305\u66f4\u65b0\u5f02\u5e38");
                logger.error(e.getMessage(), e);
            }
        }
        this.appendLog("\u53c2\u6570\u66f4\u65b0\u5b8c\u6210");
        return true;
    }
}

