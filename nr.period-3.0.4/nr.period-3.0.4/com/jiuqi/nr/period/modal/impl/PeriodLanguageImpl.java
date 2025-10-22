/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.interval.anno.DBAnno$DBField
 *  com.jiuqi.nvwa.definition.interval.anno.DBAnno$DBTable
 */
package com.jiuqi.nr.period.modal.impl;

import com.jiuqi.nr.period.modal.IPeriodLanguage;
import com.jiuqi.nvwa.definition.interval.anno.DBAnno;
import java.util.Date;

@DBAnno.DBTable(dbTable="NR_PERIOD_LANGUAGE")
public class PeriodLanguageImpl
implements IPeriodLanguage {
    private static final long serialVersionUID = 1L;
    @DBAnno.DBField(dbField="PL_ENTITY")
    private String entity;
    @DBAnno.DBField(dbField="PL_CODE")
    private String code;
    @DBAnno.DBField(dbField="PL_TITLE")
    private String title;
    @DBAnno.DBField(dbField="PL_TYPE")
    private String type;

    @Override
    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getEntity() {
        return this.entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public Date getUpdateTime() {
        return null;
    }

    public String getKey() {
        return null;
    }

    public String getOrder() {
        return null;
    }

    public String getVersion() {
        return null;
    }

    public String getOwnerLevelAndId() {
        return null;
    }

    public String getID() {
        return null;
    }
}

