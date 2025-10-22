/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 */
package nr.single.map.data.facade;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.Serializable;
import nr.single.map.data.internal.SingleFileEnumItemImpl;

@JsonDeserialize(as=SingleFileEnumItemImpl.class)
public interface SingleFileEnumItem
extends Serializable {
    public String getItemCode();

    public void setItemCode(String var1);

    public String getItemTitle();

    public void setItemTitle(String var1);

    public String getNetItemCode();

    public void setNetItemCode(String var1);

    public String getNetItemTitle();

    public void setNetItemTitle(String var1);

    public void copyFrom(SingleFileEnumItem var1);
}

