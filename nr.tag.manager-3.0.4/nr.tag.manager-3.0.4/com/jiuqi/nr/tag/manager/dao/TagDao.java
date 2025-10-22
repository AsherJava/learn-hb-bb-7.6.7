/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.tag.manager.dao;

import com.jiuqi.nr.tag.manager.bean.TagImpl;
import java.util.List;

public interface TagDao {
    public static final String TABLE_NAME = "ITREE_TAG";
    public static final String TG_KEY = "tg_key";
    public static final String TG_TITLE = "tg_title";
    public static final String TG_OWNER = "tg_owner";
    public static final String TG_SHARED = "tg_shared";
    public static final String TG_VIEWKEY = "tg_viewkey";
    public static final String TG_CATEGORY = "tg_category";
    public static final String TG_FORMULA = "tg_formula";
    public static final String TG_ORDER = "tg_order";
    public static final String TG_DESC = "tg_desc";
    public static final String TG_RANGE_MODIFY = "tg_range_modify";

    public int[] batchInsert(List<TagImpl> var1);

    public int[] batchUpdate(List<TagImpl> var1);

    public int batchDelete(List<String> var1);

    public TagImpl findByKey(String var1);

    public List<TagImpl> findAllByOV(String var1, String var2);

    public boolean checkTitleRepeat(String var1);

    public List<String> findAllTagTitles();

    public List<TagImpl> findAllTags();
}

