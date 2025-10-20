/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 */
package com.jiuqi.gcreport.samecontrol.dao;

import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import com.jiuqi.gcreport.samecontrol.entity.SameCtrlExtractLogEO;
import java.util.List;

public interface SameCtrlExtractLogDao
extends IDbSqlGenericDAO<SameCtrlExtractLogEO, String> {
    public int updateLogToUnLatestStateReportInfo(SameCtrlExtractLogEO var1);

    public int updateLogToUnLatestStateOffsetInfo(SameCtrlExtractLogEO var1);

    public int updateSamrCtrlLogById(SameCtrlExtractLogEO var1);

    public List<SameCtrlExtractLogEO> querySameCtrlExtractLog(SameCtrlExtractLogEO var1);
}

