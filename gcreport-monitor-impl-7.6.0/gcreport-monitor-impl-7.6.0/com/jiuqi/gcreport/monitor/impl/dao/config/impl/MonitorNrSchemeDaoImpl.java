/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 */
package com.jiuqi.gcreport.monitor.impl.dao.config.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.monitor.impl.dao.config.MonitorNrSchemeDao;
import com.jiuqi.gcreport.monitor.impl.entity.MonitorNrSchemeEO;
import java.util.Collection;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class MonitorNrSchemeDaoImpl
extends GcDbSqlGenericDAO<MonitorNrSchemeEO, String>
implements MonitorNrSchemeDao {
    public MonitorNrSchemeDaoImpl() {
        super(MonitorNrSchemeEO.class);
    }

    @Override
    public List<MonitorNrSchemeEO> getNrSchemeByMonitorId(String monitotId) {
        String allFieldsSQL = SqlUtils.getNewColumnsSqlByTableDefine((String)"GC_MONITORREPORTSCHEME", (String)"t");
        String sql = "  select " + allFieldsSQL + " from " + "GC_MONITORREPORTSCHEME" + "  t \n where t.monitorid=?\n";
        return this.selectEntity(sql, new Object[]{monitotId});
    }

    @Override
    public void deleteNrSchemeByMonitorId(String monitotId) {
        String sql = "  delete  from GC_MONITORREPORTSCHEME  \n where monitorid= ? \n";
        this.execute(sql, new Object[]{monitotId});
    }

    @Override
    public MonitorNrSchemeEO findByNrIdAndDataTime(String nrId, String dataTime) {
        String allFieldsSQL = SqlUtils.getNewColumnsSqlByTableDefine((String)"GC_MONITORREPORTSCHEME", (String)"t");
        String sql = "  select " + allFieldsSQL + " from " + "GC_MONITORREPORTSCHEME" + "  t \n where t.nrid=? and ? <=t.enddate and ? >=t.startdate \n";
        List queryBySql = this.selectEntity(sql, new Object[]{nrId, dataTime});
        if (queryBySql == null || queryBySql.size() == 0) {
            return null;
        }
        return (MonitorNrSchemeEO)((Object)this.selectEntity(sql, new Object[]{nrId, dataTime}).get(0));
    }

    @Override
    public boolean checkDateRange(String id, String nrId, String start, String end) {
        String sql = "  select t.id from GC_MONITORREPORTSCHEME  t \n where t.nrid=? and ((?<=t.enddate and ?>=t.startdate) or (?<=t.enddate and ?>=t.startdate)) \n" + (id == null ? "" : " and t.id <> ? \n");
        List queryBySql = this.selectEntity(sql, new Object[]{nrId, start, start, end, end, id});
        return queryBySql.size() != 0;
    }

    @Override
    public MonitorNrSchemeEO findByFormSchemeId(String formSchemeId) {
        String allFieldsSQL = SqlUtils.getNewColumnsSqlByTableDefine((String)"GC_MONITORREPORTSCHEME", (String)"t");
        String sql = "  select " + allFieldsSQL + " from " + "GC_MONITORREPORTSCHEME" + "  t \n where t.nrid=? \n";
        List queryBySql = this.selectEntity(sql, new Object[]{formSchemeId});
        if (CollectionUtils.isEmpty((Collection)queryBySql)) {
            return null;
        }
        return (MonitorNrSchemeEO)((Object)queryBySql.get(0));
    }
}

