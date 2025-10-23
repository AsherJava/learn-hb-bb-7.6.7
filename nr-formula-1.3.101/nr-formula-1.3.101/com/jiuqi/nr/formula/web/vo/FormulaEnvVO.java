/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.formula.web.vo;

import com.jiuqi.nr.formula.dto.FormulaAuditTypeDTO;
import java.util.List;

public class FormulaEnvVO {
    private boolean enableCondition;
    private boolean enableCustom;
    private String ownUnit;
    private boolean isAdmin;
    private List<FormulaAuditTypeDTO> auditType;

    public boolean isEnableCondition() {
        return this.enableCondition;
    }

    public void setEnableCondition(boolean enableCondition) {
        this.enableCondition = enableCondition;
    }

    public boolean isEnableCustom() {
        return this.enableCustom;
    }

    public void setEnableCustom(boolean enableCustom) {
        this.enableCustom = enableCustom;
    }

    public List<FormulaAuditTypeDTO> getAuditType() {
        return this.auditType;
    }

    public void setAuditType(List<FormulaAuditTypeDTO> auditType) {
        this.auditType = auditType;
    }

    public String getOwnUnit() {
        return this.ownUnit;
    }

    public void setOwnUnit(String ownUnit) {
        this.ownUnit = ownUnit;
    }

    public boolean isAdmin() {
        return this.isAdmin;
    }

    public void setAdmin(boolean admin) {
        this.isAdmin = admin;
    }
}

