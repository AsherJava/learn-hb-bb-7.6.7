/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.TreeBasedTable
 *  com.jiuqi.bde.common.dto.FloatQueryFieldVO
 *  com.jiuqi.bde.common.dto.FloatRegionConfigVO
 *  com.jiuqi.bde.common.dto.FloatZbMappingVO
 *  com.jiuqi.bde.common.intf.Dimension
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.bde.common.vo.FetchSettingVO
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.executor.handler.impl;

import com.google.common.collect.TreeBasedTable;
import com.jiuqi.bde.common.dto.FloatQueryFieldVO;
import com.jiuqi.bde.common.dto.FloatRegionConfigVO;
import com.jiuqi.bde.common.dto.FloatZbMappingVO;
import com.jiuqi.bde.common.intf.Dimension;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.bde.common.vo.FetchSettingVO;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dto.ExcelSettingDTO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dto.FailedSettingLog;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dto.FetchSettingExcelContext;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dto.FetchSettingExportContext;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dto.FixedAdaptSettingExcelDTO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dto.RegionFetchSetting;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.consts.ExcelRowFetchSettingVO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.enums.FloatSettingTypeEnum;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.handler.impl.AbstractExportInnerRowHandler;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.intf.ImpExpDataField;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.intf.ImpExpDataLink;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.intf.ImpExpDataTable;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.intf.ImpExpFieldDefine;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.utils.FetchSettingNrUtil;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.utils.ImportInnerColumnUtil;
import com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchFloatSettingDesService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FloatFetchSettingExcelRowHandler
extends AbstractExportInnerRowHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(FloatFetchSettingExcelRowHandler.class);
    @Autowired
    private FetchFloatSettingDesService fetchFloatSettingService;
    @Autowired
    private IRunTimeViewController runTimeViewController;

    @Override
    public RegionFetchSetting handleImportData(ExcelSettingDTO excelSettingDTO, FetchSettingExcelContext fetchSettingExcelContext) {
        Map<String, ImpExpDataLink> dataLinkDefineCodeMap = this.getDataLinkDefineCodeMap(fetchSettingExcelContext.getBizType(), excelSettingDTO.getFormId(), excelSettingDTO.getRegionId());
        Map<String, ImpExpDataLink> dataLinkDefineNameMap = this.getDataLinkDefineNameMap(fetchSettingExcelContext.getBizType(), excelSettingDTO.getFormId(), excelSettingDTO.getRegionId());
        HashMap<String, List<FixedAdaptSettingExcelDTO>> fixedAdaptSettingMap = new HashMap<String, List<FixedAdaptSettingExcelDTO>>();
        HashSet<String> failedDefineCodeSet = new HashSet<String>();
        TreeBasedTable adaptFormaulIndexTable = TreeBasedTable.create();
        TreeBasedTable logicFormulaStrTable = TreeBasedTable.create();
        Map<String, String> fieldTitleToVOMap = excelSettingDTO.getFloatRegionConfigVO().getQueryConfigInfo().getQueryFields().stream().collect(Collectors.toMap(FloatQueryFieldVO::getTitle, FloatQueryFieldVO::getName, (K1, K2) -> K1));
        HashMap<String, FloatZbMappingVO> dataLinkDefineZbMappingMap = new HashMap<String, FloatZbMappingVO>();
        HashMap<String, String> dataLinkDefineKeyToFieldCodeMap = new HashMap<String, String>();
        HashSet<String> faildDataLinkDefineKeySet = new HashSet<String>();
        block7: for (ExcelRowFetchSettingVO excelRowFetchSettingVO : excelSettingDTO.getExportFetchSourceRowImpSettingVOS()) {
            ImpExpDataLink dataLinkDefine = null;
            if (!StringUtils.isEmpty((String)excelRowFetchSettingVO.getFieldDefineCode())) {
                dataLinkDefine = dataLinkDefineCodeMap.get(excelRowFetchSettingVO.getFieldDefineCode());
            }
            if (dataLinkDefine == null) {
                dataLinkDefine = dataLinkDefineNameMap.get(excelRowFetchSettingVO.getFieldDefineTitle());
            }
            if (dataLinkDefine == null) {
                excelRowFetchSettingVO.setErrorLog("\u672a\u627e\u5230\u5bf9\u5e94\u6307\u6807");
                fetchSettingExcelContext.addFailedSettingLog(new FailedSettingLog(excelRowFetchSettingVO));
                continue;
            }
            if (faildDataLinkDefineKeySet.contains(dataLinkDefine.getKey())) continue;
            FloatZbMappingVO floatZbMappingVO = new FloatZbMappingVO();
            floatZbMappingVO.setDataLinkId(dataLinkDefine.getKey());
            floatZbMappingVO.setDataLinkName(dataLinkDefine.getName());
            floatZbMappingVO.setFieldDefineId(dataLinkDefine.getDataFieldId());
            dataLinkDefineKeyToFieldCodeMap.put(dataLinkDefine.getKey(), excelRowFetchSettingVO.getFieldDefineCode());
            FloatSettingTypeEnum floatSettingTypeEnum = FloatSettingTypeEnum.getEnumByName(excelRowFetchSettingVO.getFieldType());
            if (floatSettingTypeEnum == null) {
                excelRowFetchSettingVO.setErrorLog("\u6839\u636e\u6d6e\u52a8\u5217\u8bbe\u7f6e\u7c7b\u578b\u672a\u627e\u5230\u5bf9\u5e94\u679a\u4e3e");
                FailedSettingLog failedSettingLog = new FailedSettingLog(excelRowFetchSettingVO);
                fetchSettingExcelContext.addFailedSettingLog(failedSettingLog);
                faildDataLinkDefineKeySet.add(dataLinkDefine.getKey());
                continue;
            }
            switch (floatSettingTypeEnum) {
                case CUSTOM_TEXT: {
                    if (dataLinkDefineZbMappingMap.containsKey(dataLinkDefine.getKey())) {
                        excelRowFetchSettingVO.setErrorLog("\u81ea\u5b9a\u4e49\u6587\u672c\u5728\u540c\u4e00\u6307\u6807\u4e0b\u5e94\u8be5\u552f\u4e00\uff0c\u4e0d\u80fd\u91cd\u590d\u914d\u7f6e");
                        fetchSettingExcelContext.addFailedSettingLog(new FailedSettingLog(excelRowFetchSettingVO));
                        faildDataLinkDefineKeySet.add(dataLinkDefine.getKey());
                        continue block7;
                    }
                    floatZbMappingVO.setQueryName(excelRowFetchSettingVO.getFieldValue());
                    break;
                }
                case RESULT_COLUMN: {
                    if (dataLinkDefineZbMappingMap.containsKey(dataLinkDefine.getKey())) {
                        excelRowFetchSettingVO.setErrorLog("\u81ea\u5b9a\u4e49\u7ed3\u679c\u5217\u5728\u540c\u4e00\u6307\u6807\u4e0b\u5e94\u8be5\u552f\u4e00\uff0c\u4e0d\u80fd\u91cd\u590d\u914d\u7f6e");
                        fetchSettingExcelContext.addFailedSettingLog(new FailedSettingLog(excelRowFetchSettingVO));
                        faildDataLinkDefineKeySet.add(dataLinkDefine.getKey());
                        continue block7;
                    }
                    floatZbMappingVO.setQueryName("${" + fieldTitleToVOMap.get(excelRowFetchSettingVO.getFieldValue()) + "}");
                    break;
                }
                case CUSTOM_RULE: {
                    ExcelRowFetchSettingVO fetchSourceRowSettingVO;
                    FixedAdaptSettingExcelDTO fixedAdaptSettingDTO;
                    String logicFormual = (String)logicFormulaStrTable.get((Object)dataLinkDefine.getKey(), (Object)excelRowFetchSettingVO.getAdaptFormula());
                    if (!StringUtils.isEmpty((String)logicFormual) && !logicFormual.equals(excelRowFetchSettingVO.getLogicFormula())) {
                        failedDefineCodeSet.add(dataLinkDefine.getKey());
                        excelRowFetchSettingVO.setErrorLog("\u5bfc\u5165\u6587\u4ef6\u4e2d\uff0c\u540c\u4e00\u6307\u6807\u7684\u540c\u4e00\u4e2a\u9002\u5e94\u6761\u4ef6\u4e0b\u6240\u6709\u89c4\u5219\u5747\u9700\u8bbe\u7f6e\u76f8\u540c\u7684\u903b\u8f91\u8868\u8fbe\u5f0f\uff0c\u8bf7\u68c0\u67e5\u3002");
                        fetchSettingExcelContext.addFailedSettingLog(new FailedSettingLog(excelRowFetchSettingVO));
                        continue block7;
                    }
                    List fixedAdaptSettingVOS = fixedAdaptSettingMap.computeIfAbsent(dataLinkDefine.getKey(), k -> new ArrayList());
                    dataLinkDefineKeyToFieldCodeMap.put(dataLinkDefine.getKey(), StringUtils.isEmpty((String)excelRowFetchSettingVO.getFieldDefineCode()) ? excelRowFetchSettingVO.getFieldDefineTitle() : excelRowFetchSettingVO.getFieldDefineCode());
                    Integer index = (Integer)adaptFormaulIndexTable.get((Object)dataLinkDefine.getKey(), (Object)excelRowFetchSettingVO.getAdaptFormula());
                    if (index == null) {
                        fixedAdaptSettingDTO = new FixedAdaptSettingExcelDTO(excelRowFetchSettingVO.getSheetName());
                        fixedAdaptSettingDTO.setAdaptFormula(excelRowFetchSettingVO.getAdaptFormula());
                        fixedAdaptSettingDTO.setLogicFormula(excelRowFetchSettingVO.getLogicFormula());
                        fixedAdaptSettingDTO.setDescription(excelRowFetchSettingVO.getDescription());
                        fixedAdaptSettingDTO.setWildcardFormula(excelRowFetchSettingVO.getWildcardFormula());
                        fixedAdaptSettingDTO.setBizModelFormula(new LinkedHashMap<String, List<ExcelRowFetchSettingVO>>());
                        fixedAdaptSettingVOS.add(fixedAdaptSettingDTO);
                        adaptFormaulIndexTable.put((Object)dataLinkDefine.getKey(), (Object)excelRowFetchSettingVO.getAdaptFormula(), (Object)adaptFormaulIndexTable.row((Object)dataLinkDefine.getKey()).size());
                        logicFormulaStrTable.put((Object)dataLinkDefine.getKey(), (Object)excelRowFetchSettingVO.getAdaptFormula(), (Object)(StringUtils.isEmpty((String)excelRowFetchSettingVO.getLogicFormula()) ? "" : excelRowFetchSettingVO.getLogicFormula()));
                    } else {
                        fixedAdaptSettingDTO = (FixedAdaptSettingExcelDTO)fixedAdaptSettingVOS.get(index);
                    }
                    Map<String, List<ExcelRowFetchSettingVO>> fixedAdaptSettingVOBizModelFormula = fixedAdaptSettingDTO.getBizModelFormula();
                    if (StringUtils.isEmpty((String)excelRowFetchSettingVO.getFetchSourceCode()) && !StringUtils.isEmpty((String)excelRowFetchSettingVO.getLogicFormula())) {
                        floatZbMappingVO.setQueryName("=");
                        break;
                    }
                    try {
                        fetchSourceRowSettingVO = this.convertToFetchSetting(excelRowFetchSettingVO, fetchSettingExcelContext);
                    }
                    catch (Exception e) {
                        LOGGER.error("\u89e3\u6790\u53d6\u6570\u8bbe\u7f6e\u6570\u636e\u5f02\u5e38", e);
                        excelRowFetchSettingVO.setErrorLog("\u89e3\u6790\u5931\u8d25\uff0c\u8bf7\u68c0\u67e5\u914d\u7f6e");
                        fetchSettingExcelContext.addFailedSettingLog(new FailedSettingLog(excelRowFetchSettingVO));
                        failedDefineCodeSet.add(dataLinkDefine.getKey());
                        continue block7;
                    }
                    fetchSourceRowSettingVO.setFetchSourceCode(excelRowFetchSettingVO.getFetchSourceCode());
                    List fetchSourceRowSettingVOS = fixedAdaptSettingVOBizModelFormula.computeIfAbsent(fetchSourceRowSettingVO.getFetchSourceCode(), K -> new ArrayList());
                    fetchSourceRowSettingVO = this.covertQueryFieldToName(fetchSourceRowSettingVO, fieldTitleToVOMap);
                    fetchSourceRowSettingVOS.add(fetchSourceRowSettingVO);
                    floatZbMappingVO.setQueryName("=");
                    break;
                }
            }
            dataLinkDefineZbMappingMap.putIfAbsent(dataLinkDefine.getKey(), floatZbMappingVO);
        }
        failedDefineCodeSet.forEach(item -> {
            dataLinkDefineZbMappingMap.remove(item);
            fixedAdaptSettingMap.remove(item);
        });
        excelSettingDTO.getFloatRegionConfigVO().getQueryConfigInfo().setZbMapping(new ArrayList(dataLinkDefineZbMappingMap.values()));
        RegionFetchSetting regionFetchSetting = new RegionFetchSetting();
        regionFetchSetting.setFixeAdaptSettingDTOMap(fixedAdaptSettingMap);
        regionFetchSetting.setFetchSchemeId(fetchSettingExcelContext.getFetchSchemeId());
        regionFetchSetting.setFormSchemeId(fetchSettingExcelContext.getFormSchemeId());
        regionFetchSetting.setFormId(excelSettingDTO.getFormId());
        regionFetchSetting.setRegionId(excelSettingDTO.getRegionId());
        regionFetchSetting.setDataLinkTitleMap(dataLinkDefineKeyToFieldCodeMap);
        regionFetchSetting.setFloatRegionConfigVO(excelSettingDTO.getFloatRegionConfigVO());
        return regionFetchSetting;
    }

    @Override
    public List<Object[]> handleExportData(FetchSettingExportContext fetchSettingExcelContext, FetchSettingCond fetchSettingCond) {
        ArrayList<Object[]> rowDatas = new ArrayList<Object[]>();
        ArrayList<ExcelRowFetchSettingVO> fetchSourceRowImpSettingVOS = new ArrayList();
        FloatRegionConfigVO floatRegionConfigVO = this.fetchFloatSettingService.getFetchFloatSettingDes(fetchSettingCond);
        DataRegionDefine regionDefine = this.runTimeViewController.queryDataRegionDefine(fetchSettingCond.getRegionId());
        if (fetchSettingExcelContext.getTemplateExportFlag().booleanValue()) {
            fetchSourceRowImpSettingVOS = this.exportTemplateToVos(fetchSettingExcelContext.getBizType(), fetchSettingCond);
        } else {
            if (floatRegionConfigVO == null) {
                return null;
            }
            Map<String, List<FetchSettingVO>> fetchSettingDesGroupByDataLinkId = this.fetchSettingDesService.getFetchFloatSettingDesGroupByDataLinkId(fetchSettingCond);
            Map zbMappingVOMap = floatRegionConfigVO.getQueryConfigInfo().getZbMapping().stream().collect(Collectors.toMap(FloatZbMappingVO::getDataLinkId, Function.identity(), (K1, K2) -> K1));
            List<ImpExpDataLink> dataLinks = this.getDataLinkDefines(fetchSettingExcelContext.getBizType(), fetchSettingCond.getFormId(), fetchSettingCond.getRegionId());
            Map<String, String> nameToTitleMap = floatRegionConfigVO.getQueryConfigInfo().getQueryFields().stream().collect(Collectors.toMap(FloatQueryFieldVO::getName, FloatQueryFieldVO::getTitle, (o1, o2) -> o1));
            for (ImpExpDataLink dataLink : dataLinks) {
                ImpExpDataField dataField = this.getDataField(fetchSettingExcelContext.getBizType(), fetchSettingCond.getFormId(), fetchSettingCond.getRegionId(), dataLink.getDataFieldId());
                if (dataField == null || zbMappingVOMap.get(dataLink.getKey()) == null) continue;
                FloatZbMappingVO floatZbMappingVO = (FloatZbMappingVO)zbMappingVOMap.get(dataLink.getKey());
                if ("=".equals(floatZbMappingVO.getQueryName())) {
                    List<FetchSettingVO> fetchSettingVOS = fetchSettingDesGroupByDataLinkId.get(dataLink.getKey());
                    if (CollectionUtils.isEmpty(fetchSettingVOS)) {
                        LOGGER.info("\u6839\u636edataLinkId\u3010{}\u3011\u672a\u83b7\u53d6\u5230\u5bf9\u5e94\u53d6\u6570\u8bbe\u7f6e", (Object)dataLink.getKey());
                        continue;
                    }
                    List<ExcelRowFetchSettingVO> customFetchSetting = new ArrayList<ExcelRowFetchSettingVO>();
                    for (FetchSettingVO fetchSettingVO : fetchSettingVOS) {
                        customFetchSetting = this.convert2RowFetchSetting(fetchSettingVO, fetchSettingExcelContext, floatRegionConfigVO.getQueryConfigInfo().getQueryFields());
                    }
                    fetchSourceRowImpSettingVOS.addAll(this.convertQueryField(customFetchSetting, nameToTitleMap));
                    continue;
                }
                ImpExpDataTable dataTable = this.getDataTable(fetchSettingExcelContext.getBizType(), fetchSettingCond.getFormId(), fetchSettingCond.getRegionId(), dataField.getDataTableKey());
                ExcelRowFetchSettingVO excelRowFetchSettingVO = new ExcelRowFetchSettingVO();
                ImpExpFieldDefine fieldDefineInfo = this.getFieldDefineInfo(fetchSettingExcelContext.getBizType(), fetchSettingCond.getFormId(), fetchSettingCond.getRegionId(), dataTable, dataField, dataLink, null);
                excelRowFetchSettingVO.setFieldDefineCode(fieldDefineInfo.getCode());
                excelRowFetchSettingVO.setFieldDefineTitle(fieldDefineInfo.getName());
                if (floatZbMappingVO.getQueryName().contains("$")) {
                    excelRowFetchSettingVO.setFieldValue(nameToTitleMap.get(floatZbMappingVO.getQueryName().substring(2, floatZbMappingVO.getQueryName().length() - 1)));
                    excelRowFetchSettingVO.setFieldType(FloatSettingTypeEnum.RESULT_COLUMN.getName());
                    fetchSourceRowImpSettingVOS.add(excelRowFetchSettingVO);
                    continue;
                }
                excelRowFetchSettingVO.setFieldValue(floatZbMappingVO.getQueryName());
                excelRowFetchSettingVO.setFieldType(FloatSettingTypeEnum.CUSTOM_TEXT.getName());
                fetchSourceRowImpSettingVOS.add(excelRowFetchSettingVO);
            }
        }
        if (CollectionUtils.isEmpty(fetchSourceRowImpSettingVOS)) {
            return null;
        }
        rowDatas.addAll(FetchSettingNrUtil.listExportFloatExcelTitles(regionDefine, floatRegionConfigVO, fetchSettingExcelContext));
        rowDatas.addAll(ImportInnerColumnUtil.getTitle(fetchSettingExcelContext.getFloatColumns()));
        rowDatas.addAll(ImportInnerColumnUtil.getSettings(fetchSettingExcelContext.getFloatColumns(), fetchSourceRowImpSettingVOS));
        rowDatas.add(new Object[0]);
        return rowDatas;
    }

    private List<ExcelRowFetchSettingVO> convertQueryField(List<ExcelRowFetchSettingVO> customFetchSetting, Map<String, String> nameToTitleMap) {
        for (ExcelRowFetchSettingVO excelRowFetchSettingVO : customFetchSetting) {
            excelRowFetchSettingVO.setBaseDataCode(this.getFloatQueryFieldTitle(excelRowFetchSettingVO.getBaseDataCode(), nameToTitleMap));
            excelRowFetchSettingVO.setSubjectCode(this.getFloatQueryFieldTitle(excelRowFetchSettingVO.getSubjectCode(), nameToTitleMap));
            excelRowFetchSettingVO.setExcludeSubjectCode(this.getFloatQueryFieldTitle(excelRowFetchSettingVO.getExcludeSubjectCode(), nameToTitleMap));
            excelRowFetchSettingVO.setCashCode(this.getFloatQueryFieldTitle(excelRowFetchSettingVO.getCashCode(), nameToTitleMap));
            excelRowFetchSettingVO.setReclassSubjCode(this.getFloatQueryFieldTitle(excelRowFetchSettingVO.getReclassSubjCode(), nameToTitleMap));
            excelRowFetchSettingVO.setReclassSrcSubjCode(this.getFloatQueryFieldTitle(excelRowFetchSettingVO.getReclassSubjCode(), nameToTitleMap));
            for (Map.Entry<String, Dimension> dimensionEntry : excelRowFetchSettingVO.getDimSettingValueMap().entrySet()) {
                dimensionEntry.getValue().setDimValue(this.getFloatQueryFieldTitle(dimensionEntry.getValue().getDimValue(), nameToTitleMap));
            }
        }
        return customFetchSetting;
    }

    private String getFloatQueryFieldTitle(String value, Map<String, String> nameToTitleMap) {
        if (!StringUtils.isEmpty((String)value) && value.contains("$")) {
            return nameToTitleMap.get(value.substring(2, value.length() - 1));
        }
        return value;
    }
}

