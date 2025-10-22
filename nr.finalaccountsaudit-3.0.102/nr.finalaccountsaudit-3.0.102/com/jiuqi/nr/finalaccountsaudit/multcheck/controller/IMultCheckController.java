/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.nr.finalaccountsaudit.multcheck.controller;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.finalaccountsaudit.enumcheck.common.EnumDataCheckResultInfo;
import com.jiuqi.nr.finalaccountsaudit.multcheck.common.AnalysisResultInfo;
import com.jiuqi.nr.finalaccountsaudit.multcheck.common.EntityCheckPeriodTransform;
import com.jiuqi.nr.finalaccountsaudit.multcheck.common.EntityShortInfo;
import com.jiuqi.nr.finalaccountsaudit.multcheck.common.LastCheckInfo;
import com.jiuqi.nr.finalaccountsaudit.multcheck.common.MultCheckItem;
import com.jiuqi.nr.finalaccountsaudit.multcheck.common.MultCheckResultItem;
import com.jiuqi.nr.finalaccountsaudit.multcheck.common.OneKeyCheckInfo;
import com.jiuqi.nr.finalaccountsaudit.multcheck.config.common.IMultCheckItemBase;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.util.List;

public interface IMultCheckController {
    public List<MultCheckItem> queryAllItemList(OneKeyCheckInfo var1) throws Exception;

    public boolean oneKeyCheck(OneKeyCheckInfo var1, AsyncTaskMonitor var2, JobContext var3) throws Exception;

    public List<MultCheckResultItem> getCheckItemResults(String var1, String var2);

    public LastCheckInfo lastCheckResults(String var1);

    public EnumDataCheckResultInfo getEnumCheckResults(String var1);

    public List<FormulaSchemeDefine> queryformulaSchemes(String var1);

    public EntityShortInfo getRootNode(JtableContext var1);

    public boolean paramCheck(String var1);

    public List getCheckSchemes(String var1) throws Exception;

    public List<IMultCheckItemBase> getDataAnalysis(String var1) throws Exception;

    public AnalysisResultInfo getAnalysisResults(String var1) throws Exception;

    public String periodTransform(EntityCheckPeriodTransform var1);
}

