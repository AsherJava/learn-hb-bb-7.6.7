/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 */
package nr.single.map.data.facade;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.Serializable;
import java.util.List;
import nr.single.map.data.facade.SingleFileEnumItem;
import nr.single.map.data.internal.SingleFileEnumInfoImpl;

@JsonDeserialize(as=SingleFileEnumInfoImpl.class)
public interface SingleFileEnumInfo
extends Serializable {
    public String getEnumCode();

    public void setEnumItems(List<SingleFileEnumItem> var1);

    public void setEnumCode(String var1);

    public String getEnumTitle();

    public void setEnumTitle(String var1);

    public String getNetTableCode();

    public void setNetTableCode(String var1);

    public String getNetTableKey();

    public void setNetTableKey(String var1);

    public List<SingleFileEnumItem> getEnumItems();

    public SingleFileEnumItem getNewEnumItem();

    public String getItemCodeFromNet(String var1);

    public String getNetItemCodeFromItem(String var1);

    public void copyFrom(SingleFileEnumInfo var1);
}

