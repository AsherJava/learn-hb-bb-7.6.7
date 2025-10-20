/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.adjustvchr.client.dto.AdjustVoucherQueryDTO
 *  com.jiuqi.dc.adjustvchr.client.vo.AdjustVchrSysOptionVO
 */
package com.jiuqi.dc.adjustvchr.impl.check;

import com.jiuqi.dc.adjustvchr.client.dto.AdjustVoucherQueryDTO;
import com.jiuqi.dc.adjustvchr.client.vo.AdjustVchrSysOptionVO;
import com.jiuqi.dc.adjustvchr.impl.impexp.entity.AdjustVchrItemImportVO;
import java.util.List;

public interface AdjustVchrImportCheckService {
    public String checkImportData(int var1, List<AdjustVchrItemImportVO> var2, AdjustVoucherQueryDTO var3, AdjustVchrSysOptionVO var4, boolean var5);

    public int checkOrder();
}

