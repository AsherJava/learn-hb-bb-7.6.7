/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.datamapping.client.dto;

import com.jiuqi.dc.datamapping.client.dto.DataRefDTO;

public class RefChangePairDTO {
    private DataRefDTO oldRefData;
    private DataRefDTO newRefData;

    public RefChangePairDTO() {
    }

    public RefChangePairDTO(DataRefDTO oldRefData, DataRefDTO newRefData) {
        this.oldRefData = oldRefData;
        this.newRefData = newRefData;
    }

    public DataRefDTO getNewRefData() {
        return this.newRefData;
    }

    public void setNewRefData(DataRefDTO newRefData) {
        this.newRefData = newRefData;
    }

    public DataRefDTO getOldRefData() {
        return this.oldRefData;
    }

    public void setOldRefData(DataRefDTO oldRefData) {
        this.oldRefData = oldRefData;
    }
}

