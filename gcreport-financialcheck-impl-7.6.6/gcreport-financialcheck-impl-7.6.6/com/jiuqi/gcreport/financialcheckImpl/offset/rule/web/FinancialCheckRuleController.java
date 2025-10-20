/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.financialcheckapi.offset.web.FinancialCheckRuleClient
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.financialcheckImpl.offset.rule.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.financialcheckImpl.offset.util.FinancialCheckOffsetUtils;
import com.jiuqi.gcreport.financialcheckapi.offset.web.FinancialCheckRuleClient;
import java.util.List;
import java.util.Map;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Primary
public class FinancialCheckRuleController
implements FinancialCheckRuleClient {
    public BusinessResponseEntity<List<Map<String, String>>> getOffsetGroupingField() {
        List<Map<String, String>> offsetGroups = FinancialCheckOffsetUtils.getOffsetGroupingField();
        return BusinessResponseEntity.ok(offsetGroups);
    }
}

