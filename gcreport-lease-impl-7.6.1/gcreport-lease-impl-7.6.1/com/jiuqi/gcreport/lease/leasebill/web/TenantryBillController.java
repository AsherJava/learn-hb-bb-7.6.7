/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.gcreport.leasebill.api.TenantryBillClient
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.lease.leasebill.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.gcreport.lease.leasebill.service.TenantryBillService;
import com.jiuqi.gcreport.leasebill.api.TenantryBillClient;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TenantryBillController
implements TenantryBillClient {
    @Autowired
    private TenantryBillService tenantryBillService;

    public BusinessResponseEntity<PageInfo<Map<String, Object>>> listTenantryBills(Map<String, Object> params) {
        return BusinessResponseEntity.ok(this.tenantryBillService.listTenantryBills(params));
    }

    public BusinessResponseEntity<String> batchDelete(List<String> ids) {
        this.tenantryBillService.batchDelete(ids);
        return BusinessResponseEntity.ok();
    }
}

