/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.conversion.conversionrate.service;

import java.util.List;

public interface CommonService<V, E> {
    public V save(V var1);

    public V update(V var1);

    public V delete(String var1);

    public V get(String var1);

    public List<V> queryAll();

    public void beforeSave(E var1);

    public E doSave(E var1);

    public void beforeUpdate(E var1);

    public E doUpdate(E var1);

    public void beforeDelete(E var1);

    public E convertVO2EO(V var1);

    public V convertEO2VO(E var1);
}

