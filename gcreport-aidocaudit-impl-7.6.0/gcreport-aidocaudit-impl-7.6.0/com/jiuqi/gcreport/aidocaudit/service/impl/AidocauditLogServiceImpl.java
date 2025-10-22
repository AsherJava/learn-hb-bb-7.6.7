/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.np.core.context.NpContextHolder
 */
package com.jiuqi.gcreport.aidocaudit.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.gcreport.aidocaudit.dao.IAidocauditLogDao;
import com.jiuqi.gcreport.aidocaudit.dto.AuditProgressReusltDTO;
import com.jiuqi.gcreport.aidocaudit.eo.AidocAuditLogEO;
import com.jiuqi.gcreport.aidocaudit.service.IAidocauditLogService;
import com.jiuqi.np.core.context.NpContextHolder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class AidocauditLogServiceImpl
implements IAidocauditLogService {
    private Logger logger = LoggerFactory.getLogger(AidocauditLogServiceImpl.class);
    @Autowired
    private IAidocauditLogDao logDao;
    @Value(value="${jiuqi.gcreport.aidocaudit.audit.singletimeout}")
    private int singleTimeout;
    private static final String PROGRESS_RESULT_MESSAGE = "\u5ba1\u6838\u5f00\u59cb\u65f6\u95f4\uff1a%s\uff0c\u6210\u529f%d\u5bb6\uff0c\u5931\u8d25%d\u5bb6\uff0c\u5f85\u5ba1\u6838%d\u5bb6\uff0c\u8bf7\u60a8\u8010\u5fc3\u7b49\u5f85\u3002";
    private static final String TIMEOUT_RESULT_MESSAGE = "\u5ba1\u6838\u5f00\u59cb\u65f6\u95f4%s\uff0c\u7ed3\u675f\u65f6\u95f4%s\uff0c\u6210\u529f%d\u5bb6\uff0c\u5931\u8d25%d\u5bb6\uff0c\u7528\u65f6%s\u3002";
    private static final String DATE_FORMAT_PATTERN = "MM\u6708dd\u65e5HH\u65f6mm\u5206";

    @Override
    public AuditProgressReusltDTO getAuditProgress() {
        String userId = NpContextHolder.getContext().getUserId();
        AuditProgressReusltDTO result = new AuditProgressReusltDTO();
        List<AidocAuditLogEO> auditLogs = this.logDao.getUnCompletedAuditLog(userId);
        if (!CollectionUtils.isEmpty(auditLogs)) {
            Set scoreTasks = auditLogs.stream().map(AidocAuditLogEO::getScoreTask).collect(Collectors.toSet());
            if (scoreTasks.size() > 1) {
                this.logger.error("\u5b58\u5728\u591a\u4e2a\u672a\u5b8c\u6210\u7684\u5ba1\u6838\u4efb\u52a1\uff0cscore_task \u5217\u8868: {}", (Object)scoreTasks);
                throw new BusinessRuntimeException("\u5b58\u5728\u591a\u4e2a\u672a\u5b8c\u6210\u7684\u5ba1\u6838\u4efb\u52a1");
            }
            String scoreTask = auditLogs.get(0).getScoreTask();
            List<AidocAuditLogEO> auditLogsByTask = this.logDao.getAuditLogByTask(scoreTask);
            int taskSize = auditLogsByTask.size();
            long expireTime = (long)this.singleTimeout * (long)taskSize;
            long startTime = auditLogs.get(0).getStartTime().getTime();
            if (System.currentTimeMillis() - startTime > expireTime * 1000L) {
                Date endTime = new Date(startTime + expireTime * 1000L);
                result = this.handleTimeOutResult(auditLogsByTask, endTime);
            } else {
                result = this.handleProgressResult(auditLogsByTask);
            }
        } else {
            AidocAuditLogEO auditLog;
            List<AidocAuditLogEO> completedAuditLog = this.logDao.getCompletedLatestAuditLog(userId);
            if (CollectionUtils.isEmpty(completedAuditLog)) {
                result.setStatus(0);
            }
            if (!(auditLog = completedAuditLog.get(0)).getIsFeedback().equals(1)) {
                String scoreTask = auditLog.getScoreTask();
                List<AidocAuditLogEO> auditLogsByTask = this.logDao.getAuditLogByTask(scoreTask);
                result = this.handleCompletedResult(auditLogsByTask);
            } else {
                result.setStatus(0);
            }
        }
        return result;
    }

    private AuditProgressReusltDTO handleCompletedResult(List<AidocAuditLogEO> auditLogsByTask) {
        Date endTime = new Date();
        int successCount = 0;
        int failedCount = 0;
        for (AidocAuditLogEO auditLog : auditLogsByTask) {
            if (auditLog.getStatus() == 1) {
                ++successCount;
            } else {
                ++failedCount;
            }
            auditLog.setIsFeedback(1);
        }
        this.logDao.updateBatch(auditLogsByTask);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_PATTERN);
        Date startTime = auditLogsByTask.get(0).getStartTime();
        String formattedStartTime = sdf.format(startTime);
        String formattedEndTime = sdf.format(endTime);
        String duration = this.formatDuration(startTime, endTime);
        String message = String.format(TIMEOUT_RESULT_MESSAGE, formattedStartTime, formattedEndTime, successCount, failedCount, duration);
        AuditProgressReusltDTO result = new AuditProgressReusltDTO();
        result.setStatus(2);
        result.setMessage(message);
        return result;
    }

    private String formatDuration(Date startTime, Date endTime) {
        long durationInMillis = endTime.getTime() - startTime.getTime();
        int hours = (int)(durationInMillis / 3600000L);
        int minutes = (int)(durationInMillis % 3600000L / 60000L);
        String durationString = hours > 0 ? String.format("%d\u5c0f\u65f6%d\u5206", hours, minutes) : String.format("%d\u5206", minutes);
        return durationString;
    }

    private AuditProgressReusltDTO handleTimeOutResult(List<AidocAuditLogEO> auditLogsByTask, Date endTime) {
        int successCount = 0;
        int failedCount = 0;
        for (AidocAuditLogEO auditLog : auditLogsByTask) {
            if (auditLog.getStatus() == 0) {
                auditLog.setStatus(3);
                auditLog.setEndTime(endTime);
                ++failedCount;
            } else if (auditLog.getStatus() == 1) {
                ++successCount;
            } else {
                ++failedCount;
            }
            auditLog.setIsFeedback(1);
        }
        this.logDao.updateBatch(auditLogsByTask);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_PATTERN);
        Date startTime = auditLogsByTask.get(0).getStartTime();
        String formattedStartTime = sdf.format(startTime);
        String formattedEndTime = sdf.format(endTime);
        String duration = this.formatDuration(startTime, endTime);
        String message = String.format(TIMEOUT_RESULT_MESSAGE, formattedStartTime, formattedEndTime, successCount, failedCount, duration);
        AuditProgressReusltDTO result = new AuditProgressReusltDTO();
        result.setStatus(2);
        result.setMessage(message);
        return result;
    }

    private AuditProgressReusltDTO handleProgressResult(List<AidocAuditLogEO> auditLogsByTask) {
        Date startTime = auditLogsByTask.get(0).getStartTime();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_PATTERN);
        String formattedStartTime = sdf.format(startTime);
        int successCount = 0;
        int failedCount = 0;
        int pendingCount = 0;
        Map<String, List<AidocAuditLogEO>> groupedByOrg = auditLogsByTask.stream().collect(Collectors.groupingBy(AidocAuditLogEO::getMdCode));
        for (Map.Entry<String, List<AidocAuditLogEO>> entry : groupedByOrg.entrySet()) {
            List<AidocAuditLogEO> logs = entry.getValue();
            boolean allSuccess = true;
            boolean hasFailed = false;
            for (AidocAuditLogEO log : logs) {
                switch (log.getStatus()) {
                    case 1: {
                        break;
                    }
                    case 2: 
                    case 3: {
                        allSuccess = false;
                        hasFailed = true;
                        break;
                    }
                    case 0: {
                        allSuccess = false;
                    }
                }
            }
            if (allSuccess) {
                ++successCount;
                continue;
            }
            if (hasFailed) {
                ++failedCount;
                continue;
            }
            ++pendingCount;
        }
        String message = String.format(PROGRESS_RESULT_MESSAGE, formattedStartTime, successCount, failedCount, pendingCount);
        AuditProgressReusltDTO result = new AuditProgressReusltDTO();
        result.setStatus(1);
        result.setMessage(message);
        return result;
    }
}

