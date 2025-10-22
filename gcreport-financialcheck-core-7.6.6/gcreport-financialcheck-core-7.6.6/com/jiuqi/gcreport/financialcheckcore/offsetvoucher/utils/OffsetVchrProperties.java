/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.financialcheckcore.offsetvoucher.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="jiuqi.gc.financial-check")
public class OffsetVchrProperties {
    private Boolean offsetVchrMode;

    public Boolean getOffsetVchrMode() {
        return this.offsetVchrMode;
    }

    public void setOffsetVchrMode(Boolean offsetVchrMode) {
        this.offsetVchrMode = offsetVchrMode;
    }
}

