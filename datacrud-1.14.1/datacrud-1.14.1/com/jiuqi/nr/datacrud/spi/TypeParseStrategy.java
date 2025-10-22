/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.fmdm.IFMDMAttribute
 */
package com.jiuqi.nr.datacrud.spi;

import com.jiuqi.nr.datacrud.ParseReturnRes;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.fmdm.IFMDMAttribute;

public interface TypeParseStrategy {
    default public void setRowKey(DimensionCombination masterKey) {
    }

    public ParseReturnRes checkParse(DataLinkDefine var1, DataField var2, Object var3);

    public ParseReturnRes checkParse(DataLinkDefine var1, IFMDMAttribute var2, Object var3);
}

