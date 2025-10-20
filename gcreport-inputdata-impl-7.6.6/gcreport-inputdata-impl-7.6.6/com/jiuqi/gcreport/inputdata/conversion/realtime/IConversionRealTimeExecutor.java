/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.conversion.common.GcConversionOrgAndFormContextEnv
 *  com.jiuqi.nr.dataentry.bean.DataEntryContext
 */
package com.jiuqi.gcreport.inputdata.conversion.realtime;

import com.jiuqi.gcreport.conversion.common.GcConversionOrgAndFormContextEnv;
import com.jiuqi.gcreport.inputdata.inputdata.entity.InputDataEO;
import com.jiuqi.nr.dataentry.bean.DataEntryContext;
import java.util.List;
import java.util.Map;

public interface IConversionRealTimeExecutor {
    public String getExecutorName();

    public Map<String, List<InputDataEO>> realTimeConversion(DataEntryContext var1, List<InputDataEO> var2, List<InputDataEO> var3);

    public void deleteHistoryData(String var1, Map<String, String> var2, List<String> var3, boolean var4);

    public boolean conversionButtonCheck(GcConversionOrgAndFormContextEnv var1);

    public List<String> getCancelOffsetCurrencyList(String var1, Map<String, String> var2);
}

