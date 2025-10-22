/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheManager
 *  nr.single.map.configurations.bean.ISingleMappingConfig
 *  nr.single.map.configurations.bean.SingleConfigInfo
 *  nr.single.map.data.service.SingleMappingService
 */
package nr.single.para.var;

import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import nr.single.map.configurations.bean.ISingleMappingConfig;
import nr.single.map.configurations.bean.SingleConfigInfo;
import nr.single.map.data.service.SingleMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class JIOSQCache {
    @Autowired
    private NedisCacheManager cacheManager;
    @Autowired
    private SingleMappingService mappingConfigService;
    static final String CACHE_KEY = "JIOSQ_CACHE";

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public String getSQMap(String schemeKey, String periodStr) {
        Cache.ValueWrapper valueWrapper;
        NedisCache cache = this.cacheManager.getCache(CACHE_KEY);
        Cache.ValueWrapper valueWrapper1 = cache.get(schemeKey);
        if (null == valueWrapper1) {
            JIOSQCache jIOSQCache = this;
            synchronized (jIOSQCache) {
                Cache.ValueWrapper valueWrapper2 = cache.hGet(schemeKey, (Object)periodStr);
                if (null != valueWrapper2) {
                    return (String)valueWrapper2.get();
                }
                this.loadSQMapping(schemeKey);
            }
        }
        if (null != (valueWrapper = cache.hGet(schemeKey, (Object)periodStr))) {
            return (String)valueWrapper.get();
        }
        return null;
    }

    public void evict(String formSchemeKey) {
        NedisCache cache = this.cacheManager.getCache(CACHE_KEY);
        cache.evict(formSchemeKey);
    }

    private void loadSQMapping(String schemeKey) {
        NedisCache cache = this.cacheManager.getCache(CACHE_KEY);
        List mappingInReport = this.mappingConfigService.getAllMappingInReport(schemeKey);
        Map<Object, Object> mapping = new HashMap();
        if (!CollectionUtils.isEmpty(mappingInReport)) {
            SingleConfigInfo singleConfigInfo = (SingleConfigInfo)mappingInReport.get(0);
            ISingleMappingConfig config = this.mappingConfigService.getConfigByKey(singleConfigInfo.getConfigKey());
            Map periodMapping = config.getMapping().getPeriodMapping();
            mapping = periodMapping.entrySet().stream().collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey, (o, n) -> o));
        }
        cache.hMSet(schemeKey, mapping);
    }
}

