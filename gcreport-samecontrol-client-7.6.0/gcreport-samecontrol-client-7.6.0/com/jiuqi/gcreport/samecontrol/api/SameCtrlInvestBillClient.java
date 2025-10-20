/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  org.springframework.cloud.openfeign.FeignClient
 */
package com.jiuqi.gcreport.samecontrol.api;

import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(contextId="com.jiuqi.gcreport.samecontrol.api.SameCtrlInvestBillClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
public interface SameCtrlInvestBillClient {
    public Map<String, Object> getByUnitAndYear(String var1, String var2, int var3);

    public List<DefaultTableEntity> getMastByInvestAndInvestedUnit(Set<String> var1, Set<String> var2, int var3, int var4);
}

