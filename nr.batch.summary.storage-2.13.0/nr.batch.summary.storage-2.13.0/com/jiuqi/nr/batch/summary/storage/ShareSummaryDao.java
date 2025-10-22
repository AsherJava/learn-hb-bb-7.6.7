/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.batch.summary.storage;

import com.jiuqi.nr.batch.summary.storage.entity.ShareSummaryGroup;
import com.jiuqi.nr.batch.summary.storage.entity.ShareSummaryScheme;
import com.jiuqi.nr.batch.summary.storage.entity.impl.ShareSummarySchemeDefine;
import java.util.List;
import java.util.Set;

public interface ShareSummaryDao {
    public int findScheme(String var1, String var2, String var3);

    public int findScheme(String var1);

    public List<ShareSummaryScheme> findSchemes(String var1);

    public List<ShareSummaryScheme> findSchemes(List<String> var1);

    public List<ShareSummaryScheme> findGroupSchemes(String var1, String var2);

    public List<ShareSummaryScheme> findGroupSchemes(String var1, List<String> var2);

    public ShareSummaryGroup findGroup(String var1);

    public List<ShareSummaryGroup> findChildGroups(String var1, String var2);

    public List<ShareSummaryGroup> findAllChildGroups(String var1, String var2);

    public int removeShareSummaryScheme(ShareSummarySchemeDefine var1);

    public int removeShareSummaryScheme(String var1);

    public int addShareSummaryScheme(ShareSummarySchemeDefine var1);

    public Set<String> findToUsers(String var1, String var2);
}

