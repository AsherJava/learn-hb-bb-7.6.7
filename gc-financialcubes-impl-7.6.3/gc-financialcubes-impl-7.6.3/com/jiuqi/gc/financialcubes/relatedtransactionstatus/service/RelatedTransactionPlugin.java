/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gc.financial.financialstatus.vo.FinancialStatusConfigVo
 *  com.jiuqi.gc.financial.status.intf.IFinancialStatusModulePlugin
 */
package com.jiuqi.gc.financialcubes.relatedtransactionstatus.service;

import com.jiuqi.gc.financial.financialstatus.vo.FinancialStatusConfigVo;
import com.jiuqi.gc.financial.status.intf.IFinancialStatusModulePlugin;
import org.springframework.stereotype.Component;

@Component
public class RelatedTransactionPlugin
implements IFinancialStatusModulePlugin {
    public static final String MODULE_CODE = "RELATED_TRANSACTION";
    public static final String APP_NAME = "financial-status-plugin";
    public static final String PROD_LINE = "@gc";

    public String getModuleCode() {
        return MODULE_CODE;
    }

    public String getModuleName() {
        return "\u5173\u8054\u4ea4\u6613";
    }

    public String getAppName() {
        return APP_NAME;
    }

    public String getProdLine() {
        return PROD_LINE;
    }

    public FinancialStatusConfigVo getPluginDefaultConfig() {
        return new FinancialStatusConfigVo(false, false);
    }

    public int order() {
        return 2;
    }
}

