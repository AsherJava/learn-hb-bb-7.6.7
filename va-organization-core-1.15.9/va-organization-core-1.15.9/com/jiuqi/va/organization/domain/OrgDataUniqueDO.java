/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.organization.domain;

import java.util.Date;
import java.util.UUID;

public class OrgDataUniqueDO {
    private UUID id;
    private String code;
    private Date validtime;

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getValidtime() {
        return this.validtime;
    }

    public void setValidtime(Date validtime) {
        this.validtime = validtime;
    }
}

