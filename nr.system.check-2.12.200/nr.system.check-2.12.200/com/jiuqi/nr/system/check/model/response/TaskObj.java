/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.TaskDefine
 */
package com.jiuqi.nr.system.check.model.response;

import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.system.check.model.response.PeriodObj;
import com.jiuqi.nr.system.check.model.response.SchemeObj;
import java.util.List;

public class TaskObj {
    private String key;
    private String title;
    private String dataSchemeKey;
    private List<SchemeObj> schemeObjs;
    private String fromPeriod;
    private String toPeriod;
    private PeriodObj periodObj;

    public TaskObj() {
    }

    public String getFromPeriod() {
        return this.fromPeriod;
    }

    public void setFromPeriod(String fromPeriod) {
        this.fromPeriod = fromPeriod;
    }

    public String getToPeriod() {
        return this.toPeriod;
    }

    public void setToPeriod(String toPeriod) {
        this.toPeriod = toPeriod;
    }

    public TaskObj(TaskDefine taskDefine) {
        if (taskDefine != null) {
            this.key = taskDefine.getKey();
            this.title = taskDefine.getTitle();
            this.dataSchemeKey = taskDefine.getDataScheme();
            this.fromPeriod = taskDefine.getFromPeriod();
            this.toPeriod = taskDefine.getToPeriod();
        }
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<SchemeObj> getSchemeObjs() {
        return this.schemeObjs;
    }

    public void setSchemeObjs(List<SchemeObj> schemeObjs) {
        this.schemeObjs = schemeObjs;
    }

    public PeriodObj getPeriodObj() {
        return this.periodObj;
    }

    public void setPeriodObj(PeriodObj periodObj) {
        this.periodObj = periodObj;
    }

    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }
}

