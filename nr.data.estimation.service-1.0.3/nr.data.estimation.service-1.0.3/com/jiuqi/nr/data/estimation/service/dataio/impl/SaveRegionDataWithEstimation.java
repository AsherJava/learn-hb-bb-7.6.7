/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.data.estimation.common.StringLogger
 *  com.jiuqi.nr.data.estimation.storage.entity.IEstimationScheme
 */
package com.jiuqi.nr.data.estimation.service.dataio.impl;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.data.estimation.common.StringLogger;
import com.jiuqi.nr.data.estimation.service.dataio.CheckPolicy;
import com.jiuqi.nr.data.estimation.service.dataio.IDataRegionTableData;
import com.jiuqi.nr.data.estimation.service.dataio.IRegionTableModelSub;
import com.jiuqi.nr.data.estimation.service.dataio.impl.OverRegionDataWithEstimation;
import com.jiuqi.nr.data.estimation.storage.entity.IEstimationScheme;

public class SaveRegionDataWithEstimation
extends OverRegionDataWithEstimation {
    public SaveRegionDataWithEstimation(IEstimationScheme estimationScheme, StringLogger logger) {
        super(estimationScheme, logger);
    }

    @Override
    protected void dealFloatRegionValues(IRegionTableModelSub regionTableModel, IDataRegionTableData dataRegionValueSet, DimensionValueSet pubColumnValueSet) throws Exception {
        if (!dataRegionValueSet.isEmpty(CheckPolicy.NewSet)) {
            this.addOrUpdateTableDataRows(regionTableModel, dataRegionValueSet, pubColumnValueSet);
        }
        if (!dataRegionValueSet.isEmpty(CheckPolicy.UpdateSet)) {
            this.updateTableDataRows(regionTableModel, dataRegionValueSet, pubColumnValueSet);
        }
        if (!dataRegionValueSet.isEmpty(CheckPolicy.DeleteSet)) {
            this.deleteTableDataRows(regionTableModel, dataRegionValueSet, pubColumnValueSet);
        }
    }
}

