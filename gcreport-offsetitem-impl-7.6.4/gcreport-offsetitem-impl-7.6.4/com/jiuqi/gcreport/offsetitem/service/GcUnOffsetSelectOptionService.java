/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.offsetitem.vo.GcInputAdjustVO
 *  com.jiuqi.gcreport.offsetitem.vo.GcUnOffsetOptionVo
 */
package com.jiuqi.gcreport.offsetitem.service;

import com.jiuqi.gcreport.offsetitem.vo.GcInputAdjustVO;
import com.jiuqi.gcreport.offsetitem.vo.GcUnOffsetOptionVo;
import java.util.List;
import java.util.Map;

public interface GcUnOffsetSelectOptionService {
    public List<GcInputAdjustVO> queryContentById(String var1);

    public List<Map<String, Object>> listUnOffsetConfigDatas(String var1);

    public void exchangeSort(String var1, String var2, String var3, int var4);

    public void delete(GcUnOffsetOptionVo var1);

    public void save(List<GcUnOffsetOptionVo> var1);

    public List<Map<String, Object>> listSelectOptionForTab(String var1);

    public void saveSelectOption(Map<Object, List<Map<String, Object>>> var1, String var2);

    public String getCurDataSource();
}

