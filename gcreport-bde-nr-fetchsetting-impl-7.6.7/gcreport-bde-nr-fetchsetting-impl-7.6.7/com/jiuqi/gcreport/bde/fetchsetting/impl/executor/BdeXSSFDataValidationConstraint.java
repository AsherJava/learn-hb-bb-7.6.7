/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.executor;

import org.apache.poi.xssf.usermodel.XSSFDataValidationConstraint;

public class BdeXSSFDataValidationConstraint
extends XSSFDataValidationConstraint {
    public BdeXSSFDataValidationConstraint(String[] explicitListOfValues) {
        super(explicitListOfValues);
    }

    @Override
    public void validate() {
        if (this.getValidationType() == 3) {
            if (BdeXSSFDataValidationConstraint.isFormulaEmpty(this.getFormula1())) {
                throw new IllegalArgumentException("A valid formula or a list of values must be specified for list validation.");
            }
            if (this.getFormula1().length() > 1000) {
                throw new IllegalArgumentException("A valid formula or a list of values must be less than or equal to 255 characters (including separators).");
            }
            return;
        }
        super.validate();
    }
}

