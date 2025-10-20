/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.bde.bizmodel.client.dto.BizModelDTO
 *  com.jiuqi.bde.bizmodel.client.dto.FinBizModelDTO
 *  com.jiuqi.bde.bizmodel.client.enums.BizModelCategoryEnum
 *  com.jiuqi.bde.bizmodel.client.intf.IBizComputationModel
 *  com.jiuqi.bde.bizmodel.client.vo.AssistExtendDimVO
 *  com.jiuqi.bde.bizmodel.client.vo.BizModelColumnDefineVO
 *  com.jiuqi.bde.bizmodel.client.vo.Dimension
 *  com.jiuqi.bde.bizmodel.define.gather.IBizModelGather
 *  com.jiuqi.bde.common.constant.BdeFunctionModuleEnum
 *  com.jiuqi.bde.common.constant.FetchFixedFieldEnum
 *  com.jiuqi.bde.common.constant.FetchTypeEnum
 *  com.jiuqi.bde.common.constant.MatchRuleEnum
 *  com.jiuqi.bde.common.dto.BizModelExtFieldInfo
 *  com.jiuqi.bde.common.dto.ColumnDefineVO
 *  com.jiuqi.bde.common.dto.SelectOptionVO
 *  com.jiuqi.bde.common.exception.BdeRuntimeException
 *  com.jiuqi.bde.common.i18n.util.BdeI18nHelper
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.dc.base.common.enums.BooleanValEnum
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$AuthType
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.bde.bizmodel.impl.model.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.bde.bizmodel.client.dto.BizModelDTO;
import com.jiuqi.bde.bizmodel.client.dto.FinBizModelDTO;
import com.jiuqi.bde.bizmodel.client.enums.BizModelCategoryEnum;
import com.jiuqi.bde.bizmodel.client.intf.IBizComputationModel;
import com.jiuqi.bde.bizmodel.client.vo.AssistExtendDimVO;
import com.jiuqi.bde.bizmodel.client.vo.BizModelColumnDefineVO;
import com.jiuqi.bde.bizmodel.client.vo.Dimension;
import com.jiuqi.bde.bizmodel.define.gather.IBizModelGather;
import com.jiuqi.bde.bizmodel.impl.dimension.service.AssistExtendDimService;
import com.jiuqi.bde.bizmodel.impl.dimension.service.FetchDimensionService;
import com.jiuqi.bde.bizmodel.impl.model.dao.BizModelDao;
import com.jiuqi.bde.bizmodel.impl.model.dao.FinBizModelDao;
import com.jiuqi.bde.bizmodel.impl.model.entity.BizModelBuildContext;
import com.jiuqi.bde.bizmodel.impl.model.entity.FinBizModelEO;
import com.jiuqi.bde.bizmodel.impl.model.service.BizModelBaseDataConfigService;
import com.jiuqi.bde.bizmodel.impl.model.service.BizModelService;
import com.jiuqi.bde.bizmodel.impl.model.service.impl.BizModelManageServiceImpl;
import com.jiuqi.bde.common.constant.BdeFunctionModuleEnum;
import com.jiuqi.bde.common.constant.FetchFixedFieldEnum;
import com.jiuqi.bde.common.constant.FetchTypeEnum;
import com.jiuqi.bde.common.constant.MatchRuleEnum;
import com.jiuqi.bde.common.dto.BizModelExtFieldInfo;
import com.jiuqi.bde.common.dto.ColumnDefineVO;
import com.jiuqi.bde.common.dto.SelectOptionVO;
import com.jiuqi.bde.common.exception.BdeRuntimeException;
import com.jiuqi.bde.common.i18n.util.BdeI18nHelper;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.dc.base.common.enums.BooleanValEnum;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.feign.client.BaseDataClient;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FinBizModelManageServiceImpl
extends BizModelManageServiceImpl {
    @Autowired
    private FinBizModelDao finBizModelDao;
    @Autowired
    private BizModelService bizModelService;
    @Autowired
    private NedisCacheProvider cacheProvider;
    @Autowired
    private BizModelDao bizModelDao;
    @Autowired
    private BdeI18nHelper bdeI18nHelper;
    @Autowired
    protected IBizModelGather bizModelGather;
    @Autowired
    private FetchDimensionService dimensionService;
    @Autowired
    private AssistExtendDimService assistExtendDimService;
    @Autowired
    private BizModelBaseDataConfigService bizModelBaseDataConfigService;
    @Autowired
    private BaseDataClient baseDataClient;
    public static final List<SelectOptionVO> MATCH_RULE_SELECT_OPTIONS = CollectionUtils.newArrayList();
    private NedisCache bdeBizmodelCache;
    private static Logger logger;

    @Autowired
    public void setBdeBizmodelCache() {
        NedisCacheManager cacheManager = this.cacheProvider.getCacheManager("BDE_BIZMODEL_MANAGE");
        this.bdeBizmodelCache = cacheManager.getCache("BDE_BIZMODEL");
    }

    @Override
    public String getCategoryCode() {
        return BizModelCategoryEnum.BIZMODEL_FINDATA.getCode();
    }

    @Override
    public String list() {
        List<FinBizModelDTO> finBizModelDTOList = this.listModel();
        return JsonUtils.writeValueAsString(finBizModelDTOList);
    }

    public List<FinBizModelDTO> listModel() {
        return (List)this.bdeBizmodelCache.get("BDE_FINDATA_BIZMODEL_CACHE_ID", () -> {
            List<FinBizModelEO> finBizModelDatas = this.finBizModelDao.loadAll();
            if (CollectionUtils.isEmpty(finBizModelDatas)) {
                return Collections.emptyList();
            }
            return finBizModelDatas.stream().map(finBizModelDTO -> this.convertFinBizModelDTO((FinBizModelEO)finBizModelDTO)).filter(Objects::nonNull).collect(Collectors.toList());
        });
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void save(String bizModelDtoStr) {
        FinBizModelDTO finBizModelDTO = (FinBizModelDTO)JsonUtils.readValue((String)bizModelDtoStr, (TypeReference)new TypeReference<FinBizModelDTO>(){});
        this.checkFinBizModelDTO(finBizModelDTO, true);
        FinBizModelEO finBizModelData = new FinBizModelEO();
        BeanUtils.copyProperties(finBizModelDTO, finBizModelData);
        if (!CollectionUtils.isEmpty((Collection)finBizModelDTO.getDimensions())) {
            if (BooleanValEnum.NO.getCode().equals(finBizModelDTO.getSelectAll())) {
                finBizModelData.setDimensions(JsonUtils.writeValueAsString((Object)finBizModelDTO.getDimensions()));
            } else {
                finBizModelData.setDimensions(JsonUtils.writeValueAsString(finBizModelDTO.getDimensions().stream().filter(dimension -> dimension.getRequired()).collect(Collectors.toList())));
            }
        }
        finBizModelData.setId(UUIDUtils.newHalfGUIDStr());
        finBizModelData.setFetchTypes(CollectionUtils.toString((List)finBizModelDTO.getFetchTypes()).toUpperCase());
        finBizModelData.setSelectAll(finBizModelDTO.getSelectAll());
        BizModelExtFieldInfo bizModelExtFieldInfo = finBizModelData.getBizModelExtFieldInfo();
        if (bizModelExtFieldInfo != null) {
            bizModelExtFieldInfo.generateId();
            finBizModelData.setBizModelExtFieldInfo(bizModelExtFieldInfo);
        }
        this.finBizModelDao.save(finBizModelData);
        this.bizmodelCacheClear();
        LogHelper.info((String)BdeFunctionModuleEnum.BIZMODEL.getFullModuleName(), (String)String.format("\u65b0\u589e-%1$s", finBizModelDTO.getName()), (String)JsonUtils.writeValueAsString((Object)finBizModelDTO));
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void update(String bizModelDtoStr) {
        FinBizModelDTO finBizModelDTO = (FinBizModelDTO)JsonUtils.readValue((String)bizModelDtoStr, (TypeReference)new TypeReference<FinBizModelDTO>(){});
        this.checkFinBizModelDTO(finBizModelDTO, false);
        FinBizModelEO finBizModelData = new FinBizModelEO();
        BeanUtils.copyProperties(finBizModelDTO, finBizModelData);
        if (!CollectionUtils.isEmpty((Collection)finBizModelDTO.getDimensions())) {
            if (BooleanValEnum.NO.getCode().equals(finBizModelDTO.getSelectAll())) {
                finBizModelData.setDimensions(JsonUtils.writeValueAsString((Object)finBizModelDTO.getDimensions()));
            } else {
                finBizModelData.setDimensions(JsonUtils.writeValueAsString(finBizModelDTO.getDimensions().stream().filter(dimension -> dimension.getRequired()).collect(Collectors.toList())));
            }
        }
        finBizModelData.setFetchTypes(CollectionUtils.toString((List)finBizModelDTO.getFetchTypes()).toUpperCase());
        finBizModelData.setSelectAll(finBizModelDTO.getSelectAll());
        BizModelExtFieldInfo bizModelExtFieldInfo = finBizModelData.getBizModelExtFieldInfo();
        if (bizModelExtFieldInfo != null) {
            bizModelExtFieldInfo.generateId();
            finBizModelData.setBizModelExtFieldInfo(bizModelExtFieldInfo);
        }
        this.finBizModelDao.update(finBizModelData);
        this.bizmodelCacheClear();
        LogHelper.info((String)BdeFunctionModuleEnum.BIZMODEL.getFullModuleName(), (String)String.format("\u4fee\u6539-%1$s", finBizModelDTO.getName()), (String)JsonUtils.writeValueAsString((Object)finBizModelDTO));
    }

    public FinBizModelDTO convertFinBizModelDTO(FinBizModelEO finBizModelData) {
        FinBizModelDTO finBizModelDTO = new FinBizModelDTO();
        BeanUtils.copyProperties(finBizModelData, finBizModelDTO);
        IBizComputationModel computationModel = this.bizModelGather.findComputationModelByCode(finBizModelDTO.getComputationModelCode());
        if (computationModel == null) {
            logger.warn("\u8d26\u52a1\u6a21\u578b\u6ca1\u6709{}\u7684\u8ba1\u7b97\u6a21\u578b\uff0c\u4e1a\u52a1\u6a21\u578b\u5c55\u793a\u754c\u9762\u8fc7\u6ee4\u6389\uff0c\u4e0d\u5c55\u793a", (Object)finBizModelDTO.getComputationModelCode());
            return null;
        }
        finBizModelDTO.setFetchTypes(Arrays.asList(finBizModelData.getFetchTypes().split(",")));
        finBizModelDTO.setComputationModelName(computationModel.getName());
        finBizModelDTO.setSelectAll(finBizModelData.getSelectAll());
        finBizModelDTO.setFetchTypeNames(this.getFetchTypeNames(finBizModelDTO));
        if (CollectionUtils.isEmpty((Collection)computationModel.getDimensions())) {
            return finBizModelDTO;
        }
        Map<String, Boolean> storeDimensionMap = FinBizModelManageServiceImpl.parseDimensionByStr(finBizModelData.getCode(), finBizModelData.getDimensions()).stream().collect(Collectors.toMap(Dimension::getDimensionCode, Dimension::getRequired, (k1, k2) -> k2));
        if (BooleanValEnum.YES.getCode().equals(finBizModelDTO.getSelectAll())) {
            ArrayList<Dimension> dimensions = new ArrayList<Dimension>();
            LinkedHashMap<String, String> dimensionMap = new LinkedHashMap<String, String>();
            ArrayList<String> dimensionName = new ArrayList<String>();
            boolean required = false;
            for (SelectOptionVO dimension : computationModel.getDimensions()) {
                required = Boolean.TRUE.equals(storeDimensionMap.get(dimension.getCode()));
                dimensions.add(new Dimension(dimension.getCode(), Boolean.valueOf(required)));
                dimensionMap.put(dimension.getCode(), dimension.getName());
                dimensionName.add(required ? "*" + dimension.getName() : dimension.getName());
            }
            finBizModelDTO.setDimensions(dimensions);
            finBizModelDTO.setDimensionMap(dimensionMap);
            finBizModelDTO.setDimensionNames("\u6240\u6709\u7ef4\u5ea6:\u3010" + String.join((CharSequence)"\uff0c", dimensionName) + "\u3011");
            return finBizModelDTO;
        }
        ArrayList<Dimension> dimensions = new ArrayList<Dimension>();
        LinkedHashMap<String, String> dimensionMap = new LinkedHashMap<String, String>();
        ArrayList<String> dimensionName = new ArrayList<String>();
        boolean required = false;
        for (SelectOptionVO dimension : computationModel.getDimensions()) {
            if (!storeDimensionMap.containsKey(dimension.getCode())) continue;
            required = Boolean.TRUE.equals(storeDimensionMap.get(dimension.getCode()));
            dimensions.add(new Dimension(dimension.getCode(), Boolean.valueOf(required)));
            dimensionMap.put(dimension.getCode(), dimension.getName());
            dimensionName.add(required ? "*" + dimension.getName() : dimension.getName());
        }
        finBizModelDTO.setDimensions(dimensions);
        finBizModelDTO.setDimensionMap(dimensionMap);
        finBizModelDTO.setDimensionNames(String.join((CharSequence)"\uff0c", dimensionName));
        return finBizModelDTO;
    }

    private static List<Dimension> parseDimensionByStr(String bizModelCode, String dimensionStr) {
        ArrayList dimensions = new ArrayList();
        try {
            dimensions = !StringUtils.isEmpty((String)dimensionStr) ? (List)JsonUtils.readValue((String)dimensionStr, (TypeReference)new TypeReference<List<Dimension>>(){}) : CollectionUtils.newArrayList();
        }
        catch (Exception e) {
            logger.error("\u4e1a\u52a1\u6a21\u578b\u3010{}\u3011\u7ef4\u5ea6\u3010{}\u3011\u53cd\u5e8f\u5217\u5316\u51fa\u73b0\u9519\u8bef\uff0c\u8fdb\u884c\u517c\u5bb9\u5904\u7406", (Object)bizModelCode, (Object)dimensionStr);
        }
        return dimensions;
    }

    private String getFetchTypeNames(FinBizModelDTO finBizModelDTO) {
        List fetchTypes = finBizModelDTO.getFetchTypes();
        if (CollectionUtils.isEmpty((Collection)fetchTypes)) {
            return "";
        }
        ArrayList fetchTypesNames = new ArrayList();
        fetchTypes.forEach(fetchType -> fetchTypesNames.add(FetchTypeEnum.getEnumByCode((String)fetchType).getName()));
        return String.join((CharSequence)"\uff0c", fetchTypesNames);
    }

    private void checkFinBizModelDTO(FinBizModelDTO finBizModelDTO, boolean isSaveFlag) {
        if (null == finBizModelDTO) {
            throw new BusinessRuntimeException("\u8d26\u52a1\u6a21\u578b\u914d\u7f6e\u4e0d\u5141\u8bb8\u4e3a\u7a7a");
        }
        if (StringUtils.isEmpty((String)finBizModelDTO.getCode()) || StringUtils.isEmpty((String)finBizModelDTO.getName())) {
            throw new BdeRuntimeException("\u8d26\u52a1\u6a21\u578b\u540d\u79f0\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        }
        IBizComputationModel computationModel = this.bizModelGather.getComputationModelByCode(finBizModelDTO.getComputationModelCode());
        finBizModelDTO.setComputationModelName(computationModel.getName());
        if (!CollectionUtils.isEmpty((Collection)computationModel.getFetchTypes()) && CollectionUtils.isEmpty((Collection)finBizModelDTO.getFetchTypes())) {
            throw new BusinessRuntimeException("\u53d6\u6570\u7c7b\u578b\u4e0d\u5141\u8bb8\u4e3a\u7a7a.");
        }
        List<BizModelDTO> list = this.bizModelService.listAll();
        Map<String, String> allCodeAndNames = list.stream().collect(Collectors.toMap(BizModelDTO::getName, BizModelDTO::getCode));
        if (isSaveFlag && !allCodeAndNames.isEmpty() && allCodeAndNames.values().contains(finBizModelDTO.getCode())) {
            finBizModelDTO.setCode((finBizModelDTO.getCode().length() > 10 ? finBizModelDTO.getCode().substring(0, 10) : finBizModelDTO.getCode()) + UUIDUtils.newHalfGUIDStr().toUpperCase());
        }
        if (!(allCodeAndNames.isEmpty() || StringUtils.isEmpty((String)allCodeAndNames.get(finBizModelDTO.getName())) || allCodeAndNames.get(finBizModelDTO.getName()).equals(finBizModelDTO.getCode()))) {
            throw new BusinessRuntimeException("\u4e1a\u52a1\u6a21\u578b\u540d\u79f0\u4e0d\u5141\u8bb8\u91cd\u590d,\u540d\u79f0:" + finBizModelDTO.getName());
        }
    }

    public FinBizModelDTO getById(String id) {
        Assert.isNotEmpty((String)id);
        return this.listModel().stream().filter(item -> id.equals(item.getId())).findFirst().orElseThrow(() -> new BusinessRuntimeException(String.format("\u6839\u636e\u4e1a\u52a1\u6a21\u578b\u6807\u8bc6\u3010%1$s\u3011\u6ca1\u6709\u83b7\u53d6\u5230\u5bf9\u5e94\u7684\u6570\u636e\u9879\uff0c\u8bf7\u68c0\u67e5\u4e1a\u52a1\u6a21\u578b\u662f\u5426\u88ab\u505c\u7528\u6216\u5220\u9664", id)));
    }

    public FinBizModelDTO getByCode(String code) {
        Assert.isNotEmpty((String)code);
        return this.listModel().stream().filter(item -> code.equals(item.getCode())).findFirst().orElseThrow(() -> new BusinessRuntimeException(String.format("\u6839\u636e\u4e1a\u52a1\u6a21\u578b\u4ee3\u7801\u3010%1$s\u3011\u6ca1\u6709\u83b7\u53d6\u5230\u5bf9\u5e94\u7684\u6570\u636e\u9879\uff0c\u8bf7\u68c0\u67e5\u4e1a\u52a1\u6a21\u578b\u662f\u5426\u88ab\u505c\u7528\u6216\u5220\u9664", code)));
    }

    @Override
    public BizModelColumnDefineVO getColumnDefines(String bizModelCode) {
        Assert.isNotEmpty((String)bizModelCode);
        Map<String, String> baseDataInputConfigMap = this.bizModelBaseDataConfigService.getBaseDataInputConfig();
        List<DimensionVO> dimensionVOS = this.dimensionService.listDimension();
        FinBizModelDTO bizModelDTO = (FinBizModelDTO)this.bizModelService.get(bizModelCode);
        List<SelectOptionVO> baseDataSelectOptions = this.getColumnDefineMatch();
        return this.buildBizModelColumnDefine(bizModelDTO, baseDataInputConfigMap, dimensionVOS, baseDataSelectOptions);
    }

    private BizModelColumnDefineVO buildBizModelColumnDefine(FinBizModelDTO bizModelDTO, Map<String, String> baseDataInputConfigMap, List<DimensionVO> dimensionVOS, List<SelectOptionVO> baseDataSelectOptions) {
        BizModelColumnDefineVO modelColumnDefine = new BizModelColumnDefineVO();
        IBizComputationModel bizModel = this.bizModelGather.getComputationModelByCode(bizModelDTO.getComputationModelCode());
        ArrayList<ColumnDefineVO> columnDefines = new ArrayList<ColumnDefineVO>();
        columnDefines.addAll(this.getFixedColumnDefines(bizModel, bizModelDTO));
        columnDefines.addAll(this.getDimensionColumnDefines(bizModelDTO, dimensionVOS, baseDataSelectOptions));
        if (!Objects.isNull(baseDataInputConfigMap)) {
            for (ColumnDefineVO columnDefine : columnDefines) {
                if (!baseDataInputConfigMap.containsKey(columnDefine.getCode())) continue;
                columnDefine.setType("BASEDATA");
                columnDefine.setBaseDataTableName(baseDataInputConfigMap.get(columnDefine.getCode()));
            }
        }
        modelColumnDefine.setColumnDefines(columnDefines);
        modelColumnDefine.setOptionItems(bizModel.getOptionItems());
        modelColumnDefine.setFetchSourceCode(bizModelDTO.getCode());
        modelColumnDefine.setComputationModelIcon(bizModel.getIcon());
        return modelColumnDefine;
    }

    @Override
    public Map<String, BizModelColumnDefineVO> getBatchColumnDefinesForExtInfo(List<BizModelDTO> listBizModelDTO, BizModelBuildContext context) {
        if (CollectionUtils.isEmpty(listBizModelDTO)) {
            return new HashMap<String, BizModelColumnDefineVO>();
        }
        List<DimensionVO> dimensionVOS = context.getDimensionVOS();
        List<SelectOptionVO> matchRuleBaseDataOptions = context.getMatchRuleBaseDataOptions();
        Assert.isNotNull(dimensionVOS, (String)"\u7ef4\u5ea6\u53c2\u6570\u4e3aNull", (Object[])new Object[0]);
        HashMap<String, BizModelColumnDefineVO> baseDataOutputConfigMap = new HashMap<String, BizModelColumnDefineVO>();
        for (BizModelDTO bizModel : listBizModelDTO) {
            BizModelColumnDefineVO modelColumnDefine = this.buildBizModelColumnDefine((FinBizModelDTO)bizModel, null, dimensionVOS, matchRuleBaseDataOptions);
            baseDataOutputConfigMap.put(bizModel.getCode(), modelColumnDefine);
        }
        return baseDataOutputConfigMap;
    }

    private List<ColumnDefineVO> getFixedColumnDefines(IBizComputationModel bizModel, FinBizModelDTO bizModelDTO) {
        ArrayList<ColumnDefineVO> columnDefines = new ArrayList<ColumnDefineVO>();
        if (CollectionUtils.isEmpty((Collection)bizModel.getFixedFields())) {
            return CollectionUtils.newArrayList();
        }
        for (ColumnDefineVO columnDefine : bizModel.getFixedFields()) {
            if (FetchFixedFieldEnum.FETCHTYPE.getCode().equalsIgnoreCase(columnDefine.getCode()) && !CollectionUtils.isEmpty((Collection)bizModelDTO.getFetchTypes())) {
                ArrayList fetchTypeSelectOptions = new ArrayList();
                bizModelDTO.getFetchTypes().forEach(code -> fetchTypeSelectOptions.add(new SelectOptionVO(code, FetchTypeEnum.getEnumByCode((String)code).getName())));
                columnDefine.setData(fetchTypeSelectOptions);
            }
            columnDefines.add(columnDefine);
        }
        return columnDefines;
    }

    private List<ColumnDefineVO> getDimensionColumnDefines(FinBizModelDTO bizModelDTO, List<DimensionVO> dimensionVOS, List<SelectOptionVO> baseDataSelectOptions) {
        if (CollectionUtils.isEmpty((Collection)bizModelDTO.getDimensions())) {
            return CollectionUtils.newArrayList();
        }
        IBizComputationModel computationModel = this.bizModelGather.getComputationModelByCode(bizModelDTO.getComputationModelCode());
        ArrayList<ColumnDefineVO> columnDefines = new ArrayList<ColumnDefineVO>();
        Map<String, String> dimMatchRuleMap = dimensionVOS.stream().collect(Collectors.toMap(DimensionVO::getCode, dim -> !StringUtils.isEmpty((String)dim.getMatchRule()) ? dim.getMatchRule() : MatchRuleEnum.LIKE.getCode()));
        Map<String, Integer> dimSortMap = dimensionVOS.stream().collect(Collectors.toMap(DimensionVO::getCode, DimensionVO::getSortOrder));
        Map<String, AssistExtendDimVO> extendDimMap = this.assistExtendDimService.getAllStartAssistExtendDim().stream().collect(Collectors.toMap(AssistExtendDimVO::getCode, Function.identity()));
        Map<String, String> dimensionCodeMap = computationModel.getDimensions().stream().collect(Collectors.toMap(SelectOptionVO::getCode, SelectOptionVO::getName));
        bizModelDTO.getDimensions().stream().sorted(new columnDefineOrder(extendDimMap, dimSortMap)).forEach(dim -> {
            if (!dimensionCodeMap.containsKey(dim.getDimensionCode()) || StringUtils.isEmpty((String)((String)dimensionCodeMap.get(dim.getDimensionCode())))) {
                return;
            }
            String matchRuleValue = extendDimMap.get(dim.getDimensionCode()) == null ? (String)dimMatchRuleMap.get(dim.getDimensionCode()) : ((AssistExtendDimVO)extendDimMap.get(dim.getDimensionCode())).getMatchRule();
            columnDefines.add(new ColumnDefineVO(dim.getDimensionCode() + "MatchRule", this.bdeI18nHelper.getMessage(dim.getDimensionCode(), (String)dimensionCodeMap.get(dim.getDimensionCode())) + GcI18nUtil.getMessage((String)"bde.fixe.column.matchRule"), false, "SINGLE", "SELECT", StringUtils.isEmpty((String)matchRuleValue) ? MatchRuleEnum.LIKE.getCode() : matchRuleValue, extendDimMap.get(dim.getDimensionCode()) != null ? MATCH_RULE_SELECT_OPTIONS : baseDataSelectOptions));
            columnDefines.add(new ColumnDefineVO(dim.getDimensionCode(), this.bdeI18nHelper.getMessage(dim.getDimensionCode(), (String)dimensionCodeMap.get(dim.getDimensionCode())), dim.getRequired().booleanValue(), "SINGLE,MULTIPLE", "INPUT", null, null));
        });
        return columnDefines;
    }

    private List<SelectOptionVO> getColumnDefineMatch() {
        ArrayList<SelectOptionVO> matchRuleSelectOptions = new ArrayList<SelectOptionVO>();
        BaseDataDTO condi = new BaseDataDTO();
        condi.setAuthType(BaseDataOption.AuthType.NONE);
        condi.setTableName("MD_MATCHRULE");
        PageVO pageVo = this.baseDataClient.list(condi);
        pageVo.getRows().forEach(item -> matchRuleSelectOptions.add(new SelectOptionVO(item.getCode(), item.getName())));
        return matchRuleSelectOptions;
    }

    public Map<String, String> getFetchTypesByCode(String bizModelCode) {
        FinBizModelDTO bizModel = this.getByCode(bizModelCode);
        IBizComputationModel computationModel = this.bizModelGather.getComputationModelByCode(bizModel.getComputationModelCode());
        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
        if (!CollectionUtils.isEmpty((Collection)computationModel.getDimensions())) {
            if (null != bizModel.getDimensions()) {
                Map<String, String> dimensionsNameAndCode = computationModel.getDimensions().stream().collect(Collectors.toMap(SelectOptionVO::getCode, SelectOptionVO::getName));
                for (Dimension dim : bizModel.getDimensions()) {
                    map.put(dim.getDimensionCode(), dimensionsNameAndCode.get(dim.getDimensionCode()));
                }
            } else {
                computationModel.getDimensions().forEach(item -> map.put(item.getCode(), item.getName()));
            }
        }
        List fetchTypeList = bizModel.getFetchTypes().stream().filter(item -> !FetchTypeEnum.ZSC.getCode().equals(item)).collect(Collectors.toList());
        for (String fetchType : fetchTypeList) {
            map.put(fetchType, FetchTypeEnum.getEnumByCode((String)fetchType).getName());
        }
        return map;
    }

    @Override
    public void bizmodelCacheClear() {
        this.bdeBizmodelCache.clear();
    }

    @Override
    public String getTableName() {
        return "BDE_BIZMODEL_FINDATA";
    }

    @Override
    public BizModelDao getBizModelDao() {
        return this.bizModelDao;
    }

    @Override
    public IBizModelGather getBizModelGather() {
        return this.bizModelGather;
    }

    static {
        MATCH_RULE_SELECT_OPTIONS.add(new SelectOptionVO(MatchRuleEnum.EQ.getCode(), MatchRuleEnum.EQ.getName()));
        MATCH_RULE_SELECT_OPTIONS.add(new SelectOptionVO(MatchRuleEnum.LIKE.getCode(), MatchRuleEnum.LIKE.getName()));
        logger = LoggerFactory.getLogger(FinBizModelManageServiceImpl.class);
    }

    private class columnDefineOrder
    implements Comparator<Dimension> {
        private Map<String, AssistExtendDimVO> extendDimMap;
        private Map<String, Integer> dimSortMap;

        columnDefineOrder(Map<String, AssistExtendDimVO> extendDimMap, Map<String, Integer> dimSortMap) {
            this.extendDimMap = extendDimMap;
            this.dimSortMap = dimSortMap;
        }

        @Override
        public int compare(Dimension dimension1, Dimension dimension2) {
            return this.getOrder(dimension1) - this.getOrder(dimension2);
        }

        private int getOrder(Dimension dimension) {
            if (this.dimSortMap.get(dimension.getDimensionCode()) != null) {
                return this.dimSortMap.get(dimension.getDimensionCode()) * 100;
            }
            if (this.extendDimMap.get(dimension.getDimensionCode()) != null) {
                return this.dimSortMap.get(this.extendDimMap.get(dimension.getDimensionCode()).getAssistDimCode()) == null ? 9900 : this.dimSortMap.get(this.extendDimMap.get(dimension.getDimensionCode()).getAssistDimCode()) * 100 + 1;
            }
            return 9900;
        }
    }
}

