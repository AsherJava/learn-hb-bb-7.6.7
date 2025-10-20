/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.event.DataSchemeDeployEvent
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 */
package com.jiuqi.gc.financialcubes.query.redisservice;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.event.DataSchemeDeployEvent;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class PenetrationParamCache
implements ApplicationListener<DataSchemeDeployEvent> {
    private static final String CACHE_NAME = "datafield:dbpenetration";
    private static final String DIM_KIND = "TABLE_FIELD_DIM";
    private NedisCache cache;
    private Logger logger = LoggerFactory.getLogger(PenetrationParamCache.class);
    @Autowired
    private IRuntimeDataSchemeService iRuntimeDataSchemeService;

    @Autowired
    private void setCacheManager(NedisCacheProvider cacheProvider) {
        this.cache = cacheProvider.getCacheManager("nr:scheme").getCache(CACHE_NAME);
    }

    public void setDefaultValue(String dataSchemeKey, Map<String, Map<String, String>> value) {
        this.cache.hMSet(dataSchemeKey, value);
    }

    public Map<String, Map<String, String>> getDefaultValue(String dataSchemeKey, String tableName) {
        if (Objects.isNull(dataSchemeKey)) {
            throw new BusinessRuntimeException("\u6570\u636e\u65b9\u6848key\u4e3a\u7a7a");
        }
        Map result = this.cache.hGetAll(dataSchemeKey);
        if (result != null && !result.isEmpty() && result.containsKey(tableName)) {
            try {
                return result.entrySet().stream().collect(Collectors.toMap(e -> (String)e.getKey(), e -> (Map)e.getValue()));
            }
            catch (ClassCastException e2) {
                this.logger.error("\u7c7b\u578b\u8f6c\u6362\u9519\u8bef: " + e2.getMessage(), e2);
            }
        }
        return this.loadDataIntoCache(dataSchemeKey, tableName);
    }

    public void clear(String dataSchemeKey) {
        this.cache.evict(dataSchemeKey);
    }

    @Override
    public void onApplicationEvent(DataSchemeDeployEvent event) {
        this.clear(event.getSource().getDataSchemeKey());
    }

    private Map<String, Map<String, String>> loadDataIntoCache(String dataSchemeKey, String tableName) {
        List dataFieldByTableCode = this.iRuntimeDataSchemeService.getDataFieldByTableCode(tableName);
        HashSet<String> allFieldSet = new HashSet<String>();
        HashSet<String> duplicates = new HashSet<String>();
        for (Object field : dataFieldByTableCode) {
            String referenceEntity = field.getRefDataEntityKey();
            if (referenceEntity == null) continue;
            if (allFieldSet.contains(referenceEntity)) {
                duplicates.add(referenceEntity);
                continue;
            }
            allFieldSet.add(referenceEntity);
        }
        HashMap<String, String> cacheFieldData = new HashMap<String, String>();
        for (DataField dataField : dataFieldByTableCode) {
            String refData;
            if (!dataField.getDataFieldKind().name().equals(DIM_KIND)) continue;
            String refDataEntityKey = dataField.getRefDataEntityKey();
            if (StringUtils.isEmpty((String)refDataEntityKey)) {
                refData = String.join((CharSequence)".", tableName, dataField.getCode());
                cacheFieldData.put(refData, dataField.getCode());
                continue;
            }
            refData = duplicates.contains(refDataEntityKey) ? dataField.getCode() + "_" + refDataEntityKey.split("@")[0] : refDataEntityKey.split("@")[0];
            cacheFieldData.put(refData, dataField.getCode());
        }
        HashMap<String, Map<String, String>> valueMap = new HashMap<String, Map<String, String>>();
        valueMap.put(tableName, cacheFieldData);
        this.setDefaultValue(dataSchemeKey, valueMap);
        return valueMap;
    }
}

