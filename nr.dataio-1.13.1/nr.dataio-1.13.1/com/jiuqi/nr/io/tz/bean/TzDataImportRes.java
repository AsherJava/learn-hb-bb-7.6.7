/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.io.tz.bean;

import com.jiuqi.nr.io.tz.bean.MdCodeDataTime;
import java.util.List;
import java.util.Map;

public class TzDataImportRes {
    private int status;
    private Map<String, List<MdCodeDataTime>> mdCodeLog;

    public Map<String, List<MdCodeDataTime>> getMdCodeLog() {
        return this.mdCodeLog;
    }

    public void setMdCodeLog(Map<String, List<MdCodeDataTime>> mdCodeLog) {
        this.mdCodeLog = mdCodeLog;
    }

    public String toString() {
        return "TzDataImportRes{mdCodeLog=" + this.mdCodeLog + '}';
    }

    private boolean isSuccess() {
        return this.status == 0;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static TzDataImportRes Success() {
        return new TzDataImportRes();
    }
}

