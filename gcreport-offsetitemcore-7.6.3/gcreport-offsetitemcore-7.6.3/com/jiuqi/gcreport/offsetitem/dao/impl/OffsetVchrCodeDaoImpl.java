/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 */
package com.jiuqi.gcreport.offsetitem.dao.impl;

import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.offsetitem.dao.OffsetVchrCodeDao;
import com.jiuqi.gcreport.offsetitem.entity.GcOffsetVchrFlowEO;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class OffsetVchrCodeDaoImpl
extends GcDbSqlGenericDAO<GcOffsetVchrFlowEO, String>
implements OffsetVchrCodeDao {
    public OffsetVchrCodeDaoImpl() {
        super(GcOffsetVchrFlowEO.class);
    }

    @Override
    public int updateFlow(GcOffsetVchrFlowEO vchrFlowEO) {
        String sql = " update GC_OFFSETVCHR_FLOW  t \n    set FLOWNUMBER = FLOWNUMBER + ? \n  where t.DIMENSIONS = ? \n";
        return this.execute(sql, new Object[]{vchrFlowEO.getFlowNumber(), vchrFlowEO.getDimensions()});
    }

    @Override
    public GcOffsetVchrFlowEO getFlowNumberByDimensions(String dim) {
        String columnSQL = SqlUtils.getColumnsSqlByEntity(GcOffsetVchrFlowEO.class, (String)"vchr");
        String msql = " select %s from GC_OFFSETVCHR_FLOW  vchr \n  where vchr.DIMENSIONS = ? \n ";
        String sql = String.format(msql, columnSQL);
        List eos = this.selectEntity(sql, new Object[]{dim});
        if (eos.isEmpty()) {
            return null;
        }
        return (GcOffsetVchrFlowEO)((Object)eos.get(0));
    }
}

