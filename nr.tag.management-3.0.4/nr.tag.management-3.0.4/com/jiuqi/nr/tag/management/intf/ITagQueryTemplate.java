/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.tag.management.intf;

import com.jiuqi.nr.tag.management.intf.ITagFacade;
import com.jiuqi.nr.tag.management.intf.ITagQueryContext;
import java.util.List;
import java.util.Map;

public interface ITagQueryTemplate {
    public int getOrder();

    public List<ITagFacade> getInfoTags(ITagQueryContext var1);

    public List<ITagFacade> getInfoTags(ITagQueryContext var1, List<String> var2);

    public Map<String, List<String>> tagCountUnits(ITagQueryContext var1, List<String> var2);

    public Map<String, List<String>> unitCountTags(ITagQueryContext var1, List<String> var2);
}

