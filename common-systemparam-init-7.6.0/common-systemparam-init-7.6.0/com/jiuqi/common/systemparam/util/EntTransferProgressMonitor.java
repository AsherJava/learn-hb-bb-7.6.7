/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.transfer.TransferContext
 *  com.jiuqi.nvwa.transfer.TransferProgressMonitor
 */
package com.jiuqi.common.systemparam.util;

import com.jiuqi.common.systemparam.dto.EntTransferProgressDTO;
import com.jiuqi.common.systemparam.enums.EntInitMsgType;
import com.jiuqi.nvwa.transfer.TransferContext;
import com.jiuqi.nvwa.transfer.TransferProgressMonitor;
import java.util.function.Consumer;

public class EntTransferProgressMonitor
extends TransferProgressMonitor {
    private TransferContext context;
    private static final double FINISHED_VALUE = 1.0;
    private Consumer outPutConsumer;

    public EntTransferProgressMonitor(TransferContext context, Consumer<EntTransferProgressDTO> outPutConsumer) {
        this.context = context;
        this.outPutConsumer = outPutConsumer;
    }

    public void prompt(String msg) {
        double position = this.getPosition();
        if (position < 1.0) {
            this.outputMsg(msg, EntInitMsgType.INFO);
        }
    }

    protected void notify(double v) {
    }

    public void error(String msg) {
        this.outputMsg(msg, EntInitMsgType.ERROR);
    }

    public void success(String msg) {
        this.outputMsg(msg, EntInitMsgType.INFO);
    }

    private void outputMsg(String msg, EntInitMsgType msgType) {
        this.outPutConsumer.accept(new EntTransferProgressDTO(this.context.getProgressId(), msg, this.getPosition(), msgType));
    }
}

