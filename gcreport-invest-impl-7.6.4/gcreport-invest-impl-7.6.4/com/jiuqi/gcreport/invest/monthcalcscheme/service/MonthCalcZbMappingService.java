/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.gcreport.monthcalcscheme.vo.MonthCalcZbMappingCond
 *  com.jiuqi.gcreport.monthcalcscheme.vo.MonthCalcZbMappingVO
 */
package com.jiuqi.gcreport.invest.monthcalcscheme.service;

import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.gcreport.monthcalcscheme.vo.MonthCalcZbMappingCond;
import com.jiuqi.gcreport.monthcalcscheme.vo.MonthCalcZbMappingVO;
import java.util.List;
import java.util.Map;

public interface MonthCalcZbMappingService {
    public PageInfo<MonthCalcZbMappingVO> listZbMappings(MonthCalcZbMappingCond var1);

    public void saveZbMappings(String var1, List<MonthCalcZbMappingVO> var2);

    public Map<String, Object> getZbMappingCache(String var1, int var2);

    public void deleteZbMappings(List<String> var1);

    public void exchangeSort(String var1, int var2);
}

