/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme
 *  com.jiuqi.nr.batch.summary.storage.entity.impl.SummarySchemeDefine
 *  com.jiuqi.nr.batch.summary.storage.entity.impl.SummarySchemeImpl
 */
package com.jiuqi.nr.batch.summary.service;

import com.jiuqi.nr.batch.summary.service.enumeration.SchemeServiceState;
import com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme;
import com.jiuqi.nr.batch.summary.storage.entity.impl.SummarySchemeDefine;
import com.jiuqi.nr.batch.summary.storage.entity.impl.SummarySchemeImpl;
import java.util.Date;
import java.util.List;

public interface BSSchemeService {
    public SummaryScheme findScheme(String var1);

    public SummaryScheme copyScheme(String var1);

    public SummaryScheme findSchemeByTaskGroupAndTitle(String var1, String var2, String var3);

    public SummaryScheme findScheme(String var1, String var2);

    public List<SummaryScheme> findSchemes(List<String> var1);

    public List<SummaryScheme> findSchemes(String var1);

    public List<SummaryScheme> findChildSchemeByGroup(String var1, String var2);

    public List<SummaryScheme> findAllChildSchemeByGroup(String var1, String var2);

    public int updateSumDataDate(String var1, Date var2);

    public int moveScheme2Group(String var1, List<String> var2);

    public SchemeServiceState saveSummaryScheme(SummarySchemeDefine var1);

    public SchemeServiceState updateSummaryScheme(SummarySchemeImpl var1);

    public SchemeServiceState updateSummarySchemeDefine(SummarySchemeDefine var1);

    public SchemeServiceState removeSummaryScheme(String var1);
}

