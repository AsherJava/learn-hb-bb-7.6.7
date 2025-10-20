/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.gcreport.financialcheckapi.checkquery.FinancialCheckQueryClient
 *  com.jiuqi.gcreport.financialcheckapi.checkquery.vo.FinancialCheckQueryColumnVO
 *  com.jiuqi.gcreport.financialcheckapi.checkquery.vo.FinancialCheckQueryDataVO
 *  com.jiuqi.gcreport.financialcheckapi.checkquery.vo.FinancialCheckQueryInitDataVO
 *  com.jiuqi.gcreport.financialcheckapi.checkquery.vo.FinancialCheckQueryVO
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.financialcheckImpl.checkquery.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.gcreport.financialcheckImpl.checkquery.service.FinancialCheckQueryService;
import com.jiuqi.gcreport.financialcheckapi.checkquery.FinancialCheckQueryClient;
import com.jiuqi.gcreport.financialcheckapi.checkquery.vo.FinancialCheckQueryColumnVO;
import com.jiuqi.gcreport.financialcheckapi.checkquery.vo.FinancialCheckQueryDataVO;
import com.jiuqi.gcreport.financialcheckapi.checkquery.vo.FinancialCheckQueryInitDataVO;
import com.jiuqi.gcreport.financialcheckapi.checkquery.vo.FinancialCheckQueryVO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Primary
public class FinancialCheckQueryController
implements FinancialCheckQueryClient {
    @Autowired
    private FinancialCheckQueryService financialCheckQueryService;

    public BusinessResponseEntity<FinancialCheckQueryInitDataVO> initData() {
        return BusinessResponseEntity.ok((Object)this.financialCheckQueryService.initData());
    }

    public BusinessResponseEntity<FinancialCheckQueryInitDataVO> queryDefaultNode(FinancialCheckQueryVO financialCheckQueryVO) {
        return BusinessResponseEntity.ok((Object)this.financialCheckQueryService.queryDefaultNode(financialCheckQueryVO));
    }

    public BusinessResponseEntity<List<FinancialCheckQueryColumnVO>> queryColumns(FinancialCheckQueryVO financialCheckQueryVO) {
        return BusinessResponseEntity.ok(this.financialCheckQueryService.queryColumns(financialCheckQueryVO));
    }

    public BusinessResponseEntity<PageInfo<FinancialCheckQueryDataVO>> queryData(FinancialCheckQueryVO financialCheckQueryVO) {
        return BusinessResponseEntity.ok(this.financialCheckQueryService.queryData(financialCheckQueryVO));
    }
}

