/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bi.integration;

import com.jiuqi.nr.bi.integration.ChartServices;
import com.jiuqi.nr.bi.integration.IBIIntegrationServices;
import com.jiuqi.nr.bi.integration.ReportServices;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BIIntegrationServices
implements IBIIntegrationServices {
    @Autowired
    ReportServices reportServ;
    @Autowired
    ChartServices chartServ;

    @Override
    public byte[] ExportBIReport(String reportID, Map<String, List<String>> masterValues, String moduleCode) {
        return this.reportServ.Export(reportID, masterValues, moduleCode);
    }

    @Override
    public byte[] ExportChart(String chartID, Map<String, List<String>> masterValues, String moduleCode) {
        return this.chartServ.Export(chartID, masterValues, moduleCode);
    }
}

