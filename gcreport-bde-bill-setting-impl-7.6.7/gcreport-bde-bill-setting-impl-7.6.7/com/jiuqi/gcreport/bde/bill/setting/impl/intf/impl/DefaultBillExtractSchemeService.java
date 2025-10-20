/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.billextract.client.dto.BillFetchSchemeDTO
 *  com.jiuqi.gcreport.billextract.client.intf.IBillExtractSchemeService
 */
package com.jiuqi.gcreport.bde.bill.setting.impl.intf.impl;

import com.jiuqi.gcreport.bde.bill.setting.impl.service.BillExtractSchemeService;
import com.jiuqi.gcreport.billextract.client.dto.BillFetchSchemeDTO;
import com.jiuqi.gcreport.billextract.client.intf.IBillExtractSchemeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultBillExtractSchemeService
implements IBillExtractSchemeService {
    @Autowired
    private BillExtractSchemeService billExtractSchemeService;

    public List<BillFetchSchemeDTO> listScheme(String billType) {
        return this.billExtractSchemeService.listScheme(billType);
    }
}

