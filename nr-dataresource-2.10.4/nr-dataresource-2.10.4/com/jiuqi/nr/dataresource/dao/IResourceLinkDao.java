/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataField
 *  org.springframework.dao.DataAccessException
 */
package com.jiuqi.nr.dataresource.dao;

import com.jiuqi.nr.dataresource.entity.DataResourceLinkDO;
import com.jiuqi.nr.dataresource.entity.SearchDataFieldDO;
import com.jiuqi.nr.datascheme.api.DataField;
import java.util.List;
import org.springframework.dao.DataAccessException;

public interface IResourceLinkDao {
    public void insert(List<DataResourceLinkDO> var1) throws DataAccessException;

    public void delete(String var1, List<String> var2) throws DataAccessException;

    public void delete(String var1) throws DataAccessException;

    public void delete(List<String> var1) throws DataAccessException;

    public void deleteByDefineKey(String var1) throws DataAccessException;

    public void update(List<DataResourceLinkDO> var1) throws DataAccessException;

    public List<DataField> getByGroup(String var1) throws DataAccessException;

    public List<DataResourceLinkDO> getByDataFieldKey(String var1) throws DataAccessException;

    public List<SearchDataFieldDO> searchByDefineKey(String var1, String var2) throws DataAccessException;

    public List<DataResourceLinkDO> getByDefineKey(String var1) throws DataAccessException;
}

