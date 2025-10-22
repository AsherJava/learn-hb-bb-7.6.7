/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 */
package com.jiuqi.gcreport.workingpaper.dao;

import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import com.jiuqi.gcreport.workingpaper.entity.WorkingPaperQueryWayItemEO;
import com.jiuqi.gcreport.workingpaper.enums.WorkingPaperType;
import java.util.List;

public interface WorkingPaperQueryWayDao
extends IDbSqlGenericDAO<WorkingPaperQueryWayItemEO, String> {
    public List<WorkingPaperQueryWayItemEO> getWorkingPaperQueryWays(WorkingPaperType var1);
}

