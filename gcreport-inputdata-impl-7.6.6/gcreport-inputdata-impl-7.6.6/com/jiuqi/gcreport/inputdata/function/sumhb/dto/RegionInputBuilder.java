/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.gcreport.inputdata.function.sumhb.dto;

import com.jiuqi.gcreport.inputdata.function.sumhb.dto.RegionInputItem;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.util.CollectionUtils;

public class RegionInputBuilder {
    public static Map<String, RegionInputItem> build(List<Map<String, Object>> inputItems, List<ColumnModelDefine> allFields) {
        HashMap<String, RegionInputItem> regionInputItems = new HashMap<String, RegionInputItem>();
        if (CollectionUtils.isEmpty(inputItems)) {
            return regionInputItems;
        }
        for (Map<String, Object> inputItem : inputItems) {
            String regionId = String.valueOf(inputItem.get("GROUPXYZ"));
            RegionInputItem regionInputItem = (RegionInputItem)regionInputItems.get(regionId);
            if (regionInputItem == null) {
                regionInputItem = new RegionInputItem(regionId, allFields);
                regionInputItems.put(regionId, regionInputItem);
            }
            regionInputItem.addItem(inputItem);
        }
        return regionInputItems;
    }
}

