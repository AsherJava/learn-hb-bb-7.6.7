/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.common.param.CommonImportDetails
 */
package com.jiuqi.nr.data.checkdes.facade.obj;

import com.jiuqi.nr.data.checkdes.facade.obj.ImpFailType;
import com.jiuqi.nr.data.checkdes.obj.CKDTransObj;
import com.jiuqi.nr.data.common.param.CommonImportDetails;
import java.io.Serializable;

public class ImpFailedInfo
extends CommonImportDetails
implements Serializable {
    private static final long serialVersionUID = 3938894536941447130L;
    private String formulaSchemeKey;
    private String formulaSchemeTitle;
    private String formulaKey;
    private String formulaCode;
    private CKDTransObj ckdTransObj;
    private final ImpFailType impFailType;

    public ImpFailedInfo(ImpFailType impFailType) {
        this.impFailType = impFailType;
    }

    public String getFormulaSchemeKey() {
        return this.formulaSchemeKey;
    }

    public void setFormulaSchemeKey(String formulaSchemeKey) {
        this.formulaSchemeKey = formulaSchemeKey;
    }

    public String getFormulaSchemeTitle() {
        return this.formulaSchemeTitle;
    }

    public void setFormulaSchemeTitle(String formulaSchemeTitle) {
        this.formulaSchemeTitle = formulaSchemeTitle;
    }

    public String getFormulaKey() {
        return this.formulaKey;
    }

    public void setFormulaKey(String formulaKey) {
        this.formulaKey = formulaKey;
    }

    public String getFormulaCode() {
        return this.formulaCode;
    }

    public void setFormulaCode(String formulaCode) {
        this.formulaCode = formulaCode;
    }

    public CKDTransObj getCkdTransObj() {
        return this.ckdTransObj;
    }

    public void setCkdTransObj(CKDTransObj ckdTransObj) {
        this.ckdTransObj = ckdTransObj;
    }

    public ImpFailType getImpFailType() {
        return this.impFailType;
    }
}

