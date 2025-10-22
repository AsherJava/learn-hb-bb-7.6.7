/*
 * Decompiled with CFR 0.152.
 */
package nr.single.map.configurations.bean;

import java.util.HashMap;
import java.util.Map;
import nr.single.map.data.facade.SingleFileFieldInfo;

public class ZbMapping {
    private String reportCode;
    private Map<String, SingleFileFieldInfo> zbInfo;

    public ZbMapping() {
    }

    public ZbMapping(String reportCode, Map<String, SingleFileFieldInfo> zbInfo) {
        this.reportCode = reportCode;
        this.zbInfo = zbInfo;
    }

    public String getReportCode() {
        return this.reportCode;
    }

    public void setReportCode(String reportCode) {
        this.reportCode = reportCode;
    }

    public Map<String, SingleFileFieldInfo> getZbInfo() {
        return this.zbInfo;
    }

    public void setZbInfo(Map<String, SingleFileFieldInfo> zbInfo) {
        this.zbInfo = zbInfo;
    }

    public void putZbInfo(String netCode, SingleFileFieldInfo field) {
        if (this.zbInfo == null) {
            this.zbInfo = new HashMap<String, SingleFileFieldInfo>();
        }
        this.zbInfo.put(netCode, field);
    }
}

