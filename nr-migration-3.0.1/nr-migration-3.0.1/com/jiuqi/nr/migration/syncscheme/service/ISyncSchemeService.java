/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.migration.syncscheme.service;

import com.jiuqi.nr.migration.syncscheme.bean.SyncScheme;
import com.jiuqi.nr.migration.syncscheme.exception.SyncSchemeException;
import java.util.List;

public interface ISyncSchemeService {
    public void check(boolean var1, SyncScheme var2) throws SyncSchemeException;

    public void add(SyncScheme var1) throws SyncSchemeException;

    public void update(SyncScheme var1) throws SyncSchemeException;

    public void updateData(SyncScheme var1) throws SyncSchemeException;

    public void batchDelete(List<String> var1) throws SyncSchemeException;

    public void moveUp(String var1) throws SyncSchemeException;

    public void moveDown(String var1) throws SyncSchemeException;

    public SyncScheme getByKey(String var1) throws SyncSchemeException;

    public List<SyncScheme> getByGroup(String var1) throws SyncSchemeException;
}

