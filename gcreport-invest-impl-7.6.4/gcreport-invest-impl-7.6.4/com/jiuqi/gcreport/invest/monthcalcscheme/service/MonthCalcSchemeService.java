/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.monthcalcscheme.vo.MonthCalcSchemeVO
 *  com.jiuqi.gcreport.monthcalcscheme.vo.TaskVO
 */
package com.jiuqi.gcreport.invest.monthcalcscheme.service;

import com.jiuqi.gcreport.monthcalcscheme.vo.MonthCalcSchemeVO;
import com.jiuqi.gcreport.monthcalcscheme.vo.TaskVO;
import java.util.List;
import java.util.Map;

public interface MonthCalcSchemeService {
    public void saveMonthCalcScheme(MonthCalcSchemeVO var1);

    public MonthCalcSchemeVO getMonthCalcScheme(String var1);

    public List<MonthCalcSchemeVO> listMonthCalcSchemes();

    public Map<String, List<TaskVO>> getTasksOfType();

    public void deleteMonthCalcScheme(String var1);
}

