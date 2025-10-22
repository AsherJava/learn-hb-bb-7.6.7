/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.bde.bill.setting.client.dto.BillFetchCondiDTO
 *  com.jiuqi.va.bizmeta.domain.metadata.MetaDataDTO
 */
package com.jiuqi.gcreport.bde.bill.setting.impl.service;

import com.jiuqi.gcreport.bde.bill.setting.client.dto.BillFetchCondiDTO;
import com.jiuqi.va.bizmeta.domain.metadata.MetaDataDTO;

public interface BillFetchCondiService {
    public void initBillFetchCondi(String var1);

    public void saveBillFetchCondi(BillFetchCondiDTO var1);

    public BillFetchCondiDTO queryBillFetchCondiDTOByFetchSchemeId(String var1);

    public boolean checkBillFetchCondi(BillFetchCondiDTO var1);

    public void copyBillFetchCondiDTOByFetchSchemeId(String var1, String var2);

    public void deleteBillFetchCondiByFetchSchemeId(String var1);

    public MetaDataDTO getBillDefineByBillId(String var1);
}

