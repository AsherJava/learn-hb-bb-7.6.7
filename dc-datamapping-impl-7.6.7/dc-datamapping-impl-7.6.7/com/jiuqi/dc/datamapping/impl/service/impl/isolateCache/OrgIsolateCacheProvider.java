/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.dc.datamapping.client.dto.DataRefDTO
 *  com.jiuqi.dc.datamapping.client.dto.DataRefMappingCacheDTO
 *  com.jiuqi.dc.datamapping.client.dto.IsolationParamContext
 *  com.jiuqi.dc.mappingscheme.impl.enums.IsolationStrategy
 *  com.jiuqi.np.cache.NedisCacheManager
 */
package com.jiuqi.dc.datamapping.impl.service.impl.isolateCache;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.dc.datamapping.client.dto.DataRefDTO;
import com.jiuqi.dc.datamapping.client.dto.DataRefMappingCacheDTO;
import com.jiuqi.dc.datamapping.client.dto.IsolationParamContext;
import com.jiuqi.dc.datamapping.impl.dao.IsolateFieldMappingDao;
import com.jiuqi.dc.datamapping.impl.service.FieldMappingBizDataCacheProvider;
import com.jiuqi.dc.mappingscheme.impl.enums.IsolationStrategy;
import com.jiuqi.np.cache.NedisCacheManager;
import java.util.ArrayList;
import java.util.HashMap;
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
public class OrgIsolateCacheProvider
implements FieldMappingBizDataCacheProvider {
    @Autowired
    private IsolateFieldMappingDao isolateFieldMappingDao;
    @Autowired
    private NedisCacheManager cacheManger;
    public static final String CACHE_KEY = "OrgFieldMapping";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public IsolationStrategy getIsolateType() {
        return IsolationStrategy.ORG_ASSISTCODE;
    }

    @Override
    public boolean hasRef(IsolationParamContext isolateDimension, String dimCode) {
        Map<String, DataRefMappingCacheDTO> cache = this.getCache(isolateDimension, dimCode);
        return !ObjectUtils.isEmpty(cache);
    }

    @Override
    public Map<String, DataRefMappingCacheDTO> getCache(IsolationParamContext isolateDimension, String dimCode) {
        Cache.ValueWrapper valueWrapper = this.cacheManger.getCache(CACHE_KEY).get(isolateDimension.getSchemeCode() + "|" + isolateDimension.getAssistCode());
        if (ObjectUtils.isEmpty(valueWrapper)) {
            return new HashMap<String, DataRefMappingCacheDTO>();
        }
        return (Map)valueWrapper.get();
    }

    @Override
    public List<DataRefDTO> getBaseMappingCache(IsolationParamContext isolateDimension, String dimCode) {
        List<Object> caches;
        Cache.ValueWrapper valueWrapper = this.cacheManger.getCache(CACHE_KEY).get(isolateDimension.getSchemeCode() + "|" + isolateDimension.getAssistCode());
        if (ObjectUtils.isEmpty(valueWrapper)) {
            this.getRefDataMap(isolateDimension.getSchemeCode(), isolateDimension.getAssistCode());
            caches = this.isolateFieldMappingDao.loadOrgMapping(isolateDimension.getSchemeCode(), isolateDimension.getAssistCode());
        } else {
            caches = new ArrayList(((Map)valueWrapper.get()).values());
        }
        ArrayList<DataRefDTO> result = new ArrayList<DataRefDTO>();
        for (DataRefMappingCacheDTO cache : caches) {
            DataRefDTO dataRefDTO = new DataRefDTO();
            dataRefDTO.setId(cache.getId());
            dataRefDTO.setCode(cache.getCode());
            dataRefDTO.setOdsName(cache.getOdsName());
            dataRefDTO.setOdsCode(cache.getOdsCode());
            dataRefDTO.put((Object)"ODS_ASSISTCODE", (Object)isolateDimension.getAssistCode());
            result.add(dataRefDTO);
        }
        return result;
    }

    @Override
    public void loadCache(String schemeCode, String dimCode) {
        this.cacheManger.getCache(CACHE_KEY).clear();
        this.getRefOrgDataMap(schemeCode);
    }

    @Override
    public void syncCache(List<IsolationParamContext> isolateDimension, String schemeCode, String dimCode) {
        for (IsolationParamContext item : isolateDimension) {
            this.cacheManger.getCache(CACHE_KEY).evict(schemeCode + "|" + item.getAssistCode());
            this.getRefDataMap(schemeCode, item.getAssistCode());
        }
    }

    public Map<String, DataRefMappingCacheDTO> getRefDataMap(String dataSchemeCode, String assistCode) {
        return (Map)this.cacheManger.getCache(CACHE_KEY).get(dataSchemeCode + "|" + assistCode, () -> Optional.ofNullable(this.isolateFieldMappingDao.loadOrgMapping(dataSchemeCode, assistCode).stream().collect(Collectors.toMap(DataRefMappingCacheDTO::getOdsCode, e -> e, (k1, k2) -> k2))).orElse(CollectionUtils.newHashMap()));
    }

    public void getRefOrgDataMap(String dataSchemeCode) {
        List<DataRefMappingCacheDTO> dataRefMappingCacheDTOS = this.isolateFieldMappingDao.loadAllOrgMapping(dataSchemeCode);
        Map<String, List<DataRefMappingCacheDTO>> maps = dataRefMappingCacheDTOS.stream().collect(Collectors.groupingBy(DataRefMappingCacheDTO::getOdsAssistcode));
        for (String assistcode : maps.keySet()) {
            this.cacheManger.getCache(CACHE_KEY).put(dataSchemeCode + assistcode, maps.get(assistcode));
        }
    }

    @Override
    public Set<String> getUnitCodeRange(IsolationParamContext isolateDimension, String dimCode, List<DataRefDTO> refOrgList) {
        return refOrgList.stream().map(DataRefDTO::getCode).collect(Collectors.toSet());
    }
}

