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
import com.jiuqi.ent.bamboocloud.bim.dto.account.AccountDTO;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@JsonIgnoreProperties(ignoreUnknown=true)
public class AccountQueryDTO
extends GeneralDTO {
    private AccountDTO account;

    public AccountQueryDTO(String bimRequestId, String resultCode, String message, AccountDTO account) {
        super(bimRequestId, resultCode, message);
        this.account = account;
    }

    public AccountQueryDTO() {
    }

    public AccountDTO getAccount() {
        return this.account;
    }

    public void setAccount(AccountDTO account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString((Object)this, (ToStringStyle)ToStringStyle.SHORT_PREFIX_STYLE);
    }
}

