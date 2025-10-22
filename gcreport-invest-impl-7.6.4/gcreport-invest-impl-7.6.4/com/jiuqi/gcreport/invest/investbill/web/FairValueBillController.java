/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.investbill.api.FairValueBillClient
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.invest.investbill.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.invest.investbill.service.FairValueBillService;
import com.jiuqi.gcreport.investbill.api.FairValueBillClient;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FairValueBillController
implements FairValueBillClient {
    @Autowired
    FairValueBillService fairValueBillService;

    public BusinessResponseEntity<Map<String, Object>> queryFvchBillCode(String investBillId, String periodStr) {
        return BusinessResponseEntity.ok(this.fairValueBillService.queryFvchBillCode(investBillId, periodStr));
    }
}

