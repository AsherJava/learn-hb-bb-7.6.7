/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataresource.service;

import com.jiuqi.nr.dataresource.DataResourceDefineGroup;
import com.jiuqi.nr.dataresource.entity.ResourceTreeGroup;
import java.util.List;

public interface IDataResourceDefineGroupService {
    public DataResourceDefineGroup init();

    public String insert(DataResourceDefineGroup var1);

    public void delete(String var1);

    public void update(DataResourceDefineGroup var1);

    public DataResourceDefineGroup get(String var1);

    public String[] insert(List<DataResourceDefineGroup> var1);

    public void delete(List<String> var1);

    public void update(List<DataResourceDefineGroup> var1);

    public List<DataResourceDefineGroup> get(List<String> var1);

    public List<DataResourceDefineGroup> getByParentKey(String var1);

    public List<ResourceTreeGroup> fuzzySearch(String var1);

    public void deleteByParentKey(String var1);

    public List<DataResourceDefineGroup> searchBy(String var1, String var2);
}

