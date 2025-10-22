/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.tag.manager.resultset;

import com.jiuqi.nr.tag.manager.bean.TagImpl;
import java.util.List;
import java.util.Map;

public interface ITagRecordData {
    public Map<String, List<String>> batchQueryTags(List<String> var1);

    public Map<String, List<String>> batchQueryEntities(List<String> var1);

    public List<TagImpl> getTagsByKey(List<String> var1);
}

