/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.financialcheckapi.reltxquery.vo.RelTxCheckQueryLevel4DataV0
 *  com.jiuqi.gcreport.financialcheckapi.reltxquery.vo.RelTxCheckQueryParamVO
 */
package com.jiuqi.gcreport.financialcheckImpl.reltxquery.dao;

import com.jiuqi.gcreport.financialcheckImpl.reltxquery.domain.RelTxCheckQueryLevel4ParamDO;
import com.jiuqi.gcreport.financialcheckImpl.reltxquery.domain.RelTxCheckQueryTableDataDO;
import com.jiuqi.gcreport.financialcheckapi.reltxquery.vo.RelTxCheckQueryLevel4DataV0;
import com.jiuqi.gcreport.financialcheckapi.reltxquery.vo.RelTxCheckQueryParamVO;
import java.util.List;

public interface GcRelTxCheckQueryDao {
    public List<RelTxCheckQueryTableDataDO> queryData(RelTxCheckQueryParamVO var1, boolean var2);

    public RelTxCheckQueryLevel4DataV0 queryLevel4Data(RelTxCheckQueryLevel4ParamDO var1);
}

