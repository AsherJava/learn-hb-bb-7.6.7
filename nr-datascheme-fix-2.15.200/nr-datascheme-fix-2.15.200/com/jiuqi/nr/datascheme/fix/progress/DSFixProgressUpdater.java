/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.common.ProgressItem
 */
package com.jiuqi.nr.datascheme.fix.progress;

import com.jiuqi.nvwa.definition.common.ProgressItem;
import java.util.function.Consumer;

public class DSFixProgressUpdater {
    private ProgressItem progressItem;
    private Consumer<ProgressItem> progressConsumer;

    public DSFixProgressUpdater(ProgressItem progressItem, Consumer<ProgressItem> progressConsumer) {
        this.progressItem = progressItem;
        this.progressConsumer = progressConsumer;
    }

    public void update(String msg, int currentProgress) {
        this.progressItem.setMessage(msg);
        this.progressItem.setCurrentProgess(currentProgress);
        if (null != this.progressConsumer) {
            this.progressConsumer.accept(this.progressItem);
        }
    }

    public void updateProgress(int currentProgress) {
        this.progressItem.setCurrentProgess(currentProgress);
        if (null != this.progressConsumer) {
            this.progressConsumer.accept(this.progressItem);
        }
    }

    public void nextStep() {
        this.progressItem.nextStep();
        if (null != this.progressConsumer) {
            this.progressConsumer.accept(this.progressItem);
        }
    }

    public void end(boolean isSuccess) {
        if (isSuccess) {
            this.progressItem.setMessage("\u4fee\u590d\u6210\u529f");
            this.progressItem.setFinished(true);
        } else {
            this.progressItem.setMessage("\u4fee\u590d\u5931\u8d25");
            this.progressItem.setFailed(true);
        }
        if (null != this.progressConsumer) {
            this.progressConsumer.accept(this.progressItem);
        }
    }
}

