/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.batch.summary.storage;

import com.jiuqi.nr.batch.summary.storage.entity.CustomCalibreRow;
import com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme;
import java.util.List;

public interface CustomCalibreDao {
    public int[] insertRows(SummaryScheme var1, List<CustomCalibreRow> var2);

    public int[] updateRows(SummaryScheme var1, List<CustomCalibreRow> var2);

    public int deleteRows(List<String> var1);

    public int deleteRows(String var1);

    public List<CustomCalibreRow> findConditionRow(String var1);
}

