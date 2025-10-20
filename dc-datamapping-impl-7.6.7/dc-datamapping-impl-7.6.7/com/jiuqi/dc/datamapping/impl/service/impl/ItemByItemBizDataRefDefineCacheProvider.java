/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.dc.datamapping.client.dto.DataRefDTO
 *  com.jiuqi.dc.mappingscheme.client.common.DataRefUtil
 *  com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.impl.define.IRuleType
 *  com.jiuqi.dc.mappingscheme.impl.define.gather.IRuleTypeGather
 *  com.jiuqi.dc.mappingscheme.impl.service.BaseDataRefDefineService
 *  com.jiuqi.dc.mappingscheme.impl.service.impl.BaseDataRefDefineCacheProvider
 *  com.jiuqi.np.cache.NedisCacheManager
 *  org.apache.commons.collections4.MapUtils
 */
package com.jiuqi.dc.datamapping.impl.service.impl;

import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.dc.datamapping.client.dto.DataRefDTO;
import com.jiuqi.dc.datamapping.impl.dao.DataRefConfigureDao;
import com.jiuqi.dc.mappingscheme.client.common.DataRefUtil;
import com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.impl.define.IRuleType;
import com.jiuqi.dc.mappingscheme.impl.define.gather.IRuleTypeGather;
import com.jiuqi.dc.mappingscheme.impl.service.BaseDataRefDefineService;
import com.jiuqi.dc.mappingscheme.impl.service.impl.BaseDataRefDefineCacheProvider;
import com.jiuqi.np.cache.NedisCacheManager;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ItemByItemBizDataRefDefineCacheProvider {
    public static final String CACHE_KEY = "ItemByItemDataRefCache";
    @Autowired
    private DataRefConfigureDao dataRefConfigureDao;
    @Autowired
    private NedisCacheManager cacheManger;
    @Autowired
    private BaseDataRefDefineCacheProvider baseDataRefDefineCacheProvider;
    @Autowired
    private BaseDataRefDefineService defineService;
    @Autowired
    private IRuleTypeGather ruleTypeGather;
    private final Logger logger = LoggerFactory.getLogger(ItemByItemBizDataRefDefineCacheProvider.class);

    public void afterPropertiesSet() {
        List list = null;
        try {
            list = this.baseDataRefDefineCacheProvider.list();
        }
        catch (Exception e) {
            this.logger.warn("\u7f13\u5b58\u52a0\u8f7d\u51fa\u9519\uff0c\u7f3a\u5c11\u6570\u636e\u6620\u5c04\u5b9a\u4e49\u76f8\u5173\u8868", e);
        }
        if (!CollectionUtils.isEmpty((Collection)list)) {
            for (BaseDataMappingDefineDTO baseDataMappingDefine : list) {
                IRuleType ruleType = this.ruleTypeGather.getRuleTypeByCode(baseDataMappingDefine.getRuleType());
                Assert.isNotNull((Object)ruleType, (String)String.format("\u57fa\u7840\u6570\u636e\u5b9a\u4e49\u3010%1$s\u3011\u4e2d\u3010%2$s\u3011\u672a\u627e\u5230\u5bf9\u5e94\u7684\u6620\u5c04\u89c4\u5219,\u8bf7\u68c0\u67e5\uff01\uff01\uff01", baseDataMappingDefine.getName(), baseDataMappingDefine.getRuleType()), (Object[])new Object[0]);
                if (!Boolean.TRUE.equals(ruleType.getItem2Item())) continue;
                List<DataRefDTO> dataRefDTOS = this.dataRefConfigureDao.selectAllRefData(baseDataMappingDefine);
                HashMap dataRefDTOMap = CollectionUtils.newHashMap();
                for (DataRefDTO dataRefDTO : dataRefDTOS) {
                    if (!Objects.nonNull(dataRefDTO)) continue;
                    dataRefDTOMap.put(dataRefDTO.getOdsId(), dataRefDTO);
                }
                this.cacheManger.getCache(CACHE_KEY).put(baseDataMappingDefine.getDataSchemeCode() + "|" + DataRefUtil.getTableName((String)baseDataMappingDefine.getCode()), (Object)dataRefDTOMap);
            }
        }
    }

    public DataRefDTO getRefData(String dataSchemeCode, String tableName, String odsId) {
        return (DataRefDTO)MapUtils.getObject(this.getRefDataMap(dataSchemeCode, tableName), (Object)odsId);
    }

    public DataRefDTO getCustomRefData(String dataSchemeCode, String tableName, String odsId) {
        Map<String, DataRefDTO> refDataMap = this.getRefDataMap(dataSchemeCode, tableName);
        if (refDataMap.containsKey(odsId)) {
            return refDataMap.get(odsId);
        }
        for (Map.Entry<String, DataRefDTO> entry : refDataMap.entrySet()) {
            String key = entry.getKey();
            if (!odsId.startsWith(key)) continue;
            return entry.getValue();
        }
        return null;
    }

    public Map<String, DataRefDTO> getRefDataMap(String dataSchemeCode, String tableName) {
        return (Map)this.cacheManger.getCache(CACHE_KEY).get(dataSchemeCode + "|" + DataRefUtil.getTableName((String)tableName), () -> Optional.ofNullable(this.defineService.findByCode(dataSchemeCode, DataRefUtil.getTableName((String)tableName))).map(define -> this.dataRefConfigureDao.selectAllRefData((BaseDataMappingDefineDTO)define)).map(dataRefDTOList -> dataRefDTOList.stream().collect(Collectors.toMap(DataRefDTO::getOdsId, e -> e, (k1, k2) -> k2))).orElse(CollectionUtils.newHashMap()));
    }

    public void syncCache(String dataSchemeCode, String tableName) {
        this.cacheManger.getCache(CACHE_KEY).evict(dataSchemeCode + "|" + DataRefUtil.getTableName((String)tableName));
        this.getRefDataMap(dataSchemeCode, tableName);
    }

    public void syncCache() {
        this.cacheManger.getCache(CACHE_KEY).clear();
        this.afterPropertiesSet();
    }

    public void syncCache(DataMappingDefineDTO define) {
        this.syncCache(define.getDataSchemeCode(), define.getCode());
    }
}

