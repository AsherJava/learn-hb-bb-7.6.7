/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.unit.treestore.uselector.dao;

import com.jiuqi.nr.unit.treestore.uselector.bean.USFilterScheme;
import java.util.List;
import java.util.Set;

public interface IFilterSchemeDao {
    public static final String TABLE_NAME = "nr_uselector_filter_scheme";
    public static final String FC_KEY = "fc_key";
    public static final String FC_TITLE = "fc_title";
    public static final String FC_ENTITY = "fc_entity";
    public static final String FC_OWNER = "fc_owner";
    public static final String FC_SHARED = "fc_shared";
    public static final String FC_TEMPLATE = "fc_template";
    public static final String FC_CREATETIME = "fc_createtime";

    public String insert(USFilterScheme var1);

    public boolean update(USFilterScheme var1);

    public boolean updateShared(String var1, boolean var2);

    public int remove(String var1);

    public USFilterScheme find(String var1);

    public List<USFilterScheme> find(String var1, String var2);

    public List<USFilterScheme> find(Set<String> var1, String var2);
}

