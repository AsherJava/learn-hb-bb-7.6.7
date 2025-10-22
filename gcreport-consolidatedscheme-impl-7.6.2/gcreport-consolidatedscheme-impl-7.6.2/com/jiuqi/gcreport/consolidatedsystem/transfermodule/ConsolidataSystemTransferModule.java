/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.gcreport.consolidatedsystem.vo.ConsolidatedSystemVO
 *  com.jiuqi.gcreport.consolidatedsystem.vo.InputDataSchemeVO
 *  com.jiuqi.gcreport.consolidatedsystem.vo.formula.ConsolidatedFormulaVO
 *  com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO
 *  com.jiuqi.gcreport.consolidatedsystem.vo.subject.ConsolidatedSubjectVO
 *  com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.np.definition.internal.util.SpringUtil
 *  com.jiuqi.va.paramsync.domain.VaParamTransferBusinessNode
 *  com.jiuqi.va.paramsync.domain.VaParamTransferCategory
 *  com.jiuqi.va.paramsync.domain.VaParamTransferFolderNode
 *  com.jiuqi.va.paramsync.intf.VaParamTransferModuleIntf
 */
package com.jiuqi.gcreport.consolidatedsystem.transfermodule;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.gcreport.consolidatedsystem.entity.ConsolidatedSystemEO;
import com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO;
import com.jiuqi.gcreport.consolidatedsystem.service.ConsolidatedSubjectUIService;
import com.jiuqi.gcreport.consolidatedsystem.service.ConsolidatedSystemService;
import com.jiuqi.gcreport.consolidatedsystem.service.Formula.ConsolidatedFormulaService;
import com.jiuqi.gcreport.consolidatedsystem.service.InputDataSchemeCacheService;
import com.jiuqi.gcreport.consolidatedsystem.service.InputDataSchemeService;
import com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.util.SubjectConvertUtil;
import com.jiuqi.gcreport.consolidatedsystem.vo.ConsolidatedSystemVO;
import com.jiuqi.gcreport.consolidatedsystem.vo.InputDataSchemeVO;
import com.jiuqi.gcreport.consolidatedsystem.vo.formula.ConsolidatedFormulaVO;
import com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO;
import com.jiuqi.gcreport.consolidatedsystem.vo.subject.ConsolidatedSubjectVO;
import com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO;
import com.jiuqi.gcreport.unionrule.dao.UnionRuleDao;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.entity.UnionRuleEO;
import com.jiuqi.gcreport.unionrule.service.UnionRuleService;
import com.jiuqi.gcreport.unionrule.util.UnionRuleConverter;
import com.jiuqi.np.definition.internal.util.SpringUtil;
import com.jiuqi.va.paramsync.domain.VaParamTransferBusinessNode;
import com.jiuqi.va.paramsync.domain.VaParamTransferCategory;
import com.jiuqi.va.paramsync.domain.VaParamTransferFolderNode;
import com.jiuqi.va.paramsync.intf.VaParamTransferModuleIntf;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ConsolidataSystemTransferModule
extends VaParamTransferModuleIntf {
    private static final Logger logger = LoggerFactory.getLogger(ConsolidataSystemTransferModule.class);
    private static final String MODULE_NAME_CONSOLIDATA_SYSTEM = "MODULE_CONSOLIDATA_SYSTEM";
    private static final String MODULE_ID_CONSOLIDATA_SYSTEM = "MODULE_ID_CONSOLIDATA_SYSTEM";
    private static final String MODULE_TITLE_CONSOLIDATA_SYSTEM = "\u5408\u5e76\u4f53\u7cfb";
    private static final String CATEGORY_NAME_CONSOLIDATA_SYSTEM = "CATEGORY_CONSOLIDATA_SYSTEM";
    private static final String NODE_ID_PLACEHOLDER = "##";
    private static final String NODE_ID_TEMPLATE = "GCCONSOLIDATA##";
    private static final String SYSTEM = "system";
    private static final String INPUTDATASCHEME = "inputDataScheme";
    private static final String SUBJECTS = "subjects";
    private static final String TASKS = "tasks";
    private static final String OPTIONS = "options";
    private static final String FORMULAS = "formulas";
    private static final String RULES = "rules";
    @Autowired
    private ConsolidatedSystemService systemService;
    @Autowired
    private ConsolidatedOptionService optionService;
    @Autowired
    private ConsolidatedTaskService taskService;
    @Autowired
    private UnionRuleDao ruleDao;
    @Autowired
    private ConsolidatedSubjectService subjectService;
    @Autowired
    private ConsolidatedSubjectUIService subjectUIService;
    @Autowired
    private ConsolidatedFormulaService formulaService;
    @Autowired
    private UnionRuleService ruleService;
    @Autowired
    private InputDataSchemeCacheService inputDataSchemeCacheService;
    @Autowired
    private ApplicationContext applicationContext;

    public String getName() {
        return MODULE_NAME_CONSOLIDATA_SYSTEM;
    }

    public String getTitle() {
        return MODULE_TITLE_CONSOLIDATA_SYSTEM;
    }

    public String getModuleId() {
        return MODULE_ID_CONSOLIDATA_SYSTEM;
    }

    public List<VaParamTransferCategory> getCategorys() {
        ArrayList<VaParamTransferCategory> categories = new ArrayList<VaParamTransferCategory>();
        VaParamTransferCategory category = new VaParamTransferCategory();
        category.setName(CATEGORY_NAME_CONSOLIDATA_SYSTEM);
        category.setTitle(MODULE_TITLE_CONSOLIDATA_SYSTEM);
        category.setSupportExport(true);
        category.setSupportExportData(false);
        categories.add(category);
        return categories;
    }

    public List<VaParamTransferFolderNode> getFolderNodes(String category, String nodeId) {
        return null;
    }

    public VaParamTransferFolderNode getFolderNode(String category, String nodeId) {
        return super.getFolderNode(category, nodeId);
    }

    public List<VaParamTransferBusinessNode> getBusinessNodes(String category, String nodeId) {
        ArrayList<VaParamTransferBusinessNode> nodes = new ArrayList<VaParamTransferBusinessNode>();
        if (!category.equals(CATEGORY_NAME_CONSOLIDATA_SYSTEM)) {
            return nodes;
        }
        List<ConsolidatedSystemEO> systemEOList = this.systemService.getConsolidatedSystemEOS();
        for (ConsolidatedSystemEO systemEO : systemEOList) {
            VaParamTransferBusinessNode node = new VaParamTransferBusinessNode();
            String id = NODE_ID_TEMPLATE + systemEO.getId();
            node.setId(id);
            node.setName(systemEO.getSystemName());
            node.setTitle(systemEO.getSystemName());
            nodes.add(node);
        }
        return nodes;
    }

    public VaParamTransferBusinessNode getBusinessNode(String category, String nodeId) {
        String systemId = nodeId.substring(NODE_ID_TEMPLATE.length());
        ConsolidatedSystemEO systemEO = this.systemService.getConsolidatedSystemEO(systemId);
        if (systemEO == null) {
            return null;
        }
        VaParamTransferBusinessNode node = new VaParamTransferBusinessNode();
        node.setId(nodeId);
        node.setFolderId(nodeId);
        node.setName(systemEO.getId());
        node.setTitle(systemEO.getSystemName());
        node.setType("GCCONSOLIDATA");
        node.setTypeTitle(MODULE_TITLE_CONSOLIDATA_SYSTEM);
        return node;
    }

    public List<VaParamTransferFolderNode> getPathFolders(String category, String nodeId) {
        if (!nodeId.startsWith(NODE_ID_TEMPLATE)) {
            return null;
        }
        ArrayList<VaParamTransferFolderNode> folders = new ArrayList<VaParamTransferFolderNode>();
        String systemId = nodeId.substring(NODE_ID_TEMPLATE.length());
        ConsolidatedSystemEO systemEO = this.systemService.getConsolidatedSystemEO(systemId);
        VaParamTransferFolderNode node = new VaParamTransferFolderNode();
        node.setId(nodeId);
        node.setName(systemEO.getSystemName());
        node.setTitle(systemEO.getSystemName());
        folders.add(node);
        return folders;
    }

    public void importModelInfo(String category, String info) {
        List unionRuleDTOList;
        List formulaVOS;
        ConsolidatedOptionVO optionVO;
        List taskVOList;
        Map infoMap = (Map)JsonUtils.readValue((String)info, Map.class);
        ConsolidatedSystemVO systemVO = (ConsolidatedSystemVO)JsonUtils.readValue((String)((String)infoMap.get(SYSTEM)), ConsolidatedSystemVO.class);
        String systemId = systemVO.getId();
        ConsolidatedSystemEO oldSystem = this.systemService.getConsolidatedSystemEO(systemId);
        if (oldSystem != null) {
            systemVO.setEditFlag(Boolean.valueOf(true));
        } else {
            systemVO.setEditFlag(Boolean.valueOf(false));
        }
        this.systemService.addConsolidatedSystem(systemVO);
        InputDataSchemeVO inputDataSchemeVO = (InputDataSchemeVO)JsonUtils.readValue((String)((String)infoMap.get(INPUTDATASCHEME)), InputDataSchemeVO.class);
        InputDataSchemeService inputDataSchemeService = (InputDataSchemeService)SpringUtil.getBean(InputDataSchemeService.class);
        inputDataSchemeService.createInputDataScheme(inputDataSchemeVO);
        List subjectEOList = (List)JsonUtils.readValue((String)((String)infoMap.get(SUBJECTS)), (TypeReference)new TypeReference<List<ConsolidatedSubjectEO>>(){});
        if (!CollectionUtils.isEmpty((Collection)subjectEOList)) {
            List<ConsolidatedSubjectVO> subjectVOList = subjectEOList.stream().map(SubjectConvertUtil::convertEO2VO).collect(Collectors.toList());
            this.subjectUIService.saveSubjects(subjectVOList);
        }
        if (!CollectionUtils.isEmpty((Collection)(taskVOList = (List)JsonUtils.readValue((String)((String)infoMap.get(TASKS)), (TypeReference)new TypeReference<List<ConsolidatedTaskVO>>(){})))) {
            this.taskService.bindConsolidatedTask(taskVOList);
        }
        if ((optionVO = (ConsolidatedOptionVO)JsonUtils.readValue((String)((String)infoMap.get(OPTIONS)), ConsolidatedOptionVO.class)) != null) {
            this.optionService.saveOptionData(systemId, optionVO);
        }
        if (!CollectionUtils.isEmpty((Collection)(formulaVOS = (List)JsonUtils.readValue((String)((String)infoMap.get(FORMULAS)), (TypeReference)new TypeReference<List<ConsolidatedFormulaVO>>(){})))) {
            this.formulaService.saveConsFormula(formulaVOS);
        }
        if (!CollectionUtils.isEmpty((Collection)(unionRuleDTOList = (List)JsonUtils.readValue((String)((String)infoMap.get(RULES)), (TypeReference)new TypeReference<List<Map<String, Object>>>(){})))) {
            this.ruleService.importRule(systemId, unionRuleDTOList);
        }
    }

    public String getExportModelInfo(String category, String nodeId) {
        if (!nodeId.startsWith(NODE_ID_TEMPLATE)) {
            return null;
        }
        HashMap<String, String> info = new HashMap<String, String>();
        String systemId = nodeId.substring(NODE_ID_TEMPLATE.length());
        ConsolidatedSystemVO systemEO = this.systemService.getConsolidatedSystemVO(systemId);
        if (systemEO == null) {
            logger.error("id\u4e3a\uff1a" + systemId + "\u7684\u5408\u5e76\u4f53\u7cfb\u4e0d\u5b58\u5728");
            throw new BusinessRuntimeException("id\u4e3a\uff1a" + systemId + "\u7684\u5408\u5e76\u4f53\u7cfb\u4e0d\u5b58\u5728");
        }
        info.put(SYSTEM, JsonUtils.writeValueAsString((Object)systemEO));
        String dataSchemeKey = systemEO.getDataSchemeKey();
        InputDataSchemeVO inputDataScheme = this.inputDataSchemeCacheService.getInputDataSchemeByDataSchemeKey(dataSchemeKey);
        info.put(INPUTDATASCHEME, JsonUtils.writeValueAsString((Object)inputDataScheme));
        List<ConsolidatedSubjectEO> subjectEOList = this.subjectService.listAllSubjectsBySystemId(systemId);
        info.put(SUBJECTS, JsonUtils.writeValueAsString(subjectEOList));
        List<ConsolidatedTaskVO> taskVOList = this.taskService.getConsolidatedTasks(systemEO.getId());
        info.put(TASKS, JsonUtils.writeValueAsString(taskVOList));
        ConsolidatedOptionVO optionVO = this.optionService.getOptionData(systemEO.getId());
        info.put(OPTIONS, JsonUtils.writeValueAsString((Object)optionVO));
        List<ConsolidatedFormulaVO> formulaVOS = this.formulaService.listConsFormulas(systemId);
        info.put(FORMULAS, JsonUtils.writeValueAsString(formulaVOS));
        List<UnionRuleEO> rulesWithGroup = this.ruleDao.findRuleListByReportSystemIdWithGroup(systemEO.getId());
        ArrayList<AbstractUnionRule> unionRuleDTOList = new ArrayList<AbstractUnionRule>();
        for (UnionRuleEO unionRuleEO : rulesWithGroup) {
            AbstractUnionRule unionRuleDTO = UnionRuleConverter.convert(unionRuleEO);
            unionRuleDTOList.add(unionRuleDTO);
        }
        info.put(RULES, JsonUtils.writeValueAsString(unionRuleDTOList));
        return JsonUtils.writeValueAsString(info);
    }
}

