/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  org.springframework.dao.DataAccessException
 */
package com.jiuqi.nr.datascheme.internal.dao;

import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.datascheme.internal.entity.DataDimDO;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.lang.NonNull;

public interface IDataDimDao<DO extends DataDimDO> {
    public void insert(DO var1) throws DataAccessException;

    public void batchInsert(List<DO> var1) throws DataAccessException;

    public void insertNoUpdateTime(DO var1) throws DataAccessException;

    @Deprecated
    public void delete(@NonNull String var1, @NonNull String var2) throws DataAccessException;

    public DO get(@NonNull String var1, @NonNull String var2) throws DataAccessException;

    public void deleteByDataScheme(@NonNull String var1) throws DataAccessException;

    public List<DO> getByDataScheme(@NonNull String var1) throws DataAccessException;

    public List<DO> getByPeriodType(@NonNull PeriodType var1);

    public List<DO> getByDimKey(@NonNull String var1);

    public List<DO> getByDimKey(String ... var1);

    public List<DO> getAll();

    public void delete(String var1, DimensionType var2, String var3);

    public void delete(String var1, DimensionType var2);
}

