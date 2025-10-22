/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.compare.definition;

import java.io.Serializable;
import java.time.Instant;
import nr.single.para.compare.definition.common.CompareChangeType;
import nr.single.para.compare.definition.common.CompareDataType;
import nr.single.para.compare.definition.common.CompareUpdateType;

public interface ICompareData
extends Serializable {
    public String getKey();

    public String getInfoKey();

    public CompareDataType getDataType();

    public String getSingleCode();

    public String getSingleTitle();

    public String getSingleData();

    public String getMatchKey();

    public String getNetKey();

    public String getNetCode();

    public String getNetTitle();

    public String getNetData();

    public CompareChangeType getChangeType();

    public CompareUpdateType getUpdateType();

    public Instant getUpdateTime();

    public String getStringValue(String var1);

    public Integer getIntValue(String var1);

    public Object getObjectValue(String var1);
}

