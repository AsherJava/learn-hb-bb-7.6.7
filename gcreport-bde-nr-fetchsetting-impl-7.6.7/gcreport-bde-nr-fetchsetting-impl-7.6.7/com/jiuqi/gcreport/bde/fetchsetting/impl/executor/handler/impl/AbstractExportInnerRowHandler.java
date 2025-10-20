/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.bde.bizmodel.client.dto.BizModelDTO
 *  com.jiuqi.bde.common.constant.AgingPeriodTypeEnum
 *  com.jiuqi.bde.common.constant.FetchFixedFieldEnum
 *  com.jiuqi.bde.common.dto.FixedAdaptSettingVO
 *  com.jiuqi.bde.common.dto.FixedFetchSourceRowSettingVO
 *  com.jiuqi.bde.common.dto.FloatQueryFieldVO
 *  com.jiuqi.bde.common.intf.Dimension
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.bde.common.vo.FetchSettingVO
 *  com.jiuqi.gcreport.bde.fetchsetting.client.enums.BizTypeEnum
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond
 *  com.jiuqi.xlib.utils.CollectionUtils
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.executor.handler.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.bde.bizmodel.client.dto.BizModelDTO;
import com.jiuqi.bde.common.constant.AgingPeriodTypeEnum;
import com.jiuqi.bde.common.constant.FetchFixedFieldEnum;
import com.jiuqi.bde.common.dto.FixedAdaptSettingVO;
import com.jiuqi.bde.common.dto.FixedFetchSourceRowSettingVO;
import com.jiuqi.bde.common.dto.FloatQueryFieldVO;
import com.jiuqi.bde.common.intf.Dimension;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.bde.common.vo.FetchSettingVO;
import com.jiuqi.gcreport.bde.fetchsetting.client.enums.BizTypeEnum;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dto.FetchSettingExcelContext;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.checker.impl.BizModelHandlerGather;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.consts.ExcelRowFetchSettingVO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.enums.FloatSettingTypeEnum;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.floatconfig.gather.FloatConfigExcelGather;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.gather.ImpExpHandleAdaptorGather;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.handler.RowHandler;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.intf.ImpExpDataField;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.intf.ImpExpDataLink;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.intf.ImpExpDataTable;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.intf.ImpExpFieldDefine;
import com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchFloatSettingDesService;
import com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchSettingDesService;
import com.jiuqi.gcreport.bde.fetchsetting.impl.utils.BdeLogicFormulaUtils;
import com.jiuqi.xlib.utils.CollectionUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractExportInnerRowHandler
implements RowHandler {
    @Autowired
    protected FetchSettingDesService fetchSettingDesService;
    @Autowired
    protected FetchFloatSettingDesService fetchFloatSettingDesService;
    @Autowired
    protected FloatConfigExcelGather floatConfigExcelGather;
    @Autowired
    private BizModelHandlerGather bizModelHandlerGather;
    @Autowired
    protected ImpExpHandleAdaptorGather impExpHandleAdaptorGather;
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractExportInnerRowHandler.class);

    protected List<ExcelRowFetchSettingVO> exportTemplateToVos(BizTypeEnum bizType, FetchSettingCond fetchSettingCond) {
        ArrayList<ExcelRowFetchSettingVO> fetchSourceRowImpSettingVOS = new ArrayList<ExcelRowFetchSettingVO>();
        List<ImpExpDataLink> dataLinks = this.getDataLinkDefines(bizType, fetchSettingCond.getFormId(), fetchSettingCond.getRegionId());
        for (ImpExpDataLink dataLink : dataLinks) {
            ImpExpDataField dataField = this.getDataField(bizType, fetchSettingCond.getFormId(), fetchSettingCond.getRegionId(), dataLink.getDataFieldId());
            if (dataField == null) {
                LOGGER.info("BDE\u53d6\u6570\u8bbe\u7f6e\u5bfc\u51fa\u6a21\u677f\uff0c\u6839\u636e\u6307\u6807\u6587\u672c\u3010{}\u3011\u83b7\u53d6\u6307\u6807\u4fe1\u606f\u5931\u8d25", (Object)dataLink.getDataFieldId());
                continue;
            }
            ImpExpDataTable dataTable = this.getDataTable(bizType, fetchSettingCond.getFormId(), fetchSettingCond.getRegionId(), dataField.getDataTableKey());
            if (dataTable == null) {
                LOGGER.info("BDE\u53d6\u6570\u8bbe\u7f6e\u5bfc\u51fa\u6a21\u677f\uff0c\u6839\u636eDataTableKey\u3010{}\u3011\u83b7\u53d6DataTable\u5931\u8d25", (Object)dataField.getDataTableKey());
                continue;
            }
            ImpExpFieldDefine fieldDefineInfo = this.getFieldDefineInfo(bizType, fetchSettingCond.getFormId(), fetchSettingCond.getRegionId(), dataTable, dataField, dataLink, null);
            fetchSourceRowImpSettingVOS.add(new ExcelRowFetchSettingVO(fieldDefineInfo.getCode(), fieldDefineInfo.getName()));
        }
        return fetchSourceRowImpSettingVOS;
    }

    protected List<ExcelRowFetchSettingVO> convert2RowFetchSetting(FetchSettingVO fetchSettingVO, FetchSettingExcelContext fetchSettingExcelContext, List<FloatQueryFieldVO> queryFields) {
        ArrayList<ExcelRowFetchSettingVO> fetchSourceRowImpSettingVOS = new ArrayList<ExcelRowFetchSettingVO>();
        ImpExpDataLink dataLink = this.getDataLinkDefine(fetchSettingExcelContext.getBizType(), fetchSettingVO.getFormId(), fetchSettingVO.getRegionId(), fetchSettingVO.getDataLinkId());
        if (dataLink == null) {
            LOGGER.error("\u672a\u627e\u5230{}\u5bf9\u5e94\u94fe\u63a5\u4fe1\u606f,\u8df3\u8fc7\u5bfc\u51fa", (Object)fetchSettingVO.getDataLinkId());
            return new ArrayList<ExcelRowFetchSettingVO>();
        }
        ImpExpDataField dataField = this.getDataField(fetchSettingExcelContext.getBizType(), fetchSettingVO.getFormId(), fetchSettingVO.getRegionId(), fetchSettingVO.getFieldDefineId());
        if (dataField == null) {
            LOGGER.error("\u672a\u627e\u5230{}\u5bf9\u5e94\u6307\u6807,\u8df3\u8fc7\u5bfc\u51fa", (Object)fetchSettingVO.getFieldDefineId());
            return new ArrayList<ExcelRowFetchSettingVO>();
        }
        ImpExpDataTable dataTable = this.getDataTable(fetchSettingExcelContext.getBizType(), fetchSettingVO.getFormId(), fetchSettingVO.getRegionId(), dataField.getDataTableKey());
        for (FixedAdaptSettingVO adaptSettingVO : fetchSettingVO.getFixedSettingData()) {
            try {
                adaptSettingVO.setLogicFormula(BdeLogicFormulaUtils.convertLogicFormulaToTitle(adaptSettingVO, fetchSettingExcelContext, queryFields));
            }
            catch (Exception e) {
                LOGGER.info("\u903b\u8f91\u8868\u8fbe\u5f0f\u8f6c\u5316\u5931\u8d25\uff0c\u5bfc\u51fa\u8df3\u8fc7", e);
                continue;
            }
            fetchSourceRowImpSettingVOS.addAll(this.convertAdaptSetting(fetchSettingExcelContext, adaptSettingVO, fetchSettingVO.getFormId(), fetchSettingVO.getRegionId(), dataTable, dataField, dataLink));
        }
        return fetchSourceRowImpSettingVOS;
    }

    private List<ExcelRowFetchSettingVO> convertAdaptSetting(FetchSettingExcelContext fetchSettingExcelContext, FixedAdaptSettingVO adaptSettingVO, String formKey, String regionKey, ImpExpDataTable dataTable, ImpExpDataField dataField, ImpExpDataLink dataLink) {
        ImpExpFieldDefine fieldDefineInfo = this.getFieldDefineInfo(fetchSettingExcelContext.getBizType(), formKey, regionKey, dataTable, dataField, dataLink, adaptSettingVO);
        ArrayList<ExcelRowFetchSettingVO> fetchSourceRowImpSettingVOS = new ArrayList<ExcelRowFetchSettingVO>();
        if (!StringUtils.isEmpty((String)adaptSettingVO.getLogicFormula()) && (adaptSettingVO.getBizModelFormula() == null || adaptSettingVO.getBizModelFormula().isEmpty())) {
            ExcelRowFetchSettingVO excelRowFetchSettingVO = new ExcelRowFetchSettingVO();
            excelRowFetchSettingVO.setFieldDefineCode(fieldDefineInfo.getCode());
            excelRowFetchSettingVO.setFieldDefineTitle(fieldDefineInfo.getName());
            excelRowFetchSettingVO.setLogicFormula(adaptSettingVO.getLogicFormula());
            excelRowFetchSettingVO.setAdaptFormula(adaptSettingVO.getAdaptFormula());
            excelRowFetchSettingVO.setWildcardFormula(adaptSettingVO.getWildcardFormula());
            excelRowFetchSettingVO.setDescription(adaptSettingVO.getDescription());
            excelRowFetchSettingVO.setFieldType(FloatSettingTypeEnum.CUSTOM_RULE.getName());
            fetchSourceRowImpSettingVOS.add(excelRowFetchSettingVO);
            return fetchSourceRowImpSettingVOS;
        }
        for (Map.Entry bizModelFormulaEntry : adaptSettingVO.getBizModelFormula().entrySet()) {
            BizModelDTO bizModelDTO = fetchSettingExcelContext.getBizModelByCode((String)bizModelFormulaEntry.getKey());
            if (bizModelDTO == null) {
                LOGGER.error("\u6839\u636e\u4ee3\u7801\u3010{}\u3011\u672a\u627e\u5230\u4e1a\u52a1\u6a21\u578b,\u8bf7\u68c0\u67e5\u4e1a\u52a1\u6a21\u578b\u914d\u7f6e", bizModelFormulaEntry.getKey());
                continue;
            }
            for (FixedFetchSourceRowSettingVO fetchSourceRowSettingVO : (List)bizModelFormulaEntry.getValue()) {
                ExcelRowFetchSettingVO excelRowFetchSettingVO = null;
                try {
                    excelRowFetchSettingVO = this.bizModelHandlerGather.getBizCheckerByBizModel(bizModelDTO.getComputationModelCode()).exportDataHandle(fetchSourceRowSettingVO, bizModelDTO);
                    excelRowFetchSettingVO.setFieldDefineCode(fieldDefineInfo.getCode());
                    if (!StringUtils.isEmpty((String)excelRowFetchSettingVO.getAgeGroup())) {
                        excelRowFetchSettingVO.setAgingGroupName(fetchSettingExcelContext.getAgingBaseDataCodeMap().get(excelRowFetchSettingVO.getAgeGroup()).getTitle());
                    }
                    excelRowFetchSettingVO.setFieldDefineTitle(fieldDefineInfo.getName());
                    excelRowFetchSettingVO.setLogicFormula(adaptSettingVO.getLogicFormula());
                    excelRowFetchSettingVO.setAdaptFormula(adaptSettingVO.getAdaptFormula());
                    excelRowFetchSettingVO.setWildcardFormula(adaptSettingVO.getWildcardFormula());
                    excelRowFetchSettingVO.setDescription(adaptSettingVO.getDescription());
                    excelRowFetchSettingVO.setFetchSourceName(bizModelDTO.getName());
                    excelRowFetchSettingVO.setFieldType(FloatSettingTypeEnum.CUSTOM_RULE.getName());
                    excelRowFetchSettingVO.setDimSettingValue(StringUtils.isEmpty((String)fetchSourceRowSettingVO.getDimensionSetting()) ? new ArrayList<Dimension>() : (List)JsonUtils.readValue((String)fetchSourceRowSettingVO.getDimensionSetting(), (TypeReference)new TypeReference<List<Dimension>>(){}));
                    if (StringUtils.isEmpty((String)fetchSourceRowSettingVO.getDimType())) {
                        fetchSourceRowImpSettingVOS.add(excelRowFetchSettingVO);
                        continue;
                    }
                    StringJoiner dimTypeNames = new StringJoiner(",");
                    List<String> dimTypes = Arrays.asList(excelRowFetchSettingVO.getDimType().split(","));
                    for (String dimType : dimTypes) {
                        if ("SUBJECTCODE".equals(dimType)) {
                            dimTypeNames.add(FetchFixedFieldEnum.SUBJECTCODE.getName());
                            continue;
                        }
                        dimTypeNames.add(fetchSettingExcelContext.getDimensionVOByCode(dimType).getTitle());
                    }
                    excelRowFetchSettingVO.setDimTypeName(dimTypeNames.toString());
                }
                catch (Exception e) {
                    LOGGER.info("\u53d6\u6570\u8bbe\u7f6e\u5bfc\u51fa\u8bbe\u7f6e\u8df3\u8fc7", e);
                    continue;
                }
                fetchSourceRowImpSettingVOS.add(excelRowFetchSettingVO);
            }
        }
        return fetchSourceRowImpSettingVOS;
    }

    protected Map<String, ImpExpDataLink> getDataLinkDefineCodeMap(BizTypeEnum bizType, String formId, String regionId) {
        HashMap<String, ImpExpDataLink> dataLinkDefineHashMap = new HashMap<String, ImpExpDataLink>();
        List<ImpExpDataLink> dataLinks = this.getDataLinkDefines(bizType, formId, regionId);
        for (ImpExpDataLink dataLink : dataLinks) {
            ImpExpDataField dataField = this.getDataField(bizType, formId, regionId, dataLink.getDataFieldId());
            if (dataField == null) {
                LOGGER.info("BDE\u53d6\u6570\u8bbe\u7f6e\u5bfc\u51fa\u6a21\u677f\uff0c\u6839\u636e\u6307\u6807\u6587\u672c\u3010{}\u3011\u83b7\u53d6\u6307\u6807\u4fe1\u606f\u5931\u8d25", (Object)dataLink.getDataFieldId());
                continue;
            }
            ImpExpDataTable dataTable = this.getDataTable(bizType, formId, regionId, dataField.getDataTableKey());
            if (dataTable == null) {
                LOGGER.info("BDE\u53d6\u6570\u8bbe\u7f6e\u5bfc\u51fa\u6a21\u677f\uff0c\u6839\u636eDataTableKey\u3010{}\u3011\u83b7\u53d6DataTable\u5931\u8d25", (Object)dataField.getDataTableKey());
                continue;
            }
            ImpExpFieldDefine fieldDefineInfo = this.getFieldDefineInfo(bizType, formId, regionId, dataTable, dataField, dataLink, null);
            dataLinkDefineHashMap.put(fieldDefineInfo.getCode(), dataLink);
        }
        return dataLinkDefineHashMap;
    }

    protected Map<String, ImpExpDataLink> getDataLinkDefineNameMap(BizTypeEnum bizType, String formId, String regionId) {
        HashMap<String, ImpExpDataLink> dataLinkDefineHashMap = new HashMap<String, ImpExpDataLink>();
        List<ImpExpDataLink> dataLinks = this.getDataLinkDefines(bizType, formId, regionId);
        for (ImpExpDataLink dataLink : dataLinks) {
            ImpExpDataField dataField = this.getDataField(bizType, formId, regionId, dataLink.getDataFieldId());
            if (dataField == null) {
                LOGGER.info("BDE\u53d6\u6570\u8bbe\u7f6e\u5bfc\u51fa\u6a21\u677f\uff0c\u6839\u636e\u6307\u6807\u6587\u672c\u3010{}\u3011\u83b7\u53d6\u6307\u6807\u4fe1\u606f\u5931\u8d25", (Object)dataLink.getDataFieldId());
                continue;
            }
            dataLinkDefineHashMap.put(dataField.getName(), dataLink);
        }
        return dataLinkDefineHashMap;
    }

    protected ExcelRowFetchSettingVO convertToFetchSetting(ExcelRowFetchSettingVO excelRowFetchSettingVO, FetchSettingExcelContext fetchSettingExcelContext) {
        BizModelDTO bizModelDTO = fetchSettingExcelContext.getBizModelByCode(excelRowFetchSettingVO.getFetchSourceCode());
        ExcelRowFetchSettingVO fetchSourceRowImpSettingVO = this.bizModelHandlerGather.getBizCheckerByBizModel(bizModelDTO.getComputationModelCode()).importDataHandle(excelRowFetchSettingVO, bizModelDTO);
        fetchSourceRowImpSettingVO.setId(UUIDUtils.newHalfGUIDStr());
        fetchSourceRowImpSettingVO.setDimSettingValueMap(excelRowFetchSettingVO.getDimSettingValueMap());
        if (!StringUtils.isEmpty((String)excelRowFetchSettingVO.getAgingRangeTypeName())) {
            fetchSourceRowImpSettingVO.setAgingRangeType(AgingPeriodTypeEnum.getAgingPeriodTypeEnumByName((String)excelRowFetchSettingVO.getAgingRangeTypeName()).getCode());
        }
        if (!StringUtils.isEmpty((String)excelRowFetchSettingVO.getDimTypeName())) {
            StringJoiner dimTypes = new StringJoiner(",");
            List<String> dimTypeNames = Arrays.asList(excelRowFetchSettingVO.getDimTypeName().split(","));
            for (String dimTypeName : dimTypeNames) {
                if (FetchFixedFieldEnum.SUBJECTCODE.getName().equals(dimTypeName)) {
                    dimTypes.add("SUBJECTCODE");
                    continue;
                }
                dimTypes.add(fetchSettingExcelContext.getDimensionVOByName(dimTypeName).getCode());
            }
            fetchSourceRowImpSettingVO.setDimType(dimTypes.toString());
        }
        if (!CollectionUtils.isEmpty(excelRowFetchSettingVO.getDimSettingValueMap().values())) {
            fetchSourceRowImpSettingVO.setDimensionSetting(JsonUtils.writeValueAsString(excelRowFetchSettingVO.getDimSettingValueMap().values()));
            ArrayList<String> dimComb = new ArrayList<String>();
            for (Dimension dimension : excelRowFetchSettingVO.getDimSettingValueMap().values()) {
                dimComb.add(dimension.getDimCode());
            }
            fetchSourceRowImpSettingVO.setDimComb(dimComb);
        }
        return fetchSourceRowImpSettingVO;
    }

    protected ExcelRowFetchSettingVO covertQueryFieldToName(ExcelRowFetchSettingVO excelRowFetchSettingVO, Map<String, String> titleToNameMap) {
        excelRowFetchSettingVO.setSubjectCode(this.getFloatQueryFieldName(excelRowFetchSettingVO.getSubjectCode(), titleToNameMap));
        excelRowFetchSettingVO.setExcludeSubjectCode(this.getFloatQueryFieldName(excelRowFetchSettingVO.getExcludeSubjectCode(), titleToNameMap));
        excelRowFetchSettingVO.setCashCode(this.getFloatQueryFieldName(excelRowFetchSettingVO.getCashCode(), titleToNameMap));
        excelRowFetchSettingVO.setReclassSubjCode(this.getFloatQueryFieldName(excelRowFetchSettingVO.getReclassSubjCode(), titleToNameMap));
        excelRowFetchSettingVO.setReclassSrcSubjCode(this.getFloatQueryFieldName(excelRowFetchSettingVO.getReclassSubjCode(), titleToNameMap));
        ArrayList<Dimension> dimensions = new ArrayList<Dimension>();
        for (Map.Entry<String, Dimension> dimensionEntry : excelRowFetchSettingVO.getDimSettingValueMap().entrySet()) {
            Dimension dimension = dimensionEntry.getValue();
            dimension.setDimValue(this.getFloatQueryFieldName(dimension.getDimValue(), titleToNameMap));
            dimensions.add(dimension);
        }
        if (!CollectionUtils.isEmpty(dimensions)) {
            excelRowFetchSettingVO.setDimensionSetting(JsonUtils.writeValueAsString(dimensions));
        }
        return excelRowFetchSettingVO;
    }

    private String getFloatQueryFieldName(String value, Map<String, String> titleToNameMap) {
        if (!StringUtils.isEmpty((String)value) && titleToNameMap.get(value) != null) {
            return "${" + titleToNameMap.get(value) + "}";
        }
        return value;
    }

    protected ImpExpFieldDefine getFieldDefineInfo(BizTypeEnum bizType, String formKey, String regionKey, ImpExpDataTable dataTable, ImpExpDataField dataField, ImpExpDataLink dataLink, FixedAdaptSettingVO adaptSettingVO) {
        ImpExpFieldDefine fieldDefineInfo = this.impExpHandleAdaptorGather.getHandleAdaptor(bizType.getCode()).getFieldDefineInfo(formKey, regionKey, dataTable, dataField, dataLink, adaptSettingVO);
        Assert.isNotNull((Object)fieldDefineInfo);
        return fieldDefineInfo;
    }

    protected List<ImpExpDataLink> getDataLinkDefines(BizTypeEnum bizType, String formKey, String regionKey) {
        return this.impExpHandleAdaptorGather.getHandleAdaptor(bizType.getCode()).getDataLinkDefines(formKey, regionKey);
    }

    protected ImpExpDataLink getDataLinkDefine(BizTypeEnum bizType, String formKey, String regionKey, String dataLinkKey) {
        return this.impExpHandleAdaptorGather.getHandleAdaptor(bizType.getCode()).getDataLinkDefine(formKey, regionKey, dataLinkKey);
    }

    protected ImpExpDataField getDataField(BizTypeEnum bizType, String formKey, String regionKey, String dataFieldId) {
        return this.impExpHandleAdaptorGather.getHandleAdaptor(bizType.getCode()).getDataField(formKey, regionKey, dataFieldId);
    }

    protected ImpExpDataTable getDataTable(BizTypeEnum bizType, String formKey, String regionKey, String dataTableId) {
        return this.impExpHandleAdaptorGather.getHandleAdaptor(bizType.getCode()).getDataTable(formKey, regionKey, dataTableId);
    }
}

