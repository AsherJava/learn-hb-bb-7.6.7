/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.relation.api.InvestRelationClient
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.invest.relation.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.invest.relation.service.InvestRelationService;
import com.jiuqi.gcreport.relation.api.InvestRelationClient;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InvestRelationController
implements InvestRelationClient {
    @Autowired
    private InvestRelationService relationService;

    public BusinessResponseEntity<Object> getInvestRelationList(Map<String, Object> params) {
        return BusinessResponseEntity.ok(this.relationService.getInvestOfTree(params));
    }

    public BusinessResponseEntity<Object> getInvestRelation(Map<String, Object> params) {
        return BusinessResponseEntity.ok(this.relationService.getInvest(params));
    }
}

