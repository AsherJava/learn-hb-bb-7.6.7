/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.conversion.conversionsystem.vo.ConversionLogInfoVo
 */
package com.jiuqi.gcreport.conversion.conversionsystem.service;

import com.jiuqi.gcreport.conversion.conversionsystem.entity.ConversionLogInfoEo;
import com.jiuqi.gcreport.conversion.conversionsystem.vo.ConversionLogInfoVo;
import java.util.List;

public interface ConversionLogInfoService {
    public void saveLogInfo(ConversionLogInfoEo var1);

    public void updateLogInfo(ConversionLogInfoEo var1);

    public long count();

    public List<ConversionLogInfoVo> queryLogInfos(int var1, int var2);
}

