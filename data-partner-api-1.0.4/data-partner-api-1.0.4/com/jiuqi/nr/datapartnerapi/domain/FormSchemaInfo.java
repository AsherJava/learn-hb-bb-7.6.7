/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datapartnerapi.domain;

import com.jiuqi.nr.datapartnerapi.domain.FormInfo;
import com.jiuqi.nr.datapartnerapi.domain.SchemaPeriodInfo;
import java.util.List;

public interface FormSchemaInfo {
    public String getSchemeKey();

    public String getSchemeTitle();

    public List<SchemaPeriodInfo> getSchemaPeriods();

    public List<FormInfo> getForms();
}

