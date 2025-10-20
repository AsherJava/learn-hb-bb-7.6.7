/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.gcreport.asset.api.CommonAssetBillClient
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.asset.assetbill.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.gcreport.asset.api.CommonAssetBillClient;
import com.jiuqi.gcreport.asset.assetbill.service.CommonAssetBillService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommonAssetBillController
implements CommonAssetBillClient {
    @Autowired
    private CommonAssetBillService assetBillService;

    public BusinessResponseEntity<PageInfo<Map<String, Object>>> listAssetBills(Map<String, Object> params) {
        return BusinessResponseEntity.ok(this.assetBillService.listAssetBills(params));
    }

    public BusinessResponseEntity<String> batchDelete(List<String> ids) {
        this.assetBillService.batchDelete(ids);
        return BusinessResponseEntity.ok();
    }

    public BusinessResponseEntity<String> batchUnDisposal(List<String> ids) {
        this.assetBillService.batchUnDisposal(ids);
        return BusinessResponseEntity.ok();
    }
}

