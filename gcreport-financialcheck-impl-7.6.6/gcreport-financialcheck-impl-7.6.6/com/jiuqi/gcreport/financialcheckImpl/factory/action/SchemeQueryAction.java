/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.financialcheckapi.check.vo.FinancialCheckQueryConditionVO
 *  com.jiuqi.gcreport.financialcheckcore.gather.GcFinancialCheckAction
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 */
package com.jiuqi.gcreport.financialcheckImpl.factory.action;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.financialcheckImpl.check.service.impl.FinancialCheckServiceImpl;
import com.jiuqi.gcreport.financialcheckapi.check.vo.FinancialCheckQueryConditionVO;
import com.jiuqi.gcreport.financialcheckcore.gather.GcFinancialCheckAction;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;

public class SchemeQueryAction
implements GcFinancialCheckAction {
    public String code() {
        return "query";
    }

    public String title() {
        return "\u67e5\u8be2";
    }

    public Object execute(Object param) {
        FinancialCheckQueryConditionVO financialCheckQueryConditionVO = (FinancialCheckQueryConditionVO)param;
        FinancialCheckServiceImpl financialCheckService = (FinancialCheckServiceImpl)SpringContextUtils.getBean(FinancialCheckServiceImpl.class);
        return financialCheckService.queryUncheckedGroupByScheme(financialCheckQueryConditionVO);
    }

    public boolean isVisible(QueryParamsVO queryParamsVO) {
        return false;
    }

    public boolean isEnable(QueryParamsVO queryParamsVO) {
        return false;
    }
}

