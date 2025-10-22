/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.dao.DataAccessException
 */
package com.jiuqi.nr.dataresource.dao;

import com.jiuqi.nr.dataresource.entity.DimAttributeDO;
import java.util.List;
import org.springframework.dao.DataAccessException;

public interface IDimAttributeDao {
    public void insert(List<DimAttributeDO> var1) throws DataAccessException;

    public void update(List<DimAttributeDO> var1) throws DataAccessException;

    public void delete(String var1, String ... var2) throws DataAccessException;

    public void delete(String var1) throws DataAccessException;

    public List<DimAttributeDO> get(String var1, String var2) throws DataAccessException;

    public List<DimAttributeDO> getByDefineKey(String var1) throws DataAccessException;
}

