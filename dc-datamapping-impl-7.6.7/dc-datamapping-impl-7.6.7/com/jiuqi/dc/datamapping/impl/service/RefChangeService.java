/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.datamapping.client.dto.RefChangeDTO
 *  com.jiuqi.dc.datamapping.client.vo.DataRefSaveVO
 */
package com.jiuqi.dc.datamapping.impl.service;

import com.jiuqi.dc.datamapping.client.dto.RefChangeDTO;
import com.jiuqi.dc.datamapping.client.vo.DataRefSaveVO;
import java.util.List;

public interface RefChangeService {
    public DataRefSaveVO handleRefChange(RefChangeDTO var1);

    public DataRefSaveVO handleRefBatchChange(String var1, String var2, Boolean var3, List<RefChangeDTO> var4);

    public void deletePendingData(String var1, String var2);
}

