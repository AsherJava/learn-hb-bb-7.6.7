/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bde.bizmodel.client.dto.BizModelDTO
 *  com.jiuqi.bde.bizmodel.client.intf.IBizComputationModel
 *  com.jiuqi.bde.bizmodel.client.vo.BizModelColumnDefineVO
 *  com.jiuqi.bde.bizmodel.client.vo.ExtInfoParamVO
 *  com.jiuqi.bde.bizmodel.client.vo.ExtInfoResultVO
 *  com.jiuqi.bde.bizmodel.client.vo.FetchSettingExtInfoVo
 *  com.jiuqi.bde.bizmodel.client.vo.QueryFieldVO
 *  com.jiuqi.bde.bizmodel.define.gather.IBizModelGather
 *  com.jiuqi.bde.common.constant.ArgumentValueEnum
 *  com.jiuqi.bde.common.dto.ColumnDefineVO
 *  com.jiuqi.bde.common.dto.FetchSettingVO
 *  com.jiuqi.bde.common.dto.FixedAdaptSettingVO
 *  com.jiuqi.bde.common.dto.FixedFetchSourceRowSettingVO
 *  com.jiuqi.bde.common.dto.SelectOptionVO
 *  com.jiuqi.bde.common.intf.Dimension
 *  com.jiuqi.bde.common.util.VariableParseUtil
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$AuthType
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  tk.mybatis.mapper.util.StringUtil
 */
package com.jiuqi.bde.bizmodel.impl.model.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bde.bizmodel.client.dto.BizModelDTO;
import com.jiuqi.bde.bizmodel.client.intf.IBizComputationModel;
import com.jiuqi.bde.bizmodel.client.vo.BizModelColumnDefineVO;
import com.jiuqi.bde.bizmodel.client.vo.ExtInfoParamVO;
import com.jiuqi.bde.bizmodel.client.vo.ExtInfoResultVO;
import com.jiuqi.bde.bizmodel.client.vo.FetchSettingExtInfoVo;
import com.jiuqi.bde.bizmodel.client.vo.QueryFieldVO;
import com.jiuqi.bde.bizmodel.define.gather.IBizModelGather;
import com.jiuqi.bde.bizmodel.impl.dimension.service.FetchDimensionService;
import com.jiuqi.bde.bizmodel.impl.model.entity.BizModelBuildContext;
import com.jiuqi.bde.bizmodel.impl.model.gather.IBizModelServiceGather;
import com.jiuqi.bde.bizmodel.impl.model.service.BizModelService;
import com.jiuqi.bde.bizmodel.impl.model.service.BizModelSettingService;
import com.jiuqi.bde.common.constant.ArgumentValueEnum;
import com.jiuqi.bde.common.dto.ColumnDefineVO;
import com.jiuqi.bde.common.dto.FetchSettingVO;
import com.jiuqi.bde.common.dto.FixedAdaptSettingVO;
import com.jiuqi.bde.common.dto.FixedFetchSourceRowSettingVO;
import com.jiuqi.bde.common.dto.SelectOptionVO;
import com.jiuqi.bde.common.intf.Dimension;
import com.jiuqi.bde.common.util.VariableParseUtil;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.feign.client.BaseDataClient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.util.StringUtil;

