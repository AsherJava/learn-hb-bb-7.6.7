/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.common.expimp.dataimport.excel.common.ImportExcelSheet
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseDataDO
 *  com.jiuqi.gcreport.common.util.UUIDOrderUtils
 *  com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO
 *  com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO
 *  com.jiuqi.gcreport.rewritesetting.vo.FloatRegionTreeVO
 *  com.jiuqi.gcreport.rewritesetting.vo.RewriteFieldInfoVO
 *  com.jiuqi.gcreport.rewritesetting.vo.RewriteFieldMappingVO
 *  com.jiuqi.gcreport.rewritesetting.vo.RewriteSettingVO
 *  com.jiuqi.gcreport.rewritesetting.vo.RewriteSubjectSettingVO
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  org.apache.commons.lang3.StringUtils
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.rewritesetting.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.common.expimp.dataimport.excel.common.ImportExcelSheet;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseDataDO;
import com.jiuqi.gcreport.common.util.UUIDOrderUtils;
import com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO;
import com.jiuqi.gcreport.rewritesetting.consts.RewriteSettingConst;
import com.jiuqi.gcreport.rewritesetting.dao.RewriteSettingDao;
import com.jiuqi.gcreport.rewritesetting.dao.RewriteSubjectSettingDao;
import com.jiuqi.gcreport.rewritesetting.dto.RewriteFieldMappingDTO;
import com.jiuqi.gcreport.rewritesetting.entity.RewriteSettingEO;
import com.jiuqi.gcreport.rewritesetting.entity.RewriteSubjectSettingEO;
import com.jiuqi.gcreport.rewritesetting.service.RewriteSettingService;
import com.jiuqi.gcreport.rewritesetting.vo.FloatRegionTreeVO;
import com.jiuqi.gcreport.rewritesetting.vo.RewriteFieldInfoVO;
import com.jiuqi.gcreport.rewritesetting.vo.RewriteFieldMappingVO;
import com.jiuqi.gcreport.rewritesetting.vo.RewriteSettingVO;
import com.jiuqi.gcreport.rewritesetting.vo.RewriteSubjectSettingVO;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

