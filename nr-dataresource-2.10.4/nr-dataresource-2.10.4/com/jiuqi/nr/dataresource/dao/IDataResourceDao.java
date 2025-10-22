/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataresource.dao;

import com.jiuqi.nr.dataresource.dao.IDataDao;
import com.jiuqi.nr.dataresource.entity.DataResourceDO;
import java.util.List;

public interface IDataResourceDao
extends IDataDao<DataResourceDO> {
    public List<DataResourceDO> getByParent(String var1, String var2);

    public List<DataResourceDO> getByParentNoI18N(String var1, String var2);

    public List<DataResourceDO> getByParent(String var1);

    public List<DataResourceDO> getByParentNoI18N(String var1);

    public List<DataResourceDO> getByDefineKey(String var1);

    public void deleteByDefineKey(String var1);

    public List<DataResourceDO> getByConditions(String var1, String var2, String var3);

    public List<DataResourceDO> getByConditions(String var1, String var2);

    public List<DataResourceDO> getByConditions(String var1, int var2, List<String> var3);

    public List<DataResourceDO> searchBy(String var1, String var2);
}

