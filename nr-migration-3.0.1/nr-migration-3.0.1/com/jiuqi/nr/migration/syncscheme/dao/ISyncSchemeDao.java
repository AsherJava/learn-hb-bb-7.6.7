/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.migration.syncscheme.dao;

import com.jiuqi.nr.migration.syncscheme.bean.SyncScheme;
import java.util.List;

public interface ISyncSchemeDao {
    public void add(SyncScheme var1);

    public void update(SyncScheme var1);

    public void updateData(SyncScheme var1);

    public void batchDelete(List<String> var1);

    public SyncScheme get(String var1);

    public SyncScheme getByCode(String var1);

    public SyncScheme getByTitle(String var1);

    public List<SyncScheme> getByParent(String var1);
}

