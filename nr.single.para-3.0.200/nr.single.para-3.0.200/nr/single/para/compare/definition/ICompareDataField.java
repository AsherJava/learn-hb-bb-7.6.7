/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.compare.definition;

import nr.single.para.compare.definition.ICompareData;

public interface ICompareDataField
extends ICompareData {
    public String getFormCompareKey();

    public int getSinglePosX();

    public int getSinglePosY();

    public int getSingleFloatingIndex();

    public Integer getSingleFloatingId();

    public String getSingleMatchTitle();

    public int getNetPosX();

    public int getNetPosY();

    public String getNetMatchTitle();

    public String getNetLinkKey();

    public String getNetRegionKey();

    public String getNetFormKey();

    public String getNetTableKey();
}

