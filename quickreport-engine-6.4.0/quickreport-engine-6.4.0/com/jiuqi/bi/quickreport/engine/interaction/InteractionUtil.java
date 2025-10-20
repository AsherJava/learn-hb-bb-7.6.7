/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.ArrayKey
 */
package com.jiuqi.bi.quickreport.engine.interaction;

import com.jiuqi.bi.quickreport.engine.build.CellValue;
import com.jiuqi.bi.quickreport.engine.build.expanding.AxisDataNode;
import com.jiuqi.bi.quickreport.engine.context.filter.IFilterDescriptor;
import com.jiuqi.bi.quickreport.engine.context.filter.ValueFilterDescriptor;
import com.jiuqi.bi.quickreport.engine.context.filter.ValuesFilterDescriptor;
import com.jiuqi.bi.quickreport.engine.parser.dataset.DSFieldNode;
import com.jiuqi.bi.quickreport.engine.result.CellRestrictionInfo;
import com.jiuqi.bi.quickreport.engine.result.CellResultInfo;
import com.jiuqi.bi.util.ArrayKey;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class InteractionUtil {
    private InteractionUtil() {
    }

    public static void initRestrictions(CellResultInfo cellResult, CellValue cellValue) {
        if (cellValue._restrictions == null) {
            return;
        }
        HashSet<ArrayKey> filtered = new HashSet<ArrayKey>();
        for (AxisDataNode dataNode : cellValue._restrictions) {
            if (dataNode.getRegion().isStatic()) continue;
            DSFieldNode keyField = dataNode.getRegion().getKeyField();
            ArrayKey key = new ArrayKey(new Object[]{keyField.getDataSet().getName(), keyField.getField().getName()});
            if (filtered.contains(key)) continue;
            CellRestrictionInfo restrictionInfo = new CellRestrictionInfo();
            restrictionInfo.setDataSetName(keyField.getDataSet().getName());
            restrictionInfo.setFieldName(keyField.getField().getName());
            restrictionInfo.setValue(dataNode.getKeyValue());
            cellResult.getRestrictions().add(restrictionInfo);
            filtered.add(key);
        }
        for (AxisDataNode dataNode : cellValue._restrictions) {
            ArrayList<IFilterDescriptor> filters = new ArrayList<IFilterDescriptor>();
            dataNode.getRegion().appendNexFilters(filters);
            for (IFilterDescriptor filter : filters) {
                List<Object> value;
                ArrayKey key = new ArrayKey(new Object[]{filter.getDataSetName(), filter.getFieldName()});
                if (filtered.contains(key)) continue;
                if (filter instanceof ValueFilterDescriptor) {
                    value = ((ValueFilterDescriptor)filter).getValue();
                } else {
                    if (!(filter instanceof ValuesFilterDescriptor)) continue;
                    value = ((ValuesFilterDescriptor)filter).getValues();
                }
                CellRestrictionInfo restrictionInfo = new CellRestrictionInfo();
                restrictionInfo.setDataSetName(filter.getDataSetName());
                restrictionInfo.setFieldName(filter.getFieldName());
                restrictionInfo.setValue(value);
                cellResult.getRestrictions().add(restrictionInfo);
                filtered.add(key);
            }
        }
    }
}

