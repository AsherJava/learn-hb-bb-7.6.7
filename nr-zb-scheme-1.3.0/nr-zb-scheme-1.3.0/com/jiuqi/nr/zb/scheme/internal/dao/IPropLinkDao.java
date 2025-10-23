/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zb.scheme.internal.dao;

import com.jiuqi.nr.zb.scheme.internal.entity.PropLinkDO;
import java.util.List;

public interface IPropLinkDao {
    public List<PropLinkDO> listByScheme(String var1);

    public void deleteByScheme(String var1) throws Exception;

    public void insert(List<PropLinkDO> var1) throws Exception;
}

