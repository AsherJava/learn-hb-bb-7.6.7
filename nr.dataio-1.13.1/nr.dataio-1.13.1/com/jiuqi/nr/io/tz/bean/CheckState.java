/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.io.tz.bean;

import com.jiuqi.nr.io.tz.bean.MdCodeDataTime;
import java.util.List;
import java.util.Map;

public class CheckState {
    private int mdCodeCount;
    private List<MdCodeDataTime> codeDataTimes;
    private Map<String, List<MdCodeDataTime>> failCodeLog;

    public int getMdCodeCount() {
        return this.mdCodeCount;
    }

    public void setMdCodeCount(int mdCodeCount) {
        this.mdCodeCount = mdCodeCount;
    }

    public List<MdCodeDataTime> getCodeDataTimes() {
        return this.codeDataTimes;
    }

    public void setCodeDataTimes(List<MdCodeDataTime> codeDataTimes) {
        this.codeDataTimes = codeDataTimes;
    }

    public Map<String, List<MdCodeDataTime>> getFailCodeLog() {
        return this.failCodeLog;
    }

    public void setFailCodeLog(Map<String, List<MdCodeDataTime>> failCodeLog) {
        this.failCodeLog = failCodeLog;
    }
}

