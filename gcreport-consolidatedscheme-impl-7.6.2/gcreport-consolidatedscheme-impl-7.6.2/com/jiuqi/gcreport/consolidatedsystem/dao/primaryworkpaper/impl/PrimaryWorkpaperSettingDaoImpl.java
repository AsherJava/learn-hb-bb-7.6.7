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
import com.jiuqi.gcreport.consolidatedsystem.dao.primaryworkpaper.PrimaryWorkpaperSettingDao;
import com.jiuqi.gcreport.consolidatedsystem.entity.primaryworkpaper.PrimaryWorkPaperSettingEO;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class PrimaryWorkpaperSettingDaoImpl
extends GcDbSqlGenericDAO<PrimaryWorkPaperSettingEO, String>
implements PrimaryWorkpaperSettingDao {
    public PrimaryWorkpaperSettingDaoImpl() {
        super(PrimaryWorkPaperSettingEO.class);
    }

    private String getAllFieldsSQL() {
        return SqlUtils.getColumnsSqlByEntity(PrimaryWorkPaperSettingEO.class, (String)"t");
    }

    @Override
    public List<PrimaryWorkPaperSettingEO> querySetRecordsByTypeId(String typeId) {
        String sql = "  select " + this.getAllFieldsSQL() + " from " + "GC_PRIMARY_WORKPAPER_SETTING" + "  t \n  where t.primaryTypeId = ? \n  order by t.ordinal  \n";
        return this.selectEntity(sql, new Object[]{typeId});
    }

    @Override
    public List<PrimaryWorkPaperSettingEO> listSetRecordsBySystemId(String systemId) {
        String sql = "  select " + this.getAllFieldsSQL() + " from " + "GC_PRIMARY_WORKPAPER_SETTING" + "  t \n  where t.reportSystem = ? \n";
        return this.selectEntity(sql, new Object[]{systemId});
    }

    @Override
    public void batchDeleteByIds(List<String> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }
        String inSql = SqlUtils.getConditionOfIdsUseOr(ids, (String)"ID");
        String sql = "   delete from GC_PRIMARY_WORKPAPER_SETTING   \n   where " + inSql + " \n";
        this.execute(sql);
    }
}

