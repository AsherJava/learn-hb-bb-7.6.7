/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.IDimensionFilter
 */
package com.jiuqi.nr.definition.internal.dao;

import com.jiuqi.np.definition.facade.IDimensionFilter;
import com.jiuqi.nr.definition.internal.dao.AbstractDimensionFilterDao;
import com.jiuqi.nr.definition.internal.impl.RunTimeDimensionFilterImpl;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

@Repository
public class RunTimeDimensionFilterDao
extends AbstractDimensionFilterDao<RunTimeDimensionFilterImpl> {
    private static final Class<RunTimeDimensionFilterImpl> aClass = RunTimeDimensionFilterImpl.class;

    @Override
    public Class<RunTimeDimensionFilterImpl> getClz() {
        return aClass;
    }

    public List<IDimensionFilter> list() {
        return super.list(aClass);
    }

    public List<IDimensionFilter> getByFormSchemeKey(String formSchemeKey) {
        Assert.hasLength(formSchemeKey, "DimensionFilter formSchemeKey must not be empty");
        return super.list(new String[]{"DF_FORM_SCHEME_KEY"}, (Object[])new String[]{formSchemeKey}, this.getClz());
    }

    public IDimensionFilter getByFormSchemeAndEntityId(String formSchemeKey, String entityId) {
        Assert.hasLength(formSchemeKey, "DimensionFilter formSchemeKey must not be empty");
        Assert.hasLength(entityId, "DimensionFilter entityId must not be empty");
        return super.list(new String[]{"DF_FORM_SCHEME_KEY", "DF_ENTITY_ID"}, (Object[])new String[]{formSchemeKey, entityId}, this.getClz()).stream().findFirst().orElse(null);
    }
}

