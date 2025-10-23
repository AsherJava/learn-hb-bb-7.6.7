/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zb.scheme.internal.dao.impl;

import com.jiuqi.nr.zb.scheme.internal.db.BaseDao;
import com.jiuqi.nr.zb.scheme.internal.db.ZbSchemeTransUtil;
import java.util.List;

public abstract class ZbSchemeBaseDao<T>
extends BaseDao {
    public abstract Class<T> getClz();

    @Override
    public Class<?> getExternalTransCls() {
        return ZbSchemeTransUtil.class;
    }

    public T getByKey(String key) {
        return super.getByKey(key, this.getClz());
    }

    public List<T> listAll() {
        return this.list(this.getClz());
    }

    public void insert(List<T> data) {
        super.insert(data.toArray(new Object[0]));
    }

    public void update(List<T> data) {
        super.update(data.toArray(new Object[0]));
    }

    public void deleteByKeys(List<String> data) {
        super.delete(data.toArray(new Object[0]));
    }
}

