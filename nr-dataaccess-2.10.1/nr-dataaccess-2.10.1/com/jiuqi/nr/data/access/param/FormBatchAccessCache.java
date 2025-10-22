/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.blob.util.BeanUtil
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.data.access.param;

import com.jiuqi.np.blob.util.BeanUtil;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.data.access.common.AccessLevel;
import com.jiuqi.nr.data.access.filter.DimensionFilter;
import com.jiuqi.nr.data.access.filter.DimensionFilterProvider;
import com.jiuqi.nr.data.access.param.AccessCode;
import com.jiuqi.nr.data.access.param.FormAccessCache;
import com.jiuqi.nr.data.access.param.IBatchAccess;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import java.util.Map;
import java.util.Objects;
import org.springframework.util.CollectionUtils;

public class FormBatchAccessCache
extends FormAccessCache
implements IBatchAccess {
    private String name;
    private String formSchemeKey;
    private DimensionFilterProvider dimensionFilterProvider;
    private DimensionCombination previousKey = null;
    private DimensionValueSet previousValueSet = null;

    public FormBatchAccessCache(String name, String formSchemeKey) {
        this.name = name;
        this.formSchemeKey = formSchemeKey;
    }

    @Override
    public AccessCode getAccessCode(DimensionCombination masterKey, String formKey) {
        DimensionValueSet findKey = null;
        if (this.previousKey != null && this.previousKey == masterKey) {
            findKey = this.previousValueSet;
        }
        if (findKey == null) {
            findKey = this.filterDime(masterKey.toDimensionValueSet(), this.formSchemeKey, formKey);
        }
        this.previousKey = masterKey;
        this.previousValueSet = findKey;
        AccessCode accessCode = new AccessCode(this.name);
        Map<DimensionValueSet, Map<String, String>> cacheMap = this.getCacheMap();
        if (cacheMap.isEmpty()) {
            return accessCode;
        }
        Map<String, String> cacheValue = this.matchCacheValue(findKey);
        if (CollectionUtils.isEmpty(cacheValue)) {
            return accessCode;
        }
        String code = cacheValue.get(formKey);
        if (Objects.isNull(code)) {
            return accessCode;
        }
        accessCode.setCode(code);
        return accessCode;
    }

    private DimensionValueSet filterDime(DimensionValueSet masterKey, String formSchemeKey, String formKey) {
        DimensionFilter filter;
        if (this.dimensionFilterProvider == null) {
            this.dimensionFilterProvider = (DimensionFilterProvider)BeanUtil.getBean(DimensionFilterProvider.class);
        }
        if (Objects.nonNull(filter = this.dimensionFilterProvider.getFilterByName(this.name))) {
            return filter.filter(masterKey, formSchemeKey, formKey, AccessLevel.UNIT);
        }
        return masterKey;
    }
}

