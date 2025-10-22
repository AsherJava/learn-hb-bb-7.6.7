/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.financialcheckcore.check.dto.BalanceCondition
 *  com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO
 *  org.springframework.cloud.context.config.annotation.RefreshScope
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.PostMapping
 */
package com.jiuqi.gcreport.financialcheckImpl.offset.relationtomerge.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.financialcheckcore.check.dto.BalanceCondition;
import com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO;
import java.util.List;
import java.util.Set;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(contextId="com.jiuqi.gcreport.financialcheckImpl.offset.relationtomerge.web.GcRelatedItemQueryClient", name="${custom.service-name.reltxn:reltxn-service}", url="${custom.service-url.reltxn:}", primary=false)
@RefreshScope
public interface GcRelatedItemQueryClient {
    public static final String GC_API_BASE_PATH = "/api/gcreport/v1/itemQuery";

    @PostMapping(value={"/api/gcreport/v1/itemQuery/queryByOffsetCondition"})
    public BusinessResponseEntity<List<GcRelatedItemEO>> queryByOffsetCondition(BalanceCondition var1);

    @PostMapping(value={"/api/gcreport/v1/itemQuery/queryByIds"})
    public BusinessResponseEntity<List<GcRelatedItemEO>> queryByIds(Set<String> var1);
}