@Service
public class RewriteSettingServiceImpl
implements RewriteSettingService {
    public static final Logger logger = LoggerFactory.getLogger(RewriteSettingServiceImpl.class);
    @Autowired
    private RewriteSettingDao rewriteSettingDao;
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Autowired
    private IDataDefinitionRuntimeController iDataDefinitionRuntimeController;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private ConsolidatedTaskService taskService;
    @Autowired
    private ConsolidatedSubjectService subjectService;
    @Autowired
    private RewriteSubjectSettingDao rewriteSubjectSettingDao;
    @Autowired
    private IRuntimeDataSchemeService iRuntimeDataSchemeService;

    @Override
    public void deleteRewriteSetting(List<String> ids) {
        this.rewriteSettingDao.deleteRewriteSetting(ids);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void saveRewriteSetting(RewriteSettingVO rewriteSettingVO) {
        String groupId;
        List subjectVos = rewriteSettingVO.getSubjectVos();
        ArrayList<RewriteSettingEO> rewriteSettingEOS = new ArrayList<RewriteSettingEO>();
        String string = groupId = StringUtils.isEmpty((CharSequence)rewriteSettingVO.getRewSetGroupId()) ? UUIDOrderUtils.newUUIDStr() : rewriteSettingVO.getRewSetGroupId();
        if (!StringUtils.isEmpty((CharSequence)rewriteSettingVO.getRewSetGroupId())) {
            this.rewriteSettingDao.deleteRewriteSettingByGroupId(groupId);
        }
        ArrayList<RewriteFieldMappingDTO> fieldMappingDTOS = new ArrayList<RewriteFieldMappingDTO>();
        if (!CollectionUtils.isEmpty(rewriteSettingVO.getFieldMapping())) {
            List fieldMapping = rewriteSettingVO.getFieldMapping();
            for (RewriteFieldMappingVO vo : fieldMapping) {
                RewriteFieldMappingDTO dto = new RewriteFieldMappingDTO();
                BeanUtils.copyProperties(vo, dto);
                fieldMappingDTOS.add(dto);
            }
        }
        for (GcBaseDataDO subject : subjectVos) {
            RewriteSettingEO eo = new RewriteSettingEO();
            BeanUtils.copyProperties(rewriteSettingVO, (Object)eo);
            eo.setId(UUIDOrderUtils.newUUIDStr());
            eo.setOrdinal(ConverterUtils.getAsDouble((Object)OrderGenerator.newOrderID()));
            eo.setRewSetGroupId(groupId);
            eo.setSubjectCode(subject.getCode());
            eo.setFieldMapping(CollectionUtils.isEmpty(fieldMappingDTOS) ? null : JsonUtils.writeValueAsString(fieldMappingDTOS));
            rewriteSettingEOS.add(eo);
        }
        this.rewriteSettingDao.addBatch(rewriteSettingEOS);
    }

    @Override
    public List<RewriteSettingVO> queryRewriteSettings(String schemeId) {
        List<RewriteSettingEO> rewriteSettings = this.rewriteSettingDao.queryRewriteSettings(schemeId);
        if (CollectionUtils.isEmpty(rewriteSettings)) {
            return Collections.emptyList();
        }
        Map<String, List<RewriteSettingEO>> rewriteSettingMap = rewriteSettings.stream().collect(Collectors.groupingBy(RewriteSettingEO::getRewSetGroupId));
        List consolidatedTasksBySchemeId = this.taskService.getConsolidatedTasksBySchemeId(schemeId);
        String reportSystemId = null;
        if (!CollectionUtils.isEmpty(consolidatedTasksBySchemeId)) {
            reportSystemId = consolidatedTasksBySchemeId.stream().filter(task -> task.getTaskKey().equals(((RewriteSettingEO)((Object)((Object)rewriteSettings.get(0)))).getTaskId())).findFirst().map(ConsolidatedTaskVO::getSystemId).orElse(null);
        }
        ArrayList<RewriteSettingVO> rewriteSettingVOS = new ArrayList<RewriteSettingVO>();
        for (Map.Entry<String, List<RewriteSettingEO>> entry : rewriteSettingMap.entrySet()) {
            if (entry.getValue().size() == 0) continue;
            RewriteSettingVO vo = this.convertEoToVo(reportSystemId, entry);
            rewriteSettingVOS.add(vo);
        }
        return rewriteSettingVOS.stream().sorted(Comparator.comparing(RewriteSettingVO::getRewSetGroupId).reversed()).collect(Collectors.toList());
    }

    private RewriteSettingVO convertEoToVo(String reportSystemId, Map.Entry<String, List<RewriteSettingEO>> entry) {
        RewriteSettingVO vo = new RewriteSettingVO();
        RewriteSettingEO eo = entry.getValue().get(0);
        BeanUtils.copyProperties((Object)eo, vo);
        vo.setFieldMapping(StringUtils.isEmpty((CharSequence)eo.getFieldMapping()) ? null : (List)JsonUtils.readValue((String)eo.getFieldMapping(), (TypeReference)new TypeReference<List<RewriteFieldMappingVO>>(){}));
        List subjectCodes = entry.getValue().stream().map(RewriteSettingEO::getSubjectCode).collect(Collectors.toList());
        List<GcBaseDataDO> subjectVos = this.getSubjectVos(reportSystemId, entry);
        vo.setSubjectVos(subjectVos);
        vo.setSubjectCodes(subjectCodes);
        vo.setSubjectTitles(this.getSubjectTitles(subjectVos));
        try {
            TableDefine insideTableDefine = this.iDataDefinitionRuntimeController.queryTableDefineByCode(eo.getInsideTableName());
            FormDefine insideFormDefine = this.iRunTimeViewController.queryFormById(eo.getInsideFormKey());
            vo.setInsideRegionTitle(insideTableDefine == null || insideFormDefine == null ? "" : insideFormDefine.getTitle() + "|" + insideTableDefine.getCode() + "|" + insideTableDefine.getTitle());
            TableDefine outsideTableDefine = StringUtils.isEmpty((CharSequence)eo.getOutsideTableName()) ? null : this.iDataDefinitionRuntimeController.queryTableDefineByCode(eo.getOutsideTableName());
            FormDefine outsideFormDefine = StringUtils.isEmpty((CharSequence)eo.getOutsideFormKey()) ? null : this.iRunTimeViewController.queryFormById(eo.getOutsideFormKey());
            vo.setOutsideRegionTitle(outsideTableDefine == null || outsideFormDefine == null ? "" : outsideFormDefine.getTitle() + "|" + outsideTableDefine.getCode() + "|" + outsideTableDefine.getTitle());
            if (StringUtils.isEmpty((CharSequence)eo.getMasterColumnCodes())) {
                return vo;
            }
            List<String> columnCodeList = Arrays.asList(eo.getMasterColumnCodes().split(";"));
            TableModelDefine offsetTableDefine = this.dataModelService.getTableModelDefineByName("GC_OFFSETVCHRITEM");
            String masterColumnTitles = "";
            for (String columnCode : columnCodeList) {
                ColumnModelDefine fieldDefine = this.dataModelService.getColumnModelDefineByCode(offsetTableDefine.getID(), columnCode);
                masterColumnTitles = masterColumnTitles + fieldDefine.getTitle() + ";";
            }
            vo.setMasterColumnTitles(masterColumnTitles);
        }
        catch (Exception e) {
            logger.error("\u56de\u5199\u8bbe\u7f6e\u67e5\u8be2\u5f02\u5e38:" + e.getMessage(), e);
        }
        return vo;
    }

    private String getSubjectTitles(List<GcBaseDataDO> subjectVos) {
        StringBuilder subjectTitles = new StringBuilder();
        for (GcBaseDataDO subjectVo : subjectVos) {
            subjectTitles.append(subjectVo.getTitle()).append(";");
        }
        return subjectTitles.toString();
    }

    private List<GcBaseDataDO> getSubjectVos(String reportSystemId, Map.Entry<String, List<RewriteSettingEO>> entry) {
        return entry.getValue().stream().map(rewSetEo -> {
            ConsolidatedSubjectEO subjectEO = this.subjectService.getSubjectByCode(reportSystemId, rewSetEo.getSubjectCode());
            GcBaseDataDO baseDataVO = new GcBaseDataDO();
            baseDataVO.setCode(rewSetEo.getSubjectCode());
            baseDataVO.setTitle(subjectEO == null ? rewSetEo.getSubjectCode() : subjectEO.getTitle());
            return baseDataVO;
        }).collect(Collectors.toList());
    }

    @Override
    public List<FloatRegionTreeVO> getFloatRegionTree(String schemeId) {
        ArrayList<FloatRegionTreeVO> selectFormGroupList = new ArrayList<FloatRegionTreeVO>();
        List formGroupDefines = this.iRunTimeViewController.getAllFormGroupsInFormScheme(schemeId);
        if (!CollectionUtils.isEmpty(formGroupDefines)) {
            this.getFormGroupByInputScheme(formGroupDefines, selectFormGroupList);
        }
        return selectFormGroupList;
    }

    public void getFormGroupByInputScheme(List<FormGroupDefine> formGroupDefines, List<FloatRegionTreeVO> selectFormGroupList) {
        formGroupDefines.forEach(formGroupDefine -> {
            try {
                List formDefines = this.iRunTimeViewController.getAllFormsInGroup(formGroupDefine.getKey());
                if (!CollectionUtils.isEmpty(formDefines)) {
                    ArrayList<FloatRegionTreeVO> selectFormDefineList = new ArrayList<FloatRegionTreeVO>();
                    this.getFormDefineByFormGroup(formDefines, selectFormDefineList);
                    if (!CollectionUtils.isEmpty(selectFormDefineList)) {
                        FloatRegionTreeVO selectFormGroupVO = new FloatRegionTreeVO();
                        selectFormGroupVO.setValue((Object)formGroupDefine.getKey());
                        selectFormGroupVO.setLabel(formGroupDefine.getTitle());
                        selectFormGroupVO.setChildren(selectFormDefineList);
                        selectFormGroupList.add(selectFormGroupVO);
                    }
                }
            }
            catch (Exception e) {
                logger.error("\u83b7\u53d6\u6d6e\u52a8\u533a\u57df\u6811\u5f62\u62a5\u9519" + e.getMessage(), e);
            }
        });
    }

    public void getFormDefineByFormGroup(List<FormDefine> formDefines, List<FloatRegionTreeVO> selectFormDefineList) {
        try {
            formDefines.forEach(formDefine -> {
                HashSet<String> relateZbRegionKey = new HashSet<String>();
                List<Object> dataRegionDefines = this.filterDataRegionDefine(this.iRunTimeViewController.getAllRegionsInForm(formDefine.getKey()), relateZbRegionKey);
                if (!CollectionUtils.isEmpty(dataRegionDefines)) {
                    ArrayList<FloatRegionTreeVO> selectDataRegionList = new ArrayList<FloatRegionTreeVO>();
                    dataRegionDefines = dataRegionDefines.stream().sorted(Comparator.comparingDouble(DataRegionDefine::getRegionTop)).collect(Collectors.toList());
                    this.getDataRegionDefineByFormDefine(formDefine.getKey(), dataRegionDefines, selectDataRegionList, relateZbRegionKey);
                    if (!CollectionUtils.isEmpty(selectDataRegionList)) {
                        FloatRegionTreeVO selectFormDefineVO = new FloatRegionTreeVO();
                        selectFormDefineVO.setValue((Object)formDefine.getKey());
                        selectFormDefineVO.setLabel(formDefine.getTitle());
                        selectFormDefineVO.setChildren(selectDataRegionList);
                        selectFormDefineList.add(selectFormDefineVO);
                    }
                }
            });
        }
        catch (Exception e) {
            logger.error("\u83b7\u53d6\u6d6e\u52a8\u533a\u57df\u6811\u5f62\u62a5\u9519" + e.getMessage(), e);
        }
    }

    public void getDataRegionDefineByFormDefine(String formKey, List<DataRegionDefine> dataRegionDefines, List<FloatRegionTreeVO> selectDataRegionList, Set<String> relateZbRegionKey) {
        for (DataRegionDefine dataRegionDefine : dataRegionDefines) {
            List fieldKeys = this.iRunTimeViewController.getFieldKeysInRegion(dataRegionDefine.getKey());
            FieldDefine fieldDefine = null;
            try {
                TableDefine tableDefine;
                for (int i = 0; i < fieldKeys.size() && (fieldDefine = this.iDataDefinitionRuntimeController.queryFieldDefine((String)fieldKeys.get(0))) == null; ++i) {
                }
                if (fieldDefine == null || (tableDefine = this.iDataDefinitionRuntimeController.queryTableDefine(fieldDefine.getOwnerTableKey())) == null) continue;
                FloatRegionTreeVO selectDataRegionVO = new FloatRegionTreeVO();
                selectDataRegionVO.setValue((Object)dataRegionDefine.getKey());
                selectDataRegionVO.setLabel(tableDefine.getCode() + "|" + tableDefine.getTitle());
                selectDataRegionVO.setFormKey(formKey);
                selectDataRegionVO.setTableName(tableDefine.getCode());
                if (relateZbRegionKey.contains(dataRegionDefine.getKey())) {
                    selectDataRegionVO.setRelateZb(true);
                }
                selectDataRegionList.add(selectDataRegionVO);
            }
            catch (Exception e) {
                throw new BusinessRuntimeException("\u67e5\u8be2\u8868\u5b9a\u4e49\u5f02\u5e38", (Throwable)e);
            }
        }
    }

    public List<DataRegionDefine> filterDataRegionDefine(List<DataRegionDefine> dataRegionDefines, Set<String> relateZbRegionKey) {
        ArrayList<DataRegionDefine> dataRegionDefinesList = new ArrayList<DataRegionDefine>();
        for (DataRegionDefine dataRegionDefine : dataRegionDefines) {
            if (dataRegionDefine.getRegionKind().equals((Object)DataRegionKind.DATA_REGION_SIMPLE)) continue;
            List fieldKeys = this.iRunTimeViewController.getFieldKeysInRegion(dataRegionDefine.getKey());
            int zbCount = 0;
            for (String fieldKey : fieldKeys) {
                try {
                    FieldDefine fieldDefine = this.iDataDefinitionRuntimeController.queryFieldDefine(fieldKey);
                    if (fieldDefine == null || !"ACCTORGCODE".equalsIgnoreCase(fieldDefine.getCode()) && !"OPPUNITCODE".equalsIgnoreCase(fieldDefine.getCode()) && !"SUBJECTCODE".equalsIgnoreCase(fieldDefine.getCode()) && !"AMT".equalsIgnoreCase(fieldDefine.getCode())) continue;
                    ++zbCount;
                }
                catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
            if (zbCount >= 4) {
                relateZbRegionKey.add(dataRegionDefine.getKey());
            }
            dataRegionDefinesList.add(dataRegionDefine);
        }
        return dataRegionDefinesList;
    }

    @Override
    public List<ExportExcelSheet> exportRewriteSettingWorkbook(String schemeId) {
        ArrayList<ExportExcelSheet> exportedExcelSheets = new ArrayList<ExportExcelSheet>();
        String rewriteSettingSheetName = "\u56de\u5199\u8bbe\u7f6e";
        ExportExcelSheet rewriteSettingSheet = new ExportExcelSheet(Integer.valueOf(0), rewriteSettingSheetName, Integer.valueOf(1));
        ArrayList<Object[]> rewriteSettingRowDatas = new ArrayList<Object[]>();
        String[] rewriteSettingTitles = new String[]{"\u5e8f\u53f7", "\u79d1\u76ee", "\u96c6\u56e2\u5185\u6d6e\u52a8\u884c", "\u96c6\u56e2\u5916\u6d6e\u52a8\u884c"};
        rewriteSettingRowDatas.add(rewriteSettingTitles);
        List<RewriteSettingVO> rewriteSettings = this.queryRewriteSettings(schemeId);
        int recordIndex = 1;
        for (RewriteSettingVO rewriteSettingVO : rewriteSettings) {
            Object[] rewriteSettingDataRow = new Object[rewriteSettingTitles.length];
            rewriteSettingDataRow[0] = recordIndex++;
            rewriteSettingDataRow[1] = rewriteSettingVO.getSubjectTitles();
            rewriteSettingDataRow[2] = rewriteSettingVO.getInsideRegionTitle();
            rewriteSettingDataRow[3] = rewriteSettingVO.getOutsideRegionTitle();
            rewriteSettingRowDatas.add(rewriteSettingDataRow);
        }
        rewriteSettingSheet.getRowDatas().addAll(rewriteSettingRowDatas);
        exportedExcelSheets.add(rewriteSettingSheet);
        ArrayList<Object[]> fieldMappingRowDatas = new ArrayList<Object[]>();
        String fieldMappingSheetName = "\u5b57\u6bb5\u6620\u5c04\u8bbe\u7f6e";
        recordIndex = 0;
        String[] fieldMappingTitles = new String[]{"\u5e8f\u53f7", "\u7c7b\u578b", "\u6307\u6807", "\u5bf9\u5e94\u62b5\u9500\u5206\u5f55\u5b57\u6bb5"};
        fieldMappingRowDatas.add(fieldMappingTitles);
        ExportExcelSheet fieldMappingSheet = new ExportExcelSheet(Integer.valueOf(1), fieldMappingSheetName, Integer.valueOf(1));
        for (RewriteSettingVO rewriteSettingVO : rewriteSettings) {
            List fieldMappings = rewriteSettingVO.getFieldMapping();
            ++recordIndex;
            if (CollectionUtils.isEmpty(fieldMappings)) continue;
            for (RewriteFieldMappingVO fieldMappingVO : fieldMappings) {
                Object[] fieldMappingDataRow = new Object[fieldMappingTitles.length];
                fieldMappingDataRow[0] = recordIndex;
                fieldMappingDataRow[1] = RewriteSettingConst.FieldMappingEnum.INSIDE.getCode().equals(fieldMappingVO.getType()) ? RewriteSettingConst.FieldMappingEnum.INSIDE.getTitle() : RewriteSettingConst.FieldMappingEnum.OUTSIDE.getTitle();
                fieldMappingDataRow[2] = fieldMappingVO.getZbField();
                fieldMappingDataRow[3] = fieldMappingVO.getOffsetField();
                fieldMappingRowDatas.add(fieldMappingDataRow);
            }
        }
        fieldMappingSheet.getRowDatas().addAll(fieldMappingRowDatas);
        exportedExcelSheets.add(fieldMappingSheet);
        return exportedExcelSheets;
    }

    @Override
    public StringBuilder rewriteSettingImport(String systemId, String taskID, String schemeId, List<ImportExcelSheet> excelSheets) {
        StringBuilder log = new StringBuilder(128);
        List subjects = this.subjectService.listAllSubjectsBySystemId(systemId);
        Map<String, String> subjectCodeMap = subjects.stream().collect(Collectors.toMap(ConsolidatedSubjectEO::getCode, ConsolidatedSubjectEO::getTitle, (t1, t2) -> t1));
        List formDefines = this.iRunTimeViewController.queryAllFormDefinesByFormScheme(schemeId);
        Map<String, String> formKeys = formDefines.stream().collect(Collectors.toMap(IBaseMetaItem::getTitle, IBaseMetaItem::getKey, (f1, f2) -> f1));
        ArrayList<RewriteSettingEO> rewriteSettingEOS = new ArrayList<RewriteSettingEO>();
        List settingExcelDatas = excelSheets.get(0).getExcelSheetDatas();
        List mappingExcelDatas = excelSheets.get(1).getExcelSheetDatas();
        Map<Integer, List<Object[]>> index2DataMap = mappingExcelDatas.stream().skip(1L).collect(Collectors.groupingBy(data -> ConverterUtils.getAsInteger((Object)data[0])));
        for (int i = 1; i < settingExcelDatas.size(); ++i) {
            Object[] excelRowData = (Object[])settingExcelDatas.get(i);
            List<Object[]> mappingData = index2DataMap.get(i);
            ArrayList<RewriteFieldMappingDTO> mappingDTOS = new ArrayList<RewriteFieldMappingDTO>();
            if (!CollectionUtils.isEmpty(mappingData)) {
                for (Object[] data2 : mappingData) {
                    RewriteFieldMappingDTO mappingDTO = new RewriteFieldMappingDTO();
                    mappingDTO.setType(RewriteSettingConst.FieldMappingEnum.INSIDE.getTitle().equals(ConverterUtils.getAsString((Object)data2[1])) ? RewriteSettingConst.FieldMappingEnum.INSIDE.getCode() : RewriteSettingConst.FieldMappingEnum.OUTSIDE.getCode());
                    mappingDTO.setZbField(ConverterUtils.getAsString((Object)data2[2]));
                    mappingDTO.setOffsetField(ConverterUtils.getAsString((Object)data2[3]));
                    mappingDTOS.add(mappingDTO);
                }
            }
            try {
                String[] subjectCodes = ((String)excelRowData[1]).split(";");
                String[] insideInfoArr = ObjectUtils.isEmpty(excelRowData[2]) ? null : ((String)excelRowData[2]).split("\\|");
                Object[] outsideInfoArr = ObjectUtils.isEmpty(excelRowData[3]) ? null : ((String)excelRowData[3]).split("\\|");
                String rewSetGroupId = UUIDOrderUtils.newUUIDStr();
                String insideDataRegionKey = null;
                String outsideDataRegionKey = null;
                for (int j = 0; j < subjectCodes.length; ++j) {
                    RewriteSettingEO eo = new RewriteSettingEO();
                    eo.setTaskId(taskID);
                    eo.setSchemeId(schemeId);
                    eo.setRewSetGroupId(rewSetGroupId);
                    eo.setId(UUIDOrderUtils.newUUIDStr());
                    if (!subjectCodeMap.containsKey(subjectCodes[j])) {
                        throw new BusinessRuntimeException("\u672a\u89e3\u6790\u5230\u79d1\u76ee: " + subjectCodes[j]);
                    }
                    eo.setSubjectCode(subjectCodes[j]);
                    Assert.isNotEmpty((String)insideInfoArr[0], (String)"\u672a\u89e3\u6790\u5230\u96c6\u56e2\u5185\u5b58\u50a8\u8868", (Object[])new Object[0]);
                    String insideFormKey = formKeys.get(insideInfoArr[0]);
                    Assert.isNotEmpty((String)insideFormKey, (String)"\u672a\u89e3\u6790\u5230\u96c6\u56e2\u5916\u62a5\u8868", (Object[])new Object[0]);
                    eo.setInsideFormKey(insideFormKey);
                    eo.setInsideTableName(insideInfoArr[1]);
                    if (insideDataRegionKey == null) {
                        insideDataRegionKey = this.getDataRegionKey(eo.getInsideFormKey(), eo.getInsideTableName());
                    }
                    Assert.isNotEmpty(insideDataRegionKey, (String)"\u672a\u89e3\u6790\u5230\u96c6\u56e2\u5185\u6d6e\u52a8\u533a\u57df", (Object[])new Object[0]);
                    eo.setInsideReginonKey(insideDataRegionKey);
                    if (!ObjectUtils.isEmpty(outsideInfoArr)) {
                        Assert.isNotEmpty((String)outsideInfoArr[0], (String)"\u672a\u89e3\u6790\u5230\u96c6\u56e2\u5916\u5b58\u50a8\u8868", (Object[])new Object[0]);
                        String outsideFormKey = formKeys.get(outsideInfoArr[0]);
                        Assert.isNotEmpty((String)outsideFormKey, (String)"\u672a\u89e3\u6790\u5230\u96c6\u56e2\u5916\u62a5\u8868", (Object[])new Object[0]);
                        eo.setOutsideFormKey(outsideFormKey);
                        eo.setOutsideTableName((String)outsideInfoArr[1]);
                        if (outsideDataRegionKey == null) {
                            outsideDataRegionKey = this.getDataRegionKey(eo.getOutsideFormKey(), eo.getOutsideTableName());
                        }
                        Assert.isNotEmpty(outsideDataRegionKey, (String)"\u672a\u89e3\u6790\u5230\u96c6\u56e2\u5916\u6d6e\u52a8\u533a\u57df", (Object[])new Object[0]);
                        eo.setOutsideReginonKey(outsideDataRegionKey);
                    }
                    eo.setFieldMapping(CollectionUtils.isEmpty(mappingDTOS) ? null : JsonUtils.writeValueAsString(mappingDTOS));
                    rewriteSettingEOS.add(eo);
                }
                continue;
            }
            catch (IllegalArgumentException e) {
                log.append(String.format("\u7b2c%1d\u884c\uff1a%2s<br>", i, e.getMessage()));
                continue;
            }
            catch (Exception e) {
                log.append(String.format("\u7b2c%1d\u884c\uff1a%2s<br>", i, e.getMessage()));
                logger.error(e.getMessage(), e);
            }
        }
        this.rewriteSettingDao.addBatch(rewriteSettingEOS);
        return log;
    }

    private String getDataRegionKey(String formKey, String tableCode) {
        List<DataRegionDefine> dataRegionDefines = this.filterDataRegionDefine(this.iRunTimeViewController.getAllRegionsInForm(formKey), new HashSet<String>());
        if (CollectionUtils.isEmpty(dataRegionDefines)) {
            return null;
        }
        for (DataRegionDefine dataRegionDefine : dataRegionDefines) {
            List fieldKeys = this.iRunTimeViewController.getFieldKeysInRegion(dataRegionDefine.getKey());
            try {
                FieldDefine fieldDefine = this.iDataDefinitionRuntimeController.queryFieldDefine((String)fieldKeys.get(0));
                TableDefine tableDefine = this.iDataDefinitionRuntimeController.queryTableDefine(fieldDefine.getOwnerTableKey());
                if (!tableDefine.getCode().equals(tableCode)) continue;
                return dataRegionDefine.getKey();
            }
            catch (Exception e) {
                throw new BusinessRuntimeException("\u67e5\u8be2\u8868\u5b9a\u4e49\u5f02\u5e38", (Throwable)e);
            }
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void saveRewriteSubjectSettings(String schemeId, List<RewriteSubjectSettingVO> rewriteSubjectSettings) {
        this.rewriteSubjectSettingDao.deleteRewriteSubjectSettingBySchemeId(schemeId);
        ArrayList<RewriteSubjectSettingEO> rewriteSettingEOS = new ArrayList<RewriteSubjectSettingEO>();
        for (RewriteSubjectSettingVO subjectSetting : rewriteSubjectSettings) {
            RewriteSubjectSettingEO eo = new RewriteSubjectSettingEO();
            BeanUtils.copyProperties(subjectSetting, (Object)eo);
            eo.setId(UUIDOrderUtils.newUUIDStr());
            rewriteSettingEOS.add(eo);
        }
        this.rewriteSubjectSettingDao.addBatch(rewriteSettingEOS);
    }

    @Override
    public List<RewriteSubjectSettingVO> queryRewriteSubjectSettings(String schemeId) {
        List<RewriteSubjectSettingEO> rewriteSubjectSettingEos = this.rewriteSubjectSettingDao.queryRewriteSubjectSettings(schemeId);
        ArrayList<RewriteSubjectSettingVO> vos = new ArrayList<RewriteSubjectSettingVO>(16);
        for (RewriteSubjectSettingEO rewriteSubjectSettingEo : rewriteSubjectSettingEos) {
            RewriteSubjectSettingVO vo = new RewriteSubjectSettingVO();
            BeanUtils.copyProperties((Object)rewriteSubjectSettingEo, vo);
            vos.add(vo);
        }
        return vos;
    }

    @Override
    public List<RewriteFieldInfoVO> queryOffsetDataModel(String id) {
        RewriteSettingEO rewriteSettingEO = this.rewriteSettingDao.queryRewriteSettingsById(id);
        List columnCodeList = StringUtils.isNotBlank((CharSequence)rewriteSettingEO.getMasterColumnCodes()) ? Arrays.asList(rewriteSettingEO.getMasterColumnCodes().split(";")) : Collections.emptyList();
        TableModelDefine tableDefine = this.dataModelService.getTableModelDefineByCode("GC_OFFSETVCHRITEM");
        List defines = this.dataModelService.getColumnModelDefinesByTable(tableDefine.getID()).stream().filter(define -> {
            if (ColumnModelType.BIGDECIMAL != define.getColumnType()) {
                if (!columnCodeList.contains(define.getCode())) return false;
            }
            if (!Stream.of("DISABLEFLAG", "RECVER").noneMatch(define.getCode()::equals)) return false;
            return true;
        }).collect(Collectors.toList());
        ArrayList<RewriteFieldInfoVO> fieldInfoVOS = new ArrayList<RewriteFieldInfoVO>(defines.size() + 1);
        fieldInfoVOS.add(new RewriteFieldInfoVO("AMT", "\u62b5\u9500\u4f59\u989d"));
        for (ColumnModelDefine define2 : defines) {
            fieldInfoVOS.add(new RewriteFieldInfoVO(define2.getName(), define2.getTitle()));
        }
        return fieldInfoVOS;
    }

    @Override
    public List<RewriteFieldMappingVO> queryFieldMappingSettings(String id) {
        RewriteFieldMappingVO vo;
        RewriteSettingEO rewriteSettingEO = this.rewriteSettingDao.queryRewriteSettingsById(id);
        if (ObjectUtils.isEmpty((Object)rewriteSettingEO)) {
            return null;
        }
        Collection fieldMapping = ObjectUtils.isEmpty(rewriteSettingEO.getFieldMapping()) ? null : (List)JsonUtils.readValue((String)rewriteSettingEO.getFieldMapping(), (TypeReference)new TypeReference<List<RewriteFieldMappingVO>>(){});
        Map<String, Map<String, String>> map = ObjectUtils.isEmpty(fieldMapping) ? null : fieldMapping.stream().collect(Collectors.groupingBy(RewriteFieldMappingVO::getType, Collectors.toMap(RewriteFieldMappingVO::getZbField, RewriteFieldMappingVO::getOffsetField)));
        ArrayList<RewriteFieldMappingVO> mappingVO = new ArrayList<RewriteFieldMappingVO>();
        if (!StringUtils.isEmpty((CharSequence)rewriteSettingEO.getInsideTableName())) {
            Map<String, String> insideMap = ObjectUtils.isEmpty(map) ? null : map.get(RewriteSettingConst.FieldMappingEnum.INSIDE.getCode());
            List insideDataFields = this.iRuntimeDataSchemeService.getDataFieldByTableCodeAndKind(rewriteSettingEO.getInsideTableName(), new DataFieldKind[]{DataFieldKind.TABLE_FIELD_DIM, DataFieldKind.FIELD});
            for (DataField dataField : insideDataFields) {
                vo = new RewriteFieldMappingVO();
                vo.setType(RewriteSettingConst.FieldMappingEnum.INSIDE.getCode());
                vo.setZbField(dataField.getCode());
                vo.setZbFieldTitle(dataField.getCode() + "|" + dataField.getTitle());
                vo.setOffsetField(ObjectUtils.isEmpty(insideMap) ? null : insideMap.get(dataField.getCode()));
                mappingVO.add(vo);
            }
        }
        if (!StringUtils.isEmpty((CharSequence)rewriteSettingEO.getOutsideTableName())) {
            Map<String, String> outsideMap = ObjectUtils.isEmpty(map) ? null : map.get(RewriteSettingConst.FieldMappingEnum.OUTSIDE.getCode());
            List outsideDataFields = this.iRuntimeDataSchemeService.getDataFieldByTableCodeAndKind(rewriteSettingEO.getOutsideTableName(), new DataFieldKind[]{DataFieldKind.TABLE_FIELD_DIM, DataFieldKind.FIELD});
            for (DataField dataField : outsideDataFields) {
                vo = new RewriteFieldMappingVO();
                vo.setType(RewriteSettingConst.FieldMappingEnum.OUTSIDE.getCode());
                vo.setZbField(dataField.getCode());
                vo.setZbFieldTitle(dataField.getCode() + "|" + dataField.getTitle());
                vo.setOffsetField(ObjectUtils.isEmpty(outsideMap) ? null : outsideMap.get(dataField.getCode()));
                mappingVO.add(vo);
            }
        }
        return mappingVO;
    }
}

