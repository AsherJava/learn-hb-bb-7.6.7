/*
 * Decompiled with CFR 0.152.
 */
package nr.midstore2.data.param;

import nr.midstore2.data.extension.bean.ReportMidstoreContext;

public interface IReportMidstoreMappingService {
    public void initZbMapping(ReportMidstoreContext var1);

    public void initPeriodMapping(ReportMidstoreContext var1);

    public String getMapFieldCode(String var1);

    public String getMapTableCode(String var1);
}

