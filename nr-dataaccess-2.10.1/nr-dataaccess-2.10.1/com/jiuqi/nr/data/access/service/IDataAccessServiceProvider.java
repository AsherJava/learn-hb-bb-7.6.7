/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.access.service;

import com.jiuqi.nr.data.access.service.IDataAccessFormService;
import com.jiuqi.nr.data.access.service.IDataAccessService;
import java.util.List;
import java.util.Set;

public interface IDataAccessServiceProvider {
    public IDataAccessService getDataAccessService(String var1, String var2);

    public IDataAccessService getDataAccessService(String var1, String var2, Set<String> var3);

    public IDataAccessService getZBDataAccessService();

    public IDataAccessService getZBDataAccessService(String var1);

    public IDataAccessService getZBDataAccessService(String var1, Set<String> var2);

    public IDataAccessService getZBDataAccessService(List<String> var1);

    public IDataAccessService getZBDataAccessService(List<String> var1, Set<String> var2);

    public IDataAccessFormService getDataAccessFormService();
}

