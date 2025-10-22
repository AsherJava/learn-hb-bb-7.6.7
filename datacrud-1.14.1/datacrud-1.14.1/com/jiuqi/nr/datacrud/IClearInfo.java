/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 */
package com.jiuqi.nr.datacrud;

import com.jiuqi.nr.datacrud.IRegionInfo;
import com.jiuqi.nr.datacrud.spi.RowFilter;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import java.util.Iterator;

public interface IClearInfo
extends IRegionInfo {
    public DimensionCollection getDimensionCollection();

    public Iterator<RowFilter> rowFilterItr();
}

