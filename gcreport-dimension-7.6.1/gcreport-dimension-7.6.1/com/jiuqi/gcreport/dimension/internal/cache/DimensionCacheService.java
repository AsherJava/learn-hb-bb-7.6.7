/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.collection.ArrayMap
 *  com.jiuqi.budget.param.dimension.domain.DimensionDO
 *  com.jiuqi.budget.param.dimension.domain.IDimension
 *  com.jiuqi.budget.param.dimension.service.DimensionService
 *  com.jiuqi.budget.param.hypermodel.domain.HyperModelDO
 *  com.jiuqi.budget.param.hypermodel.domain.IHyperDataScheme
 *  com.jiuqi.budget.param.hypermodel.domain.ModelShowDimensionDTO
 *  com.jiuqi.budget.param.hypermodel.domain.ModelShowMeasurementDTO
 *  com.jiuqi.budget.param.hypermodel.domain.ModelStateType
 *  com.jiuqi.budget.param.hypermodel.domain.ShowModelDTO
 *  com.jiuqi.budget.param.hypermodel.domain.enums.DataSchemeType
 *  com.jiuqi.budget.param.hypermodel.service.HyperDataSchemeService
 *  com.jiuqi.budget.param.hypermodel.service.HyperModelService
 *  com.jiuqi.budget.param.hypermodel.service.impl.HyperModelCacheService
 *  com.jiuqi.budget.param.measurement.domain.MeasurementDO
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.Pair
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.definition.impl.basic.init.table.event.HyperDimensionPublishedEvent
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.gcreport.dimension.internal.cache;

