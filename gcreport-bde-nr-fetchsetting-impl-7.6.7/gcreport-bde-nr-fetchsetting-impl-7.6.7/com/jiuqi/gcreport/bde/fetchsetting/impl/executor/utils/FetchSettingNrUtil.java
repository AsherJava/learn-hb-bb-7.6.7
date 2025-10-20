/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.bde.bizmodel.client.BizModelClient
 *  com.jiuqi.bde.bizmodel.client.dto.BaseDataBizModelDTO
 *  com.jiuqi.bde.bizmodel.client.dto.BizModelAllPropsDTO
 *  com.jiuqi.bde.bizmodel.client.dto.BizModelDTO
 *  com.jiuqi.bde.bizmodel.client.dto.CustomBizModelDTO
 *  com.jiuqi.bde.bizmodel.client.dto.FinBizModelDTO
 *  com.jiuqi.bde.bizmodel.client.vo.Dimension
 *  com.jiuqi.bde.common.dto.FixedAdaptSettingVO
 *  com.jiuqi.bde.common.dto.FixedFetchSourceRowSettingVO
 *  com.jiuqi.bde.common.dto.FixedFieldDefineSettingVO
 *  com.jiuqi.bde.common.dto.FloatQueryFieldVO
 *  com.jiuqi.bde.common.dto.FloatRegionConfigVO
 *  com.jiuqi.bde.common.dto.FloatZbMappingVO
 *  com.jiuqi.bde.common.dto.QueryConfigInfo
 *  com.jiuqi.bde.common.exception.BdeRuntimeException
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.bde.common.utils.BdeClientUtil
 *  com.jiuqi.gcreport.bde.common.vo.FetchSettingVO
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSchemeVO
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FixedAdaptSettingDTO
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FixedFieldDefineSettingDTO
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchSettingDesEO
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchSettingEO
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.utils.BizTypeValidator
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.utils.FetchSettingUtil
 *  com.jiuqi.np.dataengine.common.DataTypesConvert
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.log.BeanUtils
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeDataLinkService
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  com.jiuqi.va.query.fetch.vo.FetchQueryFiledVO
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.executor.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.bde.bizmodel.client.BizModelClient;
import com.jiuqi.bde.bizmodel.client.dto.BaseDataBizModelDTO;
import com.jiuqi.bde.bizmodel.client.dto.BizModelAllPropsDTO;
import com.jiuqi.bde.bizmodel.client.dto.BizModelDTO;
import com.jiuqi.bde.bizmodel.client.dto.CustomBizModelDTO;
import com.jiuqi.bde.bizmodel.client.dto.FinBizModelDTO;
import com.jiuqi.bde.bizmodel.client.vo.Dimension;
import com.jiuqi.bde.common.dto.FixedAdaptSettingVO;
import com.jiuqi.bde.common.dto.FixedFetchSourceRowSettingVO;
import com.jiuqi.bde.common.dto.FixedFieldDefineSettingVO;
import com.jiuqi.bde.common.dto.FloatQueryFieldVO;
import com.jiuqi.bde.common.dto.FloatRegionConfigVO;
import com.jiuqi.bde.common.dto.FloatZbMappingVO;
import com.jiuqi.bde.common.dto.QueryConfigInfo;
import com.jiuqi.bde.common.exception.BdeRuntimeException;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.bde.common.utils.BdeClientUtil;
import com.jiuqi.gcreport.bde.common.vo.FetchSettingVO;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSchemeVO;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FixedAdaptSettingDTO;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FixedFieldDefineSettingDTO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dto.FetchSettingExcelContext;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dto.FetchSettingExportContext;
import com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchSettingDesEO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchSettingEO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.floatconfig.FloatConfigExcelHandle;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.floatconfig.gather.FloatConfigExcelGather;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.intf.ImpExpFormDefine;
import com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchSchemeNrService;
import com.jiuqi.gcreport.bde.fetchsetting.impl.utils.BizTypeValidator;
import com.jiuqi.gcreport.bde.fetchsetting.impl.utils.FetchSettingUtil;
import com.jiuqi.np.dataengine.common.DataTypesConvert;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.log.BeanUtils;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeDataLinkService;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.query.fetch.vo.FetchQueryFiledVO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class FetchSettingNrUtil
extends FetchSettingUtil {
    public static final String ASS_SETTING = "\u8f85\u52a9\u7ef4\u5ea6\u8bbe\u7f6e";
    public static final String FILLING_INSTRUCTIONS = "\u586b\u62a5\u8bf4\u660e";
    public static final String FLOAT_REGION_MARK = "\u6d6e\u52a8\u533a\u57df\u6807\u8bc6";
    public static final String FIXED = "FIXED";
    public static final String FLOAT = "FLOAT";
    public static final Integer FLOAT_HEAD_SIZE = 4;
    public static final Integer FIX_HEAD_SIZE = 2;
    public static final String FETCH_SOURCE = "FETCH_SOURCE";
    public static final String DIMENSION = "DIMENSION";
    public static final String FIXCOLUMNS = "FIXCOLUMNS";
    public static final String FLOATCOLUMNS = "FLOATCOLUMNS";
    public static final String DEFAULT = "DEFAULT";
    public static final String MD_AGING = "MD_AGING";
    private static final String FN_LOCATION = "%1$s[%2$s,%3$s]%4$s";
    public static final String FORMULA = "=";

    public static List<String[]> listExportFloatExcelTitles(DataRegionDefine regionDefine, FloatRegionConfigVO fetchFloatSettingVO, FetchSettingExportContext fetchSettingExcelContext) {
        String[] floatSecondRowTitleArray;
        ArrayList<String[]> titles = new ArrayList<String[]>();
        String[] floatFirstRowTitleArray = new String[]{FLOAT_REGION_MARK, FetchSettingNrUtil.getRegionTopStr(regionDefine.getRegionTop()), "\u6d6e\u52a8\u533a\u57df\u540d\u79f0", regionDefine.getTitle()};
        if (fetchFloatSettingVO != null) {
            FloatConfigExcelHandle floatConfigExcelHandle = ((FloatConfigExcelGather)BeanUtils.getBean(FloatConfigExcelGather.class)).getByFloatConfigCode(fetchFloatSettingVO.getQueryType());
            String floatConfig = floatConfigExcelHandle.initFloatConfig(fetchFloatSettingVO, fetchSettingExcelContext);
            floatSecondRowTitleArray = new String[]{"\u6d6e\u52a8\u884c\u8bbe\u7f6e\u7c7b\u578b", StringUtils.isEmpty((String)fetchFloatSettingVO.getQueryType()) ? "" : fetchSettingExcelContext.getFloatRegionHandlerByCode(fetchFloatSettingVO.getQueryType()).getTitle(), "\u6d6e\u52a8\u884c\u8bbe\u7f6e\u5185\u5bb9", floatConfig, "\u53d6\u6570\u89c4\u5219\u53ef\u9009\u5b57\u6bb5", FetchSettingNrUtil.getFieldTitleToFieldVOMap(fetchFloatSettingVO).stream().collect(Collectors.joining(","))};
        } else {
            floatSecondRowTitleArray = new String[]{"\u6d6e\u52a8\u884c\u8bbe\u7f6e\u7c7b\u578b", "", "\u6d6e\u52a8\u884c\u8bbe\u7f6e\u5185\u5bb9", "", "\u53d6\u6570\u89c4\u5219\u53ef\u9009\u5b57\u6bb5", ""};
        }
        titles.add(floatFirstRowTitleArray);
        titles.add(floatSecondRowTitleArray);
        return titles;
    }

    private static List<String> getFieldTitleToFieldVOMap(FloatRegionConfigVO fetchFloatSettingVO) {
        if (fetchFloatSettingVO.getQueryConfigInfo() == null) {
            return new ArrayList<String>();
        }
        return fetchFloatSettingVO.getQueryConfigInfo().getQueryFields().stream().map(FloatQueryFieldVO::getTitle).collect(Collectors.toList());
    }

    public static FetchSettingVO convertFetchSettingDesEOToVo(FetchSettingDesEO fetchSettingDesEO) {
        if (Objects.isNull(fetchSettingDesEO)) {
            return null;
        }
        FetchSettingVO fetchSettingVO = new FetchSettingVO();
        org.springframework.beans.BeanUtils.copyProperties(fetchSettingDesEO, fetchSettingVO);
        fetchSettingVO.setFixedSettingData(FetchSettingNrUtil.convertFixedSettingDataDesVOFormEo(fetchSettingDesEO));
        return fetchSettingVO;
    }

    public static List<FixedAdaptSettingVO> convertFixedSettingDataDesVOFormEo(FetchSettingDesEO fetchSettingDesEO) {
        if (Objects.isNull(fetchSettingDesEO) || Objects.isNull(fetchSettingDesEO.getFixedSettingData())) {
            return new ArrayList<FixedAdaptSettingVO>();
        }
        String fixedSettingDataStr = fetchSettingDesEO.getFixedSettingData();
        return (List)JsonUtils.readValue((String)fixedSettingDataStr, (TypeReference)new TypeReference<List<FixedAdaptSettingVO>>(){});
    }

    public static List<FixedAdaptSettingVO> convertEnableFixedSettingDataStr(String fixedSettingDataStr) {
        if (StringUtils.isEmpty((String)fixedSettingDataStr)) {
            return new ArrayList<FixedAdaptSettingVO>();
        }
        List fixedAdaptSettingVOS = (List)JsonUtils.readValue((String)fixedSettingDataStr, (TypeReference)new TypeReference<List<FixedAdaptSettingVO>>(){});
        return fixedAdaptSettingVOS.stream().filter(item -> Objects.isNull(item.getStopFlag()) || item.getStopFlag() == false).collect(Collectors.toList());
    }

    public static FixedFieldDefineSettingDTO getRuntimeFixedZbSettingVOFromEo(FetchSettingEO fetchSettingEO, IRunTimeViewController iRunTimeViewController, IRuntimeDataLinkService runtimeDataLinkService) {
        if (Objects.isNull(fetchSettingEO)) {
            return null;
        }
        FetchSchemeNrService fetchSchemeService = (FetchSchemeNrService)ApplicationContextRegister.getBean(FetchSchemeNrService.class);
        FetchSchemeVO fetchScheme = fetchSchemeService.getFetchScheme(fetchSettingEO.getFetchSchemeId());
        String bizType = fetchScheme.getBizType();
        String adaptSettingListStr = fetchSettingEO.getFixedSettingData();
        List<FixedAdaptSettingVO> fixedAdaptSettingVOS = FetchSettingNrUtil.convertEnableFixedSettingDataStr(adaptSettingListStr);
        LinkedList<FixedAdaptSettingDTO> fixedSettingData = new LinkedList<FixedAdaptSettingDTO>();
        for (FixedAdaptSettingVO adaptSettingVO : fixedAdaptSettingVOS) {
            FixedAdaptSettingDTO adaptSettingDTO = new FixedAdaptSettingDTO();
            org.springframework.beans.BeanUtils.copyProperties(adaptSettingVO, adaptSettingDTO);
            Map bizModelFormula = adaptSettingVO.getBizModelFormula();
            LinkedHashMap bizModelFormulaDTO = new LinkedHashMap();
            for (Map.Entry fetchSourceIdBizModelFormulaEntry : bizModelFormula.entrySet()) {
                String fetchSourceId = (String)fetchSourceIdBizModelFormulaEntry.getKey();
                LinkedList<Map> fetchSourceRowSettingDTOList = new LinkedList<Map>();
                for (FixedFetchSourceRowSettingVO fetchSourceRowSettingVO : (List)fetchSourceIdBizModelFormulaEntry.getValue()) {
                    String fetchSourceRowSettingStr = JSONUtil.toJSONString((Object)fetchSourceRowSettingVO);
                    Map fetchSourceRowSettingMap = JSONUtil.parseMap((String)fetchSourceRowSettingStr);
                    String dimensionSetting = fetchSourceRowSettingVO.getDimensionSetting();
                    if (!StringUtils.isEmpty((String)dimensionSetting)) {
                        List dimListMap = JSONUtil.parseMapArray((String)dimensionSetting, String.class, String.class);
                        for (Map dimMap : dimListMap) {
                            String dimCode = (String)dimMap.get("dimCode");
                            String dimValue = (String)dimMap.get("dimValue");
                            String dimRule = (String)dimMap.get("dimRule");
                            fetchSourceRowSettingMap.put(dimCode, dimValue);
                            fetchSourceRowSettingMap.put(dimCode + "MatchRule", dimRule);
                        }
                    }
                    fetchSourceRowSettingDTOList.add(fetchSourceRowSettingMap);
                }
                bizModelFormulaDTO.put(fetchSourceId, fetchSourceRowSettingDTOList);
            }
            adaptSettingDTO.setBizModelFormula(bizModelFormulaDTO);
            fixedSettingData.add(adaptSettingDTO);
        }
        FixedFieldDefineSettingDTO fieldDefineSettingVO = new FixedFieldDefineSettingDTO();
        fieldDefineSettingVO.setId(fetchSettingEO.getId());
        fieldDefineSettingVO.setDataLinkId(fetchSettingEO.getDataLinkId());
        fieldDefineSettingVO.setRegionId(fetchSettingEO.getRegionId());
        fieldDefineSettingVO.setFieldDefineId(fetchSettingEO.getFieldDefineId());
        fieldDefineSettingVO.setFixedSettingData(fixedSettingData);
        FieldDefine fieldDefine = null;
        try {
            DataLinkDefine dataLink = runtimeDataLinkService.getDataLink(fetchSettingEO.getDataLinkId(), fetchSettingEO.getFormSchemeId());
            fieldDefine = iRunTimeViewController.queryFieldDefine(fetchSettingEO.getFieldDefineId());
            if (Objects.nonNull(fieldDefine)) {
                if (Objects.nonNull(dataLink)) {
                    fieldDefineSettingVO.setFieldDefineTitle(String.format(FN_LOCATION, fieldDefine.getCode(), dataLink.getRowNum(), dataLink.getColNum(), fieldDefine.getTitle()));
                } else {
                    fieldDefineSettingVO.setFieldDefineTitle(fieldDefine.getTitle());
                }
                fieldDefineSettingVO.setFieldDefineType(Integer.valueOf(DataTypesConvert.fieldTypeToDataType((FieldType)fieldDefine.getType())));
                fieldDefineSettingVO.setFieldDefineSize(fieldDefine.getSize());
                fieldDefineSettingVO.setFieldDefineFractionDigits(fieldDefine.getFractionDigits());
            } else {
                BizTypeValidator.isValidBud((String)bizType, (String)String.format("\u6839\u636e\u6307\u6807ID%1$s\u672a\u83b7\u53d6\u5230\u6307\u6807\u5bf9\u8c61", fetchSettingEO.getFieldDefineId()));
            }
        }
        catch (Exception e) {
            throw new BdeRuntimeException((Throwable)e);
        }
        return fieldDefineSettingVO;
    }

    public static FixedFieldDefineSettingVO convertFixedZBSettingDesVOFromEo(FetchSettingDesEO fetchSettingDesEO, IRunTimeViewController iRunTimeViewController) {
        if (Objects.isNull(fetchSettingDesEO)) {
            return null;
        }
        String adaptSettingListStr = fetchSettingDesEO.getFixedSettingData();
        List fixedAdaptSettingVOS = (List)JsonUtils.readValue((String)adaptSettingListStr, (TypeReference)new TypeReference<List<FixedAdaptSettingVO>>(){});
        FixedFieldDefineSettingVO fieldDefineSettingVO = new FixedFieldDefineSettingVO();
        fieldDefineSettingVO.setFieldDefineId(fetchSettingDesEO.getFieldDefineId());
        fieldDefineSettingVO.setFixedSettingData(fixedAdaptSettingVOS);
        FieldDefine fieldDefine = null;
        try {
            fieldDefine = iRunTimeViewController.queryFieldDefine(fetchSettingDesEO.getFieldDefineId());
            fieldDefineSettingVO.setFieldDefineTitle(fieldDefine.getTitle());
            fieldDefineSettingVO.setFieldDefineType(Integer.valueOf(DataTypesConvert.fieldTypeToDataType((FieldType)fieldDefine.getType())));
            fieldDefineSettingVO.setFieldDefineSize(fieldDefine.getSize());
            fieldDefineSettingVO.setFieldDefineFractionDigits(fieldDefine.getFractionDigits());
        }
        catch (Exception e) {
            throw new BdeRuntimeException((Throwable)e);
        }
        return fieldDefineSettingVO;
    }

    public static List<FloatQueryFieldVO> convertFetchQueryFiledVO(List<FetchQueryFiledVO> fetchQueryFiledVOS) {
        ArrayList<FloatQueryFieldVO> queryFieldVOS = new ArrayList<FloatQueryFieldVO>();
        for (FetchQueryFiledVO fetchQueryFiledVO : fetchQueryFiledVOS) {
            FloatQueryFieldVO queryFieldVO = new FloatQueryFieldVO();
            org.springframework.beans.BeanUtils.copyProperties(fetchQueryFiledVO, queryFieldVO);
            queryFieldVOS.add(queryFieldVO);
        }
        return queryFieldVOS;
    }

    public static List<Integer> getFloatHeadIndexList(List<Object[]> rowDatas) {
        ArrayList<Integer> headIndexList = new ArrayList<Integer>();
        for (int i = 0; i < rowDatas.size(); ++i) {
            if (rowDatas.get(i)[0] == null || StringUtils.isEmpty((String)rowDatas.get(i)[0].toString()) || !FLOAT_REGION_MARK.equals(rowDatas.get(i)[0].toString())) continue;
            headIndexList.add(i);
        }
        headIndexList.add(rowDatas.size());
        return headIndexList;
    }

    public static String convertNorMalTitleToFloatArgs(String normalTitle) {
        if (StringUtils.isEmpty((String)normalTitle)) {
            return "";
        }
        if (!normalTitle.contains("$")) {
            return "${" + normalTitle + "}";
        }
        return normalTitle;
    }

    public static String nullConvertEmptyString(Object str) {
        return str == null ? "" : str.toString();
    }

    public static FetchSettingCond getFetchSettingCond(FetchSettingExcelContext fetchSettingExportContext, String formKey, String regionKey) {
        return new FetchSettingCond(fetchSettingExportContext.getFetchSchemeId(), fetchSettingExportContext.getFormSchemeId(), formKey, regionKey);
    }

    public static String getSheetTitleByForm(ImpExpFormDefine formDefine) {
        return formDefine.getName() + "|" + formDefine.getCode();
    }

    public static String getRegionTopStr(int regionTop) {
        return String.format("F%1$d", regionTop);
    }

    public static String getRpFieldDefineCode(String dataTableCode, String dataFieldCode) {
        return dataTableCode + "[" + dataFieldCode + "]";
    }

    public static List<BizModelDTO> getBizModelDTOs() {
        ArrayList bizModelDTOS = new ArrayList();
        BizModelClient bizModelClient = (BizModelClient)ApplicationContextRegister.getBean(BizModelClient.class);
        List finBizModelDTO = (List)BdeClientUtil.parseResponse((BusinessResponseEntity)bizModelClient.listByFin());
        List customBizModelDTO = (List)BdeClientUtil.parseResponse((BusinessResponseEntity)bizModelClient.listByCustom());
        List baseDataBizModelDTO = (List)BdeClientUtil.parseResponse((BusinessResponseEntity)bizModelClient.listByBaseData());
        List bizModelDTO = (List)BdeClientUtil.parseResponse((BusinessResponseEntity)bizModelClient.listByTfv());
        bizModelDTOS.addAll(baseDataBizModelDTO);
        bizModelDTOS.addAll(finBizModelDTO);
        bizModelDTOS.addAll(customBizModelDTO);
        bizModelDTOS.addAll(bizModelDTO);
        return bizModelDTOS.stream().filter(item -> item.getStopFlag() != null && item.getStopFlag() != 1).collect(Collectors.toList());
    }

    public static List<String> getBizModelFields(BizModelDTO bizModelDTO) {
        if (bizModelDTO instanceof CustomBizModelDTO) {
            return ((CustomBizModelDTO)bizModelDTO).getFetchFields();
        }
        if (bizModelDTO instanceof FinBizModelDTO) {
            return ((FinBizModelDTO)bizModelDTO).getFetchTypes();
        }
        if (bizModelDTO instanceof BaseDataBizModelDTO) {
            return ((BaseDataBizModelDTO)bizModelDTO).getFetchFields();
        }
        return new ArrayList<String>();
    }

    public static List<String> getDimensions(BizModelAllPropsDTO bizModelDTO) {
        if (CollectionUtils.isEmpty((Collection)bizModelDTO.getDimensions())) {
            return new ArrayList<String>();
        }
        return bizModelDTO.getDimensions().stream().map(Dimension::getDimensionCode).collect(Collectors.toList());
    }

    public static Map<String, String> getDataLinkIdQyertFieldMap(FloatRegionConfigVO fetchFloatSetting) {
        QueryConfigInfo queryConfigInfo;
        if (null != fetchFloatSetting && null != fetchFloatSetting.getQueryConfigInfo() && null != (queryConfigInfo = fetchFloatSetting.getQueryConfigInfo()) && !CollectionUtils.isEmpty((Collection)queryConfigInfo.getZbMapping())) {
            return FetchSettingNrUtil.getFloatSettingDataLinkIdQueryFieldTitleMapping(queryConfigInfo);
        }
        return new HashMap<String, String>();
    }

    public static Map<String, String> getFloatSettingDataLinkIdQueryFieldTitleMapping(QueryConfigInfo queryConfigInfo) {
        if (CollectionUtils.isEmpty((Collection)queryConfigInfo.getZbMapping())) {
            return new HashMap<String, String>();
        }
        Map<String, String> nameToTitleMap = queryConfigInfo.getQueryFields().stream().collect(Collectors.toMap(FloatQueryFieldVO::getName, FloatQueryFieldVO::getTitle, (o1, o2) -> o1));
        return queryConfigInfo.getZbMapping().stream().collect(Collectors.toMap(FloatZbMappingVO::getDataLinkId, zbMappingVO -> {
            if (FORMULA.equals(zbMappingVO.getQueryName())) {
                return FORMULA;
            }
            String patternStr = "(?<=\\$\\{)[^}]*(?=})";
            Pattern pattern = Pattern.compile(patternStr);
            Matcher matcher = pattern.matcher(zbMappingVO.getQueryName());
            if (matcher.find()) {
                String code = matcher.group(0);
                return StringUtils.isEmpty((String)((String)nameToTitleMap.get(code))) ? code : (String)nameToTitleMap.get(code);
            }
            return zbMappingVO.getQueryName();
        }, (last, next) -> next));
    }
}

