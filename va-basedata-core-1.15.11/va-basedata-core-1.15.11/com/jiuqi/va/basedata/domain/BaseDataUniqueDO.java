/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.basedata.domain;

import java.util.Date;
import java.util.UUID;

public class BaseDataUniqueDO {
    private UUID id;
    private String objectcode;
    private Date validtime;

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getObjectcode() {
        return this.objectcode;
    }

    public void setObjectcode(String objectcode) {
        this.objectcode = objectcode;
    }

    public Date getValidtime() {
        return this.validtime;
    }

    public void setValidtime(Date validtime) {
        this.validtime = validtime;
    }
}

