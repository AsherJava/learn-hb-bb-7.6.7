/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.calibre2.internal.dao;

import com.jiuqi.nr.calibre2.internal.domain.BatchCalibreSubListDO;
import com.jiuqi.nr.calibre2.internal.domain.CalibreSubListDO;
import java.util.List;

public interface ICalibreSubListDao {
    public List<CalibreSubListDO> query(CalibreSubListDO var1);

    public List<CalibreSubListDO> batchQuery(BatchCalibreSubListDO var1);

    public int add(CalibreSubListDO var1);

    public int update(CalibreSubListDO var1);

    public int delete(CalibreSubListDO var1);

    public int deleteAll(CalibreSubListDO var1);

    public int[] batchAdd(BatchCalibreSubListDO var1);

    public int[] batchUpdate(BatchCalibreSubListDO var1);

    public int[] batchDelete(BatchCalibreSubListDO var1);
}

