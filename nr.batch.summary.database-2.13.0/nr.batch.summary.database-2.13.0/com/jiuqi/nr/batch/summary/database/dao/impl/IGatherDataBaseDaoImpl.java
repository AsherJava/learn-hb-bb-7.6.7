/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.exception.DBParaException
 *  com.jiuqi.np.definition.internal.db.BaseDao
 */
package com.jiuqi.nr.batch.summary.database.dao.impl;

import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.batch.summary.database.dao.IGatherDataBaseDao;
import com.jiuqi.nr.batch.summary.database.intf.GatherSubDataBase;
import com.jiuqi.nr.batch.summary.database.intf.impl.GatherSubDataBaseImpl;
import org.springframework.stereotype.Repository;

@Repository
public class IGatherDataBaseDaoImpl
extends BaseDao
implements IGatherDataBaseDao {
    private Class<GatherSubDataBaseImpl> implClz = GatherSubDataBaseImpl.class;

    @Override
    public GatherSubDataBase getGatherSubDataBase(String dataSchemeKey, String code) {
        return (GatherSubDataBase)this.getBy("BSSD_CODE = ? AND BSSD_DS_KEY = ?", new Object[]{code, dataSchemeKey}, this.implClz);
    }

    @Override
    public void insertGatherSubDataBase(GatherSubDataBase gatherSubDataBase) throws DBParaException {
        this.insert(gatherSubDataBase);
    }

    @Override
    public void deleteGatherDataBase(String dataSchemeKey, String code) throws DBParaException {
        this.deleteBy(new String[]{"code", "dataSchemeKey"}, new Object[]{code, dataSchemeKey});
    }

    public Class<?> getClz() {
        return this.implClz;
    }
}

