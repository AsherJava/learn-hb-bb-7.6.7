/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.HashCacheValue
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  com.jiuqi.np.definition.internal.service.RuntimeDefinitionCache
 *  com.jiuqi.np.definition.internal.service.RuntimeDefinitionRefreshListener
 *  com.jiuqi.nr.definition.deploy.DeployFinishedEvent
 */
package com.jiuqi.nr.conditionalstyle.service;

import com.jiuqi.np.cache.HashCacheValue;
import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.np.definition.internal.service.RuntimeDefinitionCache;
import com.jiuqi.np.definition.internal.service.RuntimeDefinitionRefreshListener;
import com.jiuqi.nr.conditionalstyle.dao.ConditionalStyleDao;
import com.jiuqi.nr.conditionalstyle.facade.ConditionalStyle;
import com.jiuqi.nr.definition.deploy.DeployFinishedEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class ConditionStyleService
implements RuntimeDefinitionRefreshListener,
ApplicationListener<DeployFinishedEvent> {
    @Autowired
    private ConditionalStyleDao conditionalStyleDao;
    private final RuntimeDefinitionCache<ConditionalStyle> cache;
    private static final String IDX_NAME_CONDITIONAL_STYLE_XY = "xy";

    @Autowired
    public ConditionStyleService(NedisCacheProvider cacheProvider) {
        if (cacheProvider == null) {
            throw new IllegalArgumentException("'cacheProvider' must not be null.");
        }
        this.cache = new RuntimeDefinitionCache(cacheProvider.getCacheManager("NR_DEFINITION_CACHE_CONFIGURATION"), ConditionalStyle.class);
    }

    private static String createXYIndexName(String formKey) {
        return "xy::" + formKey;
    }

    private static String createXYIndexKey(int x, int y) {
        return String.format("[%s,%s]", x, y);
    }

    public List<ConditionalStyle> getCSByPos(String formKey, int x, int y) {
        String indexKey;
        if (formKey == null) {
            return null;
        }
        String indexName = ConditionStyleService.createXYIndexName(formKey);
        HashCacheValue hashValue = this.cache.getHashIndexValue(indexName, (Object)(indexKey = ConditionStyleService.createXYIndexKey(x, y)));
        if (!hashValue.isPresent()) {
            return (List)RuntimeDefinitionCache.synchronizedRun((Object)this, () -> {
                HashCacheValue rehashValue = this.cache.getHashIndexValue(indexName, (Object)indexKey);
                if (rehashValue.isPresent()) {
                    return (List)((Cache.ValueWrapper)rehashValue.get()).get();
                }
                RuntimeConditionalStyleCacheUnit cacheUnit = this.loadCacheByForm(formKey);
                List<ConditionalStyle> conditionalStyles = cacheUnit.formXYIndex.get(indexKey);
                if (CollectionUtils.isEmpty(conditionalStyles)) {
                    return Collections.emptyList();
                }
                return conditionalStyles;
            });
        }
        return (List)((Cache.ValueWrapper)hashValue.get()).get();
    }

    public List<ConditionalStyle> getCSByForm(String formKey) {
        HashCacheValue valueWrapper;
        if (formKey == null) {
            Collections.emptyList();
        }
        if (!(valueWrapper = this.cache.mGetHashIndexValue(ConditionStyleService.createXYIndexName(formKey))).isPresent()) {
            return (List)RuntimeDefinitionCache.synchronizedRun((Object)this, () -> {
                HashCacheValue valueWrapp = this.cache.mGetHashIndexValue(ConditionStyleService.createXYIndexName(formKey));
                if (valueWrapp.isPresent()) {
                    return this.convertType((HashCacheValue<Map<Object, Object>>)valueWrapp);
                }
                RuntimeConditionalStyleCacheUnit cacheUnit = this.loadCacheByForm(formKey);
                List conditionalStyles = cacheUnit.objects.stream().filter(t -> formKey.equals(t.getFormKey())).collect(Collectors.toList());
                if (conditionalStyles.isEmpty()) {
                    return Collections.emptyList();
                }
                return conditionalStyles;
            });
        }
        return this.convertType((HashCacheValue<Map<Object, Object>>)valueWrapper);
    }

    private List<ConditionalStyle> convertType(HashCacheValue<Map<Object, Object>> mapHashCacheValue) {
        ArrayList<ConditionalStyle> conditionalStyles = new ArrayList<ConditionalStyle>();
        for (Object conditionStyle : ((Map)mapHashCacheValue.get()).values()) {
            conditionalStyles.addAll((List)conditionStyle);
        }
        return conditionalStyles;
    }

    public RuntimeConditionalStyleCacheUnit loadCacheByForm(String formKey) {
        List<ConditionalStyle> styles = this.conditionalStyleDao.getCSByForm(formKey);
        RuntimeConditionalStyleCacheUnit cacheUnit = new RuntimeConditionalStyleCacheUnit(formKey, styles);
        this.loadCache(cacheUnit);
        return cacheUnit;
    }

    private void loadCache(RuntimeConditionalStyleCacheUnit cacheUnit) {
        if (cacheUnit.formKey != null) {
            this.cache.putHashIndex(ConditionStyleService.createXYIndexName(cacheUnit.formKey), cacheUnit.formXYIndex);
        }
    }

    public void onClearCache() {
        this.cache.clear();
    }

    @Override
    public void onApplicationEvent(DeployFinishedEvent event) {
        this.cache.clear();
    }

    private static class RuntimeConditionalStyleCacheUnit {
        final String formKey;
        final List<ConditionalStyle> objects;
        final Map<String, List<ConditionalStyle>> formXYIndex = new LinkedHashMap<String, List<ConditionalStyle>>();

        public RuntimeConditionalStyleCacheUnit(String formKey, List<ConditionalStyle> ConditionalStyles) {
            this.formKey = formKey;
            this.objects = ConditionalStyles;
            for (ConditionalStyle conditionalStyle : this.objects) {
                String csFormKey = conditionalStyle.getFormKey();
                if (!StringUtils.hasText(csFormKey)) continue;
                List<ConditionalStyle> objectKeysByXY = this.formXYIndex.get(ConditionStyleService.createXYIndexKey(conditionalStyle.getPosX(), conditionalStyle.getPosY()));
                if (objectKeysByXY == null) {
                    objectKeysByXY = new ArrayList<ConditionalStyle>();
                    this.formXYIndex.put(ConditionStyleService.createXYIndexKey(conditionalStyle.getPosX(), conditionalStyle.getPosY()), objectKeysByXY);
                }
                objectKeysByXY.add(conditionalStyle);
            }
        }
    }
}

