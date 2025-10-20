/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.financialcheckapi.reltxquery.vo.RelTxCheckQueryDataVO
 *  com.jiuqi.gcreport.financialcheckapi.reltxquery.vo.RelTxCheckQueryLevel4DataV0
 *  com.jiuqi.gcreport.financialcheckapi.reltxquery.vo.RelTxCheckQueryLevel4Param
 *  com.jiuqi.gcreport.financialcheckapi.reltxquery.vo.RelTxCheckQueryParamVO
 */
package com.jiuqi.gcreport.financialcheckImpl.reltxquery.service;

import com.jiuqi.gcreport.financialcheckapi.reltxquery.vo.RelTxCheckQueryDataVO;
import com.jiuqi.gcreport.financialcheckapi.reltxquery.vo.RelTxCheckQueryLevel4DataV0;
import com.jiuqi.gcreport.financialcheckapi.reltxquery.vo.RelTxCheckQueryLevel4Param;
import com.jiuqi.gcreport.financialcheckapi.reltxquery.vo.RelTxCheckQueryParamVO;

public interface RelTxCheckQueryService {
    public RelTxCheckQueryDataVO queryRelTxCheckData(RelTxCheckQueryParamVO var1);

    public RelTxCheckQueryLevel4DataV0 queryLevel4Data(RelTxCheckQueryLevel4Param var1);
}

