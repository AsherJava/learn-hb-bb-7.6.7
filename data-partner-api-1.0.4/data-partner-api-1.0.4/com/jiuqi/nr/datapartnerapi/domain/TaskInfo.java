/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datapartnerapi.domain;

import com.jiuqi.nr.datapartnerapi.domain.FormSchemaInfo;
import java.util.List;

public interface TaskInfo {
    public String getTaskKey();

    public String getTaskCode();

    public String getTaskTitle();

    public String getDataTime();

    public String getDataSchemeKey();

    public String getDataSchemeTitle();

    public List<String> getEntityIds();

    public long getTimestamp();

    public List<FormSchemaInfo> getFormSchemas();
}

