/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.batch.summary.storage.entity.ShareSummaryGroup
 *  com.jiuqi.nr.batch.summary.storage.entity.ShareSummaryScheme
 *  com.jiuqi.nr.batch.summary.storage.entity.impl.ShareSummarySchemeDefine
 */
package com.jiuqi.nr.batch.summary.service;

import com.jiuqi.nr.batch.summary.service.enumeration.ShareSchemeServiceState;
import com.jiuqi.nr.batch.summary.storage.entity.ShareSummaryGroup;
import com.jiuqi.nr.batch.summary.storage.entity.ShareSummaryScheme;
import com.jiuqi.nr.batch.summary.storage.entity.impl.ShareSummarySchemeDefine;
import java.util.List;
import java.util.Set;

public interface BSShareService {
    public List<ShareSummaryGroup> findChildGroups(String var1, String var2);

    public List<ShareSummaryScheme> findChildSchemeByGroup(String var1);

    public List<ShareSummaryScheme> findChildSchemeByGroup(String var1, String var2);

    public ShareSummaryGroup findGroup(String var1);

    public boolean findScheme(String var1);

    public ShareSummaryScheme findScheme(String var1, int var2);

    public ShareSchemeServiceState removeShareSummaryScheme(ShareSummarySchemeDefine var1);

    public ShareSchemeServiceState removeShareSummaryScheme(String var1);

    public ShareSchemeServiceState addShareSummaryScheme(ShareSummarySchemeDefine var1);

    public Set<String> findToUsers(String var1, String[] var2);
}

