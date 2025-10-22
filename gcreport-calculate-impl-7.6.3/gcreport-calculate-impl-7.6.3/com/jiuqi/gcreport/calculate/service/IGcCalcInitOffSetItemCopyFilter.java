/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.calculate.env.GcCalcEnvContext
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrDTO
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 */
package com.jiuqi.gcreport.calculate.service;

import com.jiuqi.gcreport.calculate.env.GcCalcEnvContext;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrDTO;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import java.util.List;

public interface IGcCalcInitOffSetItemCopyFilter {
    public List<GcOffSetVchrDTO> filter(GcCalcEnvContext var1, List<GcOffSetVchrDTO> var2, QueryParamsVO var3);
}

