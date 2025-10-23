/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.migration.syncscheme.service;

import com.jiuqi.nr.migration.syncscheme.bean.SyncSchemeGroup;
import com.jiuqi.nr.migration.syncscheme.exception.SyncSchemeException;
import java.util.List;

public interface ISyncSchemeGroupService {
    public void check(boolean var1, SyncSchemeGroup var2) throws SyncSchemeException;

    public void add(SyncSchemeGroup var1) throws SyncSchemeException;

    public void update(SyncSchemeGroup var1) throws SyncSchemeException;

    public void delete(String var1) throws SyncSchemeException;

    public SyncSchemeGroup getByKey(String var1);

    public void moveUp(String var1) throws SyncSchemeException;

    public void moveDown(String var1) throws SyncSchemeException;

    public List<SyncSchemeGroup> getByParent(String var1);
}

