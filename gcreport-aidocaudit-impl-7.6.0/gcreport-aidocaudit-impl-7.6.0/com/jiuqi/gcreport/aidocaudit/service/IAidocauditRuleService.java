/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 */
package com.jiuqi.gcreport.aidocaudit.service;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.aidocaudit.dto.AidocauditRuleParamDTO;
import com.jiuqi.gcreport.aidocaudit.dto.MQRuleResultDTO;
import com.jiuqi.gcreport.aidocaudit.dto.RuleDetailDTO;
import com.jiuqi.gcreport.aidocaudit.eo.AidocauditRuleEO;
import java.util.List;

public interface IAidocauditRuleService {
    public BusinessResponseEntity<List<AidocauditRuleEO>> list();

    public BusinessResponseEntity<Boolean> add(AidocauditRuleParamDTO var1);

    public void handerErrorMessage(MQRuleResultDTO var1);

    public void handerMessage(MQRuleResultDTO var1);

    public RuleDetailDTO queryRuleItem(String var1);

    public BusinessResponseEntity<Boolean> generate(String var1);

    public BusinessResponseEntity<Boolean> delete(String var1, Boolean var2);

    public BusinessResponseEntity<Boolean> updateRuleItem(AidocauditRuleParamDTO var1);

    public BusinessResponseEntity<List<AidocauditRuleEO>> queryReportType(Integer var1);

    public BusinessResponseEntity<AidocauditRuleEO> query(String var1);

    public BusinessResponseEntity<Boolean> updateRule(AidocauditRuleParamDTO var1);
}

