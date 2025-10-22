/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataresource.service;

import com.jiuqi.nr.dataresource.DataResourceDefine;
import com.jiuqi.nr.dataresource.DimType;
import java.util.List;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public interface IDataResourceDefineService {
    public DataResourceDefine init();

    public String insert(DataResourceDefine var1);

    public void delete(String var1);

    public void update(DataResourceDefine var1);

    public DataResourceDefine get(String var1);

    public String[] insert(List<DataResourceDefine> var1);

    public void delete(List<String> var1);

    public void update(List<DataResourceDefine> var1);

    public String copy(DataResourceDefine var1, String var2);

    public List<DataResourceDefine> get(List<String> var1);

    public List<DataResourceDefine> getByGroupKey(String var1);

    public List<DataResourceDefine> fuzzySearch(String var1);

    public void deleteByGroupKey(String var1);

    @Nullable
    public DimType determineByDimKey(@NonNull String var1, @NonNull String var2);
}

