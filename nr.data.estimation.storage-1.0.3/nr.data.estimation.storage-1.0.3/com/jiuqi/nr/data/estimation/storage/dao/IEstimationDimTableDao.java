/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.estimation.storage.dao;

import com.jiuqi.nr.data.estimation.storage.entity.impl.DimTableRecord;

public interface IEstimationDimTableDao {
    public int insertDimTableRecord(DimTableRecord var1);

    public int deleteDimTableRecord(String var1);

    public DimTableRecord findDimTableRecord(String var1);

    public DimTableRecord findDimTableRecordByFormScheme(String var1);
}

