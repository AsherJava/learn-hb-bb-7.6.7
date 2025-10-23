/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zb.scheme.internal.dao;

import com.jiuqi.nr.zb.scheme.internal.entity.ZbGroupDO;
import java.util.List;

public interface IZbGroupDao {
    public ZbGroupDO getByKey(String var1);

    public List<ZbGroupDO> listByKeys(List<String> var1);

    public List<ZbGroupDO> listByParent(String var1);

    public List<ZbGroupDO> listByVersion(String var1);

    public List<ZbGroupDO> listByScheme(String var1);

    public List<ZbGroupDO> listBySchemeAndVersion(String var1, String var2);

    public List<ZbGroupDO> listByVersionAndParent(String var1, String var2);

    public void deleteByVersion(String var1);

    public void insert(List<ZbGroupDO> var1);

    public void update(List<ZbGroupDO> var1);

    public void delete(String var1);

    public void deleteByKeys(List<String> var1);

    public void deleteByScheme(String var1);
}

