/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.nr.dataentry.service.IFuncExecuteService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  io.jsonwebtoken.lang.Objects
 */
package com.jiuqi.gcreport.aidocaudit.service.impl;

import com.jiuqi.gcreport.aidocaudit.dao.IAidocauditResultDao;
import com.jiuqi.gcreport.aidocaudit.dao.IAidocauditResultItemDao;
import com.jiuqi.gcreport.aidocaudit.dao.IAidocauditRuleItemDao;
import com.jiuqi.gcreport.aidocaudit.dto.AidocauditResultDTO;
import com.jiuqi.gcreport.aidocaudit.dto.AidocauditResultDetailParamDTO;
import com.jiuqi.gcreport.aidocaudit.dto.AuditParamDTO;
import com.jiuqi.gcreport.aidocaudit.dto.ResultDetailDTO;
import com.jiuqi.gcreport.aidocaudit.dto.RuleItemStatisticsDTO;
import com.jiuqi.gcreport.aidocaudit.dto.RuleItemTreeDTO;
import com.jiuqi.gcreport.aidocaudit.eo.AidocauditResultEO;
import com.jiuqi.gcreport.aidocaudit.eo.AidocauditResultitemEO;
import com.jiuqi.gcreport.aidocaudit.eo.AidocauditRuleItemEO;
import com.jiuqi.gcreport.aidocaudit.service.IAidocauditUnitdetailService;
import com.jiuqi.gcreport.aidocaudit.util.AidocauditScoreUtil;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.nr.dataentry.service.IFuncExecuteService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import io.jsonwebtoken.lang.Objects;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class AidocauditUnitdetailServiceImpl
implements IAidocauditUnitdetailService {
    @Autowired
    private IAidocauditResultDao resultDao;
    @Autowired
    private IAidocauditRuleItemDao ruleItemDao;
    @Autowired
    private IAidocauditResultItemDao resultItemDao;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IFuncExecuteService iFuncExecuteService;

    @Override
    public List<AidocauditResultDTO> orgAuditResults(AuditParamDTO param) {
        List<AidocauditResultEO> unitDetails = this.resultDao.getUnitDetailByPage(param);
        ArrayList<AidocauditResultDTO> unitDetailDTOs = new ArrayList<AidocauditResultDTO>();
        String taskId = param.getTaskId();
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(taskId);
        FormSchemeDefine formSchemeDefine = this.iFuncExecuteService.queryFormScheme(taskId, param.getDataTime());
        String orgType = taskDefine.getDw().replace("@ORG", "");
        GcOrgCenterService orgCenterService = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)new YearPeriodObject(formSchemeDefine.getKey(), param.getDataTime()));
        for (AidocauditResultEO detail : unitDetails) {
            AidocauditResultDTO dto = new AidocauditResultDTO();
            BeanUtils.copyProperties((Object)detail, dto);
            String mdCode = detail.getMdCode();
            GcOrgCacheVO orgData = orgCenterService.getOrgByCode(mdCode);
            dto.setOrgName(orgData.getTitle());
            BigDecimal ruleMatchNum = BigDecimal.valueOf(dto.getRuleMatchNum().intValue());
            BigDecimal ruleSuspectMatchNum = BigDecimal.valueOf(dto.getRuleSuspectMatchNum().intValue());
            BigDecimal ruleNum = BigDecimal.valueOf(dto.getRuleNum().intValue());
            if (ruleNum.compareTo(BigDecimal.ZERO) == 0) {
                dto.setPassRate(BigDecimal.ZERO);
            } else {
                BigDecimal passRate = ruleMatchNum.add(ruleSuspectMatchNum).multiply(new BigDecimal("100")).divide(ruleNum, 2, RoundingMode.HALF_UP);
                dto.setPassRate(passRate);
            }
            unitDetailDTOs.add(dto);
        }
        return unitDetailDTOs;
    }

    @Override
    public List<RuleItemTreeDTO> ruleAuditResults(AuditParamDTO param) {
        List<AidocauditRuleItemEO> ruleitems = this.ruleItemDao.getByRuleId(param.getRuleId());
        List<RuleItemTreeDTO> ruleItemTreeList = this.buildRuleItemTree(ruleitems);
        List<AidocauditResultEO> resultList = this.resultDao.queryByOrgIds(param.getTaskId(), param.getRuleId(), param.getDataTime(), param.getOrgIds());
        List<String> resultIds = resultList.stream().map(DefaultTableEntity::getId).collect(Collectors.toList());
        List<AidocauditResultitemEO> resultItemList = this.resultItemDao.queryByResultIds(resultIds);
        Map<String, List<AidocauditResultitemEO>> resultItemsMap = resultItemList.stream().collect(Collectors.groupingBy(AidocauditResultitemEO::getRuleItemId));
        for (RuleItemTreeDTO ruleItemTree : ruleItemTreeList) {
            List<RuleItemTreeDTO> children = ruleItemTree.getChildren();
            if (CollectionUtils.isEmpty(children)) continue;
            for (RuleItemTreeDTO child : children) {
                List<AidocauditResultitemEO> items = resultItemsMap.get(child.getId());
                RuleItemStatisticsDTO stats = this.calculateRuleItemStatistics(items, resultList.size());
                child.setMatchUnitNum(stats.getMatchUnitNum());
                child.setSuspectMatchUnitNum(stats.getSuspectMatchUnitNum());
                child.setUnMatchUnitNum(stats.getUnMatchUnitNum());
                child.setPassRate(stats.getPassRate());
            }
        }
        return ruleItemTreeList;
    }

    private RuleItemStatisticsDTO calculateRuleItemStatistics(List<AidocauditResultitemEO> resultItems, int totalUnitCount) {
        Integer matchUnitNum = 0;
        Integer suspectMatchUnitNum = 0;
        Integer unMatchUnitNum = 0;
        if (CollectionUtils.isEmpty(resultItems)) {
            return new RuleItemStatisticsDTO(matchUnitNum, suspectMatchUnitNum, unMatchUnitNum, BigDecimal.ZERO);
        }
        for (AidocauditResultitemEO item : resultItems) {
            Integer n;
            Integer n2;
            String scoreBasis = item.getScoreBasis();
            if (Boolean.TRUE.equals(AidocauditScoreUtil.isSuspectMatch(scoreBasis))) {
                n2 = suspectMatchUnitNum;
                n = suspectMatchUnitNum = Integer.valueOf(suspectMatchUnitNum + 1);
                continue;
            }
            if (item.getScore().compareTo(item.getFullScore()) == 0) {
                n2 = matchUnitNum;
                n = matchUnitNum = Integer.valueOf(matchUnitNum + 1);
                continue;
            }
            n2 = unMatchUnitNum;
            n = unMatchUnitNum = Integer.valueOf(unMatchUnitNum + 1);
        }
        BigDecimal passRate = BigDecimal.valueOf((long)matchUnitNum.intValue() + (long)suspectMatchUnitNum.intValue()).multiply(BigDecimal.valueOf(100L)).divide(BigDecimal.valueOf(totalUnitCount), 2, RoundingMode.HALF_UP);
        return new RuleItemStatisticsDTO(matchUnitNum, suspectMatchUnitNum, unMatchUnitNum, passRate);
    }

    @Override
    public List<ResultDetailDTO> ruleAuditResultDetail(AidocauditResultDetailParamDTO param) {
        List<AidocauditResultEO> resultList = this.resultDao.queryByOrgIds(param.getTaskId(), param.getRuleId(), param.getDataTime(), param.getOrgIds());
        String ruleItemId = param.getRuleItemId();
        List<String> resultIds = resultList.stream().map(DefaultTableEntity::getId).collect(Collectors.toList());
        boolean isParent = false;
        List<ResultDetailDTO> resultDetailDTOs = this.resultItemDao.queryRuleAuditResultDetail(resultIds, ruleItemId, isParent);
        Integer resultType = param.getResultType();
        List<ResultDetailDTO> filterResultDetailDTOs = Objects.isEmpty((Object)resultType) || resultType == 0 ? resultDetailDTOs : resultDetailDTOs.stream().filter(dto -> {
            String scoreBasis = dto.getScoreBasis();
            BigDecimal score = dto.getScore();
            BigDecimal fullScore = dto.getFullScore();
            if (resultType == 2) {
                return AidocauditScoreUtil.isSuspectMatch(scoreBasis);
            }
            if (resultType == 1) {
                return AidocauditScoreUtil.isSuspectMatch(scoreBasis) == false && score.compareTo(fullScore) == 0;
            }
            if (resultType == 3) {
                return AidocauditScoreUtil.isSuspectMatch(scoreBasis) == false && score.compareTo(fullScore) < 0;
            }
            return false;
        }).collect(Collectors.toList());
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(param.getTaskId());
        FormSchemeDefine formSchemeDefine = this.iFuncExecuteService.queryFormScheme(param.getTaskId(), param.getDataTime());
        String orgType = taskDefine.getDw().replace("@ORG", "");
        GcOrgCenterService orgCenterService = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)new YearPeriodObject(formSchemeDefine.getKey(), param.getDataTime()));
        for (ResultDetailDTO resultDetailDTO : filterResultDetailDTOs) {
            GcOrgCacheVO orgData = orgCenterService.getOrgByCode(resultDetailDTO.getMdCode());
            resultDetailDTO.setOrgTitle(orgData.getTitle());
        }
        return filterResultDetailDTOs;
    }

    public List<RuleItemTreeDTO> buildRuleItemTree(List<AidocauditRuleItemEO> ruleitems) {
        String ordinal;
        List ruleItemTreeDTOs = ruleitems.stream().map(this::convertToRuleItemTreeDTO).collect(Collectors.toList());
        HashMap<String, List> parentMap = new HashMap<String, List>();
        for (RuleItemTreeDTO dto2 : ruleItemTreeDTOs) {
            ordinal = dto2.getOrdinal();
            String parentOrdinal = ordinal.length() > 3 ? ordinal.substring(0, ordinal.length() - 3) : null;
            if (parentOrdinal == null) continue;
            parentMap.computeIfAbsent(parentOrdinal, k -> new ArrayList()).add(dto2);
        }
        for (RuleItemTreeDTO dto2 : ruleItemTreeDTOs) {
            ordinal = dto2.getOrdinal();
            List children = (List)parentMap.get(ordinal);
            if (children == null) continue;
            dto2.setChildren(children);
        }
        return ruleItemTreeDTOs.stream().filter(dto -> dto.getOrdinal().length() == 3).collect(Collectors.toList());
    }

    private RuleItemTreeDTO convertToRuleItemTreeDTO(AidocauditRuleItemEO ruleitem) {
        RuleItemTreeDTO dto = new RuleItemTreeDTO();
        dto.setId(ruleitem.getId());
        dto.setRuleId(ruleitem.getRuleId());
        dto.setScoreItemId(ruleitem.getScoreItemId());
        dto.setScoreItemName(ruleitem.getScoreItemName());
        dto.setFullScore(ruleitem.getFullScore());
        dto.setParentScoreItemId(ruleitem.getParentScoreItemId());
        dto.setParagraphTitle(ruleitem.getParagraphTitle());
        dto.setPrompt(ruleitem.getPrompt());
        dto.setOrdinal(ruleitem.getOrdinal());
        dto.setCreateTime(ruleitem.getCreateTime());
        dto.setCreateUser(ruleitem.getCreateUser());
        dto.setUpdateTime(ruleitem.getUpdateTime());
        dto.setUpdateUser(ruleitem.getUpdateUser());
        return dto;
    }
}

