/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.api.file.dto;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

public class ExportInfoDTO
implements Serializable {
    private static final long serialVersionUID = 1L;
    private ConcurrentHashMap<String, ConcurrentHashMap<String, String>> exportRecord;

    public ExportInfoDTO(ConcurrentHashMap<String, ConcurrentHashMap<String, String>> exportRecord) {
        this.exportRecord = exportRecord;
    }

    public ConcurrentHashMap<String, ConcurrentHashMap<String, String>> getExportRecord() {
        return this.exportRecord;
    }

    public void setExportRecord(ConcurrentHashMap<String, ConcurrentHashMap<String, String>> exportRecord) {
        this.exportRecord = exportRecord;
    }
}

