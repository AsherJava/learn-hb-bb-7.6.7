/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.entity.ext.filter.impl;

import com.jiuqi.nr.entity.ext.filter.IEntityReferFilter;
import com.jiuqi.nr.entity.model.IEntityRefer;
import java.util.List;

public class DefaultEntityReferFilter
implements IEntityReferFilter {
    @Override
    public List<IEntityRefer> getFilterAttribute(List<IEntityRefer> refs) {
        return refs;
    }
}

