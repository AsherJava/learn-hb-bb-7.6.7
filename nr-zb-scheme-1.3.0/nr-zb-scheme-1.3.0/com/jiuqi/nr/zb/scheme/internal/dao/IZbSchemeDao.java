/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zb.scheme.internal.dao;

import com.jiuqi.nr.zb.scheme.internal.entity.ZbSchemeDO;
import java.util.List;

public interface IZbSchemeDao {
    public ZbSchemeDO getByKey(String var1);

    public List<ZbSchemeDO> listAll();

    public List<ZbSchemeDO> listByParent(String var1);

    public void insert(List<ZbSchemeDO> var1) throws Exception;

    public void update(List<ZbSchemeDO> var1) throws Exception;

    public void deleteByKeys(List<String> var1) throws Exception;

    public void deleteByKey(String var1);
}

