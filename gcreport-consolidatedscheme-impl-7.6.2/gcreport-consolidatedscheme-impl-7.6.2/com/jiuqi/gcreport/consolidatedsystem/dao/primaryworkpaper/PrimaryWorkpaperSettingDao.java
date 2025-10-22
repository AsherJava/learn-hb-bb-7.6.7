/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 */
package com.jiuqi.gcreport.consolidatedsystem.dao.primaryworkpaper;

import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import com.jiuqi.gcreport.consolidatedsystem.entity.primaryworkpaper.PrimaryWorkPaperSettingEO;
import java.util.List;

public interface PrimaryWorkpaperSettingDao
extends IDbSqlGenericDAO<PrimaryWorkPaperSettingEO, String> {
    public List<PrimaryWorkPaperSettingEO> querySetRecordsByTypeId(String var1);

    public void batchDeleteByIds(List<String> var1);

    public List<PrimaryWorkPaperSettingEO> listSetRecordsBySystemId(String var1);
}

