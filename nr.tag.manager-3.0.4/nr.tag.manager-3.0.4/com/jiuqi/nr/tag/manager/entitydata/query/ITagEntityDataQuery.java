/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 */
package com.jiuqi.nr.tag.manager.entitydata.query;

import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.tag.manager.entitydata.query.TagQueryParam;
import java.util.List;

public interface ITagEntityDataQuery {
    public IEntityTable makeIEntityTable(TagQueryParam var1);

    public IEntityTable makeIEntityTable(TagQueryParam var1, List<String> var2);

    public IEntityTable makeIEntityTable(TagQueryParam var1, String ... var2);

    public IEntityTable makeIEntityTable(TagQueryParam var1, List<String> var2, String ... var3);

    public IEntityTable makeFullTreeData(TagQueryParam var1);

    public IEntityTable makeRangeFullTreeData(TagQueryParam var1, List<String> var2);
}

