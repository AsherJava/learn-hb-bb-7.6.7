/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 */
package com.jiuqi.nr.data.estimation.service.dataio.model;

import com.jiuqi.nr.data.estimation.service.dataio.IDataRegionInfo;
import com.jiuqi.nr.data.estimation.service.dataio.IRegionTableModel;
import com.jiuqi.nr.data.estimation.service.dataio.model.DataRegionInfoUpper;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import java.util.ArrayList;
import java.util.List;

public class DataRegionInfo
extends DataRegionInfoUpper
implements IDataRegionInfo {
    private List<IRegionTableModel> regionTableModels = new ArrayList<IRegionTableModel>();

    public DataRegionInfo(DataRegionDefine dataRegion) {
        super(dataRegion);
    }

    @Override
    public List<IRegionTableModel> getRegionTableModels() {
        return this.regionTableModels;
    }

    public void setRegionTableModels(List<IRegionTableModel> regionTableModels) {
        this.regionTableModels = regionTableModels;
    }
}

