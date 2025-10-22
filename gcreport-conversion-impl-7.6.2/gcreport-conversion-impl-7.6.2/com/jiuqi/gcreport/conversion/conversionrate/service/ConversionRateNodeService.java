/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.conversion.conversionrate.vo.ConversionRateNodeVO
 */
package com.jiuqi.gcreport.conversion.conversionrate.service;

import com.jiuqi.gcreport.conversion.conversionrate.entity.ConversionRateNodeEO;
import com.jiuqi.gcreport.conversion.conversionrate.service.CommonService;
import com.jiuqi.gcreport.conversion.conversionrate.vo.ConversionRateNodeVO;
import java.util.List;

public interface ConversionRateNodeService
extends CommonService<ConversionRateNodeVO, ConversionRateNodeEO> {
    public List<ConversionRateNodeVO> queryByGroupid(String var1);

    public List<ConversionRateNodeVO> queryByIds(List<String> var1);

    public ConversionRateNodeVO query(String var1, String var2, String var3);

    public ConversionRateNodeVO save(String var1, String var2, String var3);

    public void deleteByGroupId(String var1);
}

