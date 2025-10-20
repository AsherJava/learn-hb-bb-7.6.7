/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.subject.impl.subject.cache.proider;

import com.jiuqi.common.subject.impl.subject.cache.intf.ICacheDefine;
import java.util.Collection;
import java.util.List;

public interface ICacheProvider<E> {
    public ICacheDefine getCacheDefine();

    public void cleanCache();

    public Collection<E> loadCache();

    public void syncCache();

    public void syncCurrCache();

    public List<E> list();
}

