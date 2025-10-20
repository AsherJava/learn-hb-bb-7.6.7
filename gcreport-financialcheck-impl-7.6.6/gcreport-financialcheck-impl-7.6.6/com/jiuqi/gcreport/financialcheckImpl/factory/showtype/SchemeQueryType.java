/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.financialcheckcore.check.enums.CheckShowTypeEnum
 *  com.jiuqi.gcreport.financialcheckcore.gather.GcFinancialCheckAction
 *  com.jiuqi.gcreport.financialcheckcore.gather.GcFinancialCheckShowType
 */
package com.jiuqi.gcreport.financialcheckImpl.factory.showtype;

import com.jiuqi.gcreport.financialcheckImpl.factory.action.SchemeQueryAction;
import com.jiuqi.gcreport.financialcheckcore.check.enums.CheckShowTypeEnum;
import com.jiuqi.gcreport.financialcheckcore.gather.GcFinancialCheckAction;
import com.jiuqi.gcreport.financialcheckcore.gather.GcFinancialCheckShowType;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class SchemeQueryType
implements GcFinancialCheckShowType {
    public String getCode() {
        return CheckShowTypeEnum.SCHEME.getCode();
    }

    public String getTitle() {
        return CheckShowTypeEnum.SCHEME.getTitle();
    }

    public List<GcFinancialCheckAction> actions() {
        ArrayList<GcFinancialCheckAction> gcFinancialCheckActions = new ArrayList<GcFinancialCheckAction>();
        gcFinancialCheckActions.add(new SchemeQueryAction());
        return gcFinancialCheckActions;
    }
}

