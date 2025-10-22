/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.internal.dao;

import com.jiuqi.nr.datascheme.internal.entity.DataSchemeCalResultDO;
import java.util.List;

public interface IDataSchemeCalResultDao {
    public List<DataSchemeCalResultDO> getResult(String var1);

    public void insertResult(DataSchemeCalResultDO var1);

    public void updateResult(DataSchemeCalResultDO var1);

    public void deleteResult(String var1);

    public void saveResult(DataSchemeCalResultDO var1);
}

