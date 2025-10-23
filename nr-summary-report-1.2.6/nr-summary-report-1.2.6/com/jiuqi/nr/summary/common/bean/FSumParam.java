/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.common.bean;

import com.jiuqi.nr.summary.vo.SumUnit;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface FSumParam
extends Serializable {
    public String getSolutionKey();

    public List<String> getReportKeys();

    public List<String> getPeriods();

    public SumUnit getUnit();

    public Map<String, String[]> getScenes();

    public boolean isAfterCalculate();
}

