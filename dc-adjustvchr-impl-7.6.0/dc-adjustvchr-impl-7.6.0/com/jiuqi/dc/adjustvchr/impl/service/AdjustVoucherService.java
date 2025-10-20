/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.adjustvchr.client.dto.AdjustVchrCopyDTO
 *  com.jiuqi.dc.adjustvchr.client.dto.AdjustVoucherSaveDTO
 *  com.jiuqi.dc.adjustvchr.client.vo.AdjustVchrDeleteVO
 */
package com.jiuqi.dc.adjustvchr.impl.service;

import com.jiuqi.dc.adjustvchr.client.dto.AdjustVchrCopyDTO;
import com.jiuqi.dc.adjustvchr.client.dto.AdjustVoucherSaveDTO;
import com.jiuqi.dc.adjustvchr.client.vo.AdjustVchrDeleteVO;

public interface AdjustVoucherService {
    public String save(AdjustVoucherSaveDTO var1);

    public boolean batchDelete(AdjustVchrDeleteVO var1);

    public void copy(AdjustVchrCopyDTO var1);
}

