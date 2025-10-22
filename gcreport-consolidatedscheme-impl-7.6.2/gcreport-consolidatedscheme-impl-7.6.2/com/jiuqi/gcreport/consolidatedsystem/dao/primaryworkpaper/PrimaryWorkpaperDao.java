/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 */
package com.jiuqi.gcreport.consolidatedsystem.dao.primaryworkpaper;

import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import com.jiuqi.gcreport.consolidatedsystem.entity.primaryworkpaper.PrimaryWorkPaperTypeEO;
import java.util.List;

public interface PrimaryWorkpaperDao
extends IDbSqlGenericDAO<PrimaryWorkPaperTypeEO, String> {
    public List<PrimaryWorkPaperTypeEO> listTypesByReportSystem(String var1);

    public Integer findMaxSortOrder();

    public PrimaryWorkPaperTypeEO findPreNodeBySystemIdAndOrder(String var1, Integer var2);

    public PrimaryWorkPaperTypeEO findNextNodeBySystemIdAndOrder(String var1, Integer var2);
}

