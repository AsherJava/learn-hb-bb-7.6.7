/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.gcreport.basedata.api.vo.BaseDataVO
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.common.SelectOptionVO
 *  com.jiuqi.gcreport.unionrule.constant.RuleConst
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.dto.UnionGroupRuleDTO
 *  com.jiuqi.gcreport.unionrule.enums.RuleExportModeEnum
 *  com.jiuqi.gcreport.unionrule.vo.ExportExcelVO
 *  com.jiuqi.gcreport.unionrule.vo.ImportMessageVO
 *  com.jiuqi.gcreport.unionrule.vo.UnionRuleVO
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.log.LogHelper
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.gcreport.unionrule.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.gcreport.basedata.api.vo.BaseDataVO;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.common.SelectOptionVO;
import com.jiuqi.gcreport.consolidatedsystem.dao.ConsolidatedSystemDao;
import com.jiuqi.gcreport.consolidatedsystem.entity.ConsolidatedSystemEO;
import com.jiuqi.gcreport.unionrule.base.UnionRuleManager;
import com.jiuqi.gcreport.unionrule.cache.UnionRuleChangedEvent;
import com.jiuqi.gcreport.unionrule.constant.RuleConst;
import com.jiuqi.gcreport.unionrule.dao.UnionRuleDao;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.dto.UnionGroupRuleDTO;
import com.jiuqi.gcreport.unionrule.entity.UnionRuleEO;
import com.jiuqi.gcreport.unionrule.enums.RuleExportModeEnum;
import com.jiuqi.gcreport.unionrule.service.UnionRuleFileService;
import com.jiuqi.gcreport.unionrule.service.UnionRuleService;
import com.jiuqi.gcreport.unionrule.util.ExcelUtils;
import com.jiuqi.gcreport.unionrule.util.ExportProcessor;
import com.jiuqi.gcreport.unionrule.util.UnionRuleConverter;
import com.jiuqi.gcreport.unionrule.util.UnionRuleUtils;
import com.jiuqi.gcreport.unionrule.vo.ExportExcelVO;
import com.jiuqi.gcreport.unionrule.vo.ImportMessageVO;
import com.jiuqi.gcreport.unionrule.vo.UnionRuleVO;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.log.LogHelper;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UnionRuleFileServiceImpl
implements UnionRuleFileService {
    public static final Logger logger = LoggerFactory.getLogger(UnionRuleFileServiceImpl.class);
    @Autowired
    private UnionRuleService ruleService;
    @Autowired
    private UnionRuleDao ruleDao;
    @Autowired
    private ConsolidatedSystemDao consolidatedSystemDao;
    @Autowired
    private ApplicationContext applicationContext;
    public static final String OVERWRITE_MODE = "\u8986\u5199\u6a21\u5f0f";
    public static final String UPDATE_MODE = "\u66f4\u65b0\u6a21\u5f0f";

    @Override
    public Resource exportJson(String reportSystemId, Map<String, Object> params) {
        if (Objects.isNull(reportSystemId)) {
            throw new BusinessRuntimeException("\u8bf7\u9009\u62e9\u5408\u5e76\u4f53\u7cfb");
        }
        List selectRuleIds = (List)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)params.get("selectRuleIds")), (TypeReference)new TypeReference<List<String>>(){});
        String mode = (String)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)params.get("mode")), (TypeReference)new TypeReference<String>(){});
        List<UnionRuleEO> rulesWithGroup = this.ruleDao.findRuleListByReportSystemIdWithGroup(reportSystemId);
        switch (RuleExportModeEnum.fromCode((String)mode)) {
            case ALL: {
                break;
            }
            case SELECT: {
                rulesWithGroup = this.queryRulesInSelectMode(rulesWithGroup, selectRuleIds);
                break;
            }
            case CURRENT: {
                rulesWithGroup = this.queryRulesInCurrentMode(rulesWithGroup, (String)selectRuleIds.get(0));
                break;
            }
            default: {
                throw new BusinessRuntimeException("\u6682\u4e0d\u652f\u6301\u8be5\u5bfc\u51fa\u7c7b\u578b-" + mode);
            }
        }
        ArrayList<AbstractUnionRule> unionRuleDTOList = new ArrayList<AbstractUnionRule>();
        for (UnionRuleEO unionRuleEO : rulesWithGroup) {
            AbstractUnionRule unionRuleDTO = UnionRuleConverter.convert(unionRuleEO);
            unionRuleDTOList.add(unionRuleDTO);
        }
        String json = JsonUtils.writeValueAsString(unionRuleDTOList);
        byte[] bytes = json.getBytes(Charset.forName("UTF-8"));
        ByteArrayResource byteArrayResource = new ByteArrayResource(bytes);
        String systemName = "";
        ConsolidatedSystemEO consolidatedSystem = (ConsolidatedSystemEO)this.consolidatedSystemDao.get((Serializable)((Object)reportSystemId));
        if (consolidatedSystem != null) {
            systemName = consolidatedSystem.getSystemName();
        }
        LogHelper.info((String)"\u5408\u5e76-\u5408\u5e76\u89c4\u5219", (String)("\u5bfc\u51faJSON\u683c\u5f0f" + systemName + "\u5408\u5e76\u4f53\u7cfb\u4e0b\u5408\u5e76\u89c4\u5219"), (String)"");
        return byteArrayResource;
    }

    private List<UnionRuleEO> queryRulesInCurrentMode(List<UnionRuleEO> allRule, String currNodeId) {
        ArrayList<UnionRuleEO> rules = new ArrayList<UnionRuleEO>();
        UnionRuleEO currNode = allRule.stream().filter(rule -> currNodeId.equals(rule.getId())).findFirst().orElse(null);
        if (Objects.isNull((Object)currNode)) {
            return rules;
        }
        if (currNode.getLeafFlag() == 1) {
            rules.add(currNode);
            List parent = allRule.stream().filter(rule -> currNode.getParentId().equals(rule.getId())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(parent)) {
                rules.add((UnionRuleEO)((Object)parent.get(0)));
            }
            return rules;
        }
        this.findChild(currNode, allRule, rules);
        return rules;
    }

    private List<UnionRuleEO> queryRulesInSelectMode(List<UnionRuleEO> allRule, List<String> selectRuleIds) {
        if (CollectionUtils.isEmpty(selectRuleIds)) {
            return Collections.emptyList();
        }
        List<UnionRuleEO> selectRules = allRule.stream().filter(rule -> selectRuleIds.contains(rule.getId())).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(selectRules)) {
            return Collections.emptyList();
        }
        List notSelectParentIds = selectRules.stream().filter(rule -> rule.getLeafFlag() == 1).map(UnionRuleEO::getParentId).filter(parentId -> !selectRuleIds.contains(parentId)).collect(Collectors.toList());
        List notSelectParents = allRule.stream().filter(rule -> notSelectParentIds.contains(rule.getId())).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(notSelectParents)) {
            selectRules.addAll(notSelectParents);
        }
        return selectRules;
    }

    private void findChild(UnionRuleEO currNode, List<UnionRuleEO> allRule, List<UnionRuleEO> rules) {
        rules.add(currNode);
        List<UnionRuleEO> childrens = allRule.stream().filter(rule -> currNode.getId().equals(rule.getParentId())).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(childrens)) {
            return;
        }
        childrens.forEach(child -> this.findChild((UnionRuleEO)((Object)child), allRule, rules));
    }

    @Override
    public void exportRuleToExcel(String reportSystemId, Map<String, Object> params, HttpServletResponse response) {
        List selectDimensions = (List)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)params.get("dim")), (TypeReference)new TypeReference<List<String>>(){});
        List selectRuleIds = (List)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)params.get("selectRuleIds")), (TypeReference)new TypeReference<List<String>>(){});
        String mode = (String)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)params.get("mode")), (TypeReference)new TypeReference<String>(){});
        ArrayList<UnionRuleVO> allExportRules = new ArrayList<UnionRuleVO>();
        switch (RuleExportModeEnum.fromCode((String)mode)) {
            case ALL: {
                List<UnionRuleVO> ruleTree = this.ruleService.selectRuleTreeByReportSystem(reportSystemId, false);
                allExportRules = ruleTree.get(0).getChildren();
                break;
            }
            case SELECT: {
                List<UnionRuleVO> selectRuleS = this.queryNeedExportRulesInSelectMode(selectRuleIds);
                if (CollectionUtils.isEmpty(selectRuleS)) break;
                allExportRules.addAll(selectRuleS);
                break;
            }
            case CURRENT: {
                List<UnionRuleVO> nowRules = this.queryNeedExportRulesInNowMode(selectRuleIds);
                if (CollectionUtils.isEmpty(nowRules)) break;
                allExportRules.addAll(nowRules);
                break;
            }
            default: {
                throw new BusinessRuntimeException("\u6682\u4e0d\u652f\u6301\u8be5\u5bfc\u51fa\u6a21\u5f0f-" + mode);
            }
        }
        List<SelectOptionVO> dimensions = this.ruleService.getManagementDimensionVOByReportSystem(reportSystemId);
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
        String fileName = "\u5408\u5e76\u89c4\u5219" + sf.format(new Date());
        ExportProcessor exportProcessor = new ExportProcessor(allExportRules, dimensions, selectDimensions, reportSystemId);
        Map<String, String> sheets = exportProcessor.getSheets();
        List<List<ExcelUtils.ExportColumnVO>> titleLists = exportProcessor.getSheetTitles();
        List<List<ExportExcelVO>> sheetDatas = exportProcessor.getSheetDatas();
        try {
            ExcelUtils.createExcel(response, fileName, sheets, titleLists, sheetDatas, selectDimensions);
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u5bfc\u51fa\u5931\u8d25\uff01", (Throwable)e);
        }
        String systemName = "";
        ConsolidatedSystemEO consolidatedSystem = (ConsolidatedSystemEO)this.consolidatedSystemDao.get((Serializable)((Object)reportSystemId));
        if (consolidatedSystem != null) {
            systemName = consolidatedSystem.getSystemName();
        }
        LogHelper.info((String)"\u5408\u5e76-\u5408\u5e76\u89c4\u5219", (String)("\u5bfc\u51faEXCEL\u683c\u5f0f" + systemName + "\u5408\u5e76\u4f53\u7cfb\u4e0b\u5408\u5e76\u89c4\u5219"), (String)"");
    }

    private List<UnionRuleVO> queryNeedExportRulesInSelectMode(List<String> selectRuleIds) {
        if (CollectionUtils.isEmpty(selectRuleIds)) {
            return Collections.emptyList();
        }
        List<UnionRuleVO> rules = this.ruleService.selectUnionRuleByIds(selectRuleIds);
        if (CollectionUtils.isEmpty(rules)) {
            return Collections.emptyList();
        }
        List<String> parentIds = rules.stream().filter(UnionRuleVO::getLeafFlag).map(UnionRuleVO::getParentId).filter(parentId -> !"-".equals(parentId)).distinct().collect(Collectors.toList());
        List<UnionRuleVO> parentRules = this.ruleService.selectUnionRuleByIds(parentIds);
        if (CollectionUtils.isEmpty(parentRules)) {
            return Collections.emptyList();
        }
        parentRules.forEach(parent -> parent.setChildren(rules.stream().filter(rule -> parent.getId().equals(rule.getParentId())).collect(Collectors.toList())));
        return parentRules;
    }

    private List<UnionRuleVO> queryNeedExportRulesInNowMode(List<String> selectRuleIds) {
        ArrayList<UnionRuleVO> rules = new ArrayList<UnionRuleVO>();
        if (CollectionUtils.isEmpty(selectRuleIds)) {
            return rules;
        }
        UnionRuleVO ruleTree = this.ruleService.selectUnionRuleAndChildrenById(selectRuleIds.get(0), false);
        if ("group".equals(ruleTree.getRuleType()) || "root".equals(ruleTree.getRuleType())) {
            if ("root".equals(ruleTree.getRuleType())) {
                return ruleTree.getChildren();
            }
            rules.add(ruleTree);
        } else {
            UnionRuleVO parent = this.ruleService.selectUnionRuleById(ruleTree.getParentId());
            parent.setChildren(Collections.singletonList(ruleTree));
            rules.add(parent);
        }
        return rules;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public Set<ImportMessageVO> importJson(String reportSystemId, MultipartFile multipartFile, boolean isOverwrite) {
        byte[] bytes;
        if (Objects.isNull(reportSystemId)) {
            throw new BusinessRuntimeException("\u8bf7\u9009\u62e9\u5408\u5e76\u4f53\u7cfb");
        }
        HashSet<ImportMessageVO> resultList = new HashSet<ImportMessageVO>();
        try {
            bytes = multipartFile.getBytes();
        }
        catch (IOException e) {
            throw new BusinessRuntimeException("\u65e0\u6cd5\u89e3\u6790\u5bfc\u5165\u6587\u4ef6\u3002", (Throwable)e);
        }
        if (Objects.isNull(bytes) || bytes.length == 0) {
            resultList.add(new ImportMessageVO().setERROR(null, "\u6ca1\u6709\u53ef\u4ee5\u5bfc\u5165\u7684\u6570\u636e\u3002"));
            return resultList;
        }
        List<AbstractUnionRule> unionRuleDTOList = new ArrayList<AbstractUnionRule>();
        List importJsonList = null;
        try {
            importJsonList = (List)JsonUtils.readValue((byte[])bytes, (TypeReference)new TypeReference<List<Map<String, Object>>>(){});
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u5bfc\u5165\u6587\u4ef6\u4ec5\u652f\u6301json\u683c\u5f0f");
        }
        for (Map importJsonListMap : importJsonList) {
            AbstractUnionRule unionRuleDTO = this.convertMapToDTO(importJsonListMap);
            int resultListSize = resultList.size();
            if (Boolean.TRUE.equals(unionRuleDTO.getLeafFlag())) {
                this.checkRuleBusinessTypeCode(unionRuleDTO, resultList);
                this.checkRuleSubjectCode(unionRuleDTO, resultList, reportSystemId);
                if (resultList.size() > resultListSize) continue;
                unionRuleDTOList.add(unionRuleDTO);
                continue;
            }
            unionRuleDTOList.add(unionRuleDTO);
        }
        if (CollectionUtils.isEmpty(unionRuleDTOList)) {
            resultList.add(new ImportMessageVO().setERROR(null, "\u6ca1\u6709\u53ef\u4ee5\u5bfc\u5165\u7684\u6570\u636e\u3002"));
            return resultList;
        }
        Set sameRuleTitles = unionRuleDTOList.stream().filter(rule -> rule.getLeafFlag()).collect(Collectors.groupingBy(AbstractUnionRule::getTitle)).entrySet().stream().filter(entry -> ((List)entry.getValue()).size() > 1).map(Map.Entry::getKey).collect(Collectors.toSet());
        if (!CollectionUtils.isEmpty(sameRuleTitles)) {
            Iterator iterator = unionRuleDTOList.iterator();
            while (iterator.hasNext()) {
                AbstractUnionRule rule2 = (AbstractUnionRule)iterator.next();
                if (!rule2.getLeafFlag().booleanValue() || !sameRuleTitles.contains(rule2.getTitle())) continue;
                resultList.add(new ImportMessageVO().setERROR(rule2.getTitle() + "\u5bfc\u5165\u65f6\u8df3\u8fc7", "\u6e90\u6587\u4ef6\u4e2d\u89c4\u5219\u3010" + rule2.getTitle() + "\u3011\u540d\u79f0\u91cd\u590d\u7684\u6709\u591a\u4e2a,\u8bf7\u8c03\u6574\u4ee5\u907f\u514d\u89c4\u5219\u8986\u76d6\u9519\u8bef"));
                iterator.remove();
            }
        }
        unionRuleDTOList.stream().forEach(rule -> rule.setReportSystem(reportSystemId));
        List<Object> allDbRules = this.ruleService.selectAllRuleListByReportSystemAndRuleTypes(reportSystemId, null);
        if (!CollectionUtils.isEmpty(allDbRules)) {
            allDbRules = allDbRules.stream().filter(AbstractUnionRule::getLeafFlag).collect(Collectors.toList());
            Map<String, List<AbstractUnionRule>> dbRuleGroup = allDbRules.stream().collect(Collectors.groupingBy(AbstractUnionRule::getTitle));
            Iterator<AbstractUnionRule> iterator = unionRuleDTOList.iterator();
            while (iterator.hasNext()) {
                List<AbstractUnionRule> sameNameRules;
                AbstractUnionRule rule3 = iterator.next();
                if (!rule3.getLeafFlag().booleanValue() || !dbRuleGroup.containsKey(rule3.getTitle()) || (sameNameRules = dbRuleGroup.get(rule3.getTitle())).size() <= 1) continue;
                resultList.add(new ImportMessageVO().setERROR(rule3.getTitle() + "\u5bfc\u5165\u65f6\u8df3\u8fc7", "\u76ee\u6807\u4f53\u7cfb\u4e0b\u4e0e\u89c4\u5219\u3010" + rule3.getTitle() + "\u3011\u540d\u79f0\u91cd\u590d\u7684\u6709\u591a\u4e2a,\u8bf7\u8c03\u6574\u4ee5\u907f\u514d\u89c4\u5219\u8986\u76d6\u9519\u8bef"));
                iterator.remove();
            }
        }
        unionRuleDTOList = this.importJsonIgnoreLevel(unionRuleDTOList, allDbRules);
        List importRuleIds = unionRuleDTOList.stream().map(AbstractUnionRule::getId).collect(Collectors.toList());
        List<Object> importRootRules = unionRuleDTOList.stream().filter(rule -> !importRuleIds.contains(rule.getParentId())).collect(Collectors.toList());
        List errorIds = importRootRules.stream().filter(rule -> rule.getLeafFlag()).map(AbstractUnionRule::getId).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(errorIds)) {
            resultList.add(new ImportMessageVO().setERROR("\u5bfc\u5165\u5931\u8d25", MessageFormat.format("\u5305\u542b\u4e0a\u7ea7\u5206\u7ec4\u4e0d\u5b58\u5728\u7684\u6570\u636e\uff0cid\u5217\u8868\u4e3a\uff1a{0} \uff0c\u8bf7\u68c0\u67e5json\u6587\u4ef6", errorIds.toString())));
            return resultList;
        }
        Map<String, List<AbstractUnionRule>> parentMap = unionRuleDTOList.stream().collect(Collectors.groupingBy(AbstractUnionRule::getParentId));
        List<AbstractUnionRule> rootList = parentMap.get(RuleConst.ROOT_PARENT_ID);
        if (!CollectionUtils.isEmpty(rootList)) {
            AbstractUnionRule oldRoot = rootList.get(0);
            String importRootId = oldRoot.getId();
            importRootRules = parentMap.get(importRootId);
        }
        List<UnionRuleVO> ruleTree = this.ruleService.selectRuleTreeByReportSystem(reportSystemId, false);
        UnionRuleVO rootRule = this.getRootRule(ruleTree);
        List dbChildren = rootRule.getChildren();
        this.cycleCheckChildren(rootRule.getId(), importRootRules, dbChildren, parentMap);
        NpContext context = NpContextHolder.getContext();
        this.applicationContext.publishEvent(new UnionRuleChangedEvent(new UnionRuleChangedEvent.UnionRuleChangedInfo(reportSystemId), context));
        logger.info("\u89c4\u5219\u5bfc\u5165\u6210\u529f");
        return resultList;
    }

    private List<AbstractUnionRule> importJsonIgnoreLevel(List<AbstractUnionRule> unionRuleDTOList, List<AbstractUnionRule> allDbRules) {
        if (!CollectionUtils.isEmpty(allDbRules)) {
            allDbRules = allDbRules.stream().filter(AbstractUnionRule::getLeafFlag).collect(Collectors.toList());
            Map<String, List<AbstractUnionRule>> dbRuleGroup = allDbRules.stream().collect(Collectors.groupingBy(AbstractUnionRule::getTitle));
            Iterator<AbstractUnionRule> iterator = unionRuleDTOList.iterator();
            while (iterator.hasNext()) {
                AbstractUnionRule rule2 = iterator.next();
                if (!rule2.getLeafFlag().booleanValue() || !dbRuleGroup.containsKey(rule2.getTitle())) continue;
                List<AbstractUnionRule> sameNameRules = dbRuleGroup.get(rule2.getTitle());
                AbstractUnionRule sameNameRule = sameNameRules.get(0);
                rule2.setId(sameNameRule.getId());
                rule2.setParentId(sameNameRule.getParentId());
                this.ruleService.importRule(rule2);
                iterator.remove();
            }
        }
        HashSet hasLeafGroup = new HashSet();
        Set<String> parents = unionRuleDTOList.stream().filter(AbstractUnionRule::getLeafFlag).map(AbstractUnionRule::getParentId).collect(Collectors.toSet());
        if (!CollectionUtils.isEmpty(parents)) {
            Map idAndRuleMap = unionRuleDTOList.stream().collect(Collectors.toMap(AbstractUnionRule::getId, Function.identity()));
            parents.forEach(id -> this.findAllHasLeafGroup((String)id, idAndRuleMap, hasLeafGroup));
        }
        return unionRuleDTOList.stream().filter(rule -> rule.getLeafFlag() != false || hasLeafGroup.contains(rule.getId())).collect(Collectors.toList());
    }

    private void findAllHasLeafGroup(String id, Map<String, AbstractUnionRule> idAndRuleMap, Set<String> hasLeafGroup) {
        if (idAndRuleMap.containsKey(id)) {
            hasLeafGroup.add(id);
            this.findAllHasLeafGroup(idAndRuleMap.get(id).getParentId(), idAndRuleMap, hasLeafGroup);
        }
    }

    private void checkRuleBusinessTypeCode(AbstractUnionRule unionRuleDTO, Set<ImportMessageVO> resultList) {
        if (!unionRuleDTO.getBusinessTypeCode().isEmpty()) {
            BaseDataVO baseDataVO = GcBaseDataCenterTool.getInstance().convertBaseDataVO(GcBaseDataCenterTool.getInstance().queryBasedataByCode("MD_GCBUSINESSTYPE", unionRuleDTO.getBusinessTypeCode()));
            if (Objects.isNull(baseDataVO)) {
                resultList.add(new ImportMessageVO().setERROR(unionRuleDTO.getTitle(), "\u5408\u5e76\u4e1a\u52a1\u7c7b\u578b" + unionRuleDTO.getBusinessTypeCode() + "\u4e0d\u5b58\u5728\u3002"));
            }
        } else {
            resultList.add(new ImportMessageVO().setERROR(unionRuleDTO.getTitle(), "\u5408\u5e76\u4e1a\u52a1\u7c7b\u578b\u4e0d\u80fd\u4e3a\u7a7a\u3002"));
        }
    }

    private void checkRuleSubjectCode(AbstractUnionRule unionRuleDTO, Set<ImportMessageVO> resultList, String reportSystemId) {
        UnionRuleManager ruleManager = UnionRuleUtils.getManagerByRuleTypeCode(unionRuleDTO.getRuleType());
        resultList.addAll(ruleManager.getRuleHandler().checkRuleSubjectCode(unionRuleDTO, reportSystemId));
    }

    private void cycleCheckChildren(String parentId, List<AbstractUnionRule> importChildren, List<UnionRuleVO> dbChildren, Map<String, List<AbstractUnionRule>> parentMap) {
        if (CollectionUtils.isEmpty(importChildren)) {
            return;
        }
        for (AbstractUnionRule ruleDTO : importChildren) {
            UnionRuleVO rule = this.findSameRule(dbChildren, ruleDTO);
            if (rule != null) {
                String impId = ruleDTO.getId();
                ruleDTO.setId(rule.getId());
                ruleDTO.setParentId(parentId);
                this.ruleService.importRule(ruleDTO);
                List<AbstractUnionRule> list = parentMap.get(impId);
                List children = rule.getChildren();
                this.cycleCheckChildren(rule.getId(), list, children, parentMap);
                continue;
            }
            String oldId = ruleDTO.getId();
            ruleDTO.setId(null);
            ruleDTO.setParentId(parentId);
            UnionRuleVO newNode = this.ruleService.importRule(ruleDTO);
            if (!Objects.nonNull(oldId)) continue;
            this.createChildrenNode(oldId, newNode.getId(), parentMap);
        }
    }

    private UnionRuleVO findSameRule(List<UnionRuleVO> list, AbstractUnionRule rule) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        for (UnionRuleVO ruleVO : list) {
            if (!ruleVO.getTitle().equals(rule.getTitle())) continue;
            return ruleVO;
        }
        return null;
    }

    private UnionRuleVO getRootRule(List<UnionRuleVO> ruleTree) {
        Assert.notEmpty(ruleTree, "can not be empty");
        return ruleTree.get(0);
    }

    private void createChildrenNode(String oldParentId, String newParentId, Map<String, List<AbstractUnionRule>> parentMap) {
        List<AbstractUnionRule> abstractUnionRuleList = parentMap.get(oldParentId);
        if (CollectionUtils.isEmpty(abstractUnionRuleList)) {
            return;
        }
        String oldParent = null;
        for (AbstractUnionRule ruleDTO : abstractUnionRuleList) {
            oldParent = ruleDTO.getId();
            ruleDTO.setId(null);
            ruleDTO.setParentId(newParentId);
            UnionRuleVO newRule = this.ruleService.importRule(ruleDTO);
            this.createChildrenNode(oldParent, newRule.getId(), parentMap);
        }
    }

    public AbstractUnionRule convertMapToDTO(Map<String, Object> importJsonListMap) {
        if (!Boolean.TRUE.equals(importJsonListMap.get("leafFlag"))) {
            return (AbstractUnionRule)JsonUtils.readValue((String)JsonUtils.writeValueAsString(importJsonListMap), UnionGroupRuleDTO.class);
        }
        Object ruleType = importJsonListMap.get("ruleType");
        UnionRuleManager ruleManager = UnionRuleUtils.getManagerByRuleTypeCode(String.valueOf(ruleType));
        return ruleManager.getRuleHandler().convertMapToDTO(importJsonListMap);
    }
}

