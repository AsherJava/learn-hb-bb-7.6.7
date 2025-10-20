/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.navigation.dao;

import com.jiuqi.navigation.entity.NavigationEO;
import java.util.List;

public interface NavigationDao {
    public List<NavigationEO> loadAll();

    public List<NavigationEO> findByExample(NavigationEO var1);

    public void save(NavigationEO var1);

    public void update(NavigationEO var1);

    public List<NavigationEO> findByCode(String var1);

    public void deleteById(String var1);
}

