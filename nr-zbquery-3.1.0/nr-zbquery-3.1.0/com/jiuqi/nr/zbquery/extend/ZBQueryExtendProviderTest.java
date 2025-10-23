/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zbquery.extend;

import com.jiuqi.nr.zbquery.extend.IZBQueryExtendProvider;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ZBQueryExtendProviderTest
implements IZBQueryExtendProvider {
    @Override
    public String getMasterDimension(Map<String, String> extendedDatas, String dimKey) throws Exception {
        if (extendedDatas == null || extendedDatas.size() == 0) {
            return dimKey;
        }
        return "YJGM@CB";
    }

    @Override
    public List<String> getQueryOptions(Map<String, String> extendedDatas) {
        if (extendedDatas == null || extendedDatas.size() == 0) {
            return null;
        }
        String batchGatherSchemeCode = "xxxx";
        String option = "Using(\"batchGatherSchemeCode=" + batchGatherSchemeCode + "\")";
        return Arrays.asList(option);
    }

    @Override
    public List<String> getReportTasks(Map<String, String> extendedDatas) {
        if (extendedDatas == null || extendedDatas.size() == 0) {
            return null;
        }
        return Arrays.asList("8b38e2e7-c8f5-9126-514e-85c45e085c2e");
    }
}

