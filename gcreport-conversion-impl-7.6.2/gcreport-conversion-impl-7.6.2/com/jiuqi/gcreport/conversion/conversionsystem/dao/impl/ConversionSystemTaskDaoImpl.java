/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 */
package com.jiuqi.gcreport.conversion.conversionsystem.dao.impl;

import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.conversion.conversionsystem.dao.ConversionSystemTaskDao;
import com.jiuqi.gcreport.conversion.conversionsystem.entity.ConversionSystemTaskEO;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class ConversionSystemTaskDaoImpl
extends GcDbSqlGenericDAO<ConversionSystemTaskEO, String>
implements ConversionSystemTaskDao {
    public ConversionSystemTaskDaoImpl() {
        super(ConversionSystemTaskEO.class);
    }

    @Override
    public ConversionSystemTaskEO queryByTaskAndScheme(String taskId, String schemeId) {
        String sql = "  select " + SqlUtils.getColumnsSqlByEntity(ConversionSystemTaskEO.class, (String)"scheme") + "  from  " + "GC_CONV_SYSTEM_TS" + "   scheme  \n  where   1=1 \n  and  scheme.taskId = ? \n  and  scheme.schemeId = ? \n";
        List dataList = this.selectEntity(sql, new Object[]{taskId, schemeId});
        if (dataList != null && dataList.size() > 0) {
            return (ConversionSystemTaskEO)((Object)dataList.get(0));
        }
        return null;
    }

    @Override
    public List<ConversionSystemTaskEO> queryBySystemId(String systemId) {
        String sql = "  select " + SqlUtils.getColumnsSqlByEntity(ConversionSystemTaskEO.class, (String)"scheme") + "  from  " + "GC_CONV_SYSTEM_TS" + "   scheme  \n  where   1=1 \n  and  scheme.systemId = ? \n";
        return this.selectEntity(sql, new Object[]{systemId});
    }

    @Override
    public void deleteBySystemId(String systemId) {
        String sql = "  delete from GC_CONV_SYSTEM_TS   \n  where    systemId = ? \n";
        this.execute(sql, new Object[]{systemId});
    }
}

