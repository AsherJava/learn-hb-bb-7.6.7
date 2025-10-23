/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.migration.syncscheme.dao;

import com.jiuqi.nr.migration.syncscheme.bean.SyncSchemeGroup;
import java.util.List;

public interface ISyncSchemeGroupDao {
    public void add(SyncSchemeGroup var1);

    public void update(SyncSchemeGroup var1);

    public void delete(String var1);

    public SyncSchemeGroup get(String var1);

    public SyncSchemeGroup getByTitleAndGroup(String var1, String var2);

    public List<SyncSchemeGroup> getByParent(String var1);
}

