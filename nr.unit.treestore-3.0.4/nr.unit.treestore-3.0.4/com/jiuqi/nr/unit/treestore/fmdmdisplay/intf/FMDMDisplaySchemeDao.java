/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.unit.treestore.fmdmdisplay.intf;

import com.jiuqi.nr.unit.treestore.fmdmdisplay.bean.FMDMDisplayScheme;
import java.util.List;

public interface FMDMDisplaySchemeDao {
    public static final String TABLE_NAME = "NR_UNITTREE_CAPTION_FIELDS";
    public static final String CF_KEY = "CF_KEY";
    public static final String CF_FORMSCHEME = "CF_FORMSCHEME";
    public static final String CF_OWNER = "CF_OWNER";
    public static final String CF_FIELDS = "CF_FIELDS";
    public static final String CF_CREATIVE = "CF_CREATIVE";
    public static final String CF_ENTITYID = "CF_ENTITYID";

    public int[] batchInsert(List<FMDMDisplayScheme> var1);

    public int[] batchUpdate(List<FMDMDisplayScheme> var1);

    public int batchDelete(List<String> var1);

    public FMDMDisplayScheme findByKey(String var1);

    public FMDMDisplayScheme findByTaskAndOwner(String var1, String var2, String var3);
}

