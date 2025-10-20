/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.penetratebill.impl.dao;

import com.jiuqi.dc.penetratebill.impl.entity.PenetrateSchemeEO;
import java.util.List;

public interface PenetrateBillDao {
    public List<PenetrateSchemeEO> selectAll();

    public int insert(PenetrateSchemeEO var1);

    public int update(PenetrateSchemeEO var1);

    public int delete(PenetrateSchemeEO var1);
}

