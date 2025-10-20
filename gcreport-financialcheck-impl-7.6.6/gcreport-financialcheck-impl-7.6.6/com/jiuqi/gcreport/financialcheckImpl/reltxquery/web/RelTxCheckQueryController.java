/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.financialcheckapi.reltxquery.RelTxCheckQueryClient
 *  com.jiuqi.gcreport.financialcheckapi.reltxquery.vo.RelTxCheckQueryDataVO
 *  com.jiuqi.gcreport.financialcheckapi.reltxquery.vo.RelTxCheckQueryLevel4DataV0
 *  com.jiuqi.gcreport.financialcheckapi.reltxquery.vo.RelTxCheckQueryLevel4Param
 *  com.jiuqi.gcreport.financialcheckapi.reltxquery.vo.RelTxCheckQueryParamVO
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.financialcheckImpl.reltxquery.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.financialcheckImpl.reltxquery.service.RelTxCheckQueryService;
import com.jiuqi.gcreport.financialcheckapi.reltxquery.RelTxCheckQueryClient;
import com.jiuqi.gcreport.financialcheckapi.reltxquery.vo.RelTxCheckQueryDataVO;
import com.jiuqi.gcreport.financialcheckapi.reltxquery.vo.RelTxCheckQueryLevel4DataV0;
import com.jiuqi.gcreport.financialcheckapi.reltxquery.vo.RelTxCheckQueryLevel4Param;
import com.jiuqi.gcreport.financialcheckapi.reltxquery.vo.RelTxCheckQueryParamVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Primary
public class RelTxCheckQueryController
implements RelTxCheckQueryClient {
    @Autowired
    private RelTxCheckQueryService relTxCheckQueryService;

    public BusinessResponseEntity<RelTxCheckQueryDataVO> queryRelTxCheckData(@RequestBody RelTxCheckQueryParamVO param) {
        RelTxCheckQueryDataVO relTxCheckQueryDataVO = this.relTxCheckQueryService.queryRelTxCheckData(param);
        return BusinessResponseEntity.ok((Object)relTxCheckQueryDataVO);
    }

    public BusinessResponseEntity<RelTxCheckQueryLevel4DataV0> queryLevel4Data(@RequestBody RelTxCheckQueryLevel4Param param) {
        return BusinessResponseEntity.ok((Object)this.relTxCheckQueryService.queryLevel4Data(param));
    }
}

