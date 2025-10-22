/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 */
package com.jiuqi.gcreport.consolidatedsystem.dao.impl;

import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.consolidatedsystem.dao.ConsolidatedSystemDao;
import com.jiuqi.gcreport.consolidatedsystem.entity.ConsolidatedSystemEO;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class ConsolidatedSystemDaoImpl
extends GcDbSqlGenericDAO<ConsolidatedSystemEO, String>
implements ConsolidatedSystemDao {
    public ConsolidatedSystemDaoImpl() {
        super(ConsolidatedSystemEO.class);
    }

    @Override
    public List<ConsolidatedSystemEO> findAllSystemsWithOrder() {
        String columnSQL = SqlUtils.getColumnsSqlByEntity(ConsolidatedSystemEO.class, (String)"scheme");
        String sql = "  select " + columnSQL + "    from " + "GC_CONSSYSTEM" + "  scheme \n   order by scheme.DATASORT \n";
        return this.selectEntity(sql, new Object[0]);
    }

    @Override
    public ConsolidatedSystemEO getConsolidatedSystemByName(String systemName) {
        String columnSQL = SqlUtils.getColumnsSqlByEntity(ConsolidatedSystemEO.class, (String)"scheme");
        String sql = "  select " + columnSQL + "    from " + "GC_CONSSYSTEM" + "  scheme \n where scheme.systemName = ? \n";
        List eos = this.selectEntity(sql, new Object[]{systemName});
        if (eos.size() > 0) {
            return (ConsolidatedSystemEO)((Object)eos.get(0));
        }
        return null;
    }

    @Override
    public List<ConsolidatedSystemEO> listSystemsByIds(List<String> systemIds) {
        String columnSQL = SqlUtils.getColumnsSqlByEntity(ConsolidatedSystemEO.class, (String)"scheme");
        String sql = "  select " + columnSQL + "    from " + "GC_CONSSYSTEM" + "  scheme \n where " + SqlUtils.getConditionOfIdsUseOr(systemIds, (String)"scheme.id");
        List eos = this.selectEntity(sql, new Object[0]);
        return eos == null ? new ArrayList() : eos;
    }
}

