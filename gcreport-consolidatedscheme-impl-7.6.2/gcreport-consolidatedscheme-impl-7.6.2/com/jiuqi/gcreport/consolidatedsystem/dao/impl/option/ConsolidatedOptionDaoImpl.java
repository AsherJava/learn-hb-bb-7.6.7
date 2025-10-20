/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 */
package com.jiuqi.gcreport.consolidatedsystem.dao.impl.option;

import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.consolidatedsystem.dao.option.ConsolidatedOptionDao;
import com.jiuqi.gcreport.consolidatedsystem.entity.option.ConsolidatedOptionEO;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class ConsolidatedOptionDaoImpl
extends GcDbSqlGenericDAO<ConsolidatedOptionEO, String>
implements ConsolidatedOptionDao {
    public ConsolidatedOptionDaoImpl() {
        super(ConsolidatedOptionEO.class);
    }

    @Override
    public ConsolidatedOptionEO getOptionDataBySystemId(String systemId) {
        String columnSQL = SqlUtils.getColumnsSqlByEntity(ConsolidatedOptionEO.class, (String)"e");
        String msql = " select %s from GC_CONSOPTION  e \n  where e.SYSTEMID = ? \n ";
        String sql = String.format(msql, columnSQL);
        List eos = this.selectEntity(sql, new Object[]{systemId});
        if (eos.isEmpty()) {
            return null;
        }
        return (ConsolidatedOptionEO)((Object)eos.get(0));
    }

    @Override
    public void deleteBySystemId(String systemId) {
        String sql = "  delete     from GC_CONSOPTION   \n     where SYSTEMID = ?";
        this.execute(sql, new Object[]{systemId});
    }
}

