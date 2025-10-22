/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.estimation.service.dataio;

import com.jiuqi.nr.data.estimation.service.dataio.IRegionTableModel;
import com.jiuqi.nr.data.estimation.service.dataio.ITableBizKeyColumn;
import java.util.List;

public interface IRegionTableModelSub
extends IRegionTableModel {
    public List<ITableBizKeyColumn> getOtherKeyColumns();
}

