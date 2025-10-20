/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.dc.base.client.acctperiod.AcctPeriodClient
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.dc.base.impl.acctperiod.controller;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.dc.base.client.acctperiod.AcctPeriodClient;
import com.jiuqi.dc.base.impl.acctperiod.service.AcctPeriodService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AcctPeriodController
implements AcctPeriodClient {
    @Autowired
    private AcctPeriodService service;

    public BusinessResponseEntity<List<Integer>> listYear() {
        return BusinessResponseEntity.ok(this.service.listYear());
    }
}

