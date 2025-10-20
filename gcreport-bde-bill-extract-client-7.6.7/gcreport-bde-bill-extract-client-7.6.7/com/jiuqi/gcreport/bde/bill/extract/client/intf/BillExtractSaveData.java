/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.fetch.result.FetchResultDTO
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 */
package com.jiuqi.gcreport.bde.bill.extract.client.intf;

import com.jiuqi.bde.common.dto.fetch.result.FetchResultDTO;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import java.util.Map;
import java.util.Set;

public class BillExtractSaveData {
    private FetchResultDTO masterResult;
    private Map<String, FetchResultDTO> itemResult = CollectionUtils.newHashMap();

    public BillExtractSaveData() {
    }

    public BillExtractSaveData(Map<String, Object> billData) {
    }

    public FetchResultDTO getMasterResult() {
        return this.masterResult;
    }

    public void setMasterResult(FetchResultDTO masterResult) {
        this.masterResult = masterResult;
    }

    public Map<String, FetchResultDTO> getItemResult() {
        return this.itemResult;
    }

    public FetchResultDTO getItemResultByTable(String table) {
        return this.itemResult.get(table);
    }

    public void addItemResult(String table, FetchResultDTO res) {
        Assert.isNotEmpty((String)table);
        Assert.isNotNull((Object)res);
        this.itemResult.put(table, res);
    }

    public Set<String> getItemTableSet() {
        return this.itemResult.keySet();
    }

    public String toString() {
        return "BillExtractSaveData [masterResult=" + this.masterResult + ", itemResult=" + this.itemResult + "]";
    }
}

