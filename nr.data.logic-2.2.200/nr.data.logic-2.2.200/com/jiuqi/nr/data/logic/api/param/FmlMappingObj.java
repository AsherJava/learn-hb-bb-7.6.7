/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.api.param;

import java.util.Arrays;
import org.apache.logging.log4j.util.Strings;

public class FmlMappingObj {
    private String formulaKey;
    private String formulaFormKey;
    private int globRow = -1;
    private int globCol = -1;
    private int hashCode = 0;

    public FmlMappingObj() {
    }

    public FmlMappingObj(String formulaKey, String formulaFormKey) {
        this.formulaFormKey = formulaFormKey;
        this.formulaKey = formulaKey;
    }

    public FmlMappingObj(String formulaKey, String formulaFormKey, int globRow, int globCol) {
        this.formulaKey = formulaKey;
        this.formulaFormKey = formulaFormKey;
        this.globRow = globRow;
        this.globCol = globCol;
    }

    public String getFormulaFormKey() {
        return this.formulaFormKey;
    }

    public void setFormulaFormKey(String formulaFormKey) {
        this.formulaFormKey = formulaFormKey;
    }

    public String getFormulaKey() {
        return this.formulaKey;
    }

    public void setFormulaKey(String formulaKey) {
        this.formulaKey = formulaKey;
    }

    public int getGlobCol() {
        return this.globCol;
    }

    public void setGlobCol(int globCol) {
        this.globCol = globCol;
    }

    public int getGlobRow() {
        return this.globRow;
    }

    public void setGlobRow(int globRow) {
        this.globRow = globRow;
    }

    public int hashCode() {
        if (this.hashCode == 0) {
            this.hashCode = this.toString().hashCode();
        }
        return this.hashCode;
    }

    public String toString() {
        return Strings.join(Arrays.asList(this.getFormulaKey(), this.getFormulaFormKey(), String.valueOf(this.getGlobRow()), String.valueOf(this.getGlobCol())), ';');
    }
}

