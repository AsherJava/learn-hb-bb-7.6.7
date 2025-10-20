/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.budget.domain;

import com.jiuqi.budget.init.BaseDO;
import java.util.List;

public interface BaseService<T extends BaseDO> {
    public T getByCode(String var1);

    public T getById(String var1);

    public T getByParam(T var1);

    public List<T> listByParam(T var1);

    public List<T> listByParentId(String var1);

    public List<T> listAll();

    public T insert(T var1);

    public T update(T var1);

    public int delete(T var1);
}

