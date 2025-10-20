/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.datamapping.client.dto.DataRefDTO
 *  com.jiuqi.dc.datamapping.client.dto.DataRefMappingCacheDTO
 *  com.jiuqi.dc.datamapping.client.dto.IsolationParamContext
 *  com.jiuqi.dc.mappingscheme.impl.enums.IsolationStrategy
 *  com.jiuqi.np.cache.NedisCacheManager
 */
package com.jiuqi.dc.datamapping.impl.service.impl.isolateCache;

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
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class UnitCodeShareIsolateCacheProvider
implements FieldMappingBizDataCacheProvider {
    @Autowired
    private IsolateFieldMappingDao isolateFieldMappingDao;
    @Autowired
    private NedisCacheManager cacheManger;
    public static final String CACHE_KEY = "UnitCodeShareIsolateFieldMapping";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public IsolationStrategy getIsolateType() {
        return IsolationStrategy.SHARE_ISOLATION;
    }

    @Override
    public boolean hasRef(IsolationParamContext isolateDimension, String dimCode) {
        Map<String, DataRefMappingCacheDTO> cache = this.getCache(isolateDimension, dimCode);
        return !ObjectUtils.isEmpty(cache);
    }

    @Override
    public Map<String, DataRefMappingCacheDTO> getCache(IsolationParamContext isolateDimension, String dimCode) {
        this.loadCache(isolateDimension.getSchemeCode(), dimCode);
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
        List<DataRefDTO> dataRefDTOS = this.isolateFieldMappingDao.loadIsolateAllMapping(IsolationStrategy.SHARE_ISOLATION, schemeCode, dimCode);
        HashMap<Object, Map<String, DataRefMappingCacheDTO>> resultMap = new HashMap<Object, Map<String, DataRefMappingCacheDTO>>();
        for (DataRefDTO dataRefDTO : dataRefDTOS) {
            DataRefMappingCacheDTO cacheDTO = new DataRefMappingCacheDTO(dataRefDTO.getId(), dataRefDTO.getCode(), dataRefDTO.getOdsCode(), dataRefDTO.getOdsName());
            Map innerMap = resultMap.computeIfAbsent(dataRefDTO.get((Object)"DC_UNITCODE"), k -> new HashMap());
            innerMap.put(dataRefDTO.getOdsCode(), cacheDTO);
        }
        Map shareMapping = resultMap.computeIfAbsent("-", k -> new HashMap());
        resultMap.forEach((unitCode, mapping) -> {
            if (!"-".equals(unitCode)) {
                HashMap newMapping = new HashMap();
                newMapping.putAll(shareMapping);
                newMapping.putAll(mapping);
                resultMap.put(unitCode, newMapping);
            }
        });
        this.cacheManger.getCache(CACHE_KEY).put(schemeCode + "|" + dimCode, resultMap);
        return resultMap;
    }

    @Override
    public Set<String> getUnitCodeRange(IsolationParamContext isolateDimension, String dimCode, List<DataRefDTO> refOrgList) {
        if (!"-".equals(isolateDimension.getUnitCode())) {
            return Collections.singleton(isolateDimension.getUnitCode());
        }
        return refOrgList.stream().map(DataRefDTO::getCode).collect(Collectors.toSet());
    }
}

