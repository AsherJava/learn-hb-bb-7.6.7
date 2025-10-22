/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.calibre2.internal.dao;

import com.jiuqi.nr.calibre2.domain.CalibreDefineDO;
import java.util.List;

public interface ICalibreDefineDao {
    public List<CalibreDefineDO> query();

    public List<CalibreDefineDO> queryByGroup(String var1);

    public List<CalibreDefineDO> queryByRefer(String var1);

    public List<CalibreDefineDO> searchByNameOrCode(String var1, String var2);

    public List<CalibreDefineDO> searchByNameOrCode(String var1);

    public CalibreDefineDO get(String var1);

    public CalibreDefineDO getByCode(String var1);

    public int insert(CalibreDefineDO var1);

    public int update(CalibreDefineDO var1);

    public int delete(String var1);

    public int[] batchDelete(List<Object[]> var1);

    public int[] batchUpdateOrder(List<Object[]> var1);
}

