/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.user.UserLoginDTO
 */
package com.jiuqi.va.bill.service;

import com.jiuqi.va.bill.domain.BillSublistImportVO;
import com.jiuqi.va.bill.intf.BillModel;
import com.jiuqi.va.domain.user.UserLoginDTO;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public interface BillSublistImportService {
    public BillSublistImportVO getProgress(String var1);

    public void syncSublistProgress(String var1, BillSublistImportVO var2);

    public void checkExcelData(BillModel var1, Map<String, Object> var2, String var3, UserLoginDTO var4, Locale var5);

    public List<Map<String, Object>> saveExcelData(BillModel var1, Map<String, Object> var2);
}

