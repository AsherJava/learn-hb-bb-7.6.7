/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.Module
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.module.SimpleModule
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.basedata.api.vo.BaseDataVO
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.common.util.NrTool
 *  com.jiuqi.gcreport.common.util.UUIDOrderUtils
 *  com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO
 *  com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskCacheService
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.samecontrol.enums.SameCtrlZbAttrEnum
 *  com.jiuqi.gcreport.samecontrol.vo.samectrlsetting.SameCtrlChagSettingBaseDataVO
 *  com.jiuqi.gcreport.samecontrol.vo.samectrlsetting.SameCtrlChagSettingOptionVO
 *  com.jiuqi.gcreport.samecontrol.vo.samectrlsetting.SameCtrlChagSettingZbAttributeVO
 *  com.jiuqi.gcreport.samecontrol.vo.samectrlsetting.SameCtrlChagSubjectMapVO
 *  com.jiuqi.gcreport.samecontrol.vo.samectrlsetting.TaskAndSchemeMapping
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.BigDataDefine
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.GridCellData
 *  com.jiuqi.nvwa.grid2.GridEnums
 *  com.jiuqi.nvwa.grid2.GridEnums$DataType
 *  com.jiuqi.nvwa.grid2.json.Grid2DataSerialize
 *  com.jiuqi.nvwa.grid2.json.GridCellDataSerialize
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.samecontrol.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.basedata.api.vo.BaseDataVO;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.common.util.NrTool;
import com.jiuqi.gcreport.common.util.UUIDOrderUtils;
import com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskCacheService;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.samecontrol.dao.SameCtrlChgOptionDao;
import com.jiuqi.gcreport.samecontrol.dao.SameCtrlChgSettingSchemeMappingDao;
import com.jiuqi.gcreport.samecontrol.dao.SameCtrlChgSettingSubjectMapDao;
import com.jiuqi.gcreport.samecontrol.dao.SameCtrlChgSettingZbAttrDao;
import com.jiuqi.gcreport.samecontrol.entity.SameCtrlChgSettingOptionEO;
import com.jiuqi.gcreport.samecontrol.entity.SameCtrlChgSettingSchemeMappingEO;
import com.jiuqi.gcreport.samecontrol.entity.SameCtrlChgSettingSubjectMapEO;
import com.jiuqi.gcreport.samecontrol.entity.SameCtrlChgSettingZbAttrEO;
import com.jiuqi.gcreport.samecontrol.enums.SameCtrlZbAttrEnum;
import com.jiuqi.gcreport.samecontrol.service.SameCtrlChgSettingService;
import com.jiuqi.gcreport.samecontrol.vo.samectrlsetting.SameCtrlChagSettingBaseDataVO;
import com.jiuqi.gcreport.samecontrol.vo.samectrlsetting.SameCtrlChagSettingOptionVO;
import com.jiuqi.gcreport.samecontrol.vo.samectrlsetting.SameCtrlChagSettingZbAttributeVO;
import com.jiuqi.gcreport.samecontrol.vo.samectrlsetting.SameCtrlChagSubjectMapVO;
import com.jiuqi.gcreport.samecontrol.vo.samectrlsetting.TaskAndSchemeMapping;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.BigDataDefine;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.GridCellData;
import com.jiuqi.nvwa.grid2.GridEnums;
import com.jiuqi.nvwa.grid2.json.Grid2DataSerialize;
import com.jiuqi.nvwa.grid2.json.GridCellDataSerialize;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SameCtrlChgSettingServiceImpl
implements SameCtrlChgSettingService {
    private static final Logger log = LoggerFactory.getLogger(SameCtrlChgSettingServiceImpl.class);
    @Autowired
    private SameCtrlChgOptionDao sameCtrlChgOptionDao;
    @Autowired
    private SameCtrlChgSettingSubjectMapDao subjectMapDao;
    @Autowired
    private SameCtrlChgSettingZbAttrDao zbAttrDao;
    @Autowired
    private ConsolidatedSubjectService subjectService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private SameCtrlChgSettingSchemeMappingDao schemeMappingDao;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private ConsolidatedTaskService consolidatedTaskService;

    @Override
    public void saveOptionData(SameCtrlChagSettingOptionVO optionVO) {
        SameCtrlChgSettingOptionEO optionData = this.sameCtrlChgOptionDao.getOptionByTaskAndShcemeId(optionVO.getTaskId(), optionVO.getSchemeId());
        if (optionData != null) {
            this.setOptions(optionVO, optionData);
            this.sameCtrlChgOptionDao.update((BaseEntity)optionData);
        } else {
            optionData = new SameCtrlChgSettingOptionEO();
            optionData.setId(UUIDOrderUtils.newUUIDStr());
            optionData.setTaskId(optionVO.getTaskId());
            optionData.setSchemeId(optionVO.getSchemeId());
            this.setOptions(optionVO, optionData);
            this.sameCtrlChgOptionDao.save(optionData);
        }
    }

    private void setOptions(SameCtrlChagSettingOptionVO optionVO, SameCtrlChgSettingOptionEO optionData) {
        optionData.setUndividendProfitSubjectCode(optionVO.getUndividendProfitSubject() == null ? "" : optionVO.getUndividendProfitSubject().getCode());
        optionData.setNetProfitSubjectCode(optionVO.getNetProfitSubject() == null ? "" : optionVO.getNetProfitSubject().getCode());
        List disposalScenes = optionVO.getDisposalScenes();
        List<Object> disposalSceneCodes = new ArrayList();
        if (disposalScenes != null) {
            disposalSceneCodes = disposalScenes.stream().map(SameCtrlChagSettingBaseDataVO::getCode).collect(Collectors.toList());
        }
        optionData.setDisposalSceneCodes(StringUtils.join((Object[])disposalSceneCodes.toArray(), (String)";"));
        optionData.setEnableSameCtr(Objects.isNull(optionData.getEnableSameCtr()) || optionVO.getEnableSameCtr() == false ? 0 : 1);
        optionData.setEnableInvestDisposeCopy(Boolean.TRUE.equals(optionVO.getEnableInvestDisposeCopy()) ? 1 : 0);
    }

    @Override
    public SameCtrlChagSettingOptionVO getOptionData(String taskId, String schemeId, String systemId) {
        return this.getOptionData(taskId, schemeId, systemId, false);
    }

    private SameCtrlChagSettingOptionVO getOptionData(String taskId, String schemeId, String systemId, boolean isOnlySchemeMapping) {
        SameCtrlChgSettingOptionEO optionData = this.sameCtrlChgOptionDao.getOptionByTaskAndShcemeId(taskId, schemeId);
        SameCtrlChagSettingOptionVO optionVO = new SameCtrlChagSettingOptionVO();
        if (optionData == null) {
            return optionVO;
        }
        ConsolidatedTaskCacheService taskCacheService = (ConsolidatedTaskCacheService)SpringContextUtils.getBean(ConsolidatedTaskCacheService.class);
        if (!isOnlySchemeMapping) {
            SameCtrlChagSettingBaseDataVO netProfitSubject = this.getSameCtrlChagSettingSubjectVO(systemId, optionData.getNetProfitSubjectCode());
            optionVO.setNetProfitSubject(netProfitSubject);
            SameCtrlChagSettingBaseDataVO undividendProfitSubject = this.getSameCtrlChagSettingSubjectVO(systemId, optionData.getUndividendProfitSubjectCode());
            optionVO.setUndividendProfitSubject(undividendProfitSubject);
            List<SameCtrlChagSettingBaseDataVO> disposalScenes = this.listSameCtrlChagSettingDisposalScenes(optionData);
            optionVO.setDisposalScenes(disposalScenes);
            List<SameCtrlChagSettingZbAttributeVO> zbAttributes = this.listSameCtrlChagSettingZbAttributeVOS(taskId, schemeId);
            optionVO.setZbAttributes(zbAttributes);
        }
        List<TaskAndSchemeMapping> taskAndSchemeMappings = this.listTaskAndSchemeMapping(taskId, schemeId, systemId, taskCacheService);
        optionVO.setTaskAndSchemeMappings(taskAndSchemeMappings);
        optionVO.setEnableSameCtr(Boolean.valueOf(!Objects.isNull(optionData.getEnableSameCtr()) && optionData.getEnableSameCtr() != 0));
        optionVO.setEnableInvestDisposeCopy(Boolean.valueOf(!Objects.isNull(optionData.getEnableInvestDisposeCopy()) && optionData.getEnableInvestDisposeCopy() != 0));
        return optionVO;
    }

    private List<SameCtrlChagSettingBaseDataVO> listSameCtrlChagSettingDisposalScenes(SameCtrlChgSettingOptionEO optionData) {
        ArrayList<SameCtrlChagSettingBaseDataVO> baseDataVOList = new ArrayList<SameCtrlChagSettingBaseDataVO>();
        if (optionData.getDisposalSceneCodes() == null) {
            return baseDataVOList;
        }
        List<BaseDataVO> changeRatios = GcBaseDataCenterTool.getInstance().queryBasedataItemsVO("MD_CHANGERATIO");
        List<String> disposalSceneCodes = Arrays.asList(optionData.getDisposalSceneCodes().split(";"));
        changeRatios = changeRatios.stream().filter(changeRatio -> disposalSceneCodes.contains(changeRatio.getCode())).collect(Collectors.toList());
        changeRatios.forEach(changeRatio -> {
            SameCtrlChagSettingBaseDataVO baseDataVO = new SameCtrlChagSettingBaseDataVO();
            baseDataVO.setCode(changeRatio.getCode());
            baseDataVO.setTitle(changeRatio.getTitle());
            baseDataVOList.add(baseDataVO);
        });
        return baseDataVOList;
    }

    @Override
    public List<SameCtrlChagSettingZbAttributeVO> listSameCtrlChagSettingZbAttributeVOS(String taskId, String schemeId) {
        List<SameCtrlChgSettingZbAttrEO> zbAttrEOList = this.zbAttrDao.getOptionZbAttrByTaskAndShcemeId(taskId, schemeId);
        ArrayList<SameCtrlChagSettingZbAttributeVO> zbAttributes = new ArrayList<SameCtrlChagSettingZbAttributeVO>();
        for (SameCtrlChgSettingZbAttrEO zbAttr : zbAttrEOList) {
            try {
                DataLinkDefine dataLinkDefine;
                FieldDefine fieldDefine;
                SameCtrlChagSettingZbAttributeVO vo = new SameCtrlChagSettingZbAttributeVO();
                vo.setTaskId(taskId);
                vo.setSchemeId(schemeId);
                vo.setFormKey(zbAttr.getFormKey());
                if (StringUtils.isEmpty((String)zbAttr.getZbCode()) || zbAttr.getZbCode().contains("\u672a\u77e5") || (fieldDefine = NrTool.queryFieldDefineByZbCode((String)zbAttr.getZbCode())) == null || (dataLinkDefine = NrTool.queryDataLinkDefineByFieldKey((String)zbAttr.getFormKey(), (String)fieldDefine.getKey())) == null) continue;
                vo.setDataLinkKey(dataLinkDefine.getKey());
                vo.setDataRegionKind(zbAttr.getDataRegionKind());
                vo.setZbCode(zbAttr.getZbCode());
                vo.setZbAttribure(zbAttr.getZbAttribure());
                zbAttributes.add(vo);
            }
            catch (Exception e) {
                log.error("\u83b7\u53d6\u6307\u6807\u4ee3\u7801\u5bf9\u5e94\u7684\u5b57\u6bb5\u5f02\u5e38\uff1a" + zbAttr.getZbCode(), e);
            }
        }
        return zbAttributes;
    }

    private SameCtrlChagSettingBaseDataVO getSameCtrlChagSettingSubjectVO(String systemId, String netProfitSubjectCode) {
        SameCtrlChagSettingBaseDataVO subjectVO = new SameCtrlChagSettingBaseDataVO();
        ConsolidatedSubjectEO netProfitSubjectEO = this.subjectService.getSubjectByCode(systemId, netProfitSubjectCode);
        if (netProfitSubjectEO != null) {
            subjectVO.setCode(netProfitSubjectCode);
            subjectVO.setTitle(netProfitSubjectEO.getTitle());
        }
        return subjectVO;
    }

    private List<TaskAndSchemeMapping> listTaskAndSchemeMapping(String taskId, String schemeId, String systemId, ConsolidatedTaskCacheService taskCacheService) {
        ArrayList<TaskAndSchemeMapping> taskAndSchemeMappingList = new ArrayList<TaskAndSchemeMapping>();
        List<SameCtrlChgSettingSubjectMapEO> subjectMappings = this.subjectMapDao.getOptionSubjectMapByTaskAndShcemeId(taskId, schemeId);
        Map<String, List<SameCtrlChgSettingSubjectMapEO>> schemeMappingId2SubjectMapping = subjectMappings.stream().collect(Collectors.groupingBy(SameCtrlChgSettingSubjectMapEO::getSchemeMappingId));
        List<SameCtrlChgSettingSchemeMappingEO> schemeMappingEOList = this.schemeMappingDao.listSchemeMappingByTaskAndShcemeId(taskId, schemeId);
        for (SameCtrlChgSettingSchemeMappingEO schemeMappingEO : schemeMappingEOList) {
            TaskAndSchemeMapping vo = new TaskAndSchemeMapping();
            BeanUtils.copyProperties((Object)schemeMappingEO, vo);
            List<SameCtrlChgSettingSubjectMapEO> subjectMapEOList = schemeMappingId2SubjectMapping.get(schemeMappingEO.getId());
            List<SameCtrlChagSubjectMapVO> subjectMapVOList = this.convertSubjetMappingEO2VO(systemId, schemeMappingEO.getLastYearSchemeId(), subjectMapEOList);
            vo.setSubjectMappings(subjectMapVOList);
            taskAndSchemeMappingList.add(vo);
        }
        return taskAndSchemeMappingList;
    }

    private List<SameCtrlChagSubjectMapVO> convertSubjetMappingEO2VO(String systemId, String historySchemeId, List<SameCtrlChgSettingSubjectMapEO> subjectMappings) {
        ArrayList<SameCtrlChagSubjectMapVO> subjectMappingList = new ArrayList<SameCtrlChagSubjectMapVO>();
        if (CollectionUtils.isEmpty(subjectMappings)) {
            return subjectMappingList;
        }
        List consolidatedTaskVOS = this.consolidatedTaskService.getConsolidatedTasksBySchemeId(historySchemeId);
        String historySystemId = ((ConsolidatedTaskVO)consolidatedTaskVOS.get(0)).getSystemId();
        for (SameCtrlChgSettingSubjectMapEO mapping : subjectMappings) {
            SameCtrlChagSubjectMapVO vo = new SameCtrlChagSubjectMapVO();
            vo.setId(mapping.getId());
            vo.setSchemeMappingId(mapping.getSchemeMappingId());
            String currSubjectCode = mapping.getCurrSubjectCode();
            ConsolidatedSubjectEO currSubject = this.subjectService.getSubjectByCode(systemId, currSubjectCode);
            if (currSubject == null) continue;
            SameCtrlChagSettingBaseDataVO currConsSubject = new SameCtrlChagSettingBaseDataVO();
            currConsSubject.setCode(currSubject.getCode());
            currConsSubject.setTitle(currSubject.getTitle());
            vo.setCurrYearSubject(currConsSubject);
            String historySubjectCodes = mapping.getHistorySubjectCodes();
            List<String> historySubjectCodeList = Arrays.asList(historySubjectCodes.split(";"));
            ArrayList<SameCtrlChagSettingBaseDataVO> historySubjcts = new ArrayList<SameCtrlChagSettingBaseDataVO>();
            for (String subjectCode : historySubjectCodeList) {
                SameCtrlChagSettingBaseDataVO historyConsSubject = new SameCtrlChagSettingBaseDataVO();
                ConsolidatedSubjectEO historyConsSubjectVO = this.subjectService.getSubjectByCode(historySystemId, subjectCode);
                if (historyConsSubjectVO == null) continue;
                historyConsSubject.setCode(historyConsSubjectVO.getCode());
                historyConsSubject.setTitle(historyConsSubjectVO.getTitle());
                historySubjcts.add(historyConsSubject);
            }
            vo.setLastYearSubjects(historySubjcts);
            subjectMappingList.add(vo);
        }
        return subjectMappingList;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void saveZbAttribute(String taskId, String schemeId, List<SameCtrlChagSettingZbAttributeVO> zbAttributes) {
        this.addNewOptionData(taskId, schemeId);
        Map<String, SameCtrlChgSettingZbAttrEO> regionAndZb2ZbAttrEOMap = this.getSameCtrlChgSettingZbAttrEOMap(zbAttributes);
        ArrayList<SameCtrlChgSettingZbAttrEO> insertEOList = new ArrayList<SameCtrlChgSettingZbAttrEO>();
        ArrayList<SameCtrlChgSettingZbAttrEO> updateEOList = new ArrayList<SameCtrlChgSettingZbAttrEO>();
        ArrayList<SameCtrlChgSettingZbAttrEO> deleteEOList = new ArrayList<SameCtrlChgSettingZbAttrEO>();
        for (SameCtrlChagSettingZbAttributeVO zbAttribute : zbAttributes) {
            SameCtrlChgSettingZbAttrEO eo;
            DataLinkDefine dataLinkDefine;
            if (StringUtils.isEmpty((String)zbAttribute.getDataLinkKey()) || (dataLinkDefine = this.runTimeViewController.queryDataLinkDefine(zbAttribute.getDataLinkKey())) == null) continue;
            if (regionAndZb2ZbAttrEOMap.containsKey(dataLinkDefine.getKey())) {
                eo = regionAndZb2ZbAttrEOMap.get(dataLinkDefine.getKey());
                if (SameCtrlZbAttrEnum.NO_ZBATTR.getCode().equals(zbAttribute.getZbAttribure())) {
                    deleteEOList.add(eo);
                    continue;
                }
                eo.setZbAttribure(zbAttribute.getZbAttribure());
                updateEOList.add(eo);
                continue;
            }
            if (SameCtrlZbAttrEnum.NO_ZBATTR.getCode().equals(zbAttribute.getZbAttribure())) continue;
            eo = new SameCtrlChgSettingZbAttrEO();
            DataRegionDefine dataRegionDefine = this.runTimeViewController.queryDataRegionDefine(dataLinkDefine.getRegionKey());
            eo.setDataRegionKind(dataRegionDefine.getRegionKind().getValue());
            eo.setZbAttribure(zbAttribute.getZbAttribure());
            eo.setId(UUIDOrderUtils.newUUIDStr());
            eo.setTaskId(taskId);
            eo.setSchemeId(schemeId);
            eo.setFormKey(zbAttribute.getFormKey());
            eo.setZbCode(zbAttribute.getZbCode());
            insertEOList.add(eo);
        }
        this.zbAttrDao.saveAll(insertEOList);
        this.zbAttrDao.updateAll(updateEOList);
        this.zbAttrDao.deleteBatch(deleteEOList);
    }

    private void addNewOptionData(String taskId, String schemeId) {
        SameCtrlChgSettingOptionEO optionData = this.sameCtrlChgOptionDao.getOptionByTaskAndShcemeId(taskId, schemeId);
        if (optionData == null) {
            optionData = new SameCtrlChgSettingOptionEO();
            optionData.setId(UUIDOrderUtils.newUUIDStr());
            optionData.setTaskId(taskId);
            optionData.setSchemeId(schemeId);
            optionData.setEnableSameCtr(0);
            this.sameCtrlChgOptionDao.save(optionData);
        }
    }

    private Map<String, SameCtrlChgSettingZbAttrEO> getSameCtrlChgSettingZbAttrEOMap(List<SameCtrlChagSettingZbAttributeVO> zbAttributes) {
        List<SameCtrlChgSettingZbAttrEO> currFormZbAttrList = this.zbAttrDao.getOptionZbAttrByFormKey(zbAttributes.get(0).getFormKey());
        HashMap<String, SameCtrlChgSettingZbAttrEO> regionAndZb2ZbAttrEOMap = new HashMap<String, SameCtrlChgSettingZbAttrEO>();
        for (SameCtrlChgSettingZbAttrEO zbAttr : currFormZbAttrList) {
            if (StringUtils.isEmpty((String)zbAttr.getZbCode()) || zbAttr.getZbCode().contains("\u672a\u77e5")) continue;
            try {
                FieldDefine fieldDefine = NrTool.queryFieldDefineByZbCode((String)zbAttr.getZbCode());
                DataLinkDefine dataLinkDefine = NrTool.queryDataLinkDefineByFieldKey((String)zbAttr.getFormKey(), (String)fieldDefine.getKey());
                if (dataLinkDefine == null) continue;
                regionAndZb2ZbAttrEOMap.put(dataLinkDefine.getKey(), zbAttr);
            }
            catch (Exception e) {
                log.error("\u83b7\u53d6\u6307\u6807\u4ee3\u7801\u5bf9\u5e94\u7684\u5b57\u6bb5\u5f02\u5e38\uff1a" + zbAttr.getZbCode(), e);
            }
        }
        return regionAndZb2ZbAttrEOMap;
    }

    private void listSchemeMappings(List<SameCtrlChgSettingSchemeMappingEO> schemeMappingList, List<TaskAndSchemeMapping> schemeMappings, List<SameCtrlChgSettingSchemeMappingEO> insertSchemeMappingList, List<SameCtrlChgSettingSchemeMappingEO> updateSchemeMappingList) {
        Map<String, SameCtrlChgSettingSchemeMappingEO> subjectMapingMap = schemeMappingList.stream().collect(Collectors.toMap(DefaultTableEntity::getId, subject -> subject, (e1, e2) -> e1));
        for (TaskAndSchemeMapping mapping : schemeMappings) {
            SameCtrlChgSettingSchemeMappingEO eo;
            String schemeMappingId;
            if (!StringUtils.isEmpty((String)mapping.getId()) && subjectMapingMap.containsKey(mapping.getId())) {
                schemeMappingId = mapping.getId();
                eo = subjectMapingMap.get(schemeMappingId);
                updateSchemeMappingList.add(eo);
            } else {
                eo = new SameCtrlChgSettingSchemeMappingEO();
                schemeMappingId = UUIDOrderUtils.newUUIDStr();
                insertSchemeMappingList.add(eo);
            }
            BeanUtils.copyProperties(mapping, (Object)eo);
            eo.setId(schemeMappingId);
        }
    }

    @Override
    public void deleteSubjectMapping(List<String> deleteIds) {
        this.subjectMapDao.deleteSubjectByIds(deleteIds);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void saveSubjectMapping(String schemeMappingId, String taskId, String schemeId, List<SameCtrlChagSubjectMapVO> subjectMappings) {
        List<SameCtrlChgSettingSubjectMapEO> schemeMappingEOList = this.subjectMapDao.listSubjectMappingBySchemeMappingId(schemeMappingId);
        ArrayList<SameCtrlChgSettingSubjectMapEO> insertSubjectMappingList = new ArrayList<SameCtrlChgSettingSubjectMapEO>();
        ArrayList<SameCtrlChgSettingSubjectMapEO> updateSubjectMappingList = new ArrayList<SameCtrlChgSettingSubjectMapEO>();
        this.listUpdateAndInsertSubjectMappings(taskId, schemeId, schemeMappingEOList, subjectMappings, insertSubjectMappingList, updateSubjectMappingList);
        Set updateEoIdSet = updateSubjectMappingList.stream().map(DefaultTableEntity::getId).collect(Collectors.toSet());
        List deleteSchemeMappingList = schemeMappingEOList.stream().filter(subjectMap -> !updateEoIdSet.contains(subjectMap.getId())).collect(Collectors.toList());
        this.subjectMapDao.updateAll(updateSubjectMappingList);
        this.subjectMapDao.saveAll(insertSubjectMappingList);
        this.subjectMapDao.deleteBatch(deleteSchemeMappingList);
    }

    private void listUpdateAndInsertSubjectMappings(String taskId, String schemeId, List<SameCtrlChgSettingSubjectMapEO> schemeMappingEOList, List<SameCtrlChagSubjectMapVO> subjectMappings, List<SameCtrlChgSettingSubjectMapEO> insertSubjectMappingList, List<SameCtrlChgSettingSubjectMapEO> updateSubjectMappingList) {
        Map<String, SameCtrlChgSettingSubjectMapEO> subjectMapingMap = schemeMappingEOList.stream().collect(Collectors.toMap(DefaultTableEntity::getId, subject -> subject, (e1, e2) -> e1));
        for (SameCtrlChagSubjectMapVO mapping : subjectMappings) {
            SameCtrlChgSettingSubjectMapEO eo;
            if (!StringUtils.isEmpty((String)mapping.getId()) && subjectMapingMap.containsKey(mapping.getId())) {
                eo = subjectMapingMap.get(mapping.getId());
                updateSubjectMappingList.add(eo);
            } else {
                eo = new SameCtrlChgSettingSubjectMapEO();
                eo.setId(UUIDOrderUtils.newUUIDStr());
                insertSubjectMappingList.add(eo);
            }
            eo.setCurrSubjectCode(mapping.getCurrYearSubject().getCode());
            eo.setTaskId(taskId);
            eo.setSchemeId(schemeId);
            eo.setSchemeMappingId(mapping.getSchemeMappingId());
            List lastYearSubjects = CollectionUtils.isEmpty((Collection)mapping.getLastYearSubjects()) ? new ArrayList() : mapping.getLastYearSubjects();
            List lastYearSubjectCodes = lastYearSubjects.stream().map(SameCtrlChagSettingBaseDataVO::getCode).collect(Collectors.toList());
            eo.setHistorySubjectCodes(StringUtils.join((Object[])lastYearSubjectCodes.toArray(), (String)";"));
        }
    }

    @Override
    public List<SameCtrlChagSubjectMapVO> listSubjectMappings(String schemeMappingId, String systemId) {
        List<SameCtrlChgSettingSubjectMapEO> schemeMappingEOList = this.subjectMapDao.listSubjectMappingBySchemeMappingId(schemeMappingId);
        SameCtrlChgSettingSchemeMappingEO schemeMappingEO = (SameCtrlChgSettingSchemeMappingEO)this.schemeMappingDao.get((Serializable)((Object)schemeMappingId));
        if (CollectionUtils.isEmpty(schemeMappingEOList) || schemeMappingEO == null) {
            return new ArrayList<SameCtrlChagSubjectMapVO>();
        }
        ConsolidatedTaskCacheService taskCacheService = (ConsolidatedTaskCacheService)SpringContextUtils.getBean(ConsolidatedTaskCacheService.class);
        return this.convertSubjetMappingEO2VO(systemId, schemeMappingEO.getLastYearSchemeId(), schemeMappingEOList);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void saveSchemeMapping(String taskId, String schemeId, List<TaskAndSchemeMapping> taskAndSchemeMappings) {
        List<SameCtrlChgSettingSchemeMappingEO> schemeMappingEOList = this.schemeMappingDao.listSchemeMappingByTaskAndShcemeId(taskId, schemeId);
        ArrayList<SameCtrlChgSettingSchemeMappingEO> insertSchemeMappingList = new ArrayList<SameCtrlChgSettingSchemeMappingEO>();
        ArrayList<SameCtrlChgSettingSchemeMappingEO> updateSchemeMappingList = new ArrayList<SameCtrlChgSettingSchemeMappingEO>();
        this.listSchemeMappings(schemeMappingEOList, taskAndSchemeMappings, insertSchemeMappingList, updateSchemeMappingList);
        Set updateEoIdSet = updateSchemeMappingList.stream().map(DefaultTableEntity::getId).collect(Collectors.toSet());
        List deleteSchemeMappingList = schemeMappingEOList.stream().filter(subjectMap -> !updateEoIdSet.contains(subjectMap.getId())).collect(Collectors.toList());
        this.schemeMappingDao.updateAll(updateSchemeMappingList);
        this.schemeMappingDao.saveAll(insertSchemeMappingList);
        this.schemeMappingDao.deleteBatch(deleteSchemeMappingList);
    }

    @Override
    public void deleteSchemeMappingByIds(List<String> deleteIds) {
        this.schemeMappingDao.deleteSchemeMappingByIds(deleteIds);
        this.subjectMapDao.deleteSubjectBySchemeMappingIds(deleteIds);
    }

    @Override
    public List<TaskAndSchemeMapping> listSchemeMappings(String taskId, String schemeId, String systemId) {
        ConsolidatedTaskCacheService taskCacheService = (ConsolidatedTaskCacheService)SpringContextUtils.getBean(ConsolidatedTaskCacheService.class);
        return this.listTaskAndSchemeMapping(taskId, schemeId, systemId, taskCacheService);
    }

    @Override
    public boolean enableSameCtr(String taskId, String schemeId) {
        SameCtrlChgSettingOptionEO optionData = this.sameCtrlChgOptionDao.getOptionByTaskAndShcemeId(taskId, schemeId);
        if (optionData == null) {
            return false;
        }
        return !Objects.isNull(optionData.getEnableSameCtr()) && optionData.getEnableSameCtr() != 0;
    }

    @Override
    public String queryFormDataForSameCtrlSetting(String schemeId, String formKey) {
        try {
            Grid2Data griddata = this.getGridDataByRunTime(formKey);
            List<SameCtrlChgSettingZbAttrEO> zbAttrEOList = this.zbAttrDao.getOptionZbAttrByFormKey(formKey);
            HashMap<String, SameCtrlChgSettingZbAttrEO> zbAttrsMap = new HashMap<String, SameCtrlChgSettingZbAttrEO>();
            for (SameCtrlChgSettingZbAttrEO zbAttr : zbAttrEOList) {
                if (StringUtils.isEmpty((String)zbAttr.getZbCode()) || zbAttr.getZbCode().contains("\u672a\u77e5")) continue;
                try {
                    FieldDefine fieldDefine = NrTool.queryFieldDefineByZbCode((String)zbAttr.getZbCode());
                    DataLinkDefine dataLinkDefine = NrTool.queryDataLinkDefineByFieldKey((String)zbAttr.getFormKey(), (String)fieldDefine.getKey());
                    if (dataLinkDefine == null) continue;
                    zbAttrsMap.put(dataLinkDefine.getKey(), zbAttr);
                }
                catch (Exception e) {
                    log.error("\u83b7\u53d6\u6307\u6807\u4ee3\u7801\u5bf9\u5e94\u7684\u5b57\u6bb5\u5f02\u5e38\uff1a" + zbAttr.getZbCode(), e);
                }
            }
            this.fillZbCode(formKey, griddata, null, zbAttrsMap);
            String result = this.serialize(griddata);
            return result;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    private Grid2Data getGridDataByRunTime(String formKey) {
        BigDataDefine dataDefine = this.runTimeViewController.getReportDataFromForm(formKey);
        Grid2Data gridData = null;
        if (null != dataDefine) {
            if (dataDefine.getData() != null) {
                gridData = Grid2Data.bytesToGrid((byte[])dataDefine.getData());
            } else {
                gridData = new Grid2Data();
                gridData.setRowCount(10);
                gridData.setColumnCount(10);
            }
        }
        return gridData;
    }

    private String serialize(Grid2Data griddata) throws JsonProcessingException {
        SimpleModule module = new SimpleModule();
        module.addSerializer(Grid2Data.class, (JsonSerializer)new Grid2DataSerialize());
        module.addSerializer(GridCellData.class, (JsonSerializer)new GridCellDataSerialize());
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule((Module)module);
        return mapper.writeValueAsString((Object)griddata);
    }

    private void fillZbCode(String formKey, Grid2Data gridData, Consumer<GridCellData> consumer, Map<String, SameCtrlChgSettingZbAttrEO> zbAttrsMap) throws Exception {
        List regions = this.runTimeViewController.getAllRegionsInForm(formKey);
        for (DataRegionDefine region : regions) {
            String regionKey = region.getKey();
            List dataLinks = this.runTimeViewController.getAllLinksInRegion(regionKey);
            for (DataLinkDefine link : dataLinks) {
                GridCellData cellData;
                DataField fieldDefine = this.runtimeDataSchemeService.getDataField(link.getLinkExpression());
                String tableName = "";
                String fieldCode = "\u672a\u77e5";
                boolean isNumberField = true;
                if (fieldDefine != null) {
                    tableName = this.runtimeDataSchemeService.getDataTable(fieldDefine.getDataTableKey()).getCode();
                    fieldCode = fieldDefine.getCode();
                    FieldType fieldType = fieldDefine.getType();
                    if (fieldType != FieldType.FIELD_TYPE_FLOAT && fieldType != FieldType.FIELD_TYPE_INTEGER && fieldType != FieldType.FIELD_TYPE_DECIMAL) {
                        isNumberField = false;
                    }
                }
                if ((cellData = gridData.getGridCellData(link.getPosX(), link.getPosY())) == null) continue;
                cellData.setShowText(tableName + "[" + fieldCode + "]");
                cellData.setHorzAlign(3);
                cellData.setForeGroundColor(255);
                if (null != consumer) {
                    consumer.accept(cellData);
                }
                cellData.setEditable(true);
                if (isNumberField) {
                    cellData.setDataType(GridEnums.getIntValue((Enum)GridEnums.DataType.Number));
                }
                if (zbAttrsMap == null) continue;
                cellData.setShowText(null);
                cellData.setEditText(tableName + "[" + fieldCode + "];" + link.getKey());
                if (!zbAttrsMap.containsKey(link.getKey())) continue;
                cellData.setBackGroundColor(Integer.parseInt("AFEEEE", 16));
                SameCtrlChgSettingZbAttrEO zbAttrEO = zbAttrsMap.get(link.getKey());
                cellData.setShowText(SameCtrlZbAttrEnum.getTitleByCode((String)zbAttrEO.getZbAttribure()));
            }
        }
        if (null == gridData) {
            gridData = new Grid2Data();
            gridData.insertRows(0, 1, -1);
            gridData.insertColumns(0, 1);
            gridData.setRowHidden(0, true);
            gridData.setColumnHidden(0, true);
        }
    }
}

