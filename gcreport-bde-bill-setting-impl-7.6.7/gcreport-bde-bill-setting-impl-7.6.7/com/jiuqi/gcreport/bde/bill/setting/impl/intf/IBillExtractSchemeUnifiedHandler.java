/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.billextract.client.dto.BillFetchSchemeDTO
 */
package com.jiuqi.gcreport.bde.bill.setting.impl.intf;

import com.jiuqi.gcreport.billextract.client.dto.BillFetchSchemeDTO;

public interface IBillExtractSchemeUnifiedHandler {
    public int delete(BillFetchSchemeDTO var1);

    default public int publish(BillFetchSchemeDTO schemeDto) {
        return 0;
    }

    default public void copy(BillFetchSchemeDTO srcScheme, String targetId) {
    }

    public int syncCache(BillFetchSchemeDTO var1);
}

