/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.gcreport.lease.api.LessorBillClient
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.lease.leasebill.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.gcreport.lease.api.LessorBillClient;
import com.jiuqi.gcreport.lease.leasebill.service.LessorBillService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LessorBillController
implements LessorBillClient {
    @Autowired
    private LessorBillService lessorBillService;

    public BusinessResponseEntity<PageInfo<Map<String, Object>>> listLessorBills(Map<String, Object> params) {
        return BusinessResponseEntity.ok(this.lessorBillService.listLessorBills(params));
    }

    public BusinessResponseEntity<String> batchDelete(List<String> ids) {
        this.lessorBillService.batchDelete(ids);
        return BusinessResponseEntity.ok();
    }
}

