/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.batch.summary.storage;

import com.jiuqi.nr.batch.summary.storage.entity.SummaryGroup;
import com.jiuqi.nr.batch.summary.storage.entity.impl.SummaryGroupDefine;
import java.util.List;

public interface SummaryGroupDao {
    public int insertRow(SummaryGroupDefine var1);

    public int modifyRow(SummaryGroupDefine var1);

    public int removeRow(String var1);

    public int removeRow(List<String> var1);

    public int renameGroup(String var1, String var2);

    public int moveGroup(String var1, List<String> var2);

    public SummaryGroup findGroup(String var1);

    public List<SummaryGroup> findChildGroups(String var1, String var2);

    public List<SummaryGroup> findAllChildGroups(String var1, String var2);
}

