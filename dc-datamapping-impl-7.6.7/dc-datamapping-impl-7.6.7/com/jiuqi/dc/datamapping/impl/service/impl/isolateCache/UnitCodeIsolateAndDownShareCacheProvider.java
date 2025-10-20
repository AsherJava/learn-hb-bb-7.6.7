/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.datamapping.client.dto.DataRefDTO
 *  com.jiuqi.dc.datamapping.client.dto.DataRefMappingCacheDTO
 *  com.jiuqi.dc.datamapping.client.dto.IsolationParamContext
 *  com.jiuqi.dc.mappingscheme.impl.enums.IsolationStrategy
 *  com.jiuqi.np.cache.NedisCacheManager
 */
package com.jiuqi.dc.datamapping.impl.service.impl.isolateCache;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.datamapping.client.dto.DataRefDTO;
import com.jiuqi.dc.datamapping.client.dto.DataRefMappingCacheDTO;
import com.jiuqi.dc.datamapping.client.dto.IsolationParamContext;
import com.jiuqi.dc.datamapping.impl.dao.IsolateFieldMappingDao;
import com.jiuqi.dc.datamapping.impl.service.FieldMappingBizDataCacheProvider;
import com.jiuqi.dc.mappingscheme.impl.enums.IsolationStrategy;
import com.jiuqi.np.cache.NedisCacheManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class UnitCodeIsolateAndDownShareCacheProvider
implements FieldMappingBizDataCacheProvider {
    @Autowired
    private IsolateFieldMappingDao isolateFieldMappingDao;
    @Autowired
    private NedisCacheManager cacheManger;
    public static final String CACHE_KEY = "UnitCodeIsolateAndDownShareFieldMapping";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public IsolationStrategy getIsolateType() {
        return IsolationStrategy.ISOLATION_SHARE;
    }

    @Override
    public boolean hasRef(IsolationParamContext isolateDimension, String dimCode) {
        Map<String, DataRefMappingCacheDTO> cache = this.getCache(isolateDimension, dimCode);
        return !ObjectUtils.isEmpty(cache);
    }

    @Override
    public Map<String, DataRefMappingCacheDTO> getCache(IsolationParamContext isolateDimension, String dimCode) {
        Cache.ValueWrapper valueWrapper = this.cacheManger.getCache(CACHE_KEY).get(isolateDimension.getSchemeCode() + "|" + dimCode);
        if (ObjectUtils.isEmpty(valueWrapper)) {
            return new HashMap<String, DataRefMappingCacheDTO>();
        }
        Map cache = (Map)valueWrapper.get();
        return cache.computeIfAbsent(isolateDimension.getUnitCode(), k -> new HashMap());
    }

    @Override
    public List<DataRefDTO> getBaseMappingCache(IsolationParamContext isolateDimension, String dimCode) {
        Cache.ValueWrapper valueWrapper = this.cacheManger.getCache(CACHE_KEY).get(isolateDimension.getSchemeCode() + "|" + dimCode);
        Map<Object, Map<String, DataRefMappingCacheDTO>> cacheMap = ObjectUtils.isEmpty(valueWrapper) ? this.getRefDataMap(isolateDimension.getSchemeCode(), dimCode) : (Map<Object, Map<String, DataRefMappingCacheDTO>>)valueWrapper.get();
        ArrayList<DataRefDTO> result = new ArrayList<DataRefDTO>();
        Map dimensionCache = cacheMap.computeIfAbsent(isolateDimension.getUnitCode(), k -> new HashMap());
        dimensionCache.forEach((odsCode, cache) -> {
            DataRefDTO dataRefDTO = new DataRefDTO();
            dataRefDTO.setId(cache.getId());
            dataRefDTO.setCode(cache.getCode());
            dataRefDTO.setOdsName(cache.getOdsName());
            dataRefDTO.setOdsCode(cache.getOdsCode());
            dataRefDTO.put((Object)"ODS_UNITCODE", (Object)isolateDimension.getUnitCode());
            dataRefDTO.put((Object)"ODS_BOOKCODE", (Object)isolateDimension.getBookCode());
            dataRefDTO.put((Object)"ODS_ACCTYEAR", (Object)isolateDimension.getAcctYear());
            result.add(dataRefDTO);
        });
        return result;
    }

    @Override
    public void loadCache(String schemeCode, String dimCode) {
        this.cacheManger.getCache(CACHE_KEY).evict(schemeCode + "|" + dimCode);
        this.getRefDataMap(schemeCode, dimCode);
    }

    @Override
    public void syncCache(List<IsolationParamContext> isolateDimension, String schemeCode, String dimCode) {
    }

    public Map<Object, Map<String, DataRefMappingCacheDTO>> getRefDataMap(String schemeCode, String dimCode) {
        List<DataRefDTO> dataRefDTOS = this.isolateFieldMappingDao.loadIsolateAllMapping(IsolationStrategy.ISOLATION_SHARE, schemeCode, dimCode);
        HashMap<Object, Map<String, DataRefMappingCacheDTO>> resultMap = new HashMap<Object, Map<String, DataRefMappingCacheDTO>>();
        for (DataRefDTO dataRefDTO : dataRefDTOS) {
            DataRefMappingCacheDTO cacheDTO = new DataRefMappingCacheDTO(dataRefDTO.getId(), dataRefDTO.getCode(), dataRefDTO.getOdsCode(), dataRefDTO.getOdsName());
            Map innerMap = resultMap.computeIfAbsent(dataRefDTO.get((Object)"DC_UNITCODE"), k -> new HashMap());
            innerMap.put(dataRefDTO.getOdsCode(), cacheDTO);
        }
        Set<Object> unitCodes = resultMap.keySet();
        Map<String, String> odsUnit = this.isolateFieldMappingDao.getOdsUnit(schemeCode, unitCodes);
        odsUnit.forEach((unitCode, parentUnitCode) -> {
            if (StringUtils.isEmpty((String)parentUnitCode) || "-".equals(parentUnitCode)) {
                return;
            }
            Map mappingCache = resultMap.computeIfAbsent(unitCode, k -> new HashMap());
            Map parentMappingCache = resultMap.computeIfAbsent(parentUnitCode, k -> new HashMap());
            HashMap newMapping = new HashMap();
            newMapping.putAll(parentMappingCache);
            newMapping.putAll(mappingCache);
            resultMap.put(unitCode, newMapping);
        });
        this.cacheManger.getCache(CACHE_KEY).put(schemeCode + "|" + dimCode, resultMap);
        return resultMap;
    }

    @Override
    public Set<String> getUnitCodeRange(IsolationParamContext isolateDimension, String dimCode, List<DataRefDTO> refOrgList) {
        Optional<DataRefDTO> odsUnit = refOrgList.stream().filter(item -> item.getCode().equals(isolateDimension.getUnitCode())).findFirst();
        if (!odsUnit.isPresent()) {
            return Collections.emptySet();
        }
        String odsUnitCode = odsUnit.get().getOdsCode();
        Map<String, String> odsUnitMap = this.isolateFieldMappingDao.getOdsUnitByScheme(isolateDimension.getSchemeCode());
        HashMap<String, Set<String>> childrenMap = new HashMap<String, Set<String>>();
        for (Map.Entry<String, String> entry : odsUnitMap.entrySet()) {
            String parentCode;
            String code = entry.getKey();
            if (code.equals(parentCode = entry.getValue())) continue;
            childrenMap.computeIfAbsent(parentCode, k -> new HashSet()).add(code);
        }
        HashSet result = CollectionUtils.newHashSet();
        result.add(odsUnitCode);
        UnitCodeIsolateAndDownShareCacheProvider.findSubUnits(odsUnitCode, childrenMap, result);
        return refOrgList.stream().filter(item -> result.contains(item.getOdsCode())).map(DataRefDTO::getCode).collect(Collectors.toSet());
    }

    private static void findSubUnits(String parentCode, Map<String, Set<String>> childrenMap, Set<String> result) {
        if (!childrenMap.containsKey(parentCode)) {
            return;
        }
        for (String childCode : childrenMap.get(parentCode)) {
            result.add(childCode);
            UnitCodeIsolateAndDownShareCacheProvider.findSubUnits(childCode, childrenMap, result);
        }
    }
}

