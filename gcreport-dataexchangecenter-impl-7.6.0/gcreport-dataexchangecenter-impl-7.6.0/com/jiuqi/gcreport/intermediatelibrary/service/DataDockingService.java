/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.intermediatelibrary.dto.DataDockingBlockDTO
 *  com.jiuqi.gcreport.intermediatelibrary.vo.DataDockingQueryVO
 *  com.jiuqi.gcreport.intermediatelibrary.vo.DataDockingResponse
 *  com.jiuqi.gcreport.intermediatelibrary.vo.DataDockingVO
 */
package com.jiuqi.gcreport.intermediatelibrary.service;

import com.jiuqi.gcreport.intermediatelibrary.dto.DataDockingBlockDTO;
import com.jiuqi.gcreport.intermediatelibrary.vo.DataDockingQueryVO;
import com.jiuqi.gcreport.intermediatelibrary.vo.DataDockingResponse;
import com.jiuqi.gcreport.intermediatelibrary.vo.DataDockingVO;

public interface DataDockingService {
    public DataDockingResponse saveData(DataDockingVO var1);

    public String saveRegionData(DataDockingBlockDTO var1);

    public DataDockingVO queryData(DataDockingQueryVO var1);
}

