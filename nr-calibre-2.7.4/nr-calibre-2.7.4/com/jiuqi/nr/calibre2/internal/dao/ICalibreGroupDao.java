/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.calibre2.internal.dao;

import com.jiuqi.nr.calibre2.domain.CalibreGroupDO;
import java.util.List;

public interface ICalibreGroupDao {
    public List<CalibreGroupDO> query();

    public List<CalibreGroupDO> queryByParent(String var1);

    public List<CalibreGroupDO> queryByName(String var1);

    public CalibreGroupDO get(String var1);

    public int insert(CalibreGroupDO var1);

    public int update(CalibreGroupDO var1);

    public int delete(String var1);
}

