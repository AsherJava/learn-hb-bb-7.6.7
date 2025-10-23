/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zb.scheme.internal.dao;

import com.jiuqi.nr.zb.scheme.core.PropInfo;
import com.jiuqi.nr.zb.scheme.internal.entity.ZbInfoDO;
import java.util.List;

public interface IZbInfoDao {
    public ZbInfoDO getByKey(String var1);

    public ZbInfoDO getByKey(String var1, List<PropInfo> var2);

    public ZbInfoDO getByVersionAndCode(String var1, String var2);

    public ZbInfoDO getByVersionAndCode(String var1, String var2, List<PropInfo> var3);

    public List<ZbInfoDO> listByParent(String var1);

    public List<ZbInfoDO> listByParent(String var1, List<PropInfo> var2);

    public List<ZbInfoDO> listByKeys(List<String> var1);

    public List<ZbInfoDO> listByKeys(List<String> var1, List<PropInfo> var2);

    public void insert(ZbInfoDO var1) throws Exception;

    public void insert(List<ZbInfoDO> var1) throws Exception;

    public void update(ZbInfoDO var1) throws Exception;

    public void update(List<ZbInfoDO> var1) throws Exception;

    public void deleteByKeys(List<String> var1);

    public void deleteByScheme(String var1);

    public void deleteByVersion(String var1);

    @Deprecated
    public List<ZbInfoDO> listBySchemeAndVersion(String var1, String var2);

    @Deprecated
    public List<ZbInfoDO> listBySchemeAndVersion(String var1, String var2, List<PropInfo> var3);

    public List<ZbInfoDO> listByVersion(String var1);

    public List<ZbInfoDO> listByVersion(String var1, List<PropInfo> var2);

    public List<ZbInfoDO> listBySchemeAndCode(String var1, String var2);

    public List<ZbInfoDO> listBySchemeAndCode(String var1, String var2, List<PropInfo> var3);

    public List<ZbInfoDO> listByVersionAndCode(String var1, List<String> var2);

    public List<ZbInfoDO> listByVersionAndCode(String var1, List<String> var2, List<PropInfo> var3);

    public int getNonEmptyFieldCount(String var1);

    public int countZbByVersion(String var1);

    public int countZbByScheme(String var1);
}

