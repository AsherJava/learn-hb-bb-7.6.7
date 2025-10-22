/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.estimation.service.dataio;

import com.jiuqi.nr.data.estimation.service.dataio.IDataRegionInfoUpper;
import com.jiuqi.nr.data.estimation.service.dataio.IRegionTableModelSub;
import com.jiuqi.nr.data.estimation.service.dataio.ITableBizKeyColumn;
import java.util.List;

public interface IDataRegionInfoSub
extends IDataRegionInfoUpper {
    public List<ITableBizKeyColumn> getOtherKeyColumns();

    public List<IRegionTableModelSub> getRegionTableModels();
}

