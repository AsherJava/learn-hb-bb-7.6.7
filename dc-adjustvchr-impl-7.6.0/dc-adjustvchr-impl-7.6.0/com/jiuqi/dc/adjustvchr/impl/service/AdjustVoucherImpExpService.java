/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.adjustvchr.client.dto.AdjustVoucherQueryDTO
 *  javax.servlet.http.HttpServletResponse
 */
package com.jiuqi.dc.adjustvchr.impl.service;

import com.jiuqi.dc.adjustvchr.client.dto.AdjustVoucherQueryDTO;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

public interface AdjustVoucherImpExpService {
    public void exportAdjustVoucher(HttpServletResponse var1, AdjustVoucherQueryDTO var2, boolean var3);

    public Object importAdjustVoucher(List<Object[]> var1, AdjustVoucherQueryDTO var2);
}

