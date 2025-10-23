/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.api;

import com.jiuqi.nr.datascheme.api.core.Basic;
import com.jiuqi.nr.datascheme.api.core.Ordered;
import com.jiuqi.nr.datascheme.api.type.DataSchemeType;
import org.springframework.util.StringUtils;

public interface DataScheme
extends Basic,
Ordered {
    public String getPrefix();

    public Boolean getAuto();

    default public boolean isAuto() {
        return this.getAuto() != null && this.getAuto() != false;
    }

    public String getCreator();

    public DataSchemeType getType();

    public String getVersion();

    public String getLevel();

    public String getDataGroupKey();

    public String getBizCode();

    public Boolean getGatherDB();

    public String getEncryptScene();

    public String getZbSchemeKey();

    public String getZbSchemeVersion();

    default public Boolean isEnableCalibre() {
        return StringUtils.hasLength(this.getCalibre());
    }

    public String getCalibre();
}

