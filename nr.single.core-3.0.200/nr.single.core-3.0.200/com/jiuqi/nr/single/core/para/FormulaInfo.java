/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.nr.single.core.para;

import com.jiuqi.bi.util.StringUtils;

public class FormulaInfo {
    private String code;
    private String expression;
    private String description;
    private String type;
    private String calcType;
    private String userLevel;
    private String errorData;
    private String adjustCells;

    public final String getCode() {
        return this.code;
    }

    public final void setCode(String code_0) {
        this.code = code_0;
    }

    public final String getExpression() {
        return this.expression;
    }

    public final void setExpression(String expression_0) {
        this.expression = expression_0;
    }

    public final String getDescription() {
        return this.description;
    }

    public final void setDescription(String description_0) {
        this.description = description_0;
    }

    public final String getType() {
        return this.type;
    }

    public final void setType(String type_0) {
        this.type = type_0;
    }

    public final String getCalcType() {
        return this.calcType;
    }

    public final void setCalcType(String calcType_0) {
        this.calcType = calcType_0;
    }

    public final String getUserLevel() {
        return this.userLevel;
    }

    public final void setUserLevel(String userLevel_0) {
        this.userLevel = userLevel_0;
    }

    public final boolean isCaclFormula() {
        return this.calcType.length() == 4 && this.calcType.charAt(1) == '1' || this.calcType.charAt(2) == '1';
    }

    public final boolean isAutoCaclFormula() {
        return this.calcType.length() == 4 && this.calcType.charAt(1) == '1';
    }

    public final boolean isCheckFormula() {
        return !StringUtils.isEmpty((String)this.calcType) && this.calcType.charAt(0) == '1';
    }

    public final boolean isCheckBalance() {
        return !StringUtils.isEmpty((String)this.calcType) && this.calcType.charAt(3) == '1';
    }

    public final String getErrorData() {
        return this.errorData;
    }

    public final void setErrorData(String errorData_0) {
        this.errorData = errorData_0;
    }

    public final String getAdjustCells() {
        return this.adjustCells;
    }

    public final void setAdjustCells(String adjustCells_0) {
        this.adjustCells = adjustCells_0;
    }
}

