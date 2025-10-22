/*
 * Decompiled with CFR 0.152.
 */
package nr.midstore2.data.util;

import java.util.List;
import nr.midstore2.data.extension.bean.ReportMidstoreContext;

public interface IReportMidstoreConditonService {
    public String getCondtionSQl(String var1);

    public String getCondtionSQl(ReportMidstoreContext var1, String var2);

    public List<String> getDeEntityCodes(ReportMidstoreContext var1);

    public List<List<String>> getDeEntityCodes(ReportMidstoreContext var1, int var2);

    public String getCondtionSQl(List<String> var1, String var2);
}

