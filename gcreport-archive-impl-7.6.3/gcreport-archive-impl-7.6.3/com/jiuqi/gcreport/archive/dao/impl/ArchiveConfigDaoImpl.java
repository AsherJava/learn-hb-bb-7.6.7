/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 */
package com.jiuqi.gcreport.archive.dao.impl;

import com.jiuqi.gcreport.archive.dao.ArchiveConfigDao;
import com.jiuqi.gcreport.archive.entity.ArchiveConfigEO;
import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class ArchiveConfigDaoImpl
extends GcDbSqlGenericDAO<ArchiveConfigEO, String>
implements ArchiveConfigDao {
    public ArchiveConfigDaoImpl() {
        super(ArchiveConfigEO.class);
    }

    @Override
    public List<ArchiveConfigEO> queryBySchemeId(String schemeId) {
        String sql = "select %s  from GC_ARCHIVECONFIG d  where d.schemeId=? \n";
        sql = String.format(sql, SqlUtils.getColumnsSqlByTableDefine((String)"GC_ARCHIVECONFIG", (String)"d"));
        return this.selectEntity(sql, new Object[]{schemeId});
    }

    @Override
    public void save(String taskId, List<ArchiveConfigEO> checkConfigEOs) {
        this.deleteByTaskIdAndOrgType(taskId, checkConfigEOs.get(0).getOrgType());
        this.saveAll(checkConfigEOs);
    }

    @Override
    public void deleteByTaskId(String taskId) {
        String sql = "delete from GC_ARCHIVECONFIG  where taskId=?";
        this.execute(sql, new Object[]{taskId});
    }

    @Override
    public void deleteByTaskIdAndOrgType(String taskId, String orgType) {
        String sql = "delete from GC_ARCHIVECONFIG  where taskId=? and orgType=?";
        this.execute(sql, new Object[]{taskId, orgType});
    }

    @Override
    public List<ArchiveConfigEO> queryBySchemeIdAndOrgType(String schemeId, String orgType) {
        String sql = "select %s  from GC_ARCHIVECONFIG d  where d.schemeId=?  and d.orgType=?";
        sql = String.format(sql, SqlUtils.getColumnsSqlByTableDefine((String)"GC_ARCHIVECONFIG", (String)"d"));
        return this.selectEntity(sql, new Object[]{schemeId, orgType});
    }
}

