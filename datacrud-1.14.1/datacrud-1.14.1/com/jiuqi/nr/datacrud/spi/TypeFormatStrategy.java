/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.fmdm.IFMDMAttribute
 */
package com.jiuqi.nr.datacrud.spi;

import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.nr.datacrud.IMetaData;
import com.jiuqi.nr.datacrud.IRowData;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.fmdm.IFMDMAttribute;

public interface TypeFormatStrategy {
    default public void setRowKey(DimensionCombination masterKey) {
    }

    default public void setRowData(IRowData rowData) {
    }

    public String format(IMetaData var1, AbstractData var2);

    public String format(DataLinkDefine var1, DataField var2, AbstractData var3);

    public String format(DataLinkDefine var1, IFMDMAttribute var2, AbstractData var3);
}

