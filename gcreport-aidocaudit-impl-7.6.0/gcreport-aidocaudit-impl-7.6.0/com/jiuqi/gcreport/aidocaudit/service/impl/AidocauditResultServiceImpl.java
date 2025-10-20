/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.aidocaudit.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.aidocaudit.dao.IAidocauditLogDao;
import com.jiuqi.gcreport.aidocaudit.dao.IAidocauditResultDao;
import com.jiuqi.gcreport.aidocaudit.dao.IAidocauditResultItemDao;
import com.jiuqi.gcreport.aidocaudit.dao.IAidocauditRuleDao;
import com.jiuqi.gcreport.aidocaudit.dao.IAidocauditRuleItemDao;
import com.jiuqi.gcreport.aidocaudit.dto.CountResultItemDTO;
import com.jiuqi.gcreport.aidocaudit.dto.MQScoreResultDTO;
import com.jiuqi.gcreport.aidocaudit.dto.ResultItemDTO;
import com.jiuqi.gcreport.aidocaudit.eo.AidocAuditLogEO;
import com.jiuqi.gcreport.aidocaudit.eo.AidocauditResultEO;
import com.jiuqi.gcreport.aidocaudit.eo.AidocauditResultitemEO;
import com.jiuqi.gcreport.aidocaudit.eo.AidocauditRuleEO;
import com.jiuqi.gcreport.aidocaudit.eo.AidocauditRuleItemEO;
import com.jiuqi.gcreport.aidocaudit.service.IAidocauditResultService;
import com.jiuqi.gcreport.aidocaudit.util.AidocauditScoreUtil;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AidocauditResultServiceImpl
implements IAidocauditResultService {
    private final Logger logger = LoggerFactory.getLogger(AidocauditResultServiceImpl.class);
    @Autowired
    private IAidocauditRuleDao aidocauditRuleDao;
    @Autowired
    private IAidocauditResultDao aidocauditResultDao;
    @Autowired
    private IAidocauditResultItemDao aidocauditResultItemDao;
    @Autowired
    private IAidocauditRuleItemDao aidocauditRuleItemDao;
    @Autowired
    private IAidocauditLogDao logDao;
    @Value(value="${jiuqi.gcreport.aidocaudit.audit.singletimeout:1200}")
    private int singleTimeout;
    private static final String KEY_PERIOD = "PERIOD";

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void handerMessage(MQScoreResultDTO scoreResult) {
        AidocauditResultEO result = new AidocauditResultEO();
        Map<String, String> business = scoreResult.getBusiness();
        String logid = business.get("LOGID");
        Boolean logFlag = this.updateLog(logid);
        if (Boolean.FALSE.equals(logFlag)) {
            if (this.logger.isInfoEnabled()) {
                this.logger.debug("\u8be5\u4efb\u52a1\u5df2\u8d85\u65f6\uff0c\u672a\u4fdd\u5b58\u7ed3\u679c\uff0c\u65f6\u671f{},\u5355\u4f4d:{},\u4efb\u52a1\u65e5\u5fd7ID:{}", business.get(KEY_PERIOD), business.get("\u5355\u4f4d"), logid);
            }
            return;
        }
        String resultID = UUIDUtils.newUUIDStr();
        result.setId(resultID);
        result.setMdCode(business.get("UNIT"));
        result.setDataTime(business.get(KEY_PERIOD));
        result.setTaskId(business.get("TASKID"));
        AidocauditRuleEO rule = this.aidocauditRuleDao.getByScoreTmplId(scoreResult.getTempId());
        result.setZbCode(rule.getAchmentZbCode());
        result.setAttachmentId(scoreResult.getFileAttachId());
        result.setRuleId(rule.getId());
        result.setScore(scoreResult.getTotalScore());
        result.setCreateTime(new Date());
        result.setCreateUser(null);
        List<AidocauditRuleItemEO> ruleitems = this.aidocauditRuleItemDao.getByRuleId(rule.getId());
        Map ruleItemMap = ruleitems.stream().collect(Collectors.toMap(AidocauditRuleItemEO::getScoreItemName, Function.identity()));
        CountResultItemDTO countResult = this.countResultItem(scoreResult.getResultItems());
        List<ResultItemDTO> resultItemList = countResult.getResultItemList();
        result.setRuleNum(countResult.getRuleNum());
        result.setRuleMatchNum(countResult.getRuleMatchNum());
        result.setRuleUnmatchNum(countResult.getRuleUnmatchNum());
        result.setRuleSuspectMatchNum(countResult.getRuleSuspectMatchNum());
        ArrayList<AidocauditResultitemEO> resultitems = new ArrayList<AidocauditResultitemEO>();
        for (ResultItemDTO resultItemDTO : resultItemList) {
            AidocauditResultitemEO aidocauditResultitemEO = new AidocauditResultitemEO();
            aidocauditResultitemEO.setId(UUIDUtils.newUUIDStr());
            aidocauditResultitemEO.setResultId(resultID);
            aidocauditResultitemEO.setScore(resultItemDTO.getScore());
            aidocauditResultitemEO.setScoreBasis(resultItemDTO.getScoreBasis());
            aidocauditResultitemEO.setFullScore(resultItemDTO.getFullScore());
            AidocauditRuleItemEO ruleItem = (AidocauditRuleItemEO)((Object)ruleItemMap.get(resultItemDTO.getScoreItemName()));
            if (Objects.isNull((Object)ruleItem)) {
                this.logger.error("\u672a\u627e\u5230\u5bf9\u5e94\u89c4\u5219", (Object)resultItemDTO.getScoreItemName());
                throw new BusinessRuntimeException("\u672a\u627e\u5230\u5bf9\u5e94\u89c4\u5219:" + resultItemDTO.getScoreItemName());
            }
            aidocauditResultitemEO.setRuleItemId(ruleItem.getId());
            resultitems.add(aidocauditResultitemEO);
        }
        this.deleteHistory(rule.getId(), business.get("UNIT"), business.get(KEY_PERIOD), business.get("TASKID"));
        this.saveResult(result, resultitems);
        if (this.logger.isInfoEnabled()) {
            this.logger.info("\u8bc4\u5206\u6210\u529f,\u65f6\u671f{},\u5355\u4f4d:{},\u89c4\u5219:{}", business.get(KEY_PERIOD), business.get("\u5355\u4f4d"), rule.getRuleAttachmentName());
        }
    }

    @Override
    public void handerErrorMessage(Map<String, String> business) {
        String logid = business.get("LOGID");
        AidocAuditLogEO aidocAuditLogEO = (AidocAuditLogEO)this.logDao.get((Serializable)((Object)logid));
        if (Objects.nonNull((Object)aidocAuditLogEO) && aidocAuditLogEO.getStatus() == 0) {
            aidocAuditLogEO.setStatus(2);
            aidocAuditLogEO.setEndTime(new Date());
            this.logDao.update((BaseEntity)aidocAuditLogEO);
        }
    }

    private Boolean updateLog(String logid) {
        AidocAuditLogEO aidocAuditLogEO = (AidocAuditLogEO)this.logDao.get((Serializable)((Object)logid));
        if (Objects.isNull((Object)aidocAuditLogEO)) {
            return false;
        }
        if (aidocAuditLogEO.getStatus() == 0) {
            Date startTime = aidocAuditLogEO.getStartTime();
            List<AidocAuditLogEO> auditLogsByTask = this.logDao.getAuditLogByTask(aidocAuditLogEO.getScoreTask());
            int taskSize = auditLogsByTask.size();
            long expireTime = (long)this.singleTimeout * (long)taskSize * 1000L;
            if (System.currentTimeMillis() - startTime.getTime() > expireTime) {
                this.handleTimeout(auditLogsByTask);
                return false;
            }
            this.handleInProgress(aidocAuditLogEO);
            return true;
        }
        return false;
    }

    private void handleTimeout(List<AidocAuditLogEO> auditLogsByTask) {
        Date endTime = new Date();
        for (AidocAuditLogEO auditLog : auditLogsByTask) {
            if (auditLog.getStatus() != 0) continue;
            auditLog.setStatus(3);
            auditLog.setEndTime(endTime);
        }
        this.logDao.updateBatch(auditLogsByTask);
    }

    private void handleInProgress(AidocAuditLogEO aidocAuditLogEO) {
        aidocAuditLogEO.setStatus(1);
        aidocAuditLogEO.setEndTime(new Date());
        this.logDao.update((BaseEntity)aidocAuditLogEO);
    }

    private void deleteHistory(String ruleId, String unit, String period, String taskId) {
        List<AidocauditResultEO> historys = this.aidocauditResultDao.queryByTempIdAndBusiness(ruleId, unit, period, taskId);
        List<String> resultIds = historys.stream().map(DefaultTableEntity::getId).collect(Collectors.toList());
        List<AidocauditResultitemEO> resultitems = this.aidocauditResultItemDao.queryByResultIds(resultIds);
        this.aidocauditResultDao.deleteBatch(historys);
        this.aidocauditResultItemDao.deleteBatch(resultitems);
    }

    private Boolean saveResult(AidocauditResultEO result, List<AidocauditResultitemEO> resultItemList) {
        try {
            this.aidocauditResultDao.save(result);
            this.aidocauditResultItemDao.saveAll(resultItemList);
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u4fdd\u5b58\u5931\u8d25", (Throwable)e);
        }
        return true;
    }

    private CountResultItemDTO countResultItem(List<ResultItemDTO> resultItems) {
        ArrayList<ResultItemDTO> parents = new ArrayList<ResultItemDTO>();
        ArrayList<ResultItemDTO> lefts = new ArrayList<ResultItemDTO>();
        for (ResultItemDTO resultItem : resultItems) {
            parents.add(resultItem);
            lefts.addAll(resultItem.getChildren());
        }
        CountResultItemDTO countResult = new CountResultItemDTO();
        this.countNum(countResult, lefts);
        ArrayList<ResultItemDTO> resultItemList = new ArrayList<ResultItemDTO>();
        resultItemList.addAll(parents);
        resultItemList.addAll(lefts);
        countResult.setResultItemList(resultItemList);
        return countResult;
    }

    private void countNum(CountResultItemDTO countResult, List<ResultItemDTO> lefts) {
        int ruleNum = 0;
        int ruleMatchNum = 0;
        int ruleUnmatchNum = 0;
        int ruleSuspectMatchNum = 0;
        for (ResultItemDTO item : lefts) {
            String scoreBasis = item.getScoreBasis();
            if (Boolean.TRUE.equals(AidocauditScoreUtil.isSuspectMatch(scoreBasis))) {
                ++ruleSuspectMatchNum;
            } else if (item.getScore().compareTo(item.getFullScore()) == 0) {
                ++ruleMatchNum;
            } else {
                ++ruleUnmatchNum;
            }
            ++ruleNum;
        }
        countResult.setRuleNum(ruleNum);
        countResult.setRuleMatchNum(ruleMatchNum);
        countResult.setRuleUnmatchNum(ruleUnmatchNum);
        countResult.setRuleSuspectMatchNum(ruleSuspectMatchNum);
    }
}

