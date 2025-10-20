/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.efdcdatacheck.impl.dao.impl;

import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.efdcdatacheck.impl.dao.DataCheckConfigDAO;
import com.jiuqi.gcreport.efdcdatacheck.impl.entity.DataCheckConfigEO;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class DataCheckConfigDAOImp
extends GcDbSqlGenericDAO<DataCheckConfigEO, String>
implements DataCheckConfigDAO {
    public DataCheckConfigDAOImp() {
        super(DataCheckConfigEO.class);
    }

    @Override
    public void deleteByTaskId(String taskId) {
        String sql = "  delete from GC_DATACHECKCONFIG \n   where taskId=? \n";
        this.execute(sql, new Object[]{taskId});
    }

    @Override
    public List<DataCheckConfigEO> queryBySchemeId(String schemeId) {
        String sql = "  select %s \n  from GC_DATACHECKCONFIG  d \n   where d.schemeId=? \n";
        sql = String.format(sql, SqlUtils.getColumnsSqlByTableDefine((String)"GC_DATACHECKCONFIG", (String)"d"));
        return this.selectEntity(sql, new Object[]{schemeId});
    }

    @Override
    public List<DataCheckConfigEO> queryDataCheckConfig(String taskId, String schemeId) {
        String sql = "  select %s \n  from GC_DATACHECKCONFIG  d \n  where d.taskId=? \n  and d.schemeId=? \n";
        sql = String.format(sql, SqlUtils.getColumnsSqlByTableDefine((String)"GC_DATACHECKCONFIG", (String)"d"));
        return this.selectEntity(sql, new Object[]{taskId, schemeId});
    }

    @Override
    @Transactional(rollbackFor={Exception.class}, propagation=Propagation.REQUIRES_NEW)
    public void save(String taskId, List<DataCheckConfigEO> checkConfigEOs) {
        this.deleteByTaskId(taskId);
        this.saveAll(checkConfigEOs);
    }
}

