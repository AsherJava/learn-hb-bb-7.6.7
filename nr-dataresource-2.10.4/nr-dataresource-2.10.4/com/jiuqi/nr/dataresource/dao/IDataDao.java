/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.dao.DataAccessException
 */
package com.jiuqi.nr.dataresource.dao;

import com.jiuqi.nr.dataresource.DataBasic;
import java.util.List;
import org.springframework.dao.DataAccessException;

public interface IDataDao<DO extends DataBasic> {
    public String insert(DO var1) throws DataAccessException;

    public void delete(String var1) throws DataAccessException;

    public void update(DO var1) throws DataAccessException;

    public DO get(String var1) throws DataAccessException;

    public String[] insert(List<DO> var1) throws DataAccessException;

    public void delete(List<String> var1) throws DataAccessException;

    public void update(List<DO> var1) throws DataAccessException;

    public List<DO> get(List<String> var1) throws DataAccessException;

    public List<DO> getAll() throws DataAccessException;
}

