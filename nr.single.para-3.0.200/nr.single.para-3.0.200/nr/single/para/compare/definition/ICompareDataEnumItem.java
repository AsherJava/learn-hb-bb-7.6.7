/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.compare.definition;

import nr.single.para.compare.definition.ICompareData;

public interface ICompareDataEnumItem
extends ICompareData {
    public String getEnumCompareKey();

    public String getSingleParentCode();

    public String getNetParentCode();
}

