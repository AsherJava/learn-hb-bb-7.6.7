/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.client.onlinePeriod.vo.OnlinePeriodCondiVO
 *  com.jiuqi.dc.base.client.onlinePeriod.vo.OnlinePeriodDefineVO
 */
package com.jiuqi.dc.base.impl.onlinePeriod.service;

import com.jiuqi.dc.base.client.onlinePeriod.vo.OnlinePeriodCondiVO;
import com.jiuqi.dc.base.client.onlinePeriod.vo.OnlinePeriodDefineVO;
import java.util.List;

public interface OnlinePeriodDefineService {
    public List<OnlinePeriodDefineVO> getAllTableData(OnlinePeriodCondiVO var1);

    public void insertPeriod(OnlinePeriodDefineVO var1);

    public void nvwaImportPeriod(OnlinePeriodDefineVO var1);

    public void updatePeriod(OnlinePeriodDefineVO var1);

    public void deletePeriod(String var1);

    public OnlinePeriodDefineVO getPeriodDataById(String var1);

    public Integer getMinPeriodYear();
}

