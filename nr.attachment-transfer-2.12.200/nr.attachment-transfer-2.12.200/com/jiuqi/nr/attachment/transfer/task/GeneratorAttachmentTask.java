/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.attachment.transfer.task;

import com.jiuqi.nr.attachment.transfer.dto.AttachmentRecordDTO;
import com.jiuqi.nr.attachment.transfer.dto.GenerateParamDTO;
import com.jiuqi.nr.attachment.transfer.task.AbstractTaskProvider;
import com.jiuqi.nr.attachment.transfer.task.GenerateTask;
import com.jiuqi.nr.attachment.transfer.task.TransferTask;
import java.util.ArrayList;
import java.util.List;

public class GeneratorAttachmentTask
extends AbstractTaskProvider {
    private final GenerateParamDTO generateParamDTO;
    private final List<AttachmentRecordDTO> taskList;
    private List<TransferTask> list;

    public GeneratorAttachmentTask(GenerateParamDTO generateParamDTO, List<AttachmentRecordDTO> taskList) {
        this.generateParamDTO = generateParamDTO;
        this.taskList = taskList;
    }

    @Override
    protected List<TransferTask> getItems() {
        if (this.list == null) {
            this.build();
        }
        return this.list;
    }

    protected void build() {
        this.list = new ArrayList<TransferTask>(this.taskList.size());
        for (AttachmentRecordDTO recordDO : this.taskList) {
            GenerateTask task = new GenerateTask(recordDO, this.generateParamDTO);
            this.list.add(task);
        }
    }

    @Override
    public void append(TransferTask task) {
        if (task instanceof GenerateTask) {
            GenerateTask generateTask = (GenerateTask)task;
            this.taskList.add(generateTask.getRecord());
            this.list.add(task);
        }
    }
}

