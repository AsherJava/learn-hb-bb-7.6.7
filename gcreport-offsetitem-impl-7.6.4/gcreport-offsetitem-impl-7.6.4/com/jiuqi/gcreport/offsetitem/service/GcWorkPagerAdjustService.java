/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 */
package com.jiuqi.gcreport.offsetitem.service;

import com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import java.util.List;
import java.util.Map;

public interface GcWorkPagerAdjustService {
    public List<GcOffSetVchrItemAdjustEO> queryGzOffsetEntry(QueryParamsVO var1);

    public List<Map<String, Object>> queryDxOffsetEntry(QueryParamsVO var1);

    public List<Map<String, Object>> queryDetailsOffsetEntry(QueryParamsVO var1);

    public Map<String, Object> querySplitSrcOffsetEntry(List<String> var1);
}

