/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datapartnerapi.domain.FormInfo
 *  com.jiuqi.nr.datapartnerapi.domain.FormSchemaInfo
 *  com.jiuqi.nr.datapartnerapi.domain.SchemaPeriodInfo
 */
package com.jiuqi.nr.datapartnerapi.domain;

import com.jiuqi.nr.datapartnerapi.domain.FormInfo;
import com.jiuqi.nr.datapartnerapi.domain.FormSchemaInfo;
import com.jiuqi.nr.datapartnerapi.domain.SchemaPeriodInfo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FormSchemaInfoDTO
implements FormSchemaInfo,
Serializable {
    private static final long serialVersionUID = 1L;
    private String schemeKey;
    private String schemeTitle;
    private final List<SchemaPeriodInfo> schemaPeriods = new ArrayList<SchemaPeriodInfo>();
    private final List<FormInfo> forms = new ArrayList<FormInfo>();

    public FormSchemaInfoDTO(String schemeKey, String schemeTitle) {
        this.schemeKey = schemeKey;
        this.schemeTitle = schemeTitle;
    }

    public String getSchemeKey() {
        return this.schemeKey;
    }

    public void setSchemeKey(String schemeKey) {
        this.schemeKey = schemeKey;
    }

    public String getSchemeTitle() {
        return this.schemeTitle;
    }

    public void setSchemeTitle(String schemeTitle) {
        this.schemeTitle = schemeTitle;
    }

    public List<SchemaPeriodInfo> getSchemaPeriods() {
        return this.schemaPeriods;
    }

    public List<FormInfo> getForms() {
        return this.forms;
    }
}

