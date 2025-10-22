/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.dao.DataAccessException
 */
package com.jiuqi.nr.dataresource.dao;

import com.jiuqi.nr.dataresource.dao.IDataDao;
import com.jiuqi.nr.dataresource.entity.ResourceTreeDO;
import java.util.List;
import org.springframework.dao.DataAccessException;

public interface IDataResourceDefineDao
extends IDataDao<ResourceTreeDO> {
    public List<ResourceTreeDO> getByResourceGroupKey(String var1) throws DataAccessException;

    public List<ResourceTreeDO> getByResourceGroupKeyNoI18N(String var1) throws DataAccessException;

    public void deleteByResourceGroupKey(String var1) throws DataAccessException;

    public List<ResourceTreeDO> getByConditions(String var1, String var2);

    public List<ResourceTreeDO> fuzzySearch(String var1);
}

