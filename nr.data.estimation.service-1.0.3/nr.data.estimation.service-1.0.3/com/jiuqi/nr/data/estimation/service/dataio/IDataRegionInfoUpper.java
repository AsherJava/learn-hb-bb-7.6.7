/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 */
package com.jiuqi.nr.data.estimation.service.dataio;

import com.jiuqi.nr.data.estimation.service.dataio.ITableBizKeyColumn;
import com.jiuqi.nr.data.estimation.service.dataio.ITableCellLinkColumn;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import java.util.List;

public interface IDataRegionInfoUpper {
    public DataRegionDefine getDataRegion();

    public List<ITableBizKeyColumn> getDimensionColumns();

    public List<ITableBizKeyColumn> getBizKeyColumns();

    public List<ITableBizKeyColumn> getBuildColumns();

    public List<ITableCellLinkColumn> getCellLinksColumns();
}

