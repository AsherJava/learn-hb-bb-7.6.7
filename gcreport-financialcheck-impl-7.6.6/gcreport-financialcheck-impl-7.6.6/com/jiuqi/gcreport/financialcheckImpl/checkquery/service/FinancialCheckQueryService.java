/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.gcreport.financialcheckapi.checkquery.vo.FinancialCheckQueryColumnVO
 *  com.jiuqi.gcreport.financialcheckapi.checkquery.vo.FinancialCheckQueryDataVO
 *  com.jiuqi.gcreport.financialcheckapi.checkquery.vo.FinancialCheckQueryInitDataVO
 *  com.jiuqi.gcreport.financialcheckapi.checkquery.vo.FinancialCheckQueryVO
 */
package com.jiuqi.gcreport.financialcheckImpl.checkquery.service;

import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.gcreport.financialcheckapi.checkquery.vo.FinancialCheckQueryColumnVO;
import com.jiuqi.gcreport.financialcheckapi.checkquery.vo.FinancialCheckQueryDataVO;
import com.jiuqi.gcreport.financialcheckapi.checkquery.vo.FinancialCheckQueryInitDataVO;
import com.jiuqi.gcreport.financialcheckapi.checkquery.vo.FinancialCheckQueryVO;
import java.util.List;

public interface FinancialCheckQueryService {
    public FinancialCheckQueryInitDataVO initData();

    public FinancialCheckQueryInitDataVO queryDefaultNode(FinancialCheckQueryVO var1);

    public List<FinancialCheckQueryColumnVO> queryColumns(FinancialCheckQueryVO var1);

    public PageInfo<FinancialCheckQueryDataVO> queryData(FinancialCheckQueryVO var1);
}

