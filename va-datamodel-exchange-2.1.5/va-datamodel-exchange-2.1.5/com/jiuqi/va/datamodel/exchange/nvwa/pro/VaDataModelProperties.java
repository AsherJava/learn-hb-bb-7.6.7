/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.datamodel.exchange.nvwa.pro;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix="jiuqi.nvwa.vadatamodel", ignoreInvalidFields=true)
@Validated
public class VaDataModelProperties {
    public static final String VA_DATAMODEL_PRO_ROOT = "jiuqi.nvwa.vadatamodel";
    private Boolean fielddelete = Boolean.FALSE;
    private Boolean syncorgrecoveryandstopfield = Boolean.FALSE;

    public void setFielddelete(Boolean fielddelete) {
        this.fielddelete = fielddelete;
    }

    public Boolean isFielddelete() {
        if (this.fielddelete == null) {
            return false;
        }
        return this.fielddelete;
    }

    public void setSyncorgrecoveryandstopfield(Boolean syncorgrecoveryandstopfield) {
        this.syncorgrecoveryandstopfield = syncorgrecoveryandstopfield;
    }

    public Boolean getSyncorgrecoveryandstopfield() {
        if (this.syncorgrecoveryandstopfield == null) {
            return false;
        }
        return this.syncorgrecoveryandstopfield;
    }
}

