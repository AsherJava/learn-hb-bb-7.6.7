/*
 * Decompiled with CFR 0.152.
 */
package nr.single.map.data.facade.para;

import nr.single.map.data.facade.para.ReportCodeClass;
import nr.single.map.data.facade.para.ReportDataClass;

public interface ReportTaskDataEnv {
    public int getTableCount();

    public ReportCodeClass getFMDM();

    public ReportDataClass getTable(String var1);

    public String getTaskTitle();

    public void getTaskTitle(String var1);

    public String getTaskFlag();

    public void setTaskFlag(String var1);
}

