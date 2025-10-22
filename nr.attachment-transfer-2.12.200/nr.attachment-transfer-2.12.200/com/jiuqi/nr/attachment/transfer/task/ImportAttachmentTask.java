/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.attachment.transfer.task;

import com.jiuqi.nr.attachment.transfer.dto.ImportParamDTO;
import com.jiuqi.nr.attachment.transfer.dto.ImportRecordDTO;
import com.jiuqi.nr.attachment.transfer.task.AbstractTaskProvider;
import com.jiuqi.nr.attachment.transfer.task.ImportTask;
import com.jiuqi.nr.attachment.transfer.task.TransferTask;
import java.util.ArrayList;
import java.util.List;

public class ImportAttachmentTask
extends AbstractTaskProvider {
    private final ImportParamDTO importParamDTO;
    private final List<ImportRecordDTO> importRecords;
    private List<TransferTask> list;

    public ImportAttachmentTask(ImportParamDTO importParamDTO, List<ImportRecordDTO> importRecords) {
        this.importParamDTO = importParamDTO;
        this.importRecords = importRecords;
    }

    protected void build() {
        this.list = new ArrayList<TransferTask>(this.importRecords.size());
        for (ImportRecordDTO record : this.importRecords) {
            ImportTask task = new ImportTask(record, this.importParamDTO);
            this.list.add(task);
        }
    }

    @Override
    protected List<TransferTask> getItems() {
        if (this.list == null) {
            this.build();
        }
        return this.list;
    }

    @Override
    public void append(TransferTask task) {
        if (task instanceof ImportTask) {
            ImportTask importTask = (ImportTask)task;
            this.importRecords.add(importTask.getRecord());
            this.list.add(task);
        }
    }
}

