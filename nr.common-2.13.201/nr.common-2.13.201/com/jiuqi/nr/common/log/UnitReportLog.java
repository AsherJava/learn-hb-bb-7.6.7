/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.common.log;

import java.util.List;

public interface UnitReportLog {
    public void addFormToUnit(String var1, String var2);

    public void addFormToUnit(String var1, List<String> var2);

    public void addFormToUnit(List<String> var1, String var2);

    public void addTableToUnit(String var1, String var2);

    public void addTableToUnit(String var1, List<String> var2);

    public void addTableToUnit(List<String> var1, String var2);

    public String toLog();

    public String[] getUnits();
}

