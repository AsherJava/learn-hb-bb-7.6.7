/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.tag.manager.dao;

import com.jiuqi.nr.tag.manager.bean.TagNodeImpl;
import java.util.List;

public interface TagNodeDao {
    public static final String TABLE_NAME = "ITREE_TAG_NODE";
    public static final String TN_TGKEY = "tn_tgkey";
    public static final String TN_ENTKEY = "tn_entkey";
    public static final String TN_VIEWKEY = "tn_viewkey";

    public int[] batchInsert(List<TagNodeImpl> var1);

    public int delEntityDataOfTag(String var1);

    public int batchDelEntityDataOfTag(List<String> var1);

    public int sumEntityDatasOfTag(String var1);

    public List<TagNodeImpl> countOfTag(String var1);

    public List<TagNodeImpl> countOfTags(List<String> var1);

    public int delTagOfEntityData(String var1, String var2);

    public List<TagNodeImpl> countOfEntityData(String var1, String var2);

    public List<TagNodeImpl> countOfEntityDatas(String var1, List<String> var2);

    public List<TagNodeImpl> findAllTagNodes();
}

