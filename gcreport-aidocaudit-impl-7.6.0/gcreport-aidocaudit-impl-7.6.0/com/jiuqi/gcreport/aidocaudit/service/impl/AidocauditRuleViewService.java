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
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 */
package com.jiuqi.gcreport.aidocaudit.service.impl;

import com.jiuqi.gcreport.aidocaudit.constans.AidocauditConstans;
import com.jiuqi.gcreport.aidocaudit.dao.IAidocauditResultDao;
import com.jiuqi.gcreport.aidocaudit.dao.IAidocauditResultItemDao;
import com.jiuqi.gcreport.aidocaudit.dto.AuditParamDTO;
import com.jiuqi.gcreport.aidocaudit.dto.ResultItemAndRuleNameDTO;
import com.jiuqi.gcreport.aidocaudit.enums.subUnitSelectEnum;
import com.jiuqi.gcreport.aidocaudit.eo.AidocauditResultEO;
import com.jiuqi.gcreport.aidocaudit.service.IAidocauditRuleViewService;
import com.jiuqi.gcreport.aidocaudit.util.AidocauditOrgUtil;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.nr.dataentry.service.IFuncExecuteService;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class AidocauditRuleViewService
implements IAidocauditRuleViewService {
    @Autowired
    private IAidocauditResultDao resultDao;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IFuncExecuteService iFuncExecuteService;
    @Autowired
    private IAidocauditResultItemDao resultItemDao;

    @Override
    public Map<String, Object> orguploadedstatus(AuditParamDTO param) {
        String period = param.getDataTime();
        List<String> orgIds = AidocauditOrgUtil.getOrgIds(param.getIsAllOrg(), param.getOrgIds(), param.getTaskId(), period);
        HashMap<String, Object> res = new HashMap<String, Object>();
        Integer orgNum = orgIds.size();
        res.put("allNum", orgNum);
        res.put("uploadedNum", orgNum);
        List<String> ruleIds = param.getRuleIds();
        if (CollectionUtils.isEmpty(ruleIds) || CollectionUtils.isEmpty(orgIds)) {
            return res;
        }
        Integer fullCompliantNum = 0;
        for (String ruleId : ruleIds) {
            List<AidocauditResultEO> aidocauditResultEOS = this.resultDao.queryByOrgIds(param.getTaskId(), ruleId, period, orgIds);
            Integer count = Math.toIntExact(aidocauditResultEOS.stream().filter(a -> a.getScore().compareTo(AidocauditConstans.FULL_SCORE) >= 0).count());
            fullCompliantNum = fullCompliantNum + count;
        }
        BigDecimal totalNum = new BigDecimal(orgNum).multiply(new BigDecimal(ruleIds.size()));
        BigDecimal passRate = new BigDecimal(fullCompliantNum).multiply(new BigDecimal(100)).divide(totalNum, 2, RoundingMode.HALF_UP);
        res.put("passRate", passRate);
        return res;
    }

    @Override
    public Map<String, Object> orgauditstatus(AuditParamDTO param) {
        String period = param.getDataTime();
        List<String> orgIds = AidocauditOrgUtil.getOrgIds(param.getIsAllOrg(), param.getOrgIds(), param.getTaskId(), period);
        String ruleId = param.getRuleId();
        List<AidocauditResultEO> aidocauditResultEOS = this.resultDao.queryByOrgIds(param.getTaskId(), ruleId, period, orgIds);
        Integer fullMatchNum = 0;
        Integer minorUnmatchNum = 0;
        Integer majorUnmatchNum = 0;
        for (AidocauditResultEO result : aidocauditResultEOS) {
            Integer n;
            Integer n2;
            BigDecimal score = result.getScore();
            if (score.compareTo(AidocauditConstans.FULL_SCORE) >= 0) {
                n2 = fullMatchNum;
                n = fullMatchNum = Integer.valueOf(fullMatchNum + 1);
                continue;
            }
            if (score.compareTo(AidocauditConstans.MINOR_UN_MATCH_SCORE) >= 0 && score.compareTo(AidocauditConstans.FULL_SCORE) < 0) {
                n2 = minorUnmatchNum;
                n = minorUnmatchNum = Integer.valueOf(minorUnmatchNum + 1);
                continue;
            }
            n2 = majorUnmatchNum;
            n = majorUnmatchNum = Integer.valueOf(majorUnmatchNum + 1);
        }
        HashMap<String, Object> res = new HashMap<String, Object>();
        res.put("fullMatchNum", fullMatchNum);
        res.put("minorUnmatchNum", minorUnmatchNum);
        res.put("majorUnmatchNum", majorUnmatchNum);
        return res;
    }

    @Override
    public List<Map<String, Object>> orgquestionstatus(AuditParamDTO param) {
        String ruleId = param.getRuleId();
        List<AidocauditResultEO> results = this.resultDao.queryByOrgIds(param.getTaskId(), ruleId, param.getDataTime(), param.getOrgIds());
        List<String> resultIds = results.stream().map(DefaultTableEntity::getId).collect(Collectors.toList());
        List<ResultItemAndRuleNameDTO> resultitemList = this.resultItemDao.queryOrgQuestionStatus(resultIds);
        Map<String, Long> groupedResult = resultitemList.stream().collect(Collectors.groupingBy(ResultItemAndRuleNameDTO::getRuleItemName, Collectors.counting()));
        Integer record = param.getRecord();
        return groupedResult.entrySet().stream().map(entry -> {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("question", entry.getKey());
            map.put("num", ((Long)entry.getValue()).intValue());
            return map;
        }).sorted((m1, m2) -> ((Integer)m2.get("num")).compareTo((Integer)m1.get("num"))).limit(record.intValue()).collect(Collectors.toList());
    }

    @Override
    public List<Map<String, Object>> orglowestscorestatus(AuditParamDTO param) {
        String taskId = param.getTaskId();
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(taskId);
        FormSchemeDefine formSchemeDefine = this.iFuncExecuteService.queryFormScheme(taskId, param.getDataTime());
        String orgType = taskDefine.getDw().replace("@ORG", "");
        GcOrgCenterService orgCenterService = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)new YearPeriodObject(formSchemeDefine.getKey(), param.getDataTime()));
        List<String> selectOrgIds = param.getOrgIds();
        if (CollectionUtils.isEmpty(selectOrgIds)) {
            return new ArrayList<Map<String, Object>>();
        }
        ArrayList<GcOrgCacheVO> selectedOrg = new ArrayList<GcOrgCacheVO>();
        for (String org : selectOrgIds) {
            GcOrgCacheVO orgByCode = orgCenterService.getOrgByCode(org);
            selectedOrg.add(orgByCode);
        }
        List collectionTree = orgCenterService.collectionToTree(selectedOrg);
        Boolean secondOrgShowAble = collectionTree.size() == 1;
        GcOrgCacheVO rootOrg = (GcOrgCacheVO)collectionTree.get(0);
        if (Boolean.TRUE.equals(secondOrgShowAble) && param.getSubUnitSelect().equals((Object)subUnitSelectEnum.DIRECTCHILDREN)) {
            List directOrg = rootOrg.getChildren().stream().map(GcOrgCacheVO::getCode).collect(Collectors.toList());
            selectOrgIds = selectOrgIds.stream().filter(directOrg::contains).collect(Collectors.toList());
        }
        int rootIndex = 0;
        if (Boolean.TRUE.equals(secondOrgShowAble) && param.getSubUnitSelect().equals((Object)subUnitSelectEnum.ALLCHILDREN)) {
            rootIndex = rootOrg.getParents().length - 1;
        }
        String ruleId = param.getRuleId();
        Integer record = param.getRecord();
        List<AidocauditResultEO> reusltLimitList = this.resultDao.queryByOrgIdsLimit(param.getTaskId(), ruleId, param.getDataTime(), selectOrgIds, record);
        ArrayList<Map<String, Object>> orgLowestScore = new ArrayList<Map<String, Object>>();
        for (AidocauditResultEO result : reusltLimitList) {
            String mdCode = result.getMdCode();
            GcOrgCacheVO org = orgCenterService.getOrgByCode(mdCode);
            String[] parents = org.getParents();
            String secondOrgName = null;
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("orgName", org.getTitle());
            map.put("score", result.getScore());
            if (Boolean.TRUE.equals(secondOrgShowAble) && param.getSubUnitSelect().equals((Object)subUnitSelectEnum.ALLCHILDREN) && !org.getId().equals(rootOrg.getId())) {
                String secondOrgId = parents[rootIndex + 1];
                secondOrgName = orgCenterService.getOrgByCode(secondOrgId).getTitle();
            }
            map.put("secondOrgName", secondOrgName);
            orgLowestScore.add(map);
        }
        return orgLowestScore;
    }
}

