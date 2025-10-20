/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  javax.persistence.Id
 *  javax.persistence.Table
 */
package com.jiuqi.va.biz.domain.commrule;

import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.UUID;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="BIZ_COMMRULE_INFO")
public class BizCommonRuleInfoDO
extends TenantDO {
    @Id
    private UUID id;
    private String ruleinfo;

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getRuleinfo() {
        return this.ruleinfo;
    }

    public void setRuleinfo(String ruleinfo) {
        this.ruleinfo = ruleinfo;
    }
}

