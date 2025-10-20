/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ArrayListMultimap
 *  com.google.common.collect.Multimap
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.enums.DcFunctionModuleEnum
 *  com.jiuqi.dc.base.common.vo.SelectOptionVO
 *  com.jiuqi.dc.mappingscheme.client.common.DataRefUtil
 *  com.jiuqi.dc.mappingscheme.client.common.FieldDTO
 *  com.jiuqi.dc.mappingscheme.client.common.SchemeDefaultDataVO
 *  com.jiuqi.dc.mappingscheme.client.common.SchemeDefaultVO
 *  com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.FieldMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.OrgMappingTypeDTO
 *  com.jiuqi.dc.mappingscheme.client.vo.BizColumnVO
 *  com.jiuqi.dc.mappingscheme.client.vo.DimMappingVO
 *  com.jiuqi.dc.mappingscheme.client.vo.OrgMappingTypeVO
 *  com.jiuqi.dc.mappingscheme.client.vo.SchemeDimVo
 *  com.jiuqi.gcreport.dimension.service.DimensionService
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.dc.mappingscheme.impl.service.impl;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.enums.DcFunctionModuleEnum;
import com.jiuqi.dc.base.common.vo.SelectOptionVO;
import com.jiuqi.dc.mappingscheme.client.common.DataRefUtil;
import com.jiuqi.dc.mappingscheme.client.common.FieldDTO;
import com.jiuqi.dc.mappingscheme.client.common.SchemeDefaultDataVO;
import com.jiuqi.dc.mappingscheme.client.common.SchemeDefaultVO;
import com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import com.jiuqi.dc.mappingscheme.client.dto.FieldMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.client.dto.OrgMappingTypeDTO;
import com.jiuqi.dc.mappingscheme.client.vo.BizColumnVO;
import com.jiuqi.dc.mappingscheme.client.vo.DimMappingVO;
import com.jiuqi.dc.mappingscheme.client.vo.OrgMappingTypeVO;
import com.jiuqi.dc.mappingscheme.client.vo.SchemeDimVo;
import com.jiuqi.dc.mappingscheme.impl.common.DataSchemeInit;
import com.jiuqi.dc.mappingscheme.impl.common.RuleType;
import com.jiuqi.dc.mappingscheme.impl.common.SaveTypeEnum;
import com.jiuqi.dc.mappingscheme.impl.define.IDataSchemeInitializer;
import com.jiuqi.dc.mappingscheme.impl.define.IFieldMappingProvider;
import com.jiuqi.dc.mappingscheme.impl.define.IOrgMappingTypeProvider;
import com.jiuqi.dc.mappingscheme.impl.define.IPluginType;
import com.jiuqi.dc.mappingscheme.impl.define.IRuleType;
import com.jiuqi.dc.mappingscheme.impl.define.gather.IDataSchemeInitializerGather;
import com.jiuqi.dc.mappingscheme.impl.define.gather.IFieldMappingProviderGather;
import com.jiuqi.dc.mappingscheme.impl.define.gather.IOrgMappingTypeProviderGather;
import com.jiuqi.dc.mappingscheme.impl.define.gather.IPluginTypeGather;
import com.jiuqi.dc.mappingscheme.impl.define.gather.IRuleTypeGather;
import com.jiuqi.dc.mappingscheme.impl.enums.FieldMappingType;
import com.jiuqi.dc.mappingscheme.impl.enums.SchemeBaseDataRefType;
import com.jiuqi.dc.mappingscheme.impl.event.BaseDataRefDefineDelEvent;
import com.jiuqi.dc.mappingscheme.impl.service.BaseDataRefDefineService;
import com.jiuqi.dc.mappingscheme.impl.service.BizDataRefDefineService;
import com.jiuqi.dc.mappingscheme.impl.service.DataSchemeInitService;
import com.jiuqi.dc.mappingscheme.impl.util.SchemeInitUtil;
import com.jiuqi.gcreport.dimension.service.DimensionService;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DataSchemeInitServiceImpl
implements DataSchemeInitService {
    public static final String DC_STANDARD_MODEL = "DC_STANDARD_MODEL";
    @Autowired
    private BizDataRefDefineService bizDataRefDefineService;
    @Autowired
    private BaseDataRefDefineService baseDataRefDefineService;
    @Autowired
    private IDataSchemeInitializerGather iDataSchemeInitializerGather;
    @Autowired
    private IFieldMappingProviderGather iFieldMappingProviderGather;
    @Autowired
    private IPluginTypeGather iPluginTypeGather;
    @Autowired
    private DimensionService assistDimService;
    @Autowired
    private IRuleTypeGather ruleTypeGather;
    @Autowired
    private IOrgMappingTypeProviderGather orgMappingTypeGather;
    private static final String RULE_TYPE = "ruleType";
    private final String BDE_BIZ_SIGN = "Assist";

    @Override
    public SchemeDefaultDataVO getDefaultSchemeData(DataSchemeDTO dataSchemeDTO) {
        IDataSchemeInitializer iDataSchemeInitializer = this.iDataSchemeInitializerGather.getByPluginType(dataSchemeDTO.getPluginType());
        return iDataSchemeInitializer.getDefaultSchemeData(dataSchemeDTO);
    }

    @Override
    public SchemeDefaultVO getDefaultData(DataSchemeDTO dataSchemeDTO) {
        Assert.isNotNull((Object)dataSchemeDTO, (String)"\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotEmpty((String)dataSchemeDTO.getCode(), (String)"\u6570\u636e\u6620\u5c04\u65b9\u6848\u7f16\u53f7\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotEmpty((String)dataSchemeDTO.getDataSourceCode(), (String)"\u6570\u636e\u6e90\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotEmpty((String)dataSchemeDTO.getPluginType(), (String)"\u6838\u7b97\u8f6f\u4ef6\u7248\u672c\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        SchemeDefaultVO schemeDefaultVO = new SchemeDefaultVO();
        IPluginType pluginType = this.iPluginTypeGather.getPluginType(dataSchemeDTO.getPluginType());
        ArrayList assistFieldData = CollectionUtils.newArrayList();
        List<FieldDTO> listAssistField = pluginType.listAssistField(dataSchemeDTO);
        if (!CollectionUtils.isEmpty(listAssistField)) {
            assistFieldData.addAll(listAssistField);
        }
        assistFieldData.add(SchemeInitUtil.createCustomField());
        schemeDefaultVO.setAssistFieldData((List)assistFieldData);
        List<IFieldMappingProvider> fieldMappingProviderList = this.iFieldMappingProviderGather.listByPluginType(pluginType);
        ArrayList<BizColumnVO> pluginColumnList = new ArrayList<BizColumnVO>();
        HashMap<String, List<DimMappingVO>> bizFieldData = new HashMap<String, List<DimMappingVO>>();
        for (IFieldMappingProvider iFieldMappingProvider : fieldMappingProviderList) {
            BizColumnVO column = new BizColumnVO();
            column.setName(iFieldMappingProvider.getCode());
            column.setTitle(iFieldMappingProvider.getName() + "\u6e90\u5b57\u6bb5");
            pluginColumnList.add(column);
            if (Objects.isNull(dataSchemeDTO.getOptions())) {
                dataSchemeDTO.setOptions((List)CollectionUtils.newArrayList());
            }
            List<FieldDTO> odsFieldList = iFieldMappingProvider.listOdsField(dataSchemeDTO);
            bizFieldData.put(iFieldMappingProvider.getCode(), SchemeInitUtil.createDimMapping(odsFieldList));
        }
        schemeDefaultVO.setColumnData(pluginColumnList);
        schemeDefaultVO.setBizFieldData(bizFieldData);
        schemeDefaultVO.setIsolationStrategyList(pluginType.isolationStrategyList());
        ArrayList<OrgMappingTypeVO> orgMappingTypeList = new ArrayList<OrgMappingTypeVO>();
        IOrgMappingTypeProvider orgMappingTypeProvider = this.orgMappingTypeGather.getProvider(pluginType);
        List<OrgMappingTypeDTO> orgMappingTypeDTOS = orgMappingTypeProvider.listMappingType(dataSchemeDTO);
        for (OrgMappingTypeDTO dto : orgMappingTypeDTOS) {
            orgMappingTypeList.add(new OrgMappingTypeVO(dto.getCode(), dto.getName(), dto.getBaseDataMappingDefine().getAdvancedSql()));
        }
        schemeDefaultVO.setOrgMappingTypeList(orgMappingTypeList);
        ArrayList<SelectOptionVO> fieldMappingTypeList = new ArrayList<SelectOptionVO>();
        for (FieldMappingType fieldMappingType : FieldMappingType.values()) {
            fieldMappingTypeList.add(new SelectOptionVO(fieldMappingType.getCode(), fieldMappingType.getName()));
        }
        schemeDefaultVO.setFieldMappingTypeList(fieldMappingTypeList);
        schemeDefaultVO.setAssistFieldFlag(Boolean.valueOf(pluginType.assistFieldFlag()));
        return schemeDefaultVO;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public Boolean initSchemeDataConfig(DataSchemeDTO dataScheme, DataSchemeInit dataSchemeInit) {
        Assert.isNotNull((Object)dataScheme, (String)"\u6570\u636e\u6620\u5c04\u65b9\u6848\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotEmpty((String)dataScheme.getInitSchemeData(), (String)"\u521d\u59cb\u5316\u6570\u636e\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotEmpty((String)dataScheme.getSaveType(), (String)"\u6570\u636e\u4fdd\u5b58\u7c7b\u578b\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        List<DataMappingDefineDTO> bizDataRefDefineList = this.bizDataRefDefineService.listBySchemeCode(dataScheme.getCode());
        Map<String, BaseDataMappingDefineDTO> baseDataRefDefineMap = this.baseDataRefDefineService.listBySchemeCode(dataScheme.getCode()).stream().collect(Collectors.toMap(DataMappingDefineDTO::getCode, e -> e, (k1, k2) -> k2));
        List noBaseDimList = this.assistDimService.loadAllDimensions().stream().filter(dimension -> StringUtils.isEmpty((String)dimension.getDictTableName())).map(DimensionVO::getCode).collect(Collectors.toList());
        Map<String, BaseDataMappingDefineDTO> initBaseDataRefDefineMap = dataSchemeInit.getBaseDataRefDefine().stream().collect(Collectors.toMap(DataMappingDefineDTO::getCode, e -> e, (k1, k2) -> k2));
        List<String> mainDim = SchemeBaseDataRefType.getSchemeBaseDataRefList();
        if (Objects.equals(dataScheme.getPluginType(), "SAP") && Objects.equals(dataScheme.getSaveType(), SaveTypeEnum.MODIFY.getcode())) {
            for (BaseDataMappingDefineDTO defineDTO : baseDataRefDefineMap.values()) {
                if (!mainDim.contains(defineDTO.getCode())) continue;
                this.baseDataRefDefineService.schemeInitDelete(defineDTO);
            }
        } else {
            ArrayList odsFieldNameList;
            ArrayList savedOdsFieldNameList;
            HashMap baseCodeToBizOdsFieldNameMap = CollectionUtils.newHashMap();
            for (DataMappingDefineDTO dataMappingDefineDTO : bizDataRefDefineList) {
                for (Object fieldMappingDefineDTO : dataMappingDefineDTO.getItems()) {
                    Set stringSet = baseCodeToBizOdsFieldNameMap.computeIfAbsent(fieldMappingDefineDTO.getFieldName(), k -> new HashSet());
                    stringSet.add(fieldMappingDefineDTO.getOdsFieldName());
                }
            }
            HashMap newBaseCodeToBizOdsFieldNameMap = CollectionUtils.newHashMap();
            for (DataMappingDefineDTO dataMappingDefineDTO : dataSchemeInit.getBizDataRefDefine()) {
                for (FieldMappingDefineDTO fieldMappingDefineDTO : dataMappingDefineDTO.getItems()) {
                    Set stringSet = newBaseCodeToBizOdsFieldNameMap.computeIfAbsent(fieldMappingDefineDTO.getFieldName(), k -> new HashSet());
                    stringSet.add(fieldMappingDefineDTO.getOdsFieldName());
                }
            }
            Set<String> set = initBaseDataRefDefineMap.keySet();
            for (BaseDataMappingDefineDTO defineDTO : baseDataRefDefineMap.values()) {
                if (noBaseDimList.contains(defineDTO.getCode())) continue;
                savedOdsFieldNameList = new ArrayList(baseCodeToBizOdsFieldNameMap.getOrDefault(defineDTO.getCode(), new HashSet()));
                if (!mainDim.contains(defineDTO.getCode()) && !newBaseCodeToBizOdsFieldNameMap.containsKey(defineDTO.getCode()) && CollectionUtils.isEmpty(savedOdsFieldNameList)) continue;
                odsFieldNameList = new ArrayList(newBaseCodeToBizOdsFieldNameMap.getOrDefault(defineDTO.getCode(), new HashSet()));
                String initRuleType = Optional.ofNullable(initBaseDataRefDefineMap.get(defineDTO.getCode())).map(BaseDataMappingDefineDTO::getRuleType).orElse("");
                if (savedOdsFieldNameList.size() == 1 && odsFieldNameList.size() == 1 && Objects.equals(savedOdsFieldNameList.get(0), odsFieldNameList.get(0)) && Objects.equals(initRuleType, defineDTO.getRuleType()) || RuleType.CONST_TRANSFER.getCode().equals(initRuleType) || Objects.equals(DC_STANDARD_MODEL, dataScheme.getPluginType()) && !mainDim.contains(defineDTO.getCode())) continue;
                this.baseDataRefDefineService.schemeInitDelete(defineDTO);
                if ((set.contains(defineDTO.getCode()) || !Boolean.TRUE.equals(RuleType.isItemByItem(defineDTO.getRuleType()))) && (!set.contains(defineDTO.getCode()) || !this.changeInItemByItem(defineDTO.getCode(), baseDataRefDefineMap, initBaseDataRefDefineMap))) continue;
                ApplicationContextRegister.getApplicationContext().publishEvent(new BaseDataRefDefineDelEvent((Object)this, (DataMappingDefineDTO)defineDTO));
            }
            for (BaseDataMappingDefineDTO defineDTO : dataSchemeInit.getBaseDataRefDefine()) {
                if (noBaseDimList.contains(defineDTO.getCode())) continue;
                savedOdsFieldNameList = new ArrayList(baseCodeToBizOdsFieldNameMap.getOrDefault(defineDTO.getCode(), new HashSet()));
                if (!mainDim.contains(defineDTO.getCode()) && !newBaseCodeToBizOdsFieldNameMap.containsKey(defineDTO.getCode()) && CollectionUtils.isEmpty(savedOdsFieldNameList)) continue;
                odsFieldNameList = new ArrayList(newBaseCodeToBizOdsFieldNameMap.getOrDefault(defineDTO.getCode(), new HashSet()));
                String savedRuleType = Optional.ofNullable(baseDataRefDefineMap.get(defineDTO.getCode())).map(BaseDataMappingDefineDTO::getRuleType).orElse("");
                if (savedOdsFieldNameList.size() == 1 && odsFieldNameList.size() == 1 && Objects.equals(savedOdsFieldNameList.get(0), odsFieldNameList.get(0)) && Objects.equals(savedRuleType, defineDTO.getRuleType()) || RuleType.CONST_TRANSFER.getCode().equals(savedRuleType) || Objects.equals(DC_STANDARD_MODEL, dataScheme.getPluginType()) && !mainDim.contains(defineDTO.getCode())) continue;
                this.baseDataRefDefineService.schemeInitCreate(defineDTO);
            }
        }
        ArrayListMultimap bizMainDim = ArrayListMultimap.create();
        for (DataMappingDefineDTO dataMappingDefineDTO : bizDataRefDefineList) {
            dataMappingDefineDTO.getItems().forEach(arg_0 -> DataSchemeInitServiceImpl.lambda$initSchemeDataConfig$7(mainDim, (Multimap)bizMainDim, dataMappingDefineDTO, arg_0));
            this.bizDataRefDefineService.schemeInitDelete(dataMappingDefineDTO);
        }
        for (DataMappingDefineDTO dataMappingDefineDTO : dataSchemeInit.getBizDataRefDefine()) {
            Map<String, FieldMappingDefineDTO> map = bizMainDim.get((Object)dataMappingDefineDTO.getCode()).stream().collect(Collectors.toMap(FieldMappingDefineDTO::getFieldName, e -> e, (k1, k2) -> k2));
            for (int i = 0; i < dataMappingDefineDTO.getItems().size(); ++i) {
                FieldMappingDefineDTO fieldMappingDefineDTO;
                fieldMappingDefineDTO = map.get(((FieldMappingDefineDTO)dataMappingDefineDTO.getItems().get(i)).getFieldName());
                if (!Objects.nonNull(fieldMappingDefineDTO)) continue;
                fieldMappingDefineDTO.setRuleType(((FieldMappingDefineDTO)dataMappingDefineDTO.getItems().get(i)).getRuleType());
                dataMappingDefineDTO.getItems().set(i, fieldMappingDefineDTO);
            }
            this.bizDataRefDefineService.schemeInitCreate(dataMappingDefineDTO);
        }
        LogHelper.info((String)DcFunctionModuleEnum.DATAMAPPINGSCHEME.getFullModuleName(), (String)"\u65b9\u6848\u521d\u59cb\u5316", (String)JsonUtils.writeValueAsString((Object)dataSchemeInit));
        return true;
    }

    public boolean changeInItemByItem(String code, Map<String, BaseDataMappingDefineDTO> oldBaseDataMap, Map<String, BaseDataMappingDefineDTO> newBaseDataMap) {
        IRuleType oldRuleType = this.ruleTypeGather.getRuleTypeByCode(oldBaseDataMap.get(code).getRuleType());
        IRuleType newRuleType = this.ruleTypeGather.getRuleTypeByCode(newBaseDataMap.get(code).getRuleType());
        return oldRuleType.getItem2Item() != false && newRuleType.getItem2Item() != false && !Objects.equals(oldRuleType.getRuleTypeClass().getCode(), newRuleType.getRuleTypeClass().getCode());
    }

    @Override
    public Set<SchemeDimVo> getDimList(String pluginType) {
        HashSet<SchemeDimVo> dimList = new HashSet<SchemeDimVo>();
        HashSet<String> codes = new HashSet<String>();
        HashSet effectTableSet = CollectionUtils.newHashSet();
        effectTableSet.add("DC_VOUCHERITEMASS");
        effectTableSet.add("DC_PREASSBALANCE");
        effectTableSet.add("DC_UNCLEAREDITEM");
        List<IFieldMappingProvider> fieldMappingProviderList = this.iFieldMappingProviderGather.listByPluginType(this.iPluginTypeGather.getPluginType(pluginType));
        for (IFieldMappingProvider iFieldMappingProvider : fieldMappingProviderList) {
            effectTableSet.add(iFieldMappingProvider.getEffectTable());
        }
        for (String tableName : effectTableSet) {
            for (DimensionVO dimensionVO : this.assistDimService.findDimFieldsVOByTableName(tableName)) {
                if (codes.contains(dimensionVO.getCode())) continue;
                codes.add(dimensionVO.getCode());
                dimList.add(new SchemeDimVo(dimensionVO.getCode(), dimensionVO.getTitle(), dimensionVO.getReferField()));
            }
        }
        return dimList;
    }

    private static /* synthetic */ void lambda$initSchemeDataConfig$7(List mainDim, Multimap bizMainDim, DataMappingDefineDTO defineDTO, FieldMappingDefineDTO e) {
        if (mainDim.contains(DataRefUtil.getTableName((String)e.getFieldName()))) {
            bizMainDim.put((Object)defineDTO.getCode(), (Object)e);
        }
    }
}

