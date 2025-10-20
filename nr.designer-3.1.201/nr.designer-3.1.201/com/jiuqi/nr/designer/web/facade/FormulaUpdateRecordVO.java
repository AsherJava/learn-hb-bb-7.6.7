/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.common.DesignFormulaDTO
 */
package com.jiuqi.nr.designer.web.facade;

import com.jiuqi.nr.definition.common.DesignFormulaDTO;

public class FormulaUpdateRecordVO {
    private String formulaSchemeTitle;
    private String formulaSchemeOrder;
    private String formulaCode;
    private String formulaOld;
    private String formulaNew;
    private String updateResult;
    private boolean success = true;
    private String formTitle;

    public static FormulaUpdateRecordVO toFormulaUpdateRecordVO(DesignFormulaDTO designFormulaDTO) {
        FormulaUpdateRecordVO vo = new FormulaUpdateRecordVO();
        vo.setFormulaSchemeTitle(designFormulaDTO.getFormulaSchemeTitle());
        vo.setFormulaSchemeOrder(designFormulaDTO.getFormulaSchemeOrder());
        vo.setFormulaOld(designFormulaDTO.getOldExpression());
        vo.setFormulaNew(designFormulaDTO.getNewExpression());
        vo.setSuccess(designFormulaDTO.isSuccess());
        vo.setUpdateResult(designFormulaDTO.getResultMes());
        vo.setFormulaCode(designFormulaDTO.getDesignFormulaDefine().getCode());
        return vo;
    }

    public String getFormTitle() {
        return this.formTitle;
    }

    public void setFormTitle(String formTitle) {
        this.formTitle = formTitle;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getFormulaSchemeTitle() {
        return this.formulaSchemeTitle;
    }

    public void setFormulaSchemeTitle(String formulaSchemeTitle) {
        this.formulaSchemeTitle = formulaSchemeTitle;
    }

    public String getFormulaCode() {
        return this.formulaCode;
    }

    public void setFormulaCode(String formulaCode) {
        this.formulaCode = formulaCode;
    }

    public String getFormulaOld() {
        return this.formulaOld;
    }

    public void setFormulaOld(String formulaOld) {
        this.formulaOld = formulaOld;
    }

    public String getFormulaNew() {
        return this.formulaNew;
    }

    public void setFormulaNew(String formulaNew) {
        this.formulaNew = formulaNew;
    }

    public String getUpdateResult() {
        return this.updateResult;
    }

    public void setUpdateResult(String updateResult) {
        this.updateResult = updateResult;
    }

    public String getFormulaSchemeOrder() {
        return this.formulaSchemeOrder;
    }

    public void setFormulaSchemeOrder(String formulaSchemeOrder) {
        this.formulaSchemeOrder = formulaSchemeOrder;
    }
}

