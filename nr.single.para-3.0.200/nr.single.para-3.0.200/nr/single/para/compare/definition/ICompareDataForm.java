/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.compare.definition;

import nr.single.para.compare.definition.ICompareData;
import nr.single.para.compare.definition.common.CompareContextType;

public interface ICompareDataForm
extends ICompareData {
    public CompareContextType getCompareType();

    public boolean isIniFieldMap();
}

