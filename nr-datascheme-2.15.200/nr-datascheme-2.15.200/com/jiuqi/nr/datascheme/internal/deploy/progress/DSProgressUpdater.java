/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.common.ProgressItem
 */
package com.jiuqi.nr.datascheme.internal.deploy.progress;

import com.jiuqi.nvwa.definition.common.ProgressItem;
import java.util.function.Consumer;

public class DSProgressUpdater {
    private ProgressItem progressItem;
    private Consumer<ProgressItem> progressConsumer;

    public DSProgressUpdater(ProgressItem progressItem, Consumer<ProgressItem> progressConsumer) {
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

    public void nextStep() {
        this.progressItem.nextStep();
        if (null != this.progressConsumer) {
            this.progressConsumer.accept(this.progressItem);
        }
    }

    public void end(boolean isSuccess) {
        if (isSuccess) {
            this.progressItem.setMessage("\u53d1\u5e03\u6210\u529f");
            this.progressItem.setFinished(true);
        } else {
            this.progressItem.setMessage("\u53d1\u5e03\u5931\u8d25");
            this.progressItem.setFailed(true);
        }
        if (null != this.progressConsumer) {
            this.progressConsumer.accept(this.progressItem);
        }
    }

    public void warn() {
        this.progressItem.setMessage("\u53d1\u5e03\u6210\u529f\u4f46\u6709\u8b66\u544a\uff0c\u8bf7\u67e5\u770b\u8b66\u544a\u8be6\u60c5");
        this.progressItem.setFinished(true);
        if (null != this.progressConsumer) {
            this.progressConsumer.accept(this.progressItem);
        }
    }
}

