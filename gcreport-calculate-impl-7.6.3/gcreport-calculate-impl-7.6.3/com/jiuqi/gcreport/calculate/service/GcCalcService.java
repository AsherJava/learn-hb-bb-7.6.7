/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO
 *  com.jiuqi.gcreport.calculate.env.GcCalcEnvContext
 *  com.jiuqi.gcreport.calculate.env.impl.GcCalcEnvContextImpl
 *  com.jiuqi.gcreport.calculate.vo.GcCalcArgmentsVO
 *  com.jiuqi.gcreport.calculate.vo.GcCalcLogVO
 *  javax.validation.Valid
 */
package com.jiuqi.gcreport.calculate.service;

import com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO;
import com.jiuqi.gcreport.calculate.env.GcCalcEnvContext;
import com.jiuqi.gcreport.calculate.env.impl.GcCalcEnvContextImpl;
import com.jiuqi.gcreport.calculate.vo.GcCalcArgmentsVO;
import com.jiuqi.gcreport.calculate.vo.GcCalcLogVO;
import java.util.Set;
import javax.validation.Valid;

public interface GcCalcService {
    public GcCalcEnvContext calc(GcCalcEnvContextImpl var1);

    public GcCalcEnvContext calc(GcCalcArgmentsDTO var1);

    public GcCalcArgmentsDTO convertVO2DTO(@Valid GcCalcArgmentsVO var1);

    public GcCalcLogVO findCalcLogVo(GcCalcArgmentsVO var1);

    public Set<String> deleteAutoOffsetEntrysByRule(String var1, GcCalcArgmentsDTO var2);
}

