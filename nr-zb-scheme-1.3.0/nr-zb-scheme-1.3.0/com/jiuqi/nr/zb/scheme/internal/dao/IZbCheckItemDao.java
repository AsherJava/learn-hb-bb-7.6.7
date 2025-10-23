/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zb.scheme.internal.dao;

import com.jiuqi.nr.zb.scheme.internal.entity.ZbCheckItemDO;
import java.util.List;

public interface IZbCheckItemDao {
    public void insert(List<ZbCheckItemDO> var1);

    public int deleteByCheck(String var1);

    public int deleteByExpireTime(long var1);

    public List<ZbCheckItemDO> listByCheck(String var1);

    public List<ZbCheckItemDO> listByCheckAndFormGroup(String var1, String var2);

    public List<ZbCheckItemDO> listByCheckAndForm(String var1, String var2);
}

