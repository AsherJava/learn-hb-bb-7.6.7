/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.investcarryover.vo.InvestBillCarryOverSettingVO
 */
package com.jiuqi.gcreport.invest.investbillcarryover.service;

import com.jiuqi.gcreport.investcarryover.vo.InvestBillCarryOverSettingVO;
import java.util.List;
import java.util.Map;

public interface InvestBillCarryOverSettingService {
    public void saveSetting(InvestBillCarryOverSettingVO var1);

    public List<InvestBillCarryOverSettingVO> listSettings(String var1);

    public void updateSetting(InvestBillCarryOverSettingVO var1);

    public void deleteSetting(String var1);

    public Map<String, Object> listCarryOverColums();

    public void exchangeSort(String var1, String var2);
}

