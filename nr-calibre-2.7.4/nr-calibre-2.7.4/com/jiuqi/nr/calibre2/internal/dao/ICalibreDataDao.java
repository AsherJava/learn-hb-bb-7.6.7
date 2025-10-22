/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.calibre2.internal.dao;

import com.jiuqi.nr.calibre2.domain.CalibreDataDO;
import java.util.List;

public interface ICalibreDataDao {
    public List<CalibreDataDO> queryByCalibreCode(String var1);

    public List<CalibreDataDO> query(String var1, String var2, String var3);

    public List<CalibreDataDO> queryRoot(String var1);

    public List<CalibreDataDO> fuzzyQuery(String var1, String var2);

    public List<CalibreDataDO> batchQuery(String var1, String var2, List<String> var3);

    public int add(CalibreDataDO var1);

    public int[] batchAdd(List<CalibreDataDO> var1);

    public int addWithoutParent(CalibreDataDO var1);

    public int[] batchAddWithoutParent(List<CalibreDataDO> var1);

    public int delete(String var1, String var2, String var3);

    public int deleteAll(String var1);

    public int batchDelete(String var1, String var2, List<String> var3);

    public int update(CalibreDataDO var1);

    public int[] batchUpdate(List<CalibreDataDO> var1);

    public int[] batchChangeOrder(List<CalibreDataDO> var1, String var2);
}

