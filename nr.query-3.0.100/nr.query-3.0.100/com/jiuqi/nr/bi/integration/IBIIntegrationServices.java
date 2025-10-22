/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bi.integration;

import java.util.List;
import java.util.Map;

public interface IBIIntegrationServices {
    public byte[] ExportBIReport(String var1, Map<String, List<String>> var2, String var3);

    public byte[] ExportChart(String var1, Map<String, List<String>> var2, String var3);
}

