/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIdentityInfo
 *  com.fasterxml.jackson.annotation.ObjectIdGenerators$IntSequenceGenerator
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 */
package nr.single.map.data.facade;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.Serializable;
import java.util.List;
import nr.single.map.data.facade.SingleFileFieldInfo;
import nr.single.map.data.internal.SingleFileRegionInfoImpl;

@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="id")
@JsonDeserialize(as=SingleFileRegionInfoImpl.class)
public interface SingleFileRegionInfo
extends Serializable {
    public List<SingleFileFieldInfo> getFields();

    public SingleFileFieldInfo getNewField();

    public void setFeilds(List<SingleFileFieldInfo> var1);

    public List<SingleFileRegionInfo> getSubRegions();

    public SingleFileRegionInfo getNewSubRegion();

    public void setSubRegions(List<SingleFileRegionInfo> var1);

    public boolean getIsFixTable();

    public void setFixTable(boolean var1);

    public List<String> getFloatCodes();

    public void setFloatCodes(List<String> var1);

    public int getFloatingIndex();

    public void setFloatingIndex(int var1);

    public String getNetRegionKey();

    public void setNetRegionKey(String var1);

    public void copyFrom(SingleFileRegionInfo var1);
}

