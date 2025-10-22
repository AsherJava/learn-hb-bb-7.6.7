/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.common.service;

import com.jiuqi.nr.data.common.param.ReadOnlyContext;
import java.util.Set;

public interface IRegionDataLinkReadOnlyService {
    public Set<String> getReadOnlyDataLinks(ReadOnlyContext var1);

    public boolean isAllOrgShare(ReadOnlyContext var1);
}

