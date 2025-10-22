/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.compare.definition;

import nr.single.para.compare.definition.ICompareData;

public interface ICompareDataPrintItem
extends ICompareData {
    public String getSinglePrintScheme();

    public String getSingleFormCode();

    public String getSingleFormTitile();

    public String getNetPrintScheme();

    public String getNetPrintSchemeKey();

    public String getNetFormKey();

    public String getNetFormCode();

    public String getNetFormTitle();

    public String getSchemeCompareKey();
}

