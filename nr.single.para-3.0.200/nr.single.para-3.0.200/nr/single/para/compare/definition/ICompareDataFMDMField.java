/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.compare.definition;

import nr.single.para.compare.definition.ICompareData;
import nr.single.para.compare.definition.common.CompareTableType;
import nr.single.para.compare.definition.common.FieldUseType;

public interface ICompareDataFMDMField
extends ICompareData {
    public FieldUseType getSingleUseType();

    public CompareTableType getOwnerTableType();

    public String getOwnerTableKey();
}

