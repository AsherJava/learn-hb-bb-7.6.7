/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  org.apache.commons.lang3.builder.ToStringBuilder
 *  org.apache.commons.lang3.builder.ToStringStyle
 */
package com.jiuqi.ent.bamboocloud.bim.dto.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.ent.bamboocloud.bim.dto.GeneralDTO;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@JsonIgnoreProperties(ignoreUnknown=true)
public class AccountCreateDTO
extends GeneralDTO {
    private String uid;

    public AccountCreateDTO(String bimRequestId, String resultCode, String message, String uid) {
        super(bimRequestId, resultCode, message);
        this.uid = uid;
    }

    public AccountCreateDTO() {
    }

    public String getUid() {
        return this.uid;
    }

    public AccountCreateDTO setUid(String uid) {
        this.uid = uid;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString((Object)this, (ToStringStyle)ToStringStyle.SHORT_PREFIX_STYLE);
    }
}

