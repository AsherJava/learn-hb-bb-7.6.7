/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gc.financial.financialstatus.vo.FinancialStatusManageConfigVo
 *  com.jiuqi.gc.financial.financialstatus.vo.FinancialStatusManageVo
 */
package com.jiuqi.gc.financial.service;

import com.jiuqi.gc.financial.financialstatus.vo.FinancialStatusManageConfigVo;
import com.jiuqi.gc.financial.financialstatus.vo.FinancialStatusManageVo;
import java.util.List;

public interface FinancialStatusManageService {
    public List<FinancialStatusManageVo> listAllPlugin();

    public List<FinancialStatusManageConfigVo> listPluginConfig();
}

