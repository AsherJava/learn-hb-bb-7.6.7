/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.integritycheck.service;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.integritycheck.common.AddTagParam;
import com.jiuqi.nr.integritycheck.common.CheckErrDesContext;
import com.jiuqi.nr.integritycheck.common.ErrorDesInfo;
import com.jiuqi.nr.integritycheck.common.ErrorFormUnitInfo;
import com.jiuqi.nr.integritycheck.common.ExpErrDesInfo;
import com.jiuqi.nr.integritycheck.common.ExpErrDesParam;
import com.jiuqi.nr.integritycheck.common.IntegrityCheckParam;
import com.jiuqi.nr.integritycheck.common.IntegrityCheckResInfo;
import com.jiuqi.nr.integritycheck.common.PageTableICRInfo;
import com.jiuqi.nr.integritycheck.common.QueryICRParam;
import com.jiuqi.nr.integritycheck.common.ResultInfo;
import java.io.OutputStream;
import java.util.List;

public interface IIntegrityCheckService {
    public IntegrityCheckResInfo integrityCheck(IntegrityCheckParam var1, AsyncTaskMonitor var2) throws Exception;

    public ResultInfo queryICResult(QueryICRParam var1) throws Exception;

    public PageTableICRInfo pageQueryCheckResult(QueryICRParam var1);

    public List<ErrorFormUnitInfo> queryErrorFormUnit(QueryICRParam var1) throws Exception;

    public ErrorDesInfo editErrorDes(String var1, DimensionCombination var2, String var3, String var4);

    public void batchEditErrorDes(String var1, DimensionCollection var2, List<String> var3, String var4, String var5);

    public String checkErrorDes(CheckErrDesContext var1);

    public void deleteErrorDes(String var1, DimensionCombination var2, String var3);

    public ErrorDesInfo queryErrorDes(String var1, DimensionCombination var2, String var3);

    public List<ExpErrDesInfo> queryErrorDes(ExpErrDesParam var1);

    public void addTags(AddTagParam var1);

    public void exportCheckResult(QueryICRParam var1, OutputStream var2) throws Exception;

    public void exportErrorFormUnit(QueryICRParam var1, OutputStream var2) throws Exception;
}

