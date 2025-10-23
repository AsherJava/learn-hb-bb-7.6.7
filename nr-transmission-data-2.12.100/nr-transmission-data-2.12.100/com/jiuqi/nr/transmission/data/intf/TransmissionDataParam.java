/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.transmission.data.intf;

import com.jiuqi.nr.transmission.data.dto.SyncSchemeParamDTO;
import java.io.File;
import java.util.Date;

public class TransmissionDataParam {
    private File file;
    private Date startTime;
    private SyncSchemeParamDTO syncSchemeParamDTO;

    public File getFile() {
        return this.file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Date getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public SyncSchemeParamDTO getSyncSchemeParamDTO() {
        return this.syncSchemeParamDTO;
    }

    public void setSyncSchemeParamDTO(SyncSchemeParamDTO syncSchemeParamDTO) {
        this.syncSchemeParamDTO = syncSchemeParamDTO;
    }
}

