/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.db.BaseDao
 */
package com.jiuqi.nr.subdatabase.dao;

import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.subdatabase.facade.DataTableDDLInfo;
import com.jiuqi.nr.subdatabase.facade.impl.DataTableDDLInfoImpl;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class DataTableDDLInfoDao
extends BaseDao {
    private Class<DataTableDDLInfoImpl> implClz = DataTableDDLInfoImpl.class;

    public Class<?> getClz() {
        return this.implClz;
    }

    public List<DataTableDDLInfo> queryInfoBySchemeKey(String dataSchemeKey) {
        return super.list("DS_KEY =?", new Object[]{dataSchemeKey}, this.implClz);
    }

    public DataTableDDLInfo queryInfoBySKAndTMK(String dataSchemeKey, String tableModelKey) {
        return (DataTableDDLInfo)super.getBy("DS_KEY =? AND TM_KEY  =?", new Object[]{dataSchemeKey, tableModelKey}, this.implClz);
    }
}

