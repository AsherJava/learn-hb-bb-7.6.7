/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.batch.summary.storage.entity.SummaryGroup
 *  com.jiuqi.nr.batch.summary.storage.entity.impl.SummaryGroupDefine
 */
package com.jiuqi.nr.batch.summary.service;

import com.jiuqi.nr.batch.summary.service.enumeration.GroupServiceState;
import com.jiuqi.nr.batch.summary.storage.entity.SummaryGroup;
import com.jiuqi.nr.batch.summary.storage.entity.impl.SummaryGroupDefine;
import java.util.List;

public interface BSGroupService {
    public SummaryGroup findGroup(String var1);

    public int moveGroups2Group(String var1, List<String> var2);

    public List<SummaryGroup> findChildGroups(String var1, String var2);

    public List<SummaryGroup> findAllChildGroups(String var1, String var2);

    public GroupServiceState removeSchemeGroup(String var1, String var2);

    public GroupServiceState renameSchemeGroup(String var1, String var2);

    public GroupServiceState saveSchemeGroup(SummaryGroupDefine var1);
}

