/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.estimation.service.dataio;

import com.jiuqi.nr.data.estimation.service.dataio.IDataRegionInfoUpper;
import com.jiuqi.nr.data.estimation.service.dataio.IRegionTableModel;
import java.util.List;

public interface IDataRegionInfo
extends IDataRegionInfoUpper {
    public List<IRegionTableModel> getRegionTableModels();
}