import com.jiuqi.bi.util.collection.ArrayMap;
import com.jiuqi.budget.param.dimension.domain.DimensionDO;
import com.jiuqi.budget.param.dimension.domain.IDimension;
import com.jiuqi.budget.param.dimension.service.DimensionService;
import com.jiuqi.budget.param.hypermodel.domain.HyperModelDO;
import com.jiuqi.budget.param.hypermodel.domain.IHyperDataScheme;
import com.jiuqi.budget.param.hypermodel.domain.ModelShowDimensionDTO;
import com.jiuqi.budget.param.hypermodel.domain.ModelShowMeasurementDTO;
import com.jiuqi.budget.param.hypermodel.domain.ModelStateType;
import com.jiuqi.budget.param.hypermodel.domain.ShowModelDTO;
import com.jiuqi.budget.param.hypermodel.domain.enums.DataSchemeType;
import com.jiuqi.budget.param.hypermodel.service.HyperDataSchemeService;
import com.jiuqi.budget.param.hypermodel.service.HyperModelService;
import com.jiuqi.budget.param.hypermodel.service.impl.HyperModelCacheService;
import com.jiuqi.budget.param.measurement.domain.MeasurementDO;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.Pair;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.definition.impl.basic.init.table.event.HyperDimensionPublishedEvent;
import com.jiuqi.gcreport.dimension.internal.cache.converter.DimensionConverter;
import com.jiuqi.gcreport.dimension.internal.cache.converter.DimensionConverterFactory;
import com.jiuqi.gcreport.dimension.internal.entity.DimensionEO;
import com.jiuqi.gcreport.dimension.internal.enums.EffectTablePublishStateEnum;
import com.jiuqi.gcreport.dimension.vo.DimTableRelVO;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class DimensionCacheService {
    private final String CACHE_NAME = "gcreport:dimension";
    private final DimensionConverter<DimensionDO> dimensionConverter = DimensionConverterFactory.getConverter(DimensionDO.class);
    private final DimensionConverter<MeasurementDO> measurementConverter = DimensionConverterFactory.getConverter(MeasurementDO.class);
    @Autowired
    private NedisCacheManager cacheManger;
    @Autowired
    private DataModelService dataModelService;
    private HyperModelService hyperModelService;
    @Autowired
    private HyperDataSchemeService hyperDataSchemeService;
    @Autowired
    private HyperModelCacheService hyperModelCacheService;
    @Autowired
    private DimensionService budDimensionService;

    private HyperModelService getHyperModelService() {
        if (this.hyperModelService == null) {
            this.hyperModelService = (HyperModelService)SpringBeanUtils.getBean(HyperModelService.class);
        }
        return this.hyperModelService;
    }

    private Map<String, DimensionVO> loadDim2TablesCache() {
        HashMap<String, DimTableRelVO> relationInfoMap = new HashMap<String, DimTableRelVO>();
        HashMap<String, DimensionVO> result = new HashMap<String, DimensionVO>();
        Map<String, List<DimensionEO>> tableToDimMap = this.getTable2DimCache();
        Map<String, Set<String>> dimCodeToTableCodeSetMap = DimensionCacheService.getDimCodeToTableCodeSetMap(tableToDimMap);
        for (List<DimensionEO> dimensionList : tableToDimMap.values()) {
            for (DimensionEO dimension : dimensionList) {
                Pair<List<DimTableRelVO>, List<String>> dimensionRelationsAndScopes;
                String dimensionCode;
                if (dimension == null || result.containsKey(dimensionCode = dimension.getCode()) || CollectionUtils.isEmpty((Collection)dimCodeToTableCodeSetMap.get(dimensionCode))) continue;
                DimensionVO dimensionVO = new DimensionVO(dimension);
                if (!StringUtils.isEmpty((String)dimension.getReferField())) {
                    dimensionVO.setDictTableName(this.getTableName(dimension.getReferField()));
                }
                if (((List)(dimensionRelationsAndScopes = this.processRelationInfo(dimensionCode, relationInfoMap, dimCodeToTableCodeSetMap.get(dimensionCode))).getFirst()).isEmpty()) continue;
                dimensionVO.setDimTableRelVOS((List)dimensionRelationsAndScopes.getFirst());
                dimensionVO.setEffectScopeCodes((List)dimensionRelationsAndScopes.getSecond());
                result.put(dimensionCode, dimensionVO);
            }
        }
        return result;
    }

    private static Map<String, Set<String>> getDimCodeToTableCodeSetMap(Map<String, List<DimensionEO>> tableToDimMap) {
        HashMap<String, Set<String>> dimCodeToTableCodeSetMap = new HashMap<String, Set<String>>();
        tableToDimMap.forEach((tableCode, dimensionEOList) -> {
            for (DimensionEO eo : dimensionEOList) {
                String dimCode = eo.getCode();
                if (!dimCodeToTableCodeSetMap.containsKey(dimCode)) {
                    dimCodeToTableCodeSetMap.put(dimCode, new HashSet());
                }
                if (((Set)dimCodeToTableCodeSetMap.get(dimCode)).contains(tableCode)) continue;
                ((Set)dimCodeToTableCodeSetMap.get(dimCode)).add(tableCode);
            }
        });
        return dimCodeToTableCodeSetMap;
    }

    private Pair<List<DimTableRelVO>, List<String>> processRelationInfo(String code, Map<String, DimTableRelVO> modelInfoCache, Set<String> dimCodeRelationTableCodeSet) {
        ArrayList<DimTableRelVO> dimTableRelVOS = new ArrayList<DimTableRelVO>();
        ArrayList<String> effectCodes = new ArrayList<String>();
        HyperModelService hyperModelService = this.getHyperModelService();
        for (String modelCode : dimCodeRelationTableCodeSet) {
            DimTableRelVO dimTableRelVO = modelInfoCache.get(modelCode);
            if (dimTableRelVO != null) {
                DimTableRelVO clonedRelVO = dimTableRelVO.clone();
                clonedRelVO.setDimensionId(code);
                dimTableRelVOS.add(clonedRelVO);
                effectCodes.add(dimTableRelVO.getEffectScope());
                continue;
            }
            ShowModelDTO showModelDTO = hyperModelService.selectOneByCode(modelCode);
            dimTableRelVO = new DimTableRelVO();
            dimTableRelVO.setEffectScope(showModelDTO.getCode());
            dimTableRelVO.setEffectScopeTitle(showModelDTO.getName());
            dimTableRelVO.setEffectTableName(showModelDTO.getCode());
            dimTableRelVO.setId(showModelDTO.getId());
            dimTableRelVO.setState(EffectTablePublishStateEnum.SUCCESS.getCode());
            modelInfoCache.put(modelCode, dimTableRelVO);
            dimTableRelVO.setDimensionId(code);
            dimTableRelVOS.add(dimTableRelVO);
            effectCodes.add(dimTableRelVO.getEffectScope());
        }
        return new Pair(dimTableRelVOS, effectCodes);
    }

    private Map<String, List<DimensionEO>> loadTable2DimCache() {
        HashMap<String, DimensionEO> dimensionCodeCache = new HashMap<String, DimensionEO>();
        HashMap<String, DimensionEO> measurementCodeCache = new HashMap<String, DimensionEO>();
        ArrayMap res = new ArrayMap();
        List hyperModelDOS = this.hyperModelCacheService.listAll();
        Set dataSchemeIds = this.hyperDataSchemeService.listAll().stream().filter(scheme -> DataSchemeType.OTHER.equals((Object)scheme.getDataSchemeType())).map(IHyperDataScheme::getId).collect(Collectors.toSet());
        List customModels = hyperModelDOS.stream().filter(model -> dataSchemeIds.contains(model.getDataschemeID())).collect(Collectors.toList());
        HyperModelService hyperModelService = this.getHyperModelService();
        for (HyperModelDO hyperModelDO : customModels) {
            String code = hyperModelDO.getCode();
            ShowModelDTO showModelDTO = hyperModelService.selectOneByCode(code);
            List<ModelShowDimensionDTO> dimensionDOs = showModelDTO.getDimensionDOs().stream().filter(dto -> !ModelStateType.UNPUBLISHED.name().equals(dto.getPublishState())).collect(Collectors.toList());
            List<ModelShowMeasurementDTO> measurementDOs = showModelDTO.getMeasurementDOs().stream().filter(dto -> !ModelStateType.UNPUBLISHED.name().equals(dto.getPublishState())).collect(Collectors.toList());
            ArrayList<DimensionEO> dimensionEOS = new ArrayList<DimensionEO>();
            dimensionEOS.addAll(this.processDimensions(dimensionDOs, hyperModelDO, dimensionCodeCache));
            dimensionEOS.addAll(this.processMeasurements(measurementDOs, hyperModelDO, measurementCodeCache));
            res.put(code, dimensionEOS);
        }
        return res;
    }

    private List<DimensionEO> processDimensions(List<ModelShowDimensionDTO> dimensionDOs, HyperModelDO hyperModelDO, Map<String, DimensionEO> dimensionCodeCache) {
        ArrayList<DimensionEO> dimensionEOS = new ArrayList<DimensionEO>();
        for (ModelShowDimensionDTO showDimensionDTO : dimensionDOs) {
            String dimensionCode = showDimensionDTO.getCode();
            DimensionEO dimensionEO = dimensionCodeCache.get(dimensionCode);
            if (dimensionEO == null) {
                DimensionDO dimensionDO = this.createDimensionDOFromShowDTO(showDimensionDTO, hyperModelDO);
                TableModelDefine tableModelDefineByCode = null;
                if (!StringUtils.isEmpty((String)showDimensionDTO.getBaseDataCode())) {
                    tableModelDefineByCode = this.dataModelService.getTableModelDefineByCode(showDimensionDTO.getBaseDataCode());
                }
                dimensionEO = this.dimensionConverter.convert(dimensionDO, tableModelDefineByCode);
                IDimension dimension = this.budDimensionService.getByCode(dimensionCode);
                String matchRule = dimension != null ? dimension.getMatchRule() : null;
                dimensionEO.setMatchRule(matchRule);
                dimensionCodeCache.put(dimensionCode, dimensionEO);
            }
            dimensionEOS.add(dimensionEO.clone());
        }
        return dimensionEOS;
    }

    private List<DimensionEO> processMeasurements(List<ModelShowMeasurementDTO> measurementDOs, HyperModelDO hyperModelDO, Map<String, DimensionEO> measurementCodeCache) {
        ArrayList<DimensionEO> dimensionEOS = new ArrayList<DimensionEO>();
        for (ModelShowMeasurementDTO modelShowMeasurementDTO : measurementDOs) {
            String measurementCode = modelShowMeasurementDTO.getCode();
            DimensionEO dimensionEO = measurementCodeCache.get(measurementCode);
            if (dimensionEO == null) {
                MeasurementDO measurementDO = this.createMeasurementDOFromShowDTO(modelShowMeasurementDTO, hyperModelDO);
                if (StringUtils.isEmpty((String)modelShowMeasurementDTO.getBaseDataCode())) {
                    dimensionEO = this.measurementConverter.convert(measurementDO, null);
                } else {
                    TableModelDefine tableModelDefineByCode = this.dataModelService.getTableModelDefineByCode(modelShowMeasurementDTO.getBaseDataCode());
                    dimensionEO = this.measurementConverter.convert(measurementDO, tableModelDefineByCode);
                }
                measurementCodeCache.put(measurementCode, dimensionEO);
            }
            dimensionEOS.add(dimensionEO.clone());
        }
        return dimensionEOS;
    }

    private DimensionDO createDimensionDOFromShowDTO(ModelShowDimensionDTO showDTO, HyperModelDO hyperModelDO) {
        DimensionDO dimensionDO = new DimensionDO();
        dimensionDO.setCode(showDTO.getCode());
        dimensionDO.setName(showDTO.getName());
        dimensionDO.setId(showDTO.getCode());
        dimensionDO.setCreator(hyperModelDO.getCreator());
        dimensionDO.setCreateTime(hyperModelDO.getCreateTime());
        dimensionDO.setRemark(hyperModelDO.getRemark());
        dimensionDO.setOrderNum(hyperModelDO.getOrderNum());
        dimensionDO.setModifyTime(hyperModelDO.getModifyTime());
        return dimensionDO;
    }

    private MeasurementDO createMeasurementDOFromShowDTO(ModelShowMeasurementDTO showDTO, HyperModelDO hyperModelDO) {
        MeasurementDO measurementDO = new MeasurementDO();
        measurementDO.setCode(showDTO.getCode());
        measurementDO.setName(showDTO.getName());
        measurementDO.setId(showDTO.getCode());
        measurementDO.setCreator(hyperModelDO.getCreator());
        measurementDO.setCreateTime(hyperModelDO.getCreateTime());
        measurementDO.setRemark(hyperModelDO.getRemark());
        measurementDO.setOrderNum(hyperModelDO.getOrderNum());
        measurementDO.setModifyTime(hyperModelDO.getModifyTime());
        measurementDO.setDataLength(showDTO.getDataLength());
        measurementDO.setDataType(showDTO.getDataType());
        return measurementDO;
    }

    @EventListener(value={HyperDimensionPublishedEvent.class})
    public void del(HyperDimensionPublishedEvent event) {
        this.cacheManger.getCache("gcreport:dimension").clear();
    }

    public Map<String, DimensionVO> getDim2TablesCache() {
        Map dim2TablesCache = (Map)this.cacheManger.getCache("gcreport:dimension").get("dim2TablesCache", this::loadDim2TablesCache);
        HashMap<String, DimensionVO> dimensionVOMap = new HashMap<String, DimensionVO>(dim2TablesCache.size());
        dim2TablesCache.forEach((key, value) -> {
            DimensionVO dimensionVO = value.clone();
            List<DimTableRelVO> dimTableRelVOS = null;
            dimTableRelVOS = value.getDimTableRelVOS() != null ? value.getDimTableRelVOS().stream().filter(Objects::nonNull).map(DimTableRelVO::clone).collect(Collectors.toList()) : Collections.emptyList();
            dimensionVO.setDimTableRelVOS(dimTableRelVOS);
            dimensionVOMap.put((String)key, dimensionVO);
        });
        return dimensionVOMap;
    }

    public DimensionVO getDimensionByCode(String code) {
        Map dim2TablesCache = (Map)this.cacheManger.getCache("gcreport:dimension").get("dim2TablesCache", this::loadDim2TablesCache);
        DimensionVO value = (DimensionVO)dim2TablesCache.get(code);
        if (Objects.isNull(value)) {
            return null;
        }
        DimensionVO dimensionVO = value.clone();
        List<DimTableRelVO> dimTableRelVOS = value.getDimTableRelVOS().stream().map(DimTableRelVO::clone).collect(Collectors.toList());
        dimensionVO.setDimTableRelVOS(dimTableRelVOS);
        return dimensionVO;
    }

    public Map<String, List<DimensionEO>> getTable2DimCache() {
        Map table2DimCache = (Map)this.cacheManger.getCache("gcreport:dimension").get("table2DimCache", this::loadTable2DimCache);
        HashMap dimensionVOMap = new HashMap(table2DimCache.size());
        table2DimCache.forEach((key, values) -> {
            List dimensionEOS = values.stream().map(DimensionEO::clone).collect(Collectors.toList());
            dimensionVOMap.put(key, dimensionEOS);
        });
        return table2DimCache;
    }

    private String getTableName(String tableCode) {
        TableModelDefine table;
        try {
            table = this.dataModelService.getTableModelDefineByCode(tableCode);
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u83b7\u53d6\u6807\u8bc6\u4e3a\u201c" + tableCode + "\u201d\u7684\u8868\u5b9a\u4e49\u5931\u8d25\u3002");
        }
        if (table == null) {
            return "";
        }
        return table.getName();
    }
}

