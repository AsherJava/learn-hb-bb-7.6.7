/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 */
package com.jiuqi.va.bizmeta.domain.metagroup;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Set;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class MetaGroupAuthVO {
    private Integer authFlag;
    private Set<String> groupAuth;

    public Integer getAuthFlag() {
        return this.authFlag;
    }

    public void setAuthFlag(Integer authFlag) {
        this.authFlag = authFlag;
    }

    public Set<String> getGroupAuth() {
        return this.groupAuth;
    }

    public void setGroupAuth(Set<String> groupAuth) {
        this.groupAuth = groupAuth;
    }
}

