/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.investworkpaper.vo.Column
 *  com.jiuqi.gcreport.investworkpaper.vo.InvestWorkPaperSettingVo
 */
package com.jiuqi.gcreport.invest.investworkpaper.service;

import com.jiuqi.gcreport.investworkpaper.vo.Column;
import com.jiuqi.gcreport.investworkpaper.vo.InvestWorkPaperSettingVo;
import java.util.List;

public interface InvestWorkPaperSettingService {
    public void save(InvestWorkPaperSettingVo var1);

    public InvestWorkPaperSettingVo getSettingData(String var1, String var2, String var3);

    public List<Column> listInvestAmtFields();
}

