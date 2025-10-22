/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataresource.dao;

import com.jiuqi.nr.dataresource.dao.IDataDao;
import com.jiuqi.nr.dataresource.entity.ResourceTreeGroup;
import java.util.List;

public interface IDataResourceDefineGroupDao
extends IDataDao<ResourceTreeGroup> {
    public List<ResourceTreeGroup> getByParent(String var1);

    public List<ResourceTreeGroup> getByParentNoI18N(String var1);

    public void deleteByParent(String var1);

    public List<ResourceTreeGroup> getByConditions(String var1, String var2);

    public List<ResourceTreeGroup> fuzzySearch(String var1);
}

