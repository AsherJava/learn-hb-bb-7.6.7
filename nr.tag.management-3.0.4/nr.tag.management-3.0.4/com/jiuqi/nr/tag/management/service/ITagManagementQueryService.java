/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.tag.management.service;

import com.jiuqi.nr.tag.management.environment.TagCountContextData;
import com.jiuqi.nr.tag.management.environment.TagQueryContextData;
import com.jiuqi.nr.tag.management.intf.ITagCountDataSet;
import com.jiuqi.nr.tag.management.intf.ITagFacade;
import java.util.List;

public interface ITagManagementQueryService {
    public List<ITagFacade> queryAllTags(TagQueryContextData var1);

    public ITagCountDataSet tagCountUnits(TagCountContextData var1);

    public ITagCountDataSet unitCountTags(TagCountContextData var1);
}

