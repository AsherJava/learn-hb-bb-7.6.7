/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 */
package com.jiuqi.gcreport.consolidatedsystem.dao.primaryworkpaper.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.consolidatedsystem.dao.primaryworkpaper.PrimaryWorkpaperDao;
import com.jiuqi.gcreport.consolidatedsystem.entity.primaryworkpaper.PrimaryWorkPaperTypeEO;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import java.util.Collection;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class PrimaryWorkpaperDaoImpl
extends GcDbSqlGenericDAO<PrimaryWorkPaperTypeEO, String>
implements PrimaryWorkpaperDao {
    public PrimaryWorkpaperDaoImpl() {
        super(PrimaryWorkPaperTypeEO.class);
    }

    private String getAllFieldsSQL() {
        return SqlUtils.getColumnsSqlByEntity(PrimaryWorkPaperTypeEO.class, (String)"t");
    }

    @Override
    public List<PrimaryWorkPaperTypeEO> listTypesByReportSystem(String reportSystemId) {
        String sql = "  select " + this.getAllFieldsSQL() + " from " + "GC_PRIMARY_WORKPAPER_TYPE" + "  t \n  where t.reportsystem = ? \n  order by t.sortorder  \n";
        return this.selectEntity(sql, new Object[]{reportSystemId});
    }

    @Override
    public Integer findMaxSortOrder() {
        String sql = "  select max(t.sortorder) AS SORTORDER from GC_PRIMARY_WORKPAPER_TYPE  t \n";
        List re = this.selectFirstList(Integer.class, sql, new Object[0]);
        if (!re.isEmpty() && re.get(0) != null) {
            return (Integer)re.get(0);
        }
        return 0;
    }

    @Override
    public PrimaryWorkPaperTypeEO findPreNodeBySystemIdAndOrder(String reportsystemId, Integer sortOrder) {
        String sql = "  select " + this.getAllFieldsSQL() + "    from " + "GC_PRIMARY_WORKPAPER_TYPE" + "  t \n   where t.reportsystem = ? \n     and t.sortorder = (select max(s.sortorder) from " + "GC_PRIMARY_WORKPAPER_TYPE" + "  s \n                              where s.reportsystem = ?  \n                                and s.sortorder < ?) \n";
        List eos = this.selectEntity(sql, new Object[]{reportsystemId, reportsystemId, sortOrder});
        if (!CollectionUtils.isEmpty((Collection)eos)) {
            return (PrimaryWorkPaperTypeEO)((Object)eos.get(0));
        }
        return null;
    }

    @Override
    public PrimaryWorkPaperTypeEO findNextNodeBySystemIdAndOrder(String reportsystemId, Integer sortOrder) {
        String sql = "  select " + this.getAllFieldsSQL() + "    from " + "GC_PRIMARY_WORKPAPER_TYPE" + "  t \n   where t.reportsystem = ? \n     and t.sortorder = (select min(s.sortorder) from " + "GC_PRIMARY_WORKPAPER_TYPE" + "  s \n                              where s.reportsystem = ?  \n                                and s.sortorder > ?) \n";
        List eos = this.selectEntity(sql, new Object[]{reportsystemId, reportsystemId, sortOrder});
        if (!CollectionUtils.isEmpty((Collection)eos)) {
            return (PrimaryWorkPaperTypeEO)((Object)eos.get(0));
        }
        return null;
    }
}

