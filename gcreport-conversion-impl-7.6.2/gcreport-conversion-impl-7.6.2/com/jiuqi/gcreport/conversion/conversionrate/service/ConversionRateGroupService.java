/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.conversion.conversionrate.vo.CommonOptionVO
 *  com.jiuqi.gcreport.conversion.conversionrate.vo.ConversionRateGroupVO
 */
package com.jiuqi.gcreport.conversion.conversionrate.service;

import com.jiuqi.gcreport.conversion.conversionrate.entity.ConversionRateGroupEO;
import com.jiuqi.gcreport.conversion.conversionrate.service.CommonService;
import com.jiuqi.gcreport.conversion.conversionrate.vo.CommonOptionVO;
import com.jiuqi.gcreport.conversion.conversionrate.vo.ConversionRateGroupVO;
import java.util.List;
import java.util.Map;

public interface ConversionRateGroupService
extends CommonService<ConversionRateGroupVO, ConversionRateGroupEO> {
    public static final String DEFAULT_GROUP_NAME = "\u9ed8\u8ba4\u5206\u7ec4";

    public List<ConversionRateGroupVO> deleteByPeriod(String var1, String var2);

    public List<ConversionRateGroupVO> deleteBySystem(String var1);

    public List<ConversionRateGroupVO> queryByPeriod(String var1, String var2);

    public List<ConversionRateGroupVO> queryBySystem(String var1);

    public List<ConversionRateGroupVO> queryByIds(List<String> var1);

    public List<CommonOptionVO> getGroups(String var1, String var2);

    public Map<String, String> getGroupsMap(String var1, String var2);

    public List<String> getPeriods(String var1);

    public ConversionRateGroupVO get(String var1, String var2, String var3);

    public ConversionRateGroupVO save(String var1, String var2, String var3);

    public void updateGroupName(String var1, String var2);
}

