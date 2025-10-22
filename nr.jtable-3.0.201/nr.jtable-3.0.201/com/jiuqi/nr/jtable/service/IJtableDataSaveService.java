/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datacrud.impl.out.CrudOperateException
 */
package com.jiuqi.nr.jtable.service;

import com.jiuqi.nr.datacrud.impl.out.CrudOperateException;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.input.RegionDataCommitSet;
import com.jiuqi.nr.jtable.params.input.ReportDataCommitSet;
import com.jiuqi.nr.jtable.params.input.ReportDataQueryInfo;
import com.jiuqi.nr.jtable.params.output.ReturnInfo;
import com.jiuqi.nr.jtable.params.output.SaveResult;

public interface IJtableDataSaveService {
    public SaveResult saveReportFormDatas(ReportDataCommitSet var1);

    public SaveResult saveFMDMDatas(ReportDataCommitSet var1);

    public SaveResult saveRegionDatas(RegionDataCommitSet var1);

    public ReturnInfo clearReportFormDatas(ReportDataQueryInfo var1) throws CrudOperateException;

    public SaveResult deleteFmdm(JtableContext var1);
}

