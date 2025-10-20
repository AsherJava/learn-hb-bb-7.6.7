/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.financialcheckcore.gather.impl;

import com.jiuqi.gcreport.financialcheckcore.gather.GcFinancialCheckAction;
import com.jiuqi.gcreport.financialcheckcore.gather.GcFinancialCheckExecutor;
import com.jiuqi.gcreport.financialcheckcore.gather.GcFinancialCheckShowType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GcFinancialCheckExecutorImpl
implements GcFinancialCheckExecutor {
    @Autowired
    private List<GcFinancialCheckShowType> gcFinancialCheckShowTypes;

    @Override
    public List<Map<String, String>> listShowTypeForPage() {
        ArrayList<Map<String, String>> showTypeForPage = new ArrayList<Map<String, String>>();
        for (GcFinancialCheckShowType gcFinancialCheckShowType : this.gcFinancialCheckShowTypes) {
            HashMap<String, String> showTypeMap = new HashMap<String, String>();
            showTypeMap.put("value", gcFinancialCheckShowType.getCode());
            showTypeMap.put("label", gcFinancialCheckShowType.getTitle());
            showTypeForPage.add(showTypeMap);
        }
        return showTypeForPage;
    }

    @Override
    public Object execute(Object param, String showType, String action) {
        for (GcFinancialCheckShowType gcFinancialCheckShowType : this.gcFinancialCheckShowTypes) {
            if (!showType.equals(gcFinancialCheckShowType.getCode())) continue;
            List<GcFinancialCheckAction> actions = gcFinancialCheckShowType.actions();
            for (GcFinancialCheckAction gcFinancialCheckAction : actions) {
                if (!action.equals(gcFinancialCheckAction.code())) continue;
                return gcFinancialCheckAction.execute(param);
            }
        }
        return null;
    }
}

