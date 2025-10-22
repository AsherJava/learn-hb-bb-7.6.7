/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.finalaccountsaudit.blobfilesizecheck.bean;

import com.jiuqi.nr.finalaccountsaudit.blobfilesizecheck.bean.BlobFileSizeCheckFieldResultItem;
import com.jiuqi.nr.finalaccountsaudit.blobfilesizecheck.bean.BlobFileSizeCheckResultItem;
import java.io.Serializable;
import java.util.List;

public class BlobFileSizeCheckReturnInfo
implements Serializable {
    private static final long serialVersionUID = 1L;
    private int unitCount;
    private int selZBCount;
    private int errUnitCount;
    private int errFileCount;
    private List<BlobFileSizeCheckResultItem> errItems;
    private List<BlobFileSizeCheckResultItem> allItems;
    private String measureUnit = "Mb";

    public int getUnitCount() {
        return this.unitCount;
    }

    public void setUnitCount(int unitCount) {
        this.unitCount = unitCount;
    }

    public int getSelZBCount() {
        return this.selZBCount;
    }

    public void setSelZBCount(int selZBCount) {
        this.selZBCount = selZBCount;
    }

    public int getErrUnitCount() {
        return this.errUnitCount;
    }

    public void setErrUnitCount(int errUnitCount) {
        this.errUnitCount = errUnitCount;
    }

    public int getErrFileCount() {
        return this.errFileCount;
    }

    public void setErrFileCount(int errFileCount) {
        this.errFileCount = errFileCount;
    }

    public List<BlobFileSizeCheckResultItem> getErrItems() {
        return this.errItems;
    }

    public void setErrItems(List<BlobFileSizeCheckResultItem> errItems) {
        this.errItems = errItems;
        if (errItems != null) {
            this.errUnitCount = errItems.size();
            int count = 0;
            for (BlobFileSizeCheckResultItem item : errItems) {
                List<BlobFileSizeCheckFieldResultItem> fieldItems = item.getFieldItems();
                for (BlobFileSizeCheckFieldResultItem fieldItem : fieldItems) {
                    count += fieldItem.getErrFilesInField().size();
                }
            }
            this.errFileCount = count;
        }
    }

    public List<BlobFileSizeCheckResultItem> getAllItems() {
        return this.allItems;
    }

    public void setAllItems(List<BlobFileSizeCheckResultItem> allItems) {
        this.allItems = allItems;
    }

    public String getMeasureUnit() {
        return this.measureUnit;
    }

    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }
}

