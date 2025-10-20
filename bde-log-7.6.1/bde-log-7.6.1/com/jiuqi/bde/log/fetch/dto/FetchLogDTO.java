/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.log.fetch.dto;

import com.jiuqi.bde.log.fetch.dto.FetchItemLogDTO;
import java.util.ArrayList;
import java.util.List;

public class FetchLogDTO {
    private int fetchSize = 0;
    private List<FetchItemLogDTO> failedItemList = new ArrayList<FetchItemLogDTO>();

    public FetchLogDTO() {
    }

    public FetchLogDTO(int fetchSize) {
        this.fetchSize = fetchSize;
    }

    public int getFetchSize() {
        return this.fetchSize;
    }

    public void setFetchSize(int fetchSize) {
        this.fetchSize = fetchSize;
    }

    public List<FetchItemLogDTO> getFailedItemList() {
        return this.failedItemList;
    }

    public void addFailedItem(FetchItemLogDTO failedItem) {
        this.failedItemList.add(failedItem);
    }

    public void addFailedItems(List<FetchItemLogDTO> failedItems) {
        this.failedItemList.addAll(failedItems);
    }

    public void setFailedItemList(List<FetchItemLogDTO> failedItemList) {
        this.failedItemList = failedItemList;
    }

    public String toString() {
        return "FetchLogDTO [fetchSize=" + this.fetchSize + ", failedItemList=" + this.failedItemList + "]";
    }
}

