/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonSubTypes
 *  com.fasterxml.jackson.annotation.JsonSubTypes$Type
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$As
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 */
package nr.single.map.data.facade;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.io.Serializable;
import nr.single.map.data.facade.SingleFileRegionInfo;
import nr.single.map.data.internal.SingleFileFmdmInfoImpl;
import nr.single.map.data.internal.SingleFileTableInfoImpl;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="typeName")
@JsonSubTypes(value={@JsonSubTypes.Type(value=SingleFileTableInfoImpl.class, name="SingleFileTableInfoImpl"), @JsonSubTypes.Type(value=SingleFileFmdmInfoImpl.class, name="SingleFileFmdmInfoImpl")})
public interface SingleFileTableInfo
extends Serializable {
    public int getSingleTableType();

    public void setSingleTableType(int var1);

    public String getSingleTableCode();

    public void setSingleTableCode(String var1);

    public String getSingleTableTitle();

    public void setSingleTableTitle(String var1);

    public void setRegion(SingleFileRegionInfo var1);

    public SingleFileRegionInfo getRegion();

    public String getNetFormCode();

    public void setNetFormCode(String var1);

    public String getNetFormKey();

    public void setNetFormKey(String var1);

    public void copyFrom(SingleFileTableInfo var1);
}

