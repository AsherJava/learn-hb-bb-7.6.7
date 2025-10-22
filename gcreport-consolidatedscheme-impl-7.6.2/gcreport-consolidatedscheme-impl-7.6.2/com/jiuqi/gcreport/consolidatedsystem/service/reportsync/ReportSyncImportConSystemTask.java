/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.expimp.dataimport.service.ImportDispatchService
 *  com.jiuqi.common.expimp.progress.common.ProgressDataImpl
 *  com.jiuqi.common.reportsync.dto.ReportSyncExportTaskContext
 *  com.jiuqi.common.reportsync.enums.ReportDataSyncTypeEnum
 *  com.jiuqi.common.reportsync.task.IReportSyncImportTask
 *  com.jiuqi.common.reportsync.util.CommonReportUtil
 *  com.jiuqi.gcreport.common.util.UUIDOrderUtils
 *  com.jiuqi.gcreport.consolidatedsystem.vo.InputDataSchemeVO
 *  com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO
 *  com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.gcreport.unionrule.constant.RuleConst
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.dto.UnionGroupRuleDTO
 *  com.jiuqi.gcreport.unionrule.vo.UnionRuleVO
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.definition.internal.util.SpringUtil
 *  org.springframework.mock.web.MockMultipartFile
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.gcreport.consolidatedsystem.service.reportsync;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.expimp.dataimport.service.ImportDispatchService;
import com.jiuqi.common.expimp.progress.common.ProgressDataImpl;
import com.jiuqi.common.reportsync.dto.ReportSyncExportTaskContext;
import com.jiuqi.common.reportsync.enums.ReportDataSyncTypeEnum;
import com.jiuqi.common.reportsync.task.IReportSyncImportTask;
import com.jiuqi.common.reportsync.util.CommonReportUtil;
import com.jiuqi.gcreport.common.util.UUIDOrderUtils;
import com.jiuqi.gcreport.consolidatedsystem.dao.ConsolidatedSystemDao;
import com.jiuqi.gcreport.consolidatedsystem.entity.ConsolidatedSystemEO;
import com.jiuqi.gcreport.consolidatedsystem.service.ConsolidatedSystemService;
import com.jiuqi.gcreport.consolidatedsystem.service.InputDataSchemeService;
import com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.vo.InputDataSchemeVO;
import com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO;
import com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.unionrule.base.UnionRuleManager;
import com.jiuqi.gcreport.unionrule.cache.UnionRuleChangedEvent;
import com.jiuqi.gcreport.unionrule.constant.RuleConst;
import com.jiuqi.gcreport.unionrule.dao.UnionRuleDao;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.dto.UnionGroupRuleDTO;
import com.jiuqi.gcreport.unionrule.entity.UnionRuleEO;
import com.jiuqi.gcreport.unionrule.service.UnionRuleService;
import com.jiuqi.gcreport.unionrule.util.UnionRuleConverter;
import com.jiuqi.gcreport.unionrule.util.UnionRuleUtils;
import com.jiuqi.gcreport.unionrule.vo.UnionRuleVO;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.definition.internal.util.SpringUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ReportSyncImportConSystemTask
implements IReportSyncImportTask {
    @Autowired
    private ConsolidatedSystemService systemService;
    @Autowired
    private ConsolidatedSystemDao consolidatedSystemDao;
    @Autowired
    private ConsolidatedTaskService taskService;
    @Autowired
    private ConsolidatedOptionService optionService;
    @Autowired
    private UnionRuleDao ruleDao;
    @Autowired
    private UnionRuleService ruleService;
    @Autowired
    private ApplicationContext applicationContext;

    public List<String> exec(ReportSyncExportTaskContext reportSyncExportTaskContext) {
        File rootFolder = reportSyncExportTaskContext.getRootFolder();
        ProgressDataImpl progressData = reportSyncExportTaskContext.getProgressData();
        String systemId = this.importConSystem(rootFolder);
        ArrayList<String> msgList = new ArrayList<String>();
        if (StringUtils.isEmpty((String)systemId)) {
            return msgList;
        }
        msgList.add(this.importInputDataScheme(rootFolder));
        msgList.add(this.importSubject(rootFolder, systemId));
        msgList.add(this.importFormScheme(rootFolder, systemId));
        msgList.add(this.importOption(rootFolder, systemId));
        msgList.add(this.importRule(rootFolder, systemId));
        return (List)CommonReportUtil.appendImportMsgIfEmpty(msgList);
    }

    private String importRule(File rootFolder, String systemId) {
        File ruleFileNew;
        File ruleFile = new File(rootFolder.getPath() + "/GC-unionrule");
        if (ruleFile.exists()) {
            this.ruleDao.batchDeleteByReportSysId(systemId);
            CommonReportUtil.readBase64Db((String)(rootFolder.getPath() + "/GC-unionrule"), (String)"GC_UNIONRULE");
        }
        if ((ruleFileNew = new File(rootFolder.getPath() + "/GC-unionruleNew")).exists()) {
            try {
                FileInputStream input = new FileInputStream(ruleFileNew);
                MockMultipartFile multipartFile = new MockMultipartFile(ruleFileNew.getName().concat("temp"), ruleFileNew.getName(), "text/plain", (InputStream)input);
                this.uploadRuleFile(systemId, (MultipartFile)multipartFile);
            }
            catch (Exception e) {
                throw new BusinessRuntimeException("\u5408\u5e76\u89c4\u5219\u5bfc\u5165\u5931\u8d25" + e.getMessage());
            }
        }
        return null;
    }

    public void uploadRuleFile(String reportSystemId, MultipartFile multipartFile) {
        byte[] bytes;
        try {
            bytes = multipartFile.getBytes();
        }
        catch (IOException e) {
            throw new BusinessRuntimeException("\u65e0\u6cd5\u89e3\u6790\u5bfc\u5165\u6587\u4ef6\u3002", (Throwable)e);
        }
        if (bytes.length == 0) {
            return;
        }
        ArrayList<AbstractUnionRule> unionRuleDTOList = new ArrayList<AbstractUnionRule>();
        List importJsonList = null;
        importJsonList = (List)JsonUtils.readValue((byte[])bytes, (TypeReference)new TypeReference<List<Map<String, Object>>>(){});
        for (Map importJsonListMap : importJsonList) {
            AbstractUnionRule unionRuleDTO = this.convertMapToDTO(importJsonListMap);
            unionRuleDTOList.add(unionRuleDTO);
        }
        Map<String, List<AbstractUnionRule>> parentMap = unionRuleDTOList.stream().collect(Collectors.groupingBy(AbstractUnionRule::getParentId));
        List<AbstractUnionRule> rootList = parentMap.get(RuleConst.ROOT_PARENT_ID);
        AbstractUnionRule ruleRoot = rootList.get(0);
        List<UnionRuleVO> ruleTree = this.ruleService.selectRuleTreeByReportSystem(reportSystemId, false);
        String importRootId = ruleRoot.getId();
        List<AbstractUnionRule> importChildren = parentMap.get(importRootId);
        if (CollectionUtils.isEmpty(ruleTree)) {
            this.ruleService.importRule(ruleRoot);
        } else {
            String oldRootId = ruleTree.get(0).getId();
            for (AbstractUnionRule rule : importChildren) {
                rule.setParentId(oldRootId);
            }
        }
        this.cycleCheckChildren(importChildren, parentMap);
        NpContext context = NpContextHolder.getContext();
        this.applicationContext.publishEvent(new UnionRuleChangedEvent(new UnionRuleChangedEvent.UnionRuleChangedInfo(reportSystemId), context));
    }

    private String importOption(File rootFolder, String systemId) {
        File optionFile = new File(rootFolder.getPath() + "/GC-consoption");
        if (!optionFile.exists()) {
            return null;
        }
        String json = CommonReportUtil.readJsonFile((String)optionFile.getPath());
        ConsolidatedOptionVO optionVO = (ConsolidatedOptionVO)JsonUtils.readValue((String)json, ConsolidatedOptionVO.class);
        this.optionService.saveOptionData(systemId, optionVO);
        return null;
    }

    private String importFormScheme(File rootFolder, String systemId) {
        File consTaskFile = new File(rootFolder.getPath() + "/GC-constask");
        if (!consTaskFile.exists()) {
            return null;
        }
        String json = CommonReportUtil.readJsonFile((String)consTaskFile.getPath());
        List taskVOS = (List)JsonUtils.readValue((String)json, (TypeReference)new TypeReference<List<ConsolidatedTaskVO>>(){});
        this.taskService.unbindBySystemId(systemId);
        this.taskService.bindConsolidatedTask(taskVOS);
        return null;
    }

    private String importSubject(File rootFolder, String systemId) {
        if (StringUtils.isEmpty((String)systemId)) {
            return null;
        }
        Workbook workbook = CommonReportUtil.readWorkBook((String)(rootFolder.getPath() + "/GC-conssubject"));
        if (null == workbook) {
            return null;
        }
        Object msg = ((ImportDispatchService)SpringContextUtils.getBean(ImportDispatchService.class)).dataImport(workbook, "SystemDataImportExecutor", UUIDOrderUtils.newUUIDStr(), "{\"systemId\":\"" + systemId + "\"}");
        return String.valueOf(msg);
    }

    private String importConSystem(File rootFolder) {
        File consSystemFile = new File(rootFolder + "/GC-conssystem");
        if (!consSystemFile.exists()) {
            return null;
        }
        String json = CommonReportUtil.readJsonFile((String)consSystemFile.getPath());
        ConsolidatedSystemEO newSystem = (ConsolidatedSystemEO)((Object)JsonUtils.readValue((String)json, ConsolidatedSystemEO.class));
        ConsolidatedSystemEO oldSystem = this.systemService.getConsolidatedSystemEO(newSystem.getId());
        if (null == oldSystem) {
            this.systemService.save(newSystem);
        } else if (!newSystem.getSystemName().equals(oldSystem.getSystemName())) {
            this.consolidatedSystemDao.delete((BaseEntity)newSystem);
            this.systemService.save(newSystem);
        }
        return newSystem.getId();
    }

    private String importInputDataScheme(File rootFolder) {
        String inputDataSchemeJson = CommonReportUtil.readJsonFile((String)(rootFolder + "/inputDataSchme.txt"));
        if (CommonReportUtil.isEmptyJson((String)inputDataSchemeJson)) {
            return "\u6ca1\u6709\u627e\u5230\u6570\u636e\u65b9\u6848\u548c\u5185\u90e8\u8868\u7684\u6620\u5c04\u5173\u7cfb\u8868";
        }
        InputDataSchemeVO inputDataSchemeVO = (InputDataSchemeVO)JsonUtils.readValue((String)inputDataSchemeJson, InputDataSchemeVO.class);
        InputDataSchemeService inputDataSchemeService = (InputDataSchemeService)SpringUtil.getBean(InputDataSchemeService.class);
        inputDataSchemeService.createInputDataScheme(inputDataSchemeVO);
        return null;
    }

    public AbstractUnionRule convertMapToDTO(Map<String, Object> importJsonListMap) {
        if (!Boolean.TRUE.equals(importJsonListMap.get("leafFlag"))) {
            return (AbstractUnionRule)JsonUtils.readValue((String)JsonUtils.writeValueAsString(importJsonListMap), UnionGroupRuleDTO.class);
        }
        Object ruleType = importJsonListMap.get("ruleType");
        UnionRuleManager ruleManager = UnionRuleUtils.getManagerByRuleTypeCode(String.valueOf(ruleType));
        return ruleManager.getRuleHandler().convertMapToDTO(importJsonListMap);
    }

    private void cycleCheckChildren(List<AbstractUnionRule> importChildren, Map<String, List<AbstractUnionRule>> parentMap) {
        if (CollectionUtils.isEmpty(importChildren)) {
            return;
        }
        for (AbstractUnionRule ruleDTO : importChildren) {
            String impId = ruleDTO.getId();
            UnionRuleEO ruleEO = UnionRuleConverter.convertUnionRuleDTOToEO(ruleDTO);
            BeanUtils.copyProperties(ruleDTO, (Object)ruleEO);
            ruleEO.setLeafFlag(Boolean.TRUE.equals(ruleDTO.getLeafFlag()) ? 1 : 0);
            ruleEO.setStartFlag(Boolean.TRUE.equals(ruleDTO.getStartFlag()) ? 1 : 0);
            ruleEO.setInitTypeFlag(Boolean.TRUE.equals(ruleDTO.getInitTypeFlag()) ? 1 : 0);
            ruleEO.setEnableToleranceFlag(Boolean.TRUE.equals(ruleDTO.getEnableToleranceFlag()) ? 1 : 0);
            if (null == this.ruleDao.get((Serializable)((Object)impId))) {
                this.ruleDao.save(ruleEO);
            } else {
                this.ruleDao.update((BaseEntity)ruleEO);
            }
            List<AbstractUnionRule> list = parentMap.get(impId);
            this.cycleCheckChildren(list, parentMap);
        }
    }

    public String funcTitle() {
        return "\u5408\u5e76\u4f53\u7cfb";
    }

    public ReportDataSyncTypeEnum syncType() {
        return ReportDataSyncTypeEnum.PARAM;
    }
}

