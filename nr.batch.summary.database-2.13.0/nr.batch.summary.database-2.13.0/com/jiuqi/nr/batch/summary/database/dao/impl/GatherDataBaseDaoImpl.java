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
import com.jiuqi.nr.batch.summary.database.dao.GatherDataBaseDao;
import com.jiuqi.nr.batch.summary.database.intf.GatherDataBase;
import com.jiuqi.nr.batch.summary.database.intf.impl.GatherDataBaseImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class GatherDataBaseDaoImpl
extends BaseDao
implements GatherDataBaseDao {
    private static final Logger logger = LoggerFactory.getLogger(GatherDataBaseDaoImpl.class);
    private Class<GatherDataBaseImpl> implClz = GatherDataBaseImpl.class;

    @Override
    public GatherDataBase getGatherDataBase(String dataSchemeKey) {
        return (GatherDataBase)this.getBy("BSD_DS_KEY = ?", new Object[]{dataSchemeKey}, this.implClz);
    }

    @Override
    public void insertGatherDataBase(GatherDataBase gatherDataBase) throws DBParaException {
        this.insert(gatherDataBase);
    }

    @Override
    public void deleteGatherDataBase(String dataSchemeKey) throws DBParaException {
        this.deleteBy(new String[]{"dataSchemeKey"}, new Object[]{dataSchemeKey});
    }

    @Override
    public void deleteTableData(String tableName) {
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM ").append(tableName).append(" WHERE 1=1");
        try {
            this.jdbcTemplate.execute(sql.toString());
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public Class<?> getClz() {
        return this.implClz;
    }
}

