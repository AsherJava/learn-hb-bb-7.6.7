/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.nrdx.adapter.param.common.NrdxParamNodeType
 */
package com.jiuqi.nr.nrdx.param.dto;

import com.jiuqi.nr.nrdx.adapter.param.common.NrdxParamNodeType;
import com.jiuqi.nr.nrdx.param.dto.ParamDTO;
import java.util.List;
import java.util.Map;

public class FormulaParamDTO
extends ParamDTO {
    private List<String> formulaSchemes;
    private String formulaScheme;
    boolean all;
    Map<String, List<String>> formMap;

    public FormulaParamDTO() {
    }

    public FormulaParamDTO(String formulaScheme, List<String> paramKeys, NrdxParamNodeType resourceType) {
        super(paramKeys, resourceType);
        this.formulaScheme = formulaScheme;
    }

    public List<String> getFormulaSchemes() {
        return this.formulaSchemes;
    }

    public void setFormulaSchemes(List<String> formulaSchemes) {
        this.formulaSchemes = formulaSchemes;
    }

    public String getFormulaScheme() {
        return this.formulaScheme;
    }

    public void setFormulaScheme(String formulaScheme) {
        this.formulaScheme = formulaScheme;
    }

    public boolean isAll() {
        return this.all;
    }

    public void setAll(boolean all) {
        this.all = all;
    }

    public Map<String, List<String>> getFormMap() {
        return this.formMap;
    }

    public void setFormMap(Map<String, List<String>> formMap) {
        this.formMap = formMap;
    }
}

