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
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@JsonIgnoreProperties(ignoreUnknown=true)
public class AccountIdListDTO
extends GeneralDTO {
    private List<String> userIdList;

    public AccountIdListDTO(String bimRequestId, String resultCode, String message, List<String> userIdList) {
        super(bimRequestId, resultCode, message);
        this.userIdList = userIdList;
    }

    public AccountIdListDTO() {
    }

    public List<String> getUserIdList() {
        return this.userIdList;
    }

    public void setUserIdList(List<String> userIdList) {
        this.userIdList = userIdList;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString((Object)this, (ToStringStyle)ToStringStyle.SHORT_PREFIX_STYLE);
    }
}

