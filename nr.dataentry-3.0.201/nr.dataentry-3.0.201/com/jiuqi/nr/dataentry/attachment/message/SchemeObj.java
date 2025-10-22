/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 */
package com.jiuqi.nr.dataentry.attachment.message;

import com.jiuqi.nr.dataentry.attachment.message.EntityObj;
import com.jiuqi.nr.dataentry.attachment.message.PeriodObj;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import java.util.List;

public class SchemeObj {
    private String key;
    private String title;
    private String code;
    private List<EntityObj> entityObjs;
    private PeriodObj periodObj;
    private String fromPeriod;
    private String toPeriod;

    public SchemeObj() {
    }

    public SchemeObj(FormSchemeDefine formSchemeDefine) {
        if (formSchemeDefine != null) {
            this.key = formSchemeDefine.getKey();
            this.title = formSchemeDefine.getTitle();
            this.code = formSchemeDefine.getFormSchemeCode();
            this.fromPeriod = formSchemeDefine.getFromPeriod();
            this.toPeriod = formSchemeDefine.getToPeriod();
        }
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

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<EntityObj> getEntityObjs() {
        return this.entityObjs;
    }

    public void setEntityObjs(List<EntityObj> entityObjs) {
        this.entityObjs = entityObjs;
    }

    public PeriodObj getPeriodObj() {
        return this.periodObj;
    }

    public void setPeriodObj(PeriodObj periodObj) {
        this.periodObj = periodObj;
    }
}

