/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  org.springframework.web.bind.annotation.GetMapping
 */
package com.jiuqi.dc.base.client.acctperiod;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;

public interface AcctPeriodClient {
    public static final String API_BASE_PATH = "/api/datacenter/v1/base";

    @GetMapping(value={"/api/datacenter/v1/base/year/list"})
    public BusinessResponseEntity<List<Integer>> listYear();
}

