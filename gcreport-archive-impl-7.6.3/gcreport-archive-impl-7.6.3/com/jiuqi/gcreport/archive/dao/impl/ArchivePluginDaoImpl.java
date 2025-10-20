/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 */
package com.jiuqi.gcreport.archive.dao.impl;

import com.jiuqi.gcreport.archive.dao.ArchivePluginDao;
import com.jiuqi.gcreport.archive.entity.ArchivePluginEO;
import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import org.springframework.stereotype.Repository;

@Repository
public class ArchivePluginDaoImpl
extends GcDbSqlGenericDAO<ArchivePluginEO, String>
implements ArchivePluginDao {
    public ArchivePluginDaoImpl() {
        super(ArchivePluginEO.class);
    }

    @Override
    public String getPluginCode() {
        String sql = "select pluginCode  from GC_ARCHIVEPLUGIN d  where 1=1 \n";
        return (String)this.selectFirst(String.class, sql, new Object[0]);
    }

    @Override
    public void save(String pluginCode) {
        this.deletePluginName();
        this.save(new ArchivePluginEO(pluginCode));
    }

    private void deletePluginName() {
        String sql = "delete from GC_ARCHIVEPLUGIN  where 1=1";
        this.execute(sql);
    }
}

