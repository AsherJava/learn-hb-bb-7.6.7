/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zb.scheme.internal.dao;

import com.jiuqi.nr.zb.scheme.internal.entity.ZbSchemeGroupDO;
import java.util.List;

public interface IZbSchemeGroupDao {
    public ZbSchemeGroupDO getByKey(String var1);

    public List<ZbSchemeGroupDO> listAll();

    public List<ZbSchemeGroupDO> listByParent(String var1);

    public void insert(ZbSchemeGroupDO var1) throws Exception;

    public void insert(List<ZbSchemeGroupDO> var1) throws Exception;

    public void update(List<ZbSchemeGroupDO> var1) throws Exception;

    public void deleteByKeys(List<String> var1) throws Exception;
}

