/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zb.scheme.internal.dao;

import com.jiuqi.nr.zb.scheme.internal.entity.PropInfoDO;
import java.util.List;

public interface IPropInfoDao {
    public List<PropInfoDO> listAll();

    public PropInfoDO getByKey(String var1);

    public void insert(PropInfoDO var1);

    public void update(PropInfoDO var1);

    public void update(PropInfoDO var1, boolean var2);

    public void delete(PropInfoDO var1);

    public void insert(List<PropInfoDO> var1);

    public void update(List<PropInfoDO> var1);

    public void update(List<PropInfoDO> var1, boolean var2);

    public void delete(List<PropInfoDO> var1);

    public List<PropInfoDO> listPropInfoInScheme(String var1);

    public PropInfoDO getByKeyFromPropLink(String var1);
}

