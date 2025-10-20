/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.bde.bill.setting.client.BillFetchCondiClient
 *  com.jiuqi.gcreport.bde.bill.setting.client.dto.BillFetchCondiDTO
 *  com.jiuqi.va.bizmeta.domain.metadata.MetaDataDTO
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.bde.bill.setting.impl.controller;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.bde.bill.setting.client.BillFetchCondiClient;
import com.jiuqi.gcreport.bde.bill.setting.client.dto.BillFetchCondiDTO;
import com.jiuqi.gcreport.bde.bill.setting.impl.service.BillFetchCondiService;
import com.jiuqi.va.bizmeta.domain.metadata.MetaDataDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BillFetchCondiController
implements BillFetchCondiClient {
    @Autowired
    private BillFetchCondiService fetchCondiService;

    public BusinessResponseEntity<Boolean> createDefaultFetchCondi(String fetchSchemeId) {
        this.fetchCondiService.initBillFetchCondi(fetchSchemeId);
        return BusinessResponseEntity.ok();
    }

    public BusinessResponseEntity<Boolean> saveFetchCondi(BillFetchCondiDTO fetchCondiDTO) {
        boolean checkResult = this.fetchCondiService.checkBillFetchCondi(fetchCondiDTO);
        if (checkResult) {
            this.fetchCondiService.deleteBillFetchCondiByFetchSchemeId(fetchCondiDTO.getFetchSchemeId());
            this.fetchCondiService.saveBillFetchCondi(fetchCondiDTO);
            return BusinessResponseEntity.ok();
        }
        return BusinessResponseEntity.error((String)"\u5355\u636e\u53d6\u6570\u6761\u4ef6\u6821\u9a8c\u5931\u8d25");
    }

    public BusinessResponseEntity<BillFetchCondiDTO> queryFetchCondiByFetchSchemeId(String fetchSchemeId) {
        BillFetchCondiDTO billFetchCondiDTO = this.fetchCondiService.queryBillFetchCondiDTOByFetchSchemeId(fetchSchemeId);
        return BusinessResponseEntity.ok((Object)billFetchCondiDTO);
    }

    public BusinessResponseEntity<Boolean> checkFetchCondi(BillFetchCondiDTO fetchCondiDTO) {
        return BusinessResponseEntity.ok((Object)this.fetchCondiService.checkBillFetchCondi(fetchCondiDTO));
    }

    public BusinessResponseEntity<MetaDataDTO> getBillDefineByBillId(String billUniqueCode) {
        return BusinessResponseEntity.ok((Object)this.fetchCondiService.getBillDefineByBillId(billUniqueCode));
    }
}

