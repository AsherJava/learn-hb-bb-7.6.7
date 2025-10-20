/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.IDimensionFilter
 */
package com.jiuqi.nr.definition.internal.runtime.controller;

import com.jiuqi.np.definition.facade.IDimensionFilter;
import com.jiuqi.nr.definition.internal.impl.RunTimeDimensionFilterImpl;
import java.util.List;
import org.springframework.util.StringUtils;

public interface IDimensionFilterService {
    @Deprecated
    public List<IDimensionFilter> getByFormSchemeKey(String var1);

    @Deprecated
    default public IDimensionFilter getByFormSchemeAndEntityId(String formSchemeKey, String entityId) {
        if (!StringUtils.hasLength(formSchemeKey) || !StringUtils.hasLength(entityId)) {
            return null;
        }
        List<IDimensionFilter> list = this.getByFormSchemeKey(formSchemeKey);
        return list.stream().filter(l -> entityId.equals(l.getEntityId())).findFirst().orElse(new RunTimeDimensionFilterImpl(formSchemeKey, entityId));
    }

    public List<IDimensionFilter> getByTaskKey(String var1);

    public IDimensionFilter getByTaskAndEntityId(String var1, String var2);
}

