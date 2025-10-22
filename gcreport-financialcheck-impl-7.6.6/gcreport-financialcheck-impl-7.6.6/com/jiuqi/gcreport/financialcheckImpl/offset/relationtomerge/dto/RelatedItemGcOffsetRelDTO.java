/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.financialcheckcore.offset.entity.GcOffsetRelatedItemEO
 */
package com.jiuqi.gcreport.financialcheckImpl.offset.relationtomerge.dto;

import com.jiuqi.gcreport.financialcheckcore.offset.entity.GcOffsetRelatedItemEO;

public class RelatedItemGcOffsetRelDTO
extends GcOffsetRelatedItemEO {
    private String updateTime;
    private Long recordTimestamp;
    private String chkState;

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public void setRecordTimestamp(Long recordTimestamp) {
        this.recordTimestamp = recordTimestamp;
    }

    public boolean notModified(String newUpdateTime, Long newRecordTimestamp) {
        return this.equalsRecordTimestamp(newRecordTimestamp) && this.equalsUpdateTime(newUpdateTime);
    }

    private boolean equalsRecordTimestamp(Long newRecordTimestamp) {
        if (null == newRecordTimestamp) {
            return null == this.recordTimestamp;
        }
        return newRecordTimestamp.equals(this.recordTimestamp);
    }

    private boolean equalsUpdateTime(String newUpdateTime) {
        if (null == newUpdateTime) {
            return null == this.updateTime;
        }
        return newUpdateTime.equals(this.updateTime);
    }

    public void setChkState(String chkState) {
        this.chkState = chkState;
    }

    public String getChkState() {
        return this.chkState;
    }
}

