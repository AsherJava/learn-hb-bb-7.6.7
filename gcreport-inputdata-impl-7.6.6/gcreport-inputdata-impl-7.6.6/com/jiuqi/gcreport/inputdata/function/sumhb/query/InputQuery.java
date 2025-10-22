/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.inputdata.function.sumhb.query;

import java.util.List;
import java.util.Map;

public interface InputQuery {
    public static final String regionName = "GROUPXYZ";
    public static final String sumFlagName = "SUMXYZ";

    public List<Map<String, Object>> query();
}

