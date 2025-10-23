/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zb.scheme.internal.dao;

import com.jiuqi.nr.zb.scheme.internal.entity.ZbSchemeVersionDO;
import java.util.List;

public interface IZbSchemeVersionDao {
    public ZbSchemeVersionDO getByKey(String var1);

    public ZbSchemeVersionDO getBySchemeAndPeriod(String var1, String var2);

    public List<ZbSchemeVersionDO> listByScheme(String var1);

    public void insert(List<ZbSchemeVersionDO> var1) throws Exception;

    public void update(List<ZbSchemeVersionDO> var1) throws Exception;

    public void deleteByKeys(List<String> var1) throws Exception;

    public void deleteByScheme(String var1) throws Exception;

    public void deleteByKey(String var1);

    public int getReferNum(String var1);
}

