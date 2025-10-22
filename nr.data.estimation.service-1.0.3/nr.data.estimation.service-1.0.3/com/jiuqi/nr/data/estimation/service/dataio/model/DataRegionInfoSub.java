/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 */
package com.jiuqi.nr.data.estimation.service.dataio.model;

import com.jiuqi.nr.data.estimation.service.dataio.IDataRegionInfoSub;
import com.jiuqi.nr.data.estimation.service.dataio.IRegionTableModelSub;
import com.jiuqi.nr.data.estimation.service.dataio.ITableBizKeyColumn;
import com.jiuqi.nr.data.estimation.service.dataio.model.DataRegionInfoUpper;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import java.util.ArrayList;
import java.util.List;

public class DataRegionInfoSub
extends DataRegionInfoUpper
implements IDataRegionInfoSub {
    private List<ITableBizKeyColumn> otherKeyColumns = new ArrayList<ITableBizKeyColumn>();
    private List<IRegionTableModelSub> regionTableModels = new ArrayList<IRegionTableModelSub>();

    public DataRegionInfoSub(DataRegionDefine dataRegion) {
        super(dataRegion);
    }

    @Override
    public List<ITableBizKeyColumn> getOtherKeyColumns() {
        return this.otherKeyColumns;
    }

    public void setOtherKeyColumns(List<ITableBizKeyColumn> otherKeyColumns) {
        this.otherKeyColumns = otherKeyColumns;
    }

    @Override
    public List<IRegionTableModelSub> getRegionTableModels() {
        return this.regionTableModels;
    }

    public void setRegionTableModels(List<IRegionTableModelSub> regionTableModels) {
        this.regionTableModels = regionTableModels;
    }
}

