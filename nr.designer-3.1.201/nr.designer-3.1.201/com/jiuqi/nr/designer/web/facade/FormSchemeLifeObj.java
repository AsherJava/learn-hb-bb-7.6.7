/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 */
package com.jiuqi.nr.designer.web.facade;

import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.designer.util.SchemePeriodObj;
import java.util.Map;
import java.util.UUID;

public class FormSchemeLifeObj {
    private String recordKey;
    private String key;
    private String formSchemeCode;
    private String title;
    private String fromPeriod;
    private String toPeriod;
    private String fromPeriodTitle;
    private String toPeriodTitle;

    public static FormSchemeLifeObj toFormSchemeLifeObj(SchemePeriodObj schemePeriodObj, Map<String, DesignFormSchemeDefine> schemeMap) {
        FormSchemeLifeObj obj = new FormSchemeLifeObj();
        obj.setRecordKey(UUID.randomUUID().toString());
        obj.setKey(schemePeriodObj.getScheme());
        obj.setFromPeriod(schemePeriodObj.getStart());
        obj.setToPeriod(schemePeriodObj.getEnd());
        obj.setFormSchemeCode(schemeMap.get(schemePeriodObj.getScheme()).getFormSchemeCode());
        obj.setTitle(schemeMap.get(schemePeriodObj.getScheme()).getTitle());
        return obj;
    }

    public FormSchemeLifeObj() {
    }

    public FormSchemeLifeObj(String key, String formSchemeCode, String title) {
        this.key = key;
        this.formSchemeCode = formSchemeCode;
        this.title = title;
    }

    public FormSchemeLifeObj(String fromPeriod, String toPeriod) {
        this.fromPeriod = fromPeriod;
        this.toPeriod = toPeriod;
    }

    public String getFromPeriodTitle() {
        return this.fromPeriodTitle;
    }

    public void setFromPeriodTitle(String fromPeriodTitle) {
        this.fromPeriodTitle = fromPeriodTitle;
    }

    public String getToPeriodTitle() {
        return this.toPeriodTitle;
    }

    public void setToPeriodTitle(String toPeriodTitle) {
        this.toPeriodTitle = toPeriodTitle;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setFormSchemeCode(String formSchemeCode) {
        this.formSchemeCode = formSchemeCode;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setFromPeriod(String fromPeriod) {
        this.fromPeriod = fromPeriod;
    }

    public void setToPeriod(String toPeriod) {
        this.toPeriod = toPeriod;
    }

    public String getKey() {
        return this.key;
    }

    public String getFormSchemeCode() {
        return this.formSchemeCode;
    }

    public String getTitle() {
        return this.title;
    }

    public String getFromPeriod() {
        return this.fromPeriod;
    }

    public String getToPeriod() {
        return this.toPeriod;
    }

    public String getRecordKey() {
        return this.recordKey;
    }

    public void setRecordKey(String recordKey) {
        this.recordKey = recordKey;
    }
}

