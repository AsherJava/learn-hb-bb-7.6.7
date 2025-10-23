/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zbquery.extend;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public interface IZBQueryExtendProvider {
    public String getMasterDimension(Map<String, String> var1, String var2) throws Exception;

    public List<String> getQueryOptions(Map<String, String> var1);

    public List<String> getReportTasks(Map<String, String> var1);
}

