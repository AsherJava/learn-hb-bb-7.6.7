/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.tag.management.dao;

import com.jiuqi.nr.tag.management.entity.ITagMapping;
import com.jiuqi.nr.tag.management.entityimpl.TagCount;
import java.util.List;

public interface TagMappingDao {
    public List<ITagMapping> queryTagMappingRowsByTagKey(String var1);

    public List<ITagMapping> queryTagMappingRowsByEntityData(String var1);

    public int[] insertTagMappingRows(List<ITagMapping> var1);

    public int[] deleteTagMappingRowByTagKeys(String ... var1);

    public int[] deleteTagMappingRowByEntityData(String ... var1);

    public List<TagCount> countTagsUnits(List<String> var1);
}