@Service
public class BizModelSettingServiceImpl
implements BizModelSettingService {
    @Autowired
    protected IBizModelGather bizModelGather;
    @Autowired
    protected BizModelService bzModelService;
    @Autowired
    private IBizModelServiceGather bizModelServiceGather;
    @Autowired
    private FetchDimensionService fetchDimensionService;
    @Autowired
    private BaseDataClient baseDataClient;

    @Override
    public Map<String, List<ExtInfoResultVO>> queryExtInfo(ExtInfoParamVO extInfoParamVO) {
        Map dataLinkSettingList = extInfoParamVO.getDataLinkFetchSetting();
        List dimList = extInfoParamVO.getDimList();
        Map<String, String> fieldNameToTitleMap = this.getFieldNameToTitleMap(dimList);
        List<BizModelDTO> bizModelList = this.bzModelService.list();
        Map<String, BizModelDTO> bizModelMap = this.getBizModelMap(bizModelList);
        Map<String, BizModelColumnDefineVO> BizModelColumnDefineMap = this.getBizModelColumnDefineMap(bizModelList);
        HashMap<String, List<ExtInfoResultVO>> dataLinkExtResultMap = new HashMap<String, List<ExtInfoResultVO>>();
        for (Map.Entry dataLinkSettingEntry : dataLinkSettingList.entrySet()) {
            String dataLinkId = (String)dataLinkSettingEntry.getKey();
            List fixedSettingData = (List)dataLinkSettingEntry.getValue();
            List<ExtInfoResultVO> extInfoResultVOList = this.processFixedSettings(fixedSettingData, fieldNameToTitleMap, bizModelMap, BizModelColumnDefineMap);
            dataLinkExtResultMap.put(dataLinkId, extInfoResultVOList);
        }
        return dataLinkExtResultMap;
    }

    private Map<String, BizModelColumnDefineVO> getBizModelColumnDefineMap(List<BizModelDTO> bizModelList) {
        List<DimensionVO> dimensionVOS = this.fetchDimensionService.listDimension();
        HashMap<String, BizModelColumnDefineVO> BizModelColumnDefineMap = new HashMap<String, BizModelColumnDefineVO>();
        Map<String, List<BizModelDTO>> groupedByComputationModelCode = bizModelList.stream().collect(Collectors.groupingBy(BizModelDTO::getComputationModelCode));
        List<SelectOptionVO> matchRuleBaseDataOptions = this.getMatchRuleBaseData();
        BizModelBuildContext context = new BizModelBuildContext();
        context.setDimensionVOS(dimensionVOS);
        context.setMatchRuleBaseDataOptions(matchRuleBaseDataOptions);
        for (Map.Entry<String, List<BizModelDTO>> listBizModelDTOEntry : groupedByComputationModelCode.entrySet()) {
            String computationModelCode = listBizModelDTOEntry.getKey();
            List<BizModelDTO> listBizModelDTO = listBizModelDTOEntry.getValue();
            String category = this.bizModelGather.getBizDataModelByComputationModel(computationModelCode).getCategory();
            Map<String, BizModelColumnDefineVO> batchColumnDefines = this.bizModelServiceGather.getByCode(category).getBatchColumnDefinesForExtInfo(listBizModelDTO, context);
            BizModelColumnDefineMap.putAll(batchColumnDefines);
        }
        return BizModelColumnDefineMap;
    }

    private List<SelectOptionVO> getMatchRuleBaseData() {
        ArrayList<SelectOptionVO> matchRuleSelectOptions = new ArrayList<SelectOptionVO>();
        BaseDataDTO condi = new BaseDataDTO();
        condi.setAuthType(BaseDataOption.AuthType.NONE);
        condi.setTableName("MD_MATCHRULE");
        PageVO pageVo = this.baseDataClient.list(condi);
        pageVo.getRows().forEach(item -> matchRuleSelectOptions.add(new SelectOptionVO(item.getCode(), item.getName())));
        return matchRuleSelectOptions;
    }

    private List<ExtInfoResultVO> processFixedSettings(List<FixedAdaptSettingVO> fixedSettingData, Map<String, String> fieldNameToTitleMap, Map<String, BizModelDTO> bizModelMap, Map<String, BizModelColumnDefineVO> BizModelColumnDefineMap) {
        LinkedList<ExtInfoResultVO> extInfoResultVOList = new LinkedList<ExtInfoResultVO>();
        for (FixedAdaptSettingVO adaptSetting : fixedSettingData) {
            extInfoResultVOList.add(this.processAdaptSetting(adaptSetting, fieldNameToTitleMap, bizModelMap, BizModelColumnDefineMap));
        }
        return extInfoResultVOList;
    }

    private ExtInfoResultVO processAdaptSetting(FixedAdaptSettingVO adaptSetting, Map<String, String> fieldNameToTitleMap, Map<String, BizModelDTO> bizModelMap, Map<String, BizModelColumnDefineVO> BizModelColumnDefineMap) {
        String logicFormula = adaptSetting.getLogicFormula();
        Map bizModelFormula = adaptSetting.getBizModelFormula();
        HashMap<String, FetchSettingExtInfoVo> extInfoMap = new HashMap<String, FetchSettingExtInfoVo>();
        HashMap<String, String> fieldSettingMap = new HashMap<String, String>();
        for (Map.Entry fetchSourceSettingEntry : bizModelFormula.entrySet()) {
            String bizModelCode = (String)fetchSourceSettingEntry.getKey();
            List fetchSourceSettings = (List)fetchSourceSettingEntry.getValue();
            BizModelDTO bizModelDTO = bizModelMap.get(bizModelCode);
            if (bizModelDTO == null) {
                throw new BusinessRuntimeException(String.format("\u6839\u636e\u4e1a\u52a1\u6a21\u578b\u4ee3\u7801\u3010%s\u3011\u6ca1\u6709\u83b7\u53d6\u5230\u5bf9\u5e94\u7684\u6570\u636e\u9879\uff0c\u8bf7\u68c0\u67e5\u4e1a\u52a1\u6a21\u578b\u662f\u5426\u88ab\u505c\u7528\u6216\u5220\u9664", bizModelCode));
            }
            IBizComputationModel computationModel = this.bizModelGather.getComputationModelByCode(bizModelDTO.getComputationModelCode());
            if (computationModel == null) {
                throw new BusinessRuntimeException(String.format("\u6839\u636e\u8ba1\u7b97\u6a21\u578b\u3010%s\u3011\u6ca1\u6709\u83b7\u53d6\u5230\u5bf9\u5e94\u7684\u8ba1\u7b97\u6a21\u578b\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458", bizModelDTO.getComputationModelCode()));
            }
            BizModelColumnDefineVO fetchSourceColumnDefines = BizModelColumnDefineMap.get(bizModelCode);
            for (FixedFetchSourceRowSettingVO fetchSourceSetting : fetchSourceSettings) {
                this.processFetchSourceSetting(fetchSourceSetting, fetchSourceColumnDefines, computationModel, bizModelDTO, fieldNameToTitleMap, extInfoMap, fieldSettingMap);
            }
        }
        ExtInfoResultVO extInfoResultVO = new ExtInfoResultVO();
        extInfoResultVO.setBizModelSettingExtInfoMap(extInfoMap);
        if (StringUtils.isEmpty((String)logicFormula)) {
            extInfoResultVO.setLogicFormulaMemo("");
            return extInfoResultVO;
        }
        String formulaInfo = this.replaceFormulaTokens(logicFormula, fieldNameToTitleMap, fieldSettingMap);
        extInfoResultVO.setLogicFormulaMemo(formulaInfo);
        return extInfoResultVO;
    }

    private void processFetchSourceSetting(FixedFetchSourceRowSettingVO fetchSourceSetting, BizModelColumnDefineVO fetchSourceColumnDefines, IBizComputationModel computationModel, BizModelDTO bizModelDTO, Map<String, String> fieldNameToTitleMap, Map<String, FetchSettingExtInfoVo> extInfoMap, Map<String, String> fieldSettingMap) {
        String bizModelCode = bizModelDTO.getCode();
        fetchSourceSetting = this.trimBizModelFetchSettingInputColumn(fetchSourceSetting, fetchSourceColumnDefines);
        fetchSourceSetting.setBizModelCode(computationModel.getCode());
        fetchSourceSetting.setFetchSourceCode(bizModelCode);
        if (StringUtils.isEmpty((String)fetchSourceSetting.getId())) {
            return;
        }
        FetchSettingExtInfoVo extInfo = new FetchSettingExtInfoVo();
        FetchSettingVO fetchSettingVO = new FetchSettingVO();
        BeanUtils.copyProperties(fetchSourceSetting, fetchSettingVO);
        String optimizeRuleGroup = computationModel.getOptimizeRuleGroup(fetchSettingVO);
        extInfo.setOptimizeRuleGroup(optimizeRuleGroup);
        this.convertFloatField(fetchSourceSetting, fieldNameToTitleMap);
        fetchSettingVO = new FetchSettingVO();
        BeanUtils.copyProperties(fetchSourceSetting, fetchSettingVO);
        String describeInfo = bizModelDTO.getName() + "(" + computationModel.getMemo(fetchSettingVO) + ")";
        extInfo.setMemo(fetchSourceSetting.getSign() + " " + describeInfo);
        fieldSettingMap.put("BIZMODEL[" + fetchSourceSetting.getId() + "]", "\u4e1a\u52a1\u6a21\u578b[" + describeInfo + "]");
        extInfoMap.put(fetchSourceSetting.getId(), extInfo);
    }

    private String replaceFormulaTokens(String logicFormula, Map<String, String> fieldNameToTitleMap, Map<String, String> fieldSettingMap) {
        String envStartToken = "ENV[";
        String envEndToken = "]";
        HashMap<String, String> envMap = new HashMap<String, String>();
        for (ArgumentValueEnum arg : ArgumentValueEnum.values()) {
            envMap.put("ENV[" + arg.getCode() + "]", "\u62a5\u8868\u53c2\u6570[" + arg.getTitle() + "]");
        }
        String floatStartToken = "FLOAT[";
        String floatEndToken = "]";
        Map<String, String> floatMap = fieldNameToTitleMap.keySet().stream().collect(Collectors.toMap(key -> "FLOAT[" + key + "]", key -> "\u67e5\u8be2\u7ed3\u679c[" + (String)fieldNameToTitleMap.get(key) + "]"));
        String bizModelStartToken = "BIZMODEL[";
        String bizModelEndToken = "]";
        String formulaInfo = VariableParseUtil.parse((String)logicFormula, envMap, (String)envStartToken, (String)envEndToken);
        formulaInfo = VariableParseUtil.parse((String)formulaInfo, floatMap, (String)floatStartToken, (String)floatEndToken);
        return VariableParseUtil.parse((String)formulaInfo, fieldSettingMap, (String)bizModelStartToken, (String)bizModelEndToken);
    }

    private void convertFloatField(FixedFetchSourceRowSettingVO fetchSourceSetting, Map<String, String> fieldNameToTitleMap) {
        HashMap<String, String> variableNameToTitleMap = new HashMap<String, String>(16);
        for (String key : fieldNameToTitleMap.keySet()) {
            variableNameToTitleMap.put("${" + key + "}", "\u67e5\u8be2\u7ed3\u679c[" + fieldNameToTitleMap.get(key) + "]");
        }
        fetchSourceSetting.setSubjectCode(VariableParseUtil.parse((String)fetchSourceSetting.getSubjectCode(), variableNameToTitleMap));
        fetchSourceSetting.setCurrencyCode(VariableParseUtil.parse((String)fetchSourceSetting.getCurrencyCode(), variableNameToTitleMap));
        fetchSourceSetting.setExcludeSubjectCode(VariableParseUtil.parse((String)fetchSourceSetting.getExcludeSubjectCode(), variableNameToTitleMap));
        fetchSourceSetting.setCashCode(VariableParseUtil.parse((String)fetchSourceSetting.getCashCode(), variableNameToTitleMap));
        fetchSourceSetting.setReclassSubjCode(VariableParseUtil.parse((String)fetchSourceSetting.getReclassSubjCode(), variableNameToTitleMap));
        fetchSourceSetting.setReclassSrcSubjCode(VariableParseUtil.parse((String)fetchSourceSetting.getReclassSrcSubjCode(), variableNameToTitleMap));
        if (StringUtils.isEmpty((String)fetchSourceSetting.getDimensionSetting())) {
            return;
        }
        List dimensionList = (List)JsonUtils.readValue((String)fetchSourceSetting.getDimensionSetting(), (TypeReference)new TypeReference<List<Dimension>>(){});
        dimensionList.forEach(item -> item.setDimValue(VariableParseUtil.parse((String)item.getDimValue(), (Map)variableNameToTitleMap)));
        fetchSourceSetting.setDimensionSetting(JsonUtils.writeValueAsString((Object)dimensionList));
    }

    private FixedFetchSourceRowSettingVO trimBizModelFetchSettingInputColumn(FixedFetchSourceRowSettingVO rowSettingVO, BizModelColumnDefineVO fetchSourceColumnDefines) {
        if (Objects.isNull(fetchSourceColumnDefines)) {
            return rowSettingVO;
        }
        List columnDefines = fetchSourceColumnDefines.getColumnDefines();
        List optionItems = fetchSourceColumnDefines.getOptionItems();
        List inputColumn = columnDefines.stream().filter(column -> "INPUT".equals(column.getType())).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(inputColumn)) {
            return rowSettingVO;
        }
        List columnList = inputColumn.stream().map(ColumnDefineVO::getCode).collect(Collectors.toList());
        ObjectMapper objectMapper = new ObjectMapper();
        Map rowSettingmap = (Map)objectMapper.convertValue((Object)rowSettingVO, (TypeReference)new TypeReference<Map<String, Object>>(){});
        for (String column2 : columnList) {
            String value = (String)rowSettingmap.get(column2);
            if (StringUtils.isEmpty((String)value)) continue;
            value = value.trim();
            rowSettingmap.put(column2, value);
        }
        List optionItemCodes = optionItems.stream().map(SelectOptionVO::getCode).collect(Collectors.toList());
        for (String optionItemCode : optionItemCodes) {
            String value = (String)rowSettingmap.get(optionItemCode);
            if (StringUtil.isEmpty((String)value)) continue;
            value = value.trim();
            rowSettingmap.put(optionItemCode, value);
        }
        return (FixedFetchSourceRowSettingVO)objectMapper.convertValue((Object)rowSettingmap, FixedFetchSourceRowSettingVO.class);
    }

    private Map<String, String> getFieldNameToTitleMap(List<QueryFieldVO> dimList) {
        if (CollectionUtils.isEmpty(dimList)) {
            return new HashMap<String, String>();
        }
        return dimList.stream().collect(Collectors.toMap(QueryFieldVO::getName, QueryFieldVO::getTitle, (o1, o2) -> o1));
    }

    private Map<String, BizModelDTO> getBizModelMap(List<BizModelDTO> bizModelList) {
        return bizModelList.stream().collect(Collectors.toMap(BizModelDTO::getCode, item -> item, (k1, k2) -> k1));
    }
}

