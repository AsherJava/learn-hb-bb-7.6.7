/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataresource.service;

import com.jiuqi.nr.dataresource.DataResource;
import com.jiuqi.nr.dataresource.entity.DataResourceDO;
import java.util.List;

public interface IDataResourceService {
    public DataResource init();

    public String insert(DataResource var1);

    public void delete(String var1);

    public void update(DataResource var1);

    public DataResource get(String var1);

    public String[] insert(List<DataResource> var1);

    public void delete(List<String> var1);

    public void update(List<DataResource> var1);

    public List<DataResource> get(List<String> var1);

    public List<DataResource> getByParentKey(String var1);

    public List<DataResource> getBy(String var1, int var2, List<String> var3);

    public void deleteByParentKey(String var1);

    public List<DataResource> searchBy(String var1, String var2);

    public List<DataResource> getByDefineKey(String var1);

    public List<DataResourceDO> getByParent(String var1, String var2);

    public DataResourceDO getByKey(String var1);
}

