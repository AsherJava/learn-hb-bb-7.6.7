/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 */
package com.jiuqi.gcreport.offsetitem.init.carryover.dao.impl;

import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.offsetitem.init.carryover.dao.GcCarryOverOffsetDao;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class GcCarryOverOffsetDaoImpl
implements GcCarryOverOffsetDao {
    private final String SQL_QUERY_JOURNALITEM = "select %s from GC_JOURNAL  e \n %s \n";

    @Override
    public Set<String> queryJournalMrecidsByWhere(String[] columnNamesInDB, Object[] values, String orderByStr) {
        String whereSql = SqlUtils.getWhereSql((String[])columnNamesInDB, (Object[])values, (String)orderByStr, (String)"e");
        String sql = String.format("select %s from GC_JOURNAL  e \n %s \n", "e.MRECID", whereSql);
        List rs = EntNativeSqlDefaultDao.getInstance().selectFirstList(String.class, sql, new Object[0]);
        Set<String> mrecids = rs.stream().collect(Collectors.toSet());
        mrecids.remove(null);
        return mrecids;
    }

    @Override
    public Set<String> listEffectLongAdjustOffset(String[] columnNamesInDb, Object[] values, String orderByStr) {
        String whereSql = SqlUtils.getWhereSql((String[])columnNamesInDb, (Object[])values, (String)orderByStr, (String)"e");
        String sql = String.format("select %s from GC_OFFSETVCHRITEM  e  %s \n", "e.MRECID", whereSql);
        List rs = EntNativeSqlDefaultDao.getInstance().selectFirstList(String.class, sql, new Object[0]);
        Set<String> mrecids = rs.stream().collect(Collectors.toSet());
        mrecids.remove(null);
        return mrecids;
    }
}

