/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.billextract.client.dto.BillFetchSchemeDTO
 */
package com.jiuqi.gcreport.bde.bill.setting.impl.service;

import com.jiuqi.gcreport.billextract.client.dto.BillFetchSchemeDTO;
import java.util.List;

public interface BillExtractSchemeService {
    public BillFetchSchemeDTO findById(String var1);

    public BillFetchSchemeDTO getById(String var1);

    public List<BillFetchSchemeDTO> listScheme(String var1);

    public boolean save(BillFetchSchemeDTO var1);

    public boolean update(BillFetchSchemeDTO var1);

    public boolean delete(String var1);

    public boolean publish(String var1);

    public boolean copy(String var1, String var2);

    public boolean exchangeOrdinal(String var1, String var2);
}

