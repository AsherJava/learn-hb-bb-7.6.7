/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.attachmentcheck.bean;

import com.jiuqi.nr.attachmentcheck.bean.AttachmentCheckFieldResultItem;
import com.jiuqi.nr.attachmentcheck.bean.AttachmentCheckResultItem;
import java.io.Serializable;
import java.util.List;

public class AttachmentCheckReturnInfo
implements Serializable {
    private static final long serialVersionUID = 1L;
    private int unitCount;
    private int selZBCount;
    private int errUnitCount;
    private int errFileCount;
    private List<AttachmentCheckResultItem> errItems;
    private List<AttachmentCheckResultItem> allItems;
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

    public List<AttachmentCheckResultItem> getErrItems() {
        return this.errItems;
    }

    public void setErrItems(List<AttachmentCheckResultItem> errItems) {
        this.errItems = errItems;
        if (errItems != null) {
            this.errUnitCount = errItems.size();
            int count = 0;
            for (AttachmentCheckResultItem item : errItems) {
                List<AttachmentCheckFieldResultItem> fieldItems = item.getFieldItems();
                for (AttachmentCheckFieldResultItem fieldItem : fieldItems) {
                    count += fieldItem.getErrFilesInField().size();
                }
            }
            this.errFileCount = count;
        }
    }

    public List<AttachmentCheckResultItem> getAllItems() {
        return this.allItems;
    }

    public void setAllItems(List<AttachmentCheckResultItem> allItems) {
        this.allItems = allItems;
    }

    public String getMeasureUnit() {
        return this.measureUnit;
    }

    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }
}

