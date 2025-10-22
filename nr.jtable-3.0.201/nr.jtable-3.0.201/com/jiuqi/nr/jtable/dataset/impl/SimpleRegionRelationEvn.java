/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.nr.jtable.dataset.impl;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.jtable.dataset.AbstractRegionRelationEvn;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.base.RegionData;
import com.jiuqi.nr.jtable.params.base.TableData;
import java.util.List;

public class SimpleRegionRelationEvn
extends AbstractRegionRelationEvn {
    public SimpleRegionRelationEvn(RegionData regionData, JtableContext jtableContext) {
        super(regionData, jtableContext);
    }

    @Override
    protected void initOrderFieldData() {
        List<TableData> allTables = this.jtableParamService.getAllTableInRegion(this.regionData.getKey());
        if (allTables == null || allTables.isEmpty()) {
            return;
        }
        for (TableData table : allTables) {
            if (StringUtils.isEmpty((String)this.gatherType)) {
                this.gatherType = table.getGatherType();
            }
            this.tableKeys.add(table.getTableKey());
        }
    }
}

