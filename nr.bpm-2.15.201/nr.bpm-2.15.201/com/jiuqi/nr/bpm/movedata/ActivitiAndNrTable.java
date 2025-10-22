/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.movedata;

import com.jiuqi.nr.bpm.common.UploadRecordNew;
import com.jiuqi.nr.bpm.common.UploadStateNew;
import com.jiuqi.nr.bpm.movedata.ActivitiTable;
import java.util.List;

public class ActivitiAndNrTable {
    private UploadStateNew uploadStateNew;
    private List<UploadRecordNew> uploadRecordNew;
    private ActivitiTable activitiTable;

    public UploadStateNew getUploadStateNew() {
        return this.uploadStateNew;
    }

    public void setUploadStateNew(UploadStateNew uploadStateNew) {
        this.uploadStateNew = uploadStateNew;
    }

    public ActivitiTable getActivitiTable() {
        return this.activitiTable;
    }

    public void setActivitiTable(ActivitiTable activitiTable) {
        this.activitiTable = activitiTable;
    }

    public List<UploadRecordNew> getUploadRecordNew() {
        return this.uploadRecordNew;
    }

    public void setUploadRecordNew(List<UploadRecordNew> uploadRecordNew) {
        this.uploadRecordNew = uploadRecordNew;
    }
}

