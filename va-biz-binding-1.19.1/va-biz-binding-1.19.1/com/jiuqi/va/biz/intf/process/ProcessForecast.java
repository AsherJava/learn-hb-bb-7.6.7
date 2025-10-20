/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.process;

import com.jiuqi.va.biz.intf.model.Model;
import java.util.List;
import java.util.Map;

public interface ProcessForecast {
    public String getForecastCode();

    public List<Map<String, Object>> getForecastNodeInfos(Model var1, Map<String, Object> var2);

    public String getForecastOrder();

    public Map<String, Object> getForecastNodeInfo(String var1, String var2);
}

