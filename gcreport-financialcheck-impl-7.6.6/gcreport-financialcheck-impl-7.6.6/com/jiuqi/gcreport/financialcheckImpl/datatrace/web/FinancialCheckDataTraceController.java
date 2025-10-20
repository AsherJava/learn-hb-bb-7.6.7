/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.financialcheckapi.datatrace.web.FinancialCheckDataTraceClient
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.financialcheckImpl.datatrace.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.financialcheckImpl.datatrace.service.FinancialCheckDataTraceService;
import com.jiuqi.gcreport.financialcheckapi.datatrace.web.FinancialCheckDataTraceClient;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Primary
public class FinancialCheckDataTraceController
implements FinancialCheckDataTraceClient {
    @Autowired
    FinancialCheckDataTraceService dataTraceService;

    public Object listOffsetAndSourceItem(@RequestBody Map<String, Object> offsetIInfo) {
        return BusinessResponseEntity.ok((Object)this.dataTraceService.listOffsetAndSourceItem(offsetIInfo));
    }
}

