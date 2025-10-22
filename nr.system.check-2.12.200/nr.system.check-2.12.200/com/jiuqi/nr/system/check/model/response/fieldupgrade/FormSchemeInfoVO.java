/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.system.check.model.response.fieldupgrade;

import com.jiuqi.nr.system.check.model.response.fieldupgrade.EntityDefineInfoVO;
import java.util.List;

public class FormSchemeInfoVO {
    private String key;
    private String title;
    private Boolean hasFMDM = false;
    private String period;
    private List<EntityDefineInfoVO> entityDefineInfos;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getHasFMDM() {
        return this.hasFMDM;
    }

    public void setHasFMDM(Boolean hasFMDM) {
        this.hasFMDM = hasFMDM;
    }

    public List<EntityDefineInfoVO> getEntityDefineInfos() {
        return this.entityDefineInfos;
    }

    public void setEntityDefineInfos(List<EntityDefineInfoVO> entityDefineInfos) {
        this.entityDefineInfos = entityDefineInfos;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }
}

