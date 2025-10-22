/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.investworkpaper.vo.InvestWorkPaperQueryCondition
 */
package com.jiuqi.gcreport.invest.investworkpaper.dao;

import com.jiuqi.gcreport.investworkpaper.vo.InvestWorkPaperQueryCondition;
import java.util.List;
import java.util.Map;

public interface InvestWorkPaperFormDataQueryDao {
    public Map<String, Map<String, String>> getNrData(InvestWorkPaperQueryCondition var1, Map<String, List<String>> var2, Map<String, String> var3, Map<String, List<String>> var4);
}

