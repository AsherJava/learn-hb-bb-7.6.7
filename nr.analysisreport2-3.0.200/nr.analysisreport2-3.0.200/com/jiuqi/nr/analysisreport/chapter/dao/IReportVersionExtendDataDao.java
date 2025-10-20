/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.exception.DBParaException
 */
package com.jiuqi.nr.analysisreport.chapter.dao;

import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.nr.analysisreport.chapter.bean.ReportVersionExtendData;

public interface IReportVersionExtendDataDao {
    public int insert(ReportVersionExtendData var1) throws DBParaException;

    public void delete(String var1) throws DBParaException;

    public ReportVersionExtendData get(String var1);

    public void update(ReportVersionExtendData var1) throws DBParaException;
}

