/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SqlBuildUtil
 *  com.jiuqi.gcreport.definition.impl.basic.dao.AbstractEntDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.basic.intf.FEntSqlTemplate
 */
package com.jiuqi.gcreport.financialcheckcore.item.dao.impl;

import com.jiuqi.common.base.util.SqlBuildUtil;
import com.jiuqi.gcreport.definition.impl.basic.dao.AbstractEntDbSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.basic.intf.FEntSqlTemplate;
import com.jiuqi.gcreport.financialcheckcore.item.dao.GcDbLockDao;
import com.jiuqi.gcreport.financialcheckcore.item.entity.GcDbLockEO;
import com.jiuqi.gcreport.financialcheckcore.sqlutils.FcNativeSqlTemplate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class GcDbLockDaoImpl
extends AbstractEntDbSqlGenericDAO<GcDbLockEO>
implements GcDbLockDao {
    public GcDbLockDaoImpl() {
        super(GcDbLockEO.class, (FEntSqlTemplate)new FcNativeSqlTemplate());
        this.setTableName("GC_DB_LOCK");
    }

    @Override
    public List<String> queryExpiredData(long expiredTime) {
        String sql = "  select DISTINCT  l.lockId  lockId\n  from GC_DB_LOCK  l \n   where l.createTime < ? \n";
        return this.selectFirstList(String.class, "  select DISTINCT  l.lockId  lockId\n  from GC_DB_LOCK  l \n   where l.createTime < ? \n", new Object[]{expiredTime});
    }

    @Override
    public int deleteByLockId(String lockId) {
        String sql = "  delete from GC_DB_LOCK   where lockId = ? and REENTRANTCOUNT = -1 \n";
        return this.execute("  delete from GC_DB_LOCK   where lockId = ? and REENTRANTCOUNT = -1 \n", new Object[]{lockId});
    }

    @Override
    public void deleteExpiredDataByLockId(String lockId) {
        String sql = "  delete from GC_DB_LOCK   where lockId = ? \n";
        this.execute("  delete from GC_DB_LOCK   where lockId = ? \n", new Object[]{lockId});
    }

    @Override
    public List<String> queryUserNameByInputItemId(Collection<String> temIds, String key) {
        String sql = "  select DISTINCT l.userName  userName \n    from GC_DB_LOCK  l \n   where " + SqlBuildUtil.getStrInCondi((String)"l.itemId", new ArrayList<String>(temIds)) + " and l.isolationField = ? \n ";
        return this.selectFirstList(String.class, sql, new Object[]{key});
    }

    @Override
    public List<GcDbLockEO> listByItems(Collection<String> itemIds, String key) {
        String sql = "  select * \n    from GC_DB_LOCK  l \n   where " + SqlBuildUtil.getStrInCondi((String)"l.itemId", new ArrayList<String>(itemIds)) + " and l.isolationField = ? \n ";
        return this.selectEntity(sql, new Object[]{key});
    }

    @Override
    public void updateReentrantCountByLockId(String lockId, boolean isLock) {
        String sql = "";
        sql = isLock ? "  update GC_DB_LOCK set REENTRANTCOUNT = REENTRANTCOUNT + 1  where lockId = ? \n" : "  update GC_DB_LOCK set REENTRANTCOUNT = REENTRANTCOUNT - 1  where lockId = ? \n";
        this.execute(sql, new Object[]{lockId});
    }
}

