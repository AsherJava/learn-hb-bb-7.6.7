/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.batch.summary.storage;

import com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme;
import com.jiuqi.nr.batch.summary.storage.entity.impl.SummarySchemeDefine;
import com.jiuqi.nr.batch.summary.storage.entity.impl.SummarySchemeImpl;
import java.util.Date;
import java.util.List;

public interface SummarySchemeDao {
    public int insertRow(SummarySchemeDefine var1);

    public int modifyRow(SummarySchemeImpl var1);

    public int modifySchemeDataDate(String var1, Date var2);

    public int removeRow(String var1);

    public int moveRow(String var1, List<String> var2);

    public SummaryScheme findScheme(String var1);

    public SummaryScheme findSchemeByTaskGroupAndTitle(String var1, String var2, String var3);

    public SummaryScheme findScheme(String var1, String var2);

    public List<SummaryScheme> findSchemes(String var1, String var2);

    public List<SummaryScheme> findSchemes(List<String> var1);

    public List<SummaryScheme> findGroupSchemes(String var1, String var2);

    public List<SummaryScheme> findGroupSchemes(String var1, List<String> var2);
}

