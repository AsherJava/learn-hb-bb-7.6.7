/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.jiuqi.va.biz.intf.model.ModelDefine
 *  com.jiuqi.va.biz.ruler.impl.FormulaImpl
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.va.bill.domain.debug;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.va.bill.domain.debug.BillFormulaDebugInfoVO;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.ruler.impl.FormulaImpl;
import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.List;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class BillFormulaDebugVO
extends TenantDO {
    private String defineCode;
    private List<FormulaImpl> formulas;
    private BillFormulaDebugInfoVO infoVO;
    private ModelDefine modelDefine;
    private String title;
    private String formulaType;
    private List<BillFormulaDebugVO> children;

    public List<BillFormulaDebugVO> getChildren() {
        return this.children;
    }

    public void setChildren(List<BillFormulaDebugVO> children) {
        this.children = children;
    }

    public BillFormulaDebugVO() {
    }

    public BillFormulaDebugVO(String defineCode, BillFormulaDebugInfoVO infoVO, String title, String formulaType) {
        this.defineCode = defineCode;
        this.infoVO = infoVO;
        this.title = title;
        this.formulaType = formulaType;
    }

    public String getDefineCode() {
        return this.defineCode;
    }

    public void setDefineCode(String defineCode) {
        this.defineCode = defineCode;
    }

    public List<FormulaImpl> getFormulas() {
        return this.formulas;
    }

    public void setFormulas(List<FormulaImpl> formulas) {
        this.formulas = formulas;
    }

    public BillFormulaDebugInfoVO getInfoVO() {
        return this.infoVO;
    }

    public void setInfoVO(BillFormulaDebugInfoVO infoVO) {
        this.infoVO = infoVO;
    }

    public ModelDefine getModelDefine() {
        return this.modelDefine;
    }

    public void setModelDefine(ModelDefine modelDefine) {
        this.modelDefine = modelDefine;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFormulaType() {
        return this.formulaType;
    }

    public void setFormulaType(String formulaType) {
        this.formulaType = formulaType;
    }
}

