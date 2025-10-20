/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.gcreport.financialcheckapi.checkquery.vo.FinancialCheckQueryColumnVO
 *  com.jiuqi.gcreport.financialcheckapi.checkquery.vo.FinancialCheckQueryDataVO
 *  com.jiuqi.gcreport.financialcheckapi.checkquery.vo.FinancialCheckQueryVO
 *  com.jiuqi.gcreport.financialcheckcore.scheme.enums.CheckModeEnum
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 */
package com.jiuqi.gcreport.financialcheckImpl.checkquery.service.helper;

import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.gcreport.financialcheckImpl.checkquery.service.helper.FcQueryBilateralModeHelper;
import com.jiuqi.gcreport.financialcheckImpl.checkquery.service.helper.FcQueryOffsetVchrModeHelper;
import com.jiuqi.gcreport.financialcheckImpl.checkquery.service.helper.FcQueryUnilateralModeHelper;
import com.jiuqi.gcreport.financialcheckapi.checkquery.vo.FinancialCheckQueryColumnVO;
import com.jiuqi.gcreport.financialcheckapi.checkquery.vo.FinancialCheckQueryDataVO;
import com.jiuqi.gcreport.financialcheckapi.checkquery.vo.FinancialCheckQueryVO;
import com.jiuqi.gcreport.financialcheckcore.scheme.enums.CheckModeEnum;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class FcQueryHelper {
    public static FcQueryHelper newInstance(String checkMode) {
        if (CheckModeEnum.OFFSETVCHR.getCode().equals(checkMode)) {
            return new FcQueryOffsetVchrModeHelper();
        }
        if (CheckModeEnum.UNILATERAL.getCode().equals(checkMode)) {
            return new FcQueryUnilateralModeHelper();
        }
        return new FcQueryBilateralModeHelper();
    }

    public abstract List<FinancialCheckQueryColumnVO> queryColumns(FinancialCheckQueryVO var1);

    public abstract PageInfo<FinancialCheckQueryDataVO> queryCheckQueryData(FinancialCheckQueryVO var1);

    protected FinancialCheckQueryColumnVO setQueryColumn(String value, String title, String width) {
        FinancialCheckQueryColumnVO financialCheckQueryColumnVO = new FinancialCheckQueryColumnVO();
        financialCheckQueryColumnVO.setTitle(title);
        financialCheckQueryColumnVO.setValue(value);
        financialCheckQueryColumnVO.setWidth(width);
        return financialCheckQueryColumnVO;
    }

    protected Map<String, String> buildUnitMap(GcOrgCacheVO unit) {
        HashMap<String, String> unitMap = new HashMap<String, String>();
        unitMap.put("code", unit.getCode());
        unitMap.put("title", unit.getTitle());
        return unitMap;
    }
}

