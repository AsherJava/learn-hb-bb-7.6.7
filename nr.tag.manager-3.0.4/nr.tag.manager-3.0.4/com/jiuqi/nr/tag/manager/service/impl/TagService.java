/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.tag.manager.service.impl;

import com.jiuqi.nr.tag.manager.bean.TagImpl;
import java.util.List;
import java.util.Map;

public interface TagService {
    public int batchDelete(List<String> var1);

    public int sumEntityDatasOfTag(String var1);

    public TagImpl findByKey(String var1);

    public List<TagImpl> findAllByOV(String var1);

    public Map<String, List<String>> countTagsOfEntityDatas(String var1, List<String> var2);

    public Map<String, List<String>> countEntityDatasOfTags(List<String> var1);
}

