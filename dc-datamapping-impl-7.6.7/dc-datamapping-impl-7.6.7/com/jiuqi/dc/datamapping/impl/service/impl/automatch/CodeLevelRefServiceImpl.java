/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.enums.BooleanValEnum
 *  com.jiuqi.dc.base.common.enums.SourceDataTypeEnum
 *  com.jiuqi.dc.base.common.vo.SelectOptionVO
 *  com.jiuqi.dc.datamapping.client.dto.DataRefAutoMatchDTO
 *  com.jiuqi.dc.datamapping.client.dto.DataRefDTO
 *  com.jiuqi.dc.datamapping.client.dto.IsolationParamContext
 *  com.jiuqi.dc.mappingscheme.client.common.DataRefUtil
 *  com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO
 *  com.jiuqi.dc.mappingscheme.impl.define.IRuleType
 *  com.jiuqi.dc.mappingscheme.impl.define.gather.IRuleTypeGather
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSchemeService
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSourceService
 *  com.jiuqi.va.query.datasource.enumerate.DataSourceEnum
 *  org.apache.commons.collections4.MapUtils
 *  org.jetbrains.annotations.NotNull
 */
package com.jiuqi.dc.datamapping.impl.service.impl.automatch;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.enums.BooleanValEnum;
import com.jiuqi.dc.base.common.enums.SourceDataTypeEnum;
import com.jiuqi.dc.base.common.vo.SelectOptionVO;
import com.jiuqi.dc.datamapping.client.dto.DataRefAutoMatchDTO;
import com.jiuqi.dc.datamapping.client.dto.DataRefDTO;
import com.jiuqi.dc.datamapping.client.dto.IsolationParamContext;
import com.jiuqi.dc.datamapping.impl.service.impl.IsolateRefDefineCacheProvider;
import com.jiuqi.dc.datamapping.impl.service.impl.automatch.AbstractRefServiceImpl;
import com.jiuqi.dc.datamapping.impl.utils.IsolationUtil;
import com.jiuqi.dc.mappingscheme.client.common.DataRefUtil;
import com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import com.jiuqi.dc.mappingscheme.impl.define.IRuleType;
import com.jiuqi.dc.mappingscheme.impl.define.gather.IRuleTypeGather;
import com.jiuqi.dc.mappingscheme.impl.service.DataSchemeService;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import com.jiuqi.va.query.datasource.enumerate.DataSourceEnum;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import org.apache.commons.collections4.MapUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CodeLevelRefServiceImpl
extends AbstractRefServiceImpl {
    public static final String PARENT_KEY = "PARENTKEY";
    public static final String PARENT_CODE = "PARENTCODE";
    @Autowired
    private IRuleTypeGather ruleTypeGather;
    @Autowired
    private DataSchemeService dataSchemeService;
    @Autowired
    private IsolateRefDefineCacheProvider cacheProvider;
    @Autowired
    private DataSourceService dataSourceService;

    @Override
    public String getCode() {
        return "CODELEVEL";
    }

    @Override
    public String getName() {
        return "\u4ee3\u7801\u9010\u7ea7\u5339\u914d";
    }

    @Override
    public List<DataRefDTO> autoMatch(BaseDataMappingDefineDTO define, List<DataRefDTO> unRefDataList, List<Map<String, Object>> baseDataList, DataRefAutoMatchDTO dto) {
        LinkedList refList = CollectionUtils.newLinkedList();
        Map<String, Map<String, Object>> dimMap = baseDataList.stream().filter(e -> Objects.nonNull(e.get("CODE".toLowerCase()))).collect(Collectors.toMap(e -> MapUtils.getString((Map)e, (Object)"CODE".toLowerCase()), e -> e, (k1, k2) -> k2));
        Map<String, Map<String, Object>> allRefDataMap = this.getAllRefDataMap(define);
        HashMap refMap = CollectionUtils.newHashMap();
        for (DataRefDTO dataDto : unRefDataList) {
            Map<String, Object> dataDtoMap = allRefDataMap.get(this.getKey(define, (Map<String, Object>)dataDto));
            dataDto.put((Object)PARENT_KEY, (Object)MapUtils.getString(dataDtoMap, (Object)PARENT_KEY));
            if (Boolean.TRUE.equals(dataDto.getMatched())) continue;
            dataDto.setDataSchemeCode(dto.getDataSchemeCode());
            IsolationParamContext isolationParam = IsolationUtil.buildIsolationParam(dataDto, define.getIsolationStrategy());
            if (!refMap.containsKey(isolationParam)) {
                List<DataRefDTO> cacheDatas = this.cacheProvider.getBaseMappingCache(isolationParam, dto.getTableName());
                if (!CollectionUtils.isEmpty(cacheDatas)) {
                    refMap.put(isolationParam, cacheDatas.stream().collect(Collectors.toMap(DataRefDTO::getOdsCode, item -> item, (o1, o2) -> o2)));
                } else {
                    refMap.put(isolationParam, CollectionUtils.newHashMap());
                }
            }
            String matchCode = Optional.ofNullable(dimMap.get(dataDto.getOdsCode())).map(e -> MapUtils.getString((Map)e, (Object)"CODE".toLowerCase())).orElse(this.match((Map<String, Object>)dataDto, dimMap, allRefDataMap, (Map)refMap.get(isolationParam)));
            Map parentData = Optional.ofNullable(MapUtils.getString((Map)dataDto, (Object)PARENT_KEY)).map(allRefDataMap::get).orElse(null);
            String parentRefCode = this.getRefCode(parentData, refList, allRefDataMap, (Map)refMap.get(isolationParam));
            String parents = Optional.ofNullable(parentRefCode).map(dimMap::get).map(e -> MapUtils.getString((Map)e, (Object)"PARENTS".toLowerCase())).orElse("");
            String[] parentSplit = parents.split("/");
            if (!StringUtils.isEmpty((String)parentRefCode) && !Arrays.asList(parentSplit).contains(matchCode)) {
                matchCode = parentRefCode;
            }
            if (StringUtils.isEmpty((String)matchCode)) continue;
            dataDto.setCode(matchCode);
            dataDto.setAutoMatchFlag(BooleanValEnum.YES.getCode());
            dataDto.setMatched(true);
            dataDtoMap.put("CODE", matchCode);
            dataDtoMap.put("MATCHED", true);
            refList.add(dataDto);
        }
        return refList;
    }

    private String getRefCode(Map<String, Object> parentData, List<DataRefDTO> refList, Map<String, Map<String, Object>> allRefDataMap, Map<String, DataRefDTO> refedDataMap) {
        if (Boolean.TRUE.equals(MapUtils.getBoolean(parentData, (Object)"MATCHED"))) {
            return MapUtils.getString(parentData, (Object)"CODE");
        }
        DataRefDTO dataRefDTO = refedDataMap.getOrDefault(MapUtils.getString(parentData, (Object)DataRefUtil.getOdsPrefixName((String)"CODE")), refList.stream().filter(e -> e.getOdsCode().equals(MapUtils.getString((Map)parentData, (Object)DataRefUtil.getOdsPrefixName((String)"CODE")))).findFirst().orElse(null));
        if (Objects.nonNull(dataRefDTO)) {
            return dataRefDTO.getCode();
        }
        Map grandParentData = Optional.ofNullable(MapUtils.getString(parentData, (Object)PARENT_KEY)).map(allRefDataMap::get).orElse(null);
        if (Objects.nonNull(grandParentData)) {
            return this.getRefCode(grandParentData, refList, allRefDataMap, refedDataMap);
        }
        return null;
    }

    @NotNull
    private Map<String, Map<String, Object>> getAllRefDataMap(BaseDataMappingDefineDTO define) {
        DataSchemeDTO dataSchemeDTO = this.dataSchemeService.getByCode(define.getDataSchemeCode());
        this.ruleTypeGather.getRuleTypeByCode(define.getRuleType()).isolationDim();
        List isolationDimlist = Optional.ofNullable(define.getRuleType()).map(e -> this.ruleTypeGather.getRuleTypeByCode(e)).map(IRuleType::isolationDim).orElse(CollectionUtils.newArrayList());
        String dataSourceCode = SourceDataTypeEnum.DIRECT_TYPE.getCode().equals(dataSchemeDTO.getSourceDataType()) ? dataSchemeDTO.getDataSourceCode() : DataSourceEnum.CURRENT.getName();
        List allRefData = this.dataSourceService.query(dataSourceCode, define.getAdvancedSql(), null, (rs, rowNum) -> {
            HashMap result = CollectionUtils.newHashMap();
            result.put(DataRefUtil.getOdsPrefixName((String)"CODE"), rs.getString("CODE"));
            result.put(DataRefUtil.getOdsPrefixName((String)"NAME"), rs.getString("NAME"));
            result.put(PARENT_CODE, rs.getString(PARENT_CODE));
            for (SelectOptionVO selectOptionVO : isolationDimlist) {
                result.put(DataRefUtil.getOdsPrefixName((String)selectOptionVO.getCode()), rs.getString(selectOptionVO.getCode()));
            }
            return result;
        });
        HashMap allRefDataMap = CollectionUtils.newHashMap();
        for (Map refData : allRefData) {
            String key = this.getKey(define, refData);
            refData.put(PARENT_KEY, this.getKey(define, refData, PARENT_CODE));
            allRefDataMap.put(key, refData);
        }
        return allRefDataMap;
    }

    private String match(Map<String, Object> unRefData, Map<String, Map<String, Object>> dimMap, Map<String, Map<String, Object>> allRefDataMap, Map<String, DataRefDTO> refedDataMap) {
        if (Boolean.TRUE.equals(MapUtils.getBoolean(unRefData, (Object)"MATCHED"))) {
            return MapUtils.getString(unRefData, (Object)"CODE");
        }
        DataRefDTO refedData = refedDataMap.get(MapUtils.getString(unRefData, (Object)DataRefUtil.getOdsPrefixName((String)"CODE")));
        if (Objects.nonNull(refedData)) {
            return refedData.getCode();
        }
        Map<String, Object> baseDataDO = dimMap.get(MapUtils.getString(unRefData, (Object)DataRefUtil.getOdsPrefixName((String)"CODE")));
        String matchCode = MapUtils.getString(baseDataDO, (Object)"CODE".toLowerCase());
        if (!StringUtils.isEmpty((String)matchCode)) {
            return matchCode;
        }
        Map parentData = Optional.ofNullable(MapUtils.getString(unRefData, (Object)PARENT_KEY)).map(allRefDataMap::get).orElse(null);
        if (Objects.nonNull(parentData)) {
            return this.match(parentData, dimMap, allRefDataMap, refedDataMap);
        }
        return null;
    }

    private String getKey(BaseDataMappingDefineDTO define, Map<String, Object> unRefData) {
        return this.getKey(define, unRefData, DataRefUtil.getOdsPrefixName((String)"CODE"));
    }

    private String getKey(BaseDataMappingDefineDTO define, Map<String, Object> unRefData, String firstKey) {
        StringJoiner joiner = new StringJoiner("|");
        joiner.add(MapUtils.getString(unRefData, (Object)firstKey));
        this.ruleTypeGather.getRuleTypeByCode(define.getRuleType()).isolationDim();
        List isolationDimlist = Optional.ofNullable(define.getRuleType()).map(e -> this.ruleTypeGather.getRuleTypeByCode(e)).map(IRuleType::isolationDim).orElse(CollectionUtils.newArrayList());
        isolationDimlist.forEach(e -> joiner.add(MapUtils.getString((Map)unRefData, (Object)DataRefUtil.getOdsPrefixName((String)e.getCode()))));
        return joiner.toString();
    }
}

