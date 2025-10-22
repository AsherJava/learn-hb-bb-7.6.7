/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.compare.definition;

import nr.single.para.compare.definition.ICompareData;
import nr.single.para.compare.definition.common.CompareChangeType;
import nr.single.para.compare.definition.common.CompareContextType;

public interface ICompareDataEnum
extends ICompareData {
    public CompareContextType getCompareType();

    public Integer getSingleCodeLen();

    public Integer getSingleStructType();

    public String getSingleLevelCode();

    public Boolean getSingleCodeFix();

    public Integer getNetCodeLen();

    public Integer getNetStructType();

    public String getNetLevelCode();

    public Boolean getNetCodeFix();

    public CompareChangeType getItemChangeType();
}

