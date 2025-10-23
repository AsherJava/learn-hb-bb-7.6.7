/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.multcheck2.web.vo;

import com.jiuqi.nr.multcheck2.provider.CheckItemParam;
import com.jiuqi.nr.multcheck2.web.vo.MultcheckEnvContext;
import java.io.Serializable;
import java.util.Map;

public class CheckItemEnvParam
extends CheckItemParam
implements Serializable {
    private MultcheckEnvContext envContext;
    private Map<String, String> itemDimForReport;
    private String traceid;

    public MultcheckEnvContext getEnvContext() {
        return this.envContext;
    }

    public void setEnvContext(MultcheckEnvContext envContext) {
        this.envContext = envContext;
    }

    public Map<String, String> getItemDimForReport() {
        return this.itemDimForReport;
    }

    public void setItemDimForReport(Map<String, String> itemDimForReport) {
        this.itemDimForReport = itemDimForReport;
    }

    public String getTraceid() {
        return this.traceid;
    }

    public void setTraceid(String traceid) {
        this.traceid = traceid;
    }
}

