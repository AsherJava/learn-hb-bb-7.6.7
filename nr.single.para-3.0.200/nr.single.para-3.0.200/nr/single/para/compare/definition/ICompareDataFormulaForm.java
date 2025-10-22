/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.compare.definition;

import nr.single.para.compare.definition.ICompareData;
import nr.single.para.compare.definition.common.CompareChangeType;

public interface ICompareDataFormulaForm
extends ICompareData {
    public CompareChangeType getItemChangeType();

    public String getFmlSchemeCompareKey();
}

